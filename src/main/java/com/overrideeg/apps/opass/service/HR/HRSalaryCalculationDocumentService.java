/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.service.HR;

import com.overrideeg.apps.opass.exceptions.DateWrongException;
import com.overrideeg.apps.opass.exceptions.MissingRequiredFieldException;
import com.overrideeg.apps.opass.exceptions.NoRecordFoundException;
import com.overrideeg.apps.opass.io.entities.HR.HRSalaryCalculationDocument;
import com.overrideeg.apps.opass.io.entities.HR.HRSettings;
import com.overrideeg.apps.opass.io.entities.attendance;
import com.overrideeg.apps.opass.io.entities.employee;
import com.overrideeg.apps.opass.io.repositories.HRSalaryCalculationDocumentRepo;
import com.overrideeg.apps.opass.io.repositories.impl.attendanceRepoImpl;
import com.overrideeg.apps.opass.service.AbstractService;
import com.overrideeg.apps.opass.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HRSalaryCalculationDocumentService extends AbstractService<HRSalaryCalculationDocument> {
    @Autowired
    HRSettingsService hrSettingsService;
    @Autowired
    attendanceRepoImpl attendanceRepo;
    @Autowired
    HRSalaryService salaryService;
    List<attendance> attendances = new ArrayList<>();
    private HRSettings hrSetting;

    public HRSalaryCalculationDocumentService ( final HRSalaryCalculationDocumentRepo inRepository ) {
        super(inRepository);
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
        calculateSalaries(attendances);

        return super.save(inEntity);
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


    /**
     * method that found attendance logs between two dates
     *
     * @param fromDate from date to search
     * @param toDate   to date to search
     */
    private void findAttendances ( Date fromDate, Date toDate ) {
        attendances = attendanceRepo.findAttendanceBetweenTwoDates(fromDate, toDate);
        if (attendances.isEmpty()) {
            throw new NoRecordFoundException("No Attendance Logs Found in this duration");
        }
    }

    private void calculateSalaries ( List<attendance> attendancesList ) {
        // map attendance to Map<employee and List<attendance>;
        Map<employee, List<attendance>> listMapped = attendancesList.stream().collect(Collectors.toMap(
                attendance::getEmployee,
                x -> attendancesList.stream().filter(ListUtils.distinctByKey(attendance::getEmployee))
                        .collect(Collectors.toList())));
        listMapped.forEach(( employee, attendances ) -> {
            // todo continue here
        });
    }
}