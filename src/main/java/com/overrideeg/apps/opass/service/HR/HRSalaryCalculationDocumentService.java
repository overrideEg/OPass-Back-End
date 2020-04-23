/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.service.HR;

import com.overrideeg.apps.opass.enums.attStatus;
import com.overrideeg.apps.opass.exceptions.DateWrongException;
import com.overrideeg.apps.opass.exceptions.MissingRequiredFieldException;
import com.overrideeg.apps.opass.exceptions.NoRecordFoundException;
import com.overrideeg.apps.opass.io.entities.HR.HRPermissions;
import com.overrideeg.apps.opass.io.entities.HR.HRSalaryCalculationDocument;
import com.overrideeg.apps.opass.io.entities.HR.HRSettings;
import com.overrideeg.apps.opass.io.entities.employee;
import com.overrideeg.apps.opass.io.entities.officialHoliday;
import com.overrideeg.apps.opass.io.entities.workShift;
import com.overrideeg.apps.opass.io.repositories.HRSalaryCalculationDocumentRepo;
import com.overrideeg.apps.opass.io.valueObjects.attendanceReport;
import com.overrideeg.apps.opass.service.AbstractService;
import com.overrideeg.apps.opass.service.attendanceService;
import com.overrideeg.apps.opass.service.employeeUtilsService;
import com.overrideeg.apps.opass.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class HRSalaryCalculationDocumentService extends AbstractService<HRSalaryCalculationDocument> {
    @Autowired
    HRSettingsService hrSettingsService;
    @Autowired
    attendanceService attendanceService;
    @Autowired
    HRSalaryService salaryService;
    @Autowired
    employeeUtilsService employeeUtilsService;

    List<attendanceReport> attendances = new ArrayList<>();

    private HRSettings hrSetting;

    public HRSalaryCalculationDocumentService ( final HRSalaryCalculationDocumentRepo inRepository ) {
        super(inRepository);
    }

    /**
     * method that filter attendance report with in and out values;
     *
     * @param employee    employee
     * @param attendances list of attendance report
     * @return list of attendance report
     */
    private static List<attendanceReport> findInAndOutRecordsOnly ( employee employee, List<attendanceReport> attendances ) {
        return attendances.stream()
                .filter(attendanceReport -> attendanceReport.getInType() != null && attendanceReport.getOutType() != null)
                .collect(Collectors.toList());
    }


    /**
     * method that found first hr setting in database
     */
    private void findHRSettings () {
        List<HRSettings> hrSettings = hrSettingsService.findAll();

        if (hrSettings.isEmpty())
            throw new MissingRequiredFieldException("HR Settings Not Available In Company Settings");
        else
            hrSetting = hrSettings.get(0);
    }

    @Override
    public HRSalaryCalculationDocument save ( HRSalaryCalculationDocument inEntity ) {
        if (inEntity.getToDate().before(inEntity.getFromDate()) || inEntity.getFromDate().equals(inEntity.getToDate())) {
            throw new DateWrongException("From Date Must Before To Date");
        }
        // find HR Settings
        findHRSettings();
        // find attendances in twoDates
        findAttendances(inEntity.getFromDate(), inEntity.getToDate());
        // calculate salaries;
        calculateSalaries(attendances, inEntity.getFromDate(), inEntity.getToDate());

        return super.save(inEntity);
    }

    /**
     * method that found attendance logs between two dates
     *
     * @param fromDate from date to search
     * @param toDate   to date to search
     */
    private void findAttendances ( Date fromDate, Date toDate ) {
        attendances = attendanceService.findAttendanceReportBetweenTwoDates(fromDate, toDate);
        if (attendances.isEmpty()) {
            throw new NoRecordFoundException("No Attendance Logs Found in this duration");
        }
    }

    private void calculateSalaries ( List<attendanceReport> attendancesList, Date fromDate, Date toDate ) {
        // map attendance to Map<employee and List<attendance>;
        Map<employee, List<attendanceReport>> listMapped = new HashMap<>();
        attendancesList.forEach(list -> {
            employee employee = list.getEmployee();
            List<attendanceReport> listOFAttendances = attendancesList.stream().filter(attendanceReport -> attendanceReport.getEmployee().getId().equals(employee.getId())).collect(Collectors.toList());
            listMapped.put(employee, listOFAttendances);
        });


        listMapped.forEach(( employee, attendanceReports ) -> {
            List<attendanceReport> inAndOutRecords = findInAndOutRecordsOnly(employee, attendanceReports);
            Integer totalMinutes = 0;
            Integer totalOverTimeMinutes = 0;
            Integer totalLateMinutes = 0;
            Integer totalAbsenceDays = 0;
            Integer totalWorkOnDayOffMinutes = 0;

            totalAbsenceDays = calculateAbsenceDays(employee, attendanceReports, fromDate, toDate);
            totalLateMinutes = calculateLateMinutes(employee, attendanceReports);


            System.out.println(totalAbsenceDays);
            System.out.println(totalLateMinutes);


        });
    }

    public List<Date> calculateDatesToBeAttend ( employee employee, Date fromDate, Date toDate ) {
        DateUtils dateUtils = new DateUtils();
        LocalDate startDate = dateUtils.convertToLocalDateViaInstant(fromDate);
        LocalDate endDate = dateUtils.convertToLocalDateViaInstant(toDate);
        List<Date> dateList = new ArrayList<>();
        // get all permissions of employee
        List<HRPermissions> permissions = employeeUtilsService.getPermissions(employee);
        // get all hloidays
        List<officialHoliday> holidays = employeeUtilsService.getHolidays();
        for (LocalDate currentDate = startDate; currentDate.isBefore(endDate) || currentDate.isEqual(endDate); currentDate = currentDate.plusDays(1)) {
            boolean dayOff = employeeUtilsService.checkIfDayOff(employee, currentDate);
            if (dayOff) {
                continue;
            }
            boolean havePermission = employeeUtilsService.checkIfHaveAbsencePermission(permissions, currentDate);
            if (havePermission) {
                continue;
            }
            boolean holiday = employeeUtilsService.checkIfOfficialHoliday(holidays, currentDate);
            if (holiday) {
                continue;
            }
            Date date = dateUtils.convertToDateViaInstant(currentDate, TimeZone.getTimeZone("Africa/Cairo"));
            dateList.add(date);
        }
        return dateList;
    }


    public Integer calculateAbsenceDays ( employee employee, List<attendanceReport> attendanceReports, Date fromDate, Date toDate ) {
        List<Date> dateList = calculateDatesToBeAttend(employee, fromDate, toDate);
        Integer AbsenceDays = dateList.size();
        DateUtils dateUtils = new DateUtils();
        for (attendanceReport attendanceReport : attendanceReports) {
            Date scanDate = dateUtils.convertToDateViaInstant(dateUtils.convertToLocalDateViaInstantAtStartOfDate(attendanceReport.getScanDate()), TimeZone.getTimeZone("Africa/Cairo"));
            if (dateList.contains(scanDate)) {
                AbsenceDays--;
            }
        }
        return AbsenceDays;
    }


    /**
     * method tha calculate late minutes in duration
     *
     * @param employee
     * @param attendanceReports attendance Reports to search in
     * @return integer of calculated late minutes
     */
    public Integer calculateLateMinutes ( employee employee, List<attendanceReport> attendanceReports ) {
        List<attendanceReport> lateRecords = attendanceReports.stream().filter(attendanceReport -> attendanceReport.getInStatus()
                .equals(attStatus.lateEntrance.name())).collect(Collectors.toList());
        DateUtils dateUtils = new DateUtils();
        AtomicReference<Long> returnValue = new AtomicReference<>(0L);

        lateRecords.forEach(report -> {
            Long id = report.getWorkShift().getId();
            Date inTime = report.getInTime();
            Date shiftAtDate = dateUtils.copyTimeToDate(report.getScanDate(), inTime);
            workShift workShiftAtDate = employeeUtilsService.getWorkShiftAtDate(employee, id, shiftAtDate);
            report.setWorkShift(workShiftAtDate);
            Date fromHour = dateUtils.copyTimeToDate(report.getScanDate(), report.getWorkShift().getShiftHours().getFromHour());
            long allowedLate = TimeUnit.MILLISECONDS.convert(employee.fetchEmployeeAttRules().getAllowedLateMinutes(), TimeUnit.MINUTES);
            long totalLate = shiftAtDate.getTime() - fromHour.getTime() - allowedLate;
            returnValue.updateAndGet(v -> v + TimeUnit.MINUTES.convert(totalLate, TimeUnit.MILLISECONDS));
        });

        return Math.toIntExact(returnValue.get());
    }
}