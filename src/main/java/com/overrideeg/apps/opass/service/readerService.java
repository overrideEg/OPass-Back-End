/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.overrideeg.apps.opass.enums.attStatus;
import com.overrideeg.apps.opass.enums.attType;
import com.overrideeg.apps.opass.exceptions.*;
import com.overrideeg.apps.opass.io.entities.attendance;
import com.overrideeg.apps.opass.io.entities.employee;
import com.overrideeg.apps.opass.io.entities.qrMachine;
import com.overrideeg.apps.opass.io.entities.workShift;
import com.overrideeg.apps.opass.io.valueObjects.attendanceRules;
import com.overrideeg.apps.opass.ui.entrypoint.reader.qrData;
import com.overrideeg.apps.opass.ui.entrypoint.reader.readerAdminRequest;
import com.overrideeg.apps.opass.ui.entrypoint.reader.readerRequest;
import com.overrideeg.apps.opass.ui.sys.ErrorMessages;
import com.overrideeg.apps.opass.utils.DateUtils;
import com.overrideeg.apps.opass.utils.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

//todo bimbo work here
@Service
public class readerService {
    @Autowired //TODO replace with the memory wise impl
    private attendanceService attendanceService;
    @Autowired
    private employeeService employeeService;
    @Autowired
    private qrMachineService qrMachineService;

    public attendance validate(readerRequest request) {
        final qrData qr = handleQrBody(request.getQr());

        final employee employee = checkEmployeeRelated(request);

        checkMachineRelated(employee, qr);
        checkScanTime(request, qr);

        return processWorkShifts(request, employee);
    }

    public attendance adminValidate(readerAdminRequest adminRequest, Long tenantId) {
        final Optional<employee> employee = employeeService.find(adminRequest.getEmployee().getId());
        DateUtils dateUtils = new DateUtils();
        Date date = dateUtils.copyTimeToDate(adminRequest.getDate(), adminRequest.getScan_time());
        adminRequest.getDate().setTime(date.getTime());
        readerRequest readerRequest = new readerRequest();
        readerRequest.setEmployee_id(adminRequest.getEmployee().getId());
        readerRequest.setScan_time(adminRequest.getDate().getTime());
        readerRequest.setCompany_id(tenantId);
        return attendanceService.save(processWorkShifts(readerRequest, employee.get()));
    }


    private employee checkEmployeeRelated(readerRequest request) {
        final Optional<employee> employee = employeeService.find(request.getEmployee_id());

        if (!employee.isPresent()) {
            throw new EmployeeNotRelatedException(ErrorMessages.EMPLOYEE_NOT_RELATED.getErrorMessage());

        }

        return employee.get();
    }


    private void checkMachineRelated(employee employee, qrData qr) {
        final Optional<qrMachine> qrMachine = qrMachineService.find(qr.getQrMachine_id());

        if (!qrMachine.isPresent()) {
            throw new NoRecordFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }

        if (!qrMachine.get().getBranch().getId().equals(employee.getBranch().getId()) || !qrMachine.get().getDepartment().getId().equals(employee.getDepartment().getId())) {
            throw new EmployeeNotRelatedException(ErrorMessages.EMPLOYEE_NOT_RELATED.getErrorMessage());
        }

    }

    private void checkScanTime(readerRequest request, qrData qr) {

        if (TimeUnit.MILLISECONDS.toSeconds(qr.getIssueDate()) > TimeUnit.MILLISECONDS.toSeconds(request.getScan_time())
                || TimeUnit.MILLISECONDS.toSeconds(qr.getExpireDate()) < TimeUnit.MILLISECONDS.toSeconds(request.getScan_time())) {
            throw new QRExpiredException(ErrorMessages.QR_EXPIRED.getErrorMessage());
        }


    }

    private attendance processWorkShifts(readerRequest request, employee employee) {
        final List<workShift> workShifts = employee.getShifts();
        final Date scanDate = new Date(request.getScan_time());
        final int scanWeekDay = new DateUtils().getDateWeekDay(scanDate);
        final Time scanTime = new DateUtils().newTime(new Date(request.getScan_time()));

        if (!workShifts.isEmpty()) {
            final attendanceRules attendanceRules = employee.fetchEmployeeAttRules();

            if (attendanceRules == null) {
                throw new NoRecordFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
            }

            if (attendanceRules.getDaysOff().contains(scanWeekDay)) {
                return new attendance(employee, null, scanDate, scanTime, attType.LOG, attStatus.workOnDayOff);
            }

            final workShift currentWorkShift = employee.getCurrentWorkShift(attendanceService, scanDate, workShifts, attendanceRules);

            if (currentWorkShift == null) {
                return new attendance(employee, null, scanDate, scanTime, attType.LOG, attStatus.normal);
            }

            final List<attendance> todayShiftLogs = attendanceService.employeeTodaysShitLogs(employee, scanDate, currentWorkShift);

            return currentWorkShift.createAttLog(employee, scanDate, scanWeekDay, attendanceRules, todayShiftLogs);

        } else {
            return new attendance(employee, null, scanDate, scanTime, attType.LOG, attStatus.normal);

        }

    }


    /**
     * method that decode qr encoded from request and map it into object called qrData
     *
     * @param qr Contains encoded qr from request
     * @return qr data decoded
     */
    private qrData handleQrBody(String qr) {
        String encodedOverride = EntityUtils.encode("OVERRIDE");
        String override = qr.substring(0, qr.indexOf(","));
        if (!override.equalsIgnoreCase(encodedOverride))
            throw new AuthenticationException("Text Not Illegal Here");
        String qrBodyEncoded = qr.substring(qr.lastIndexOf(",") + 1);
        String decodedQr;
        try {
            decodedQr = EntityUtils.decode(qrBodyEncoded);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CouldNotCreateRecordException("Error During Decode Qr Data");
        }
        ObjectMapper mapper = new ObjectMapper();
        qrData returnValue = null;
        try {
            returnValue = mapper.readValue(decodedQr, qrData.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new CouldNotCreateRecordException("Error During Read Qr Data Or Missing Required Field");
        }
        return returnValue;
    }


}


//        List<String> whereNames = new ArrayList<>(Arrays.asList("userName", "email"));
//        List whereValues = new ArrayList<>(Arrays.asList("fafasd","assad@g"));
//
//        List<Users> query = createQuery("select u from Users u where " +
//                "(u.userName=:userName)and(u.email=:email)", whereNames, whereValues);
//
//        attendanceService.findWhere(<String>[""],[""]);
