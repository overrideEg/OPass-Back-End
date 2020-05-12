/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.service.HR;

import com.overrideeg.apps.opass.enums.attStatus;
import com.overrideeg.apps.opass.exceptions.DateWrongException;
import com.overrideeg.apps.opass.exceptions.MissingRequiredFieldException;
import com.overrideeg.apps.opass.exceptions.NoRecordFoundException;
import com.overrideeg.apps.opass.io.entities.HR.HRPermissions;
import com.overrideeg.apps.opass.io.entities.HR.HRSalary;
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
import com.overrideeg.apps.opass.utils.EntityUtils;
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

    public HRSalaryCalculationDocumentService ( final HRSalaryCalculationDocumentRepo inRepository ) {
        super(inRepository);
    }

    /**
     * hr settings for this company
     */
    HRSettings hrSetting;

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
                .filter(attendanceReport -> attendanceReport.getEmployee().getId().equals(employee.getId()))
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
        findAttendances(inEntity, inEntity.getFromDate(), inEntity.getToDate());
        // calculate salaries;
        calculateSalaries(attendances, inEntity.getFromDate(), inEntity.getToDate());

        return super.save(inEntity);
    }

    /**
     * method that found attendance logs between two dates
     *
     * @param inEntity
     * @param fromDate from date to search
     * @param toDate   to date to search
     */
    private void findAttendances ( HRSalaryCalculationDocument inEntity, Date fromDate, Date toDate ) {
        attendances = attendanceService.findAttendanceReportBetweenTwoDates(fromDate, toDate);
        if (inEntity.getEmployee() != null)
            attendances = attendances.stream().filter(attendanceReport -> attendanceReport.getEmployee().getId().equals(inEntity.getEmployee().getId())).collect(Collectors.toList());
        else if (inEntity.getBranch() != null && inEntity.getDepartment() != null)
            attendances = attendances.stream()
                    .filter(attendanceReport -> attendanceReport.getEmployee().getBranch().getId().equals(inEntity.getBranch().getId()))
                    .filter(attendanceReport -> attendanceReport.getEmployee().getDepartment().getId().equals(inEntity.getDepartment().getId()))
                    .collect(Collectors.toList());
        else if (inEntity.getDepartment() == null && inEntity.getBranch() == null && inEntity.getEmployee() == null)
            throw new MissingRequiredFieldException("Department and Branch or Employee are required");
        if (attendances.isEmpty()) {
            throw new NoRecordFoundException("No Attendance Logs Found in this duration");
        }
    }

    private void calculateSalaries ( List<attendanceReport> attendancesList, Date fromDate, Date toDate ) {
        // map attendance to Map<employee and List<attendance>;
        Map<employee, List<attendanceReport>> listMapped = new HashMap<>();
        attendancesList.forEach(list -> {
            employee employee = list.getEmployee();
            List<attendanceReport> listOFAttendances = attendancesList
                    .stream()
                    .filter(attendanceReport -> attendanceReport.getEmployee().getId().equals(employee.getId()))
                    .filter(report -> report.getScanDate().getTime() <= toDate.getTime() && report.getScanDate().getTime() >= fromDate.getTime())
                    .collect(Collectors.toList());
            listMapped.put(employee, listOFAttendances);
        });


        listMapped.forEach(( employee, attendanceReports ) -> {
            // get in and out reports only
            List<attendanceReport> inAndOutRecords = findInAndOutRecordsOnly(employee, attendanceReports);

            // get absence days
            Integer totalAbsenceDays = calculateAbsenceDays(employee, inAndOutRecords, fromDate, toDate);
            // get total late minutes
            Integer totalLateMinutes = calculateLateMinutes(employee, inAndOutRecords);
            // get total early go minutes
            Integer totalEarlyGoMinutes = calculateEarlyGoMinutes(employee, inAndOutRecords);
            // get total work on day off minutes
            Integer totalWorkOnDayOffMinutes = calcWorkOnDayOffMinutes(employee, inAndOutRecords);
            // get total overtime minutes
            Integer totalOverTimeMinutes = calcOverTimeMinutes(employee, inAndOutRecords);
            // get total minutes
            Integer totalMinutes = calcTotalMinutes(inAndOutRecords);

            HRSalary salary = calculateSalary(employee, totalMinutes, totalOverTimeMinutes, totalWorkOnDayOffMinutes, totalEarlyGoMinutes, totalLateMinutes, totalAbsenceDays);
            salary.setFromDate(fromDate);
            salary.setToDate(toDate);
            salary.setEmployee(employee);

            salaryService.save(salary);
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


    /**
     * method that calculate absence days for one employee
     *
     * @param employee          employee
     * @param attendanceReports att reports
     * @param fromDate          from date to search in
     * @param toDate            to date to search in
     * @return integer contain absence days
     */
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
     * @param employee          employee
     * @param attendanceReports attendance Reports to search in
     * @return integer of calculated late minutes
     */
    public Integer calculateLateMinutes ( employee employee, List<attendanceReport> attendanceReports ) {
        List<attendanceReport> lateRecords = attendanceReports.stream()
                .filter(attendanceReport -> attendanceReport.getInStatus().equals(attStatus.lateEntrance.name()))
                .filter(report -> report.getEmployee().getId().equals(employee.getId()))
                .collect(Collectors.toList());

        DateUtils dateUtils = new DateUtils();
        AtomicReference<Long> returnValue = new AtomicReference<>(0L);

        lateRecords.forEach(report -> {
            Date inTime = report.getInTime();
            Date shiftAtDate = dateUtils.copyTimeToDate(report.getScanDate(), inTime);
            workShift workShiftAtDate = employeeUtilsService.getWorkShiftAtDate(report.getWorkShift().getId(), shiftAtDate);
            report.setWorkShift(workShiftAtDate);
            Date fromHour = dateUtils.copyTimeToDate(report.getScanDate(), report.getWorkShift().getShiftHours().getFromHour());
            long allowedLate = TimeUnit.MILLISECONDS.convert(employee.fetchEmployeeAttRules().getAllowedLateMinutes(), TimeUnit.MINUTES);
            long totalLate = shiftAtDate.getTime() - fromHour.getTime() - allowedLate;
            returnValue.updateAndGet(v -> v + TimeUnit.MINUTES.convert(totalLate, TimeUnit.MILLISECONDS));
        });

        return Math.toIntExact(returnValue.get());
    }

    /**
     * method that calculate early go minutes
     *
     * @param employee          employee to search in
     * @param attendanceReports attendance reports to search in
     * @return total early go minutes
     */
    private Integer calculateEarlyGoMinutes ( employee employee, List<attendanceReport> attendanceReports ) {
        List<attendanceReport> earlyGoRecords = attendanceReports.stream()
                .filter(attendanceReport -> attendanceReport.getOutStatus().equals(attStatus.earlyGo.name()))
                .filter(report -> report.getEmployee().getId().equals(employee.getId()))
                .collect(Collectors.toList());

        DateUtils dateUtils = new DateUtils();
        AtomicReference<Long> returnValue = new AtomicReference<>(0L);

        earlyGoRecords.forEach(report -> {
            Date outTime = report.getOutTime();
            Date shiftAtDate = dateUtils.copyTimeToDate(report.getScanDate(), outTime);
            workShift workShiftAtDate = employeeUtilsService.getWorkShiftAtDate(report.getWorkShift().getId(), shiftAtDate);
            report.setWorkShift(workShiftAtDate);
            Date toHour = dateUtils.copyTimeToDate(report.getScanDate(), report.getWorkShift().getShiftHours().getToHour());
            long allowedEarlyGo = TimeUnit.MILLISECONDS.convert(employee.fetchEmployeeAttRules().getAllowedEarlyLeaveMinutes(), TimeUnit.MINUTES);
            long totalLate = (toHour.getTime() - shiftAtDate.getTime()) - allowedEarlyGo;
            returnValue.updateAndGet(v -> v + TimeUnit.MINUTES.convert(totalLate, TimeUnit.MILLISECONDS));
        });

        return Math.toIntExact(returnValue.get());
    }

    /**
     * method that calculate work on dayoff minutes
     *
     * @param employee          employee
     * @param attendanceReports att reports
     * @return minutes worked in day off
     */
    private Integer calcWorkOnDayOffMinutes ( employee employee, List<attendanceReport> attendanceReports ) {
        attendanceReports = attendanceReports
                .stream()
                .filter(report -> report.getInStatus().equals(attStatus.workOnDayOff.name()) && report.getOutStatus()
                        .equals(attStatus.workOnDayOff.name()))
                .filter(report -> report.getEmployee().getId().equals(employee.getId()))
                .collect(Collectors.toList());

        DateUtils dateUtils = new DateUtils();
        AtomicReference<Long> returnValue = new AtomicReference<>(0L);
        attendanceReports.forEach(report -> {
            Date inTime = dateUtils.copyTimeToDate(report.getScanDate(), report.getInTime());
            Date outTime = dateUtils.copyTimeToDate(report.getScanDate(), report.getOutTime());
            long totalTime = outTime.getTime() - inTime.getTime();
            returnValue.updateAndGet(v -> v + TimeUnit.MINUTES.convert(totalTime, TimeUnit.MILLISECONDS));
        });
        return Math.toIntExact(returnValue.get());
    }


    /**
     * method that calculate over time for one employee
     *
     * @param employee          employee
     * @param attendanceReports to search in
     * @return integer of total hours overtime
     */
    private Integer calcOverTimeMinutes ( employee employee, List<attendanceReport> attendanceReports ) {
        attendanceReports = attendanceReports
                .stream()
                .filter(report -> report.getEmployee().getId().equals(employee.getId()))
                .filter(report -> report.getOutStatus().equals(attStatus.overTime.name())).collect(Collectors.toList());


        DateUtils dateUtils = new DateUtils();
        AtomicReference<Long> returnValue = new AtomicReference<>(0L);

        attendanceReports.forEach(report -> {
            Date shiftAtDate = dateUtils.copyTimeToDate(report.getScanDate(), report.getOutTime());
            workShift workShiftAtDate = employeeUtilsService.getWorkShiftAtDate(report.getWorkShift().getId(), shiftAtDate);
            report.setWorkShift(workShiftAtDate);
            Date toHour = dateUtils.copyTimeToDate(report.getScanDate(), report.getWorkShift().getShiftHours().getToHour());
            long allowedLate = TimeUnit.MILLISECONDS.convert(employee.fetchEmployeeAttRules().getAllowedLateLeaveMinutes(), TimeUnit.MINUTES);
            long totalOverTimeHours = shiftAtDate.getTime() - toHour.getTime() - allowedLate;
            returnValue.updateAndGet(v -> v + TimeUnit.MINUTES.convert(totalOverTimeHours, TimeUnit.MILLISECONDS));
        });
        return Math.toIntExact(returnValue.get());
    }

    /**
     * method calculate total minutes of records
     *
     * @param attendanceReports attendance reports for one employee
     * @return integer of total minutes
     */
    private Integer calcTotalMinutes ( List<attendanceReport> attendanceReports ) {
        DateUtils dateUtils = new DateUtils();
        AtomicReference<Long> returnValue = new AtomicReference<>(0L);

        attendanceReports.forEach(report -> {
            Date inTime = dateUtils.copyTimeToDate(report.getScanDate(), report.getInTime());
            Date outTime = dateUtils.copyTimeToDate(report.getScanDate(), report.getOutTime());
            long totalMinutes = outTime.getTime() - inTime.getTime();
            returnValue.updateAndGet(v -> v + TimeUnit.MINUTES.convert(totalMinutes, TimeUnit.MILLISECONDS));
        });
        return Math.toIntExact(returnValue.get());
    }

    /**
     * method that calculate salary with all fields
     *
     * @param employee                 employee
     * @param totalMinutes             totalMinutes
     * @param totalOverTimeMinutes     totalOverTimeMinutes
     * @param totalWorkOnDayOffMinutes totalWorkOnDayOffMinutes
     * @param totalEarlyGoMinutes      totalEarlyGoMinutes
     * @param totalLateMinutes         totalLateMinutes
     * @param totalAbsenceDays         totalAbsenceDays
     * @return hr salary document
     */
    private HRSalary calculateSalary ( employee employee, Integer totalMinutes, Integer totalOverTimeMinutes, Integer totalWorkOnDayOffMinutes, Integer totalEarlyGoMinutes, Integer totalLateMinutes, Integer totalAbsenceDays ) {
        HRSalary returnValue = new HRSalary();

        // salary bases
        Double baseSalaryInHour = employee.getSalary();
        Double totalShiftMinutes = employee.calculateTotalMinutesShifts();
        Double salaryInDay = baseSalaryInHour * (totalShiftMinutes / 60);
        Double totalShiftHours = totalShiftMinutes / 60;
        Double salaryInMinute = (salaryInDay / totalShiftHours) / 60;

        // absence
        returnValue.setAbsenceDays(totalAbsenceDays);
        double AbsenceDeduction = 0D;
        AbsenceDeduction = totalAbsenceDays * salaryInDay * hrSetting.getAbsenceDayDeduction();
        if (AbsenceDeduction < 0)
            AbsenceDeduction = 0D;
        // early go
        returnValue.setEarlyGoMinutes(totalEarlyGoMinutes);
        double earlyGoDeduction = 0D;
        earlyGoDeduction = salaryInMinute * totalEarlyGoMinutes * hrSetting.getDelayHourDeduction();
        if (earlyGoDeduction < 0)
            earlyGoDeduction = 0D;

        // late entrance
        returnValue.setLateMinutes(totalLateMinutes);
        double lateDeduction = 0D;
        lateDeduction = salaryInMinute * totalLateMinutes * hrSetting.getDelayHourDeduction();
        if (lateDeduction < 0)
            lateDeduction = 0D;
        // work on day off
        int overTimeTotalDuration = 0;
        overTimeTotalDuration = totalWorkOnDayOffMinutes + totalOverTimeMinutes;

        returnValue.setOverTimeHours(overTimeTotalDuration);

        Double overTimeAddition = totalOverTimeMinutes * hrSetting.getOverTimeAddition() * salaryInMinute;

        Double workOnDayOffAddition = totalWorkOnDayOffMinutes * hrSetting.getWorkOnDayOffAddition() * salaryInMinute;


        // salary
        returnValue.setTotalHours(totalMinutes / 60);
        Double totalSalary = EntityUtils.round(totalMinutes * salaryInMinute, 2);
        returnValue.setSalary(totalSalary);

        // addition
        returnValue.setAddition(EntityUtils.round(overTimeAddition + workOnDayOffAddition, 2));

        // deduction
        returnValue.setDeduction(EntityUtils.round(lateDeduction + earlyGoDeduction + AbsenceDeduction, 2));


        return returnValue;
    }


}