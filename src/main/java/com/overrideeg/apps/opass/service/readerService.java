/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.overrideeg.apps.opass.enums.attStatus;
import com.overrideeg.apps.opass.enums.attType;
import com.overrideeg.apps.opass.exceptions.*;
import com.overrideeg.apps.opass.io.entities.*;
import com.overrideeg.apps.opass.io.entities.HR.HRPermissions;
import com.overrideeg.apps.opass.io.valueObjects.attendanceRules;
import com.overrideeg.apps.opass.service.HR.HRPermissionsService;
import com.overrideeg.apps.opass.ui.entrypoint.reader.qrData;
import com.overrideeg.apps.opass.ui.entrypoint.reader.readerAdminRequest;
import com.overrideeg.apps.opass.ui.entrypoint.reader.readerRequest;
import com.overrideeg.apps.opass.ui.sys.ErrorMessages;
import com.overrideeg.apps.opass.utils.DateUtils;
import com.overrideeg.apps.opass.utils.EntityUtils;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

//todo bimbo work here
@Service
public class readerService {
    private final attendanceService attendanceService;
    private final employeeService employeeService;
    private final qrMachineService qrMachineService;
    private final HRPermissionsService hrPermissionsService;
    private final officialHolidayService officialHolidayService;

    public readerService(attendanceService attendanceService, employeeService employeeService, qrMachineService qrMachineService, HRPermissionsService hrPermissionsService, officialHolidayService officialHolidayService) {
        this.officialHolidayService = officialHolidayService;
        this.hrPermissionsService = hrPermissionsService;
        this.attendanceService = attendanceService;
        this.qrMachineService = qrMachineService;
        this.employeeService = employeeService;
    }

    public attendance validate(readerRequest request) {
        final qrData qr = handleQrBody(request.getQr());

        final employee employee = checkEmployeeRelated(request);

        checkMachineRelated(employee, qr);

        checkScanTime(request, qr);

        return processWorkShifts(request, employee);
    }


    private employee checkEmployeeRelated(readerRequest request) {
        final Optional<employee> employee = employeeService.find(request.getEmployee_id());

        if (!employee.isPresent()) {
            throw new EmployeeNotRelatedException(ErrorMessages.EMPLOYEE_NOT_RELATED.getErrorMessage());
        }

        return employee.get();
    }

    //check qr machine related to any of employee main or optional branches
    //TODO refactor with stream
    private void checkMachineRelated(employee employee, qrData qr) {
        final Optional<qrMachine> qrMachine = qrMachineService.find(qr.getQrMachine_id());

        if (!qrMachine.isPresent()) {
            throw new NoRecordFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }

        if (!qrMachine.get().getBranch().getId().equals(employee.getBranch().getId()) || !qrMachine.get().getDepartment().getId().equals(employee.getDepartment().getId())) {

            boolean related = false;

            try {
                for (com.overrideeg.apps.opass.io.entities.branch branch : employee.getOptionalBranches()) {
                    if (qrMachine.get().getBranch().getId().equals(branch.getId())) {
                        related = true;
                        break;
                    }
                }

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

            if (!related) {
                throw new EmployeeNotRelatedException(ErrorMessages.EMPLOYEE_NOT_RELATED.getErrorMessage());
            }

        }

    }

    private void checkScanTime(readerRequest request, qrData qr) {

        if (TimeUnit.MILLISECONDS.toSeconds(qr.getIssueDate()) > TimeUnit.MILLISECONDS.toSeconds(request.getScan_time())
                || TimeUnit.MILLISECONDS.toSeconds(qr.getExpireDate()) < TimeUnit.MILLISECONDS.toSeconds(request.getScan_time())) {
            throw new QRExpiredException(ErrorMessages.QR_EXPIRED.getErrorMessage());
        }


    }

    ///TODO 🔥CRITICAL🔥 separate ins and outs for every branch on day level
    private attendance processWorkShifts(readerRequest request, employee employee) {
        //get all employee work shifts
        final List<workShift> workShifts = employee.getShifts();

        //get the scan date from the time stamp
        final Date scanDate = new Date(request.getScan_time());

        //get the scan week day from scan date
        final int scanWeekDay = new DateUtils().getDateWeekDay(scanDate);

        //get the scan time from the time stamp
        final Time scanTime = new DateUtils().newTime(new Date(request.getScan_time()));

        //get the working attendance rules for this employee
        final attendanceRules attendanceRules = employee.fetchEmployeeAttRules();

        //get employee's hr permissions in current date if exist
        final HRPermissions hrPermissions = hrPermissionsService.getHRPermissionsInCurrentDate(scanDate);

        //get today's current logs
        final List<attendance> todayLogs = attendanceService.employeeTodaysLogs(employee, scanDate, true);

        //throw exception if rules dont exist
        if (attendanceRules == null) {
            throw new NoRecordFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }

        final attendance dayOffAttendance = processDaysOffAndHolidaysAndAbsenceAllowed(employee, hrPermissions, todayLogs, scanDate, scanWeekDay, scanTime);

        if (dayOffAttendance != null) {
            return dayOffAttendance;
        }

        if (!workShifts.isEmpty()) {

            final workShift currentWorkShift = employee.getCurrentWorkShift(todayLogs, scanDate, scanTime, workShifts, attendanceRules);

            if (currentWorkShift == null) {
                return new attendance(employee, null, scanDate, scanTime, attType.LOG, attStatus.normal);
            }


            final List<attendance> todayShiftLogs = todayLogs.stream().filter(Objects::nonNull).filter(a -> a.getWorkShift()!=null)
                    .filter(a -> a.getWorkShift().getId().equals(currentWorkShift.getId())).collect(toList());

            return currentWorkShift.createAttLog(employee, scanDate, scanWeekDay, attendanceRules, hrPermissions, todayShiftLogs);

        } else {
            return new attendance(employee, null, scanDate, scanTime, attType.LOG, attStatus.normal);
        }

    }

    //TODO refactor with stream
    private attendance processDaysOffAndHolidaysAndAbsenceAllowed(employee employee, HRPermissions hrPermissions, List<attendance> todayShiftLogs, Date scanDate, int scanWeekDay, Time scanTime) {

        //check if today is a holiday
        final List<officialHoliday> officialHolidays = officialHolidayService.getOfficialHollidaysInCurrentDate(scanDate);

        //prepare check conditions
        boolean todayIsHoliday = !officialHolidays.isEmpty();
        boolean todayIsDayOff = employee.fetchEmployeeDaysOff().contains(scanWeekDay);
        boolean todayIsAbsenceAllowed = hrPermissions != null && hrPermissions.getAbsenceAllowed();


        if (todayIsHoliday || todayIsDayOff || todayIsAbsenceAllowed) {


            if (!todayShiftLogs.isEmpty() && todayShiftLogs.get(0).getAttType() == attType.IN) {

                return new attendance(employee, null, scanDate, scanTime, attType.OUT, attStatus.workOnDayOff);

            } else if (todayShiftLogs.isEmpty() || todayShiftLogs.get(0).getAttType() == attType.OUT) {

                return new attendance(employee, null, scanDate, scanTime, attType.IN, attStatus.workOnDayOff);
            }
        }


        return null;
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


    //method for creating attendance records by system admins
    public List<attendance> adminValidate(readerAdminRequest adminRequest, Long tenantId) {
        final Optional<employee> employee = employeeService.find(adminRequest.getEmployee().getId());
        DateUtils dateUtils = new DateUtils();
        List<attendance> responses = new ArrayList<>();
        if (adminRequest.getInTime() != null) {
            Date date = dateUtils.copyTimeToDate(adminRequest.getDate(), adminRequest.getInTime());
            adminRequest.getDate().setTime(date.getTime());
            readerRequest readerRequest = new readerRequest();
            readerRequest.setEmployee_id(adminRequest.getEmployee().getId());
            readerRequest.setScan_time(date.getTime());
            readerRequest.setCompany_id(tenantId);
            responses.add(attendanceService.save(processWorkShifts(readerRequest, employee.get())));
        }
        if (adminRequest.getOutTime() != null) {
            Date date = dateUtils.copyTimeToDate(adminRequest.getDate(), adminRequest.getOutTime());
            adminRequest.getOutTime().setTime(date.getTime());
            readerRequest readerRequest = new readerRequest();
            readerRequest.setEmployee_id(adminRequest.getEmployee().getId());
            readerRequest.setScan_time(date.getTime());
            readerRequest.setCompany_id(tenantId);
            responses.add(attendanceService.save(processWorkShifts(readerRequest, employee.get())));
        }
        return responses;
    }


}


//        List<String> whereNames = new ArrayList<>(Arrays.asList("userName", "email"));
//        List whereValues = new ArrayList<>(Arrays.asList("fafasd","assad@g"));
//
//        List<Users> query = createQuery("select u from Users u where " +
//                "(u.userName=:userName)and(u.email=:email)", whereNames, whereValues);
//
//        attendanceService.findWhere(<String>[""],[""]);
