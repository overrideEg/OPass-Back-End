/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.overrideeg.apps.opass.exceptions.*;
import com.overrideeg.apps.opass.io.entities.attendance;
import com.overrideeg.apps.opass.io.entities.employee;
import com.overrideeg.apps.opass.io.entities.qrMachine;
import com.overrideeg.apps.opass.io.entities.workShift;
import com.overrideeg.apps.opass.io.valueObjects.attendanceRules;
import com.overrideeg.apps.opass.io.valueObjects.shiftHours;
import com.overrideeg.apps.opass.ui.entrypoint.reader.qrData;
import com.overrideeg.apps.opass.ui.entrypoint.reader.readerRequest;
import com.overrideeg.apps.opass.ui.sys.ErrorMessages;
import com.overrideeg.apps.opass.utils.DateUtils;
import com.overrideeg.apps.opass.utils.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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

        checkMatchingIds(request, qr);
        checkMachineRelated(request, qr);
        checkScanTime(request, qr);

        final Boolean isLog = checkWorkShifts(request, qr, employee);

        if (isLog) {
            final attendance attendance = new attendance();
//            attendance.da
        }

        return new attendance();
    }

    private Boolean checkWorkShifts(readerRequest request, qrData qr, employee employee) {
        final List<workShift> workShifts = employee.getShifts();

        if (!workShifts.isEmpty()) {
            final Date date = new Date(request.getScan_time());
            final attendanceRules attendanceRules = fetchEmployeeAttRules(employee);

            final workShift workShift = getCurrentWorkShift(date,workShifts,attendanceRules);
            if (workShift == null) return true;




            throw new NoRecordFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        } else {
            return true;
        }

    }


    //final Date fromHourWithAllowance = new DateUtils().addOrSubtractMinutes(shiftTime.getFromHour(),attendanceRules.getAllowedLateMinutes());
    private workShift getCurrentWorkShift(Date currentDate, List<workShift> workShifts, attendanceRules attendanceRules) {

        for (workShift workShift : workShifts) {

            final shiftHours shiftTime =workShift.getShiftHours();

            final Date toHourWithAllowance = new DateUtils().addOrSubtractHours(shiftTime.getToHour(),attendanceRules.getMaxOverTimeHours());

            final boolean currentShift= new DateUtils().isBetweenTwoTime(shiftTime.getFromHour(),toHourWithAllowance,currentDate);
            if(currentShift){
                return workShift;
            }
        }
        return null;

    }

    private attendanceRules fetchEmployeeAttRules(employee employee) {
        if (employee.getDepartment().getAttendanceRules() != null) {
            return employee.getDepartment().getAttendanceRules();
        }
        if (employee.getBranch().getAttendanceRules() != null) {
            return employee.getDepartment().getAttendanceRules();
        }
        throw new NoRecordFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()); //TODO better naming for exceptions

    }

    private void checkScanTime(readerRequest request, qrData qr) {

        if (qr.getIssueDate() > request.getScan_time() || qr.getExpireDate() < request.getScan_time()) {
            throw new QRExpiredException(ErrorMessages.QR_EXPIRED.getErrorMessage());
        }


    }

    private void checkMachineRelated(readerRequest request, qrData qr) {
        final Optional<qrMachine> qrMachine = qrMachineService.find(qr.getQrMachine_id());

        if (!qrMachine.isPresent()) {
            throw new NoRecordFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }

        if (!qrMachine.get().getBranch().getId().equals(request.getBranch_id()) || !qrMachine.get().getDepartment().getId().equals(request.getDepartment_id())) {
            throw new MatchingIdsException(ErrorMessages.MATCHING_ID_EXCEPTION.getErrorMessage());
        }


    }

    private void checkMatchingIds(readerRequest request, qrData qr) {
        if (!request.getBranch_id().equals(qr.getBranch_id()) || !request.getDepartment_id().equals(qr.getDepartment_id())) {

            throw new MatchingIdsException(ErrorMessages.MATCHING_ID_EXCEPTION.getErrorMessage());
        }
    }


    private employee checkEmployeeRelated(readerRequest request) {
        final Optional<employee> employee = employeeService.find(request.getEmployee_id());

        if (!employee.isPresent()) {
            throw new NoRecordFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        }


        if (!employee.get().getDepartment().getId().equals(request.getDepartment_id()) ||
                !employee.get().getBranch().getId().equals(request.getBranch_id())) {

            throw new NoRecordFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        }


        return employee.get();
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
        String qrBodyEncoded = qr.substring(qr.indexOf(",") + 1);
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
