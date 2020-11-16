/*
 * Copyright (c) 2020. overrideeg.ocm.
 */

package com.overrideeg.apps.opass.utils;

import java.sql.Time;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * User: amr
 * Date: 4/1/2020
 * Time: 12:45 AM
 */
public class DateUtils {


    private Calendar newCalender(Date date) {
        Calendar newCalender = Calendar.getInstance();
        newCalender.setTime(date);
        return newCalender;
    }
    public Time newTime(Date date) {

        Calendar dateCalendar = newCalender(date);
        Calendar timeCalendar = Calendar.getInstance();
        timeCalendar.set(Calendar.HOUR_OF_DAY, dateCalendar.get(Calendar.HOUR_OF_DAY)); // Your hour
        timeCalendar.set(Calendar.MINUTE,  dateCalendar.get(Calendar.MINUTE)); // Your Mintue
        timeCalendar.set(Calendar.SECOND,  dateCalendar.get(Calendar.SECOND)); // Your second

        return new Time(timeCalendar.getTime().getTime());
    }

    public boolean onSameDay(Date date1, Date date2) {
        Calendar calendar1 = newCalender(date1);

        Calendar calendar2 = newCalender(date2);

        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
                && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)
                && calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH);
    }



    public boolean isBetweenTwoDates(Date fromDate, Date toDate, Date currentDate, Boolean orEqual) {
        if(orEqual != null && orEqual){

            return ((currentDate.before(toDate) || currentDate.equals(toDate)) && (currentDate.after(fromDate) || currentDate.equals(fromDate)));
        }else {

            return (currentDate.before(toDate) &&currentDate.after(fromDate));
        }

    }

    public boolean isBetweenTwoTimes(Time startTime, Time stopTime, Time currentTime) {

        return ((startTime.before(currentTime) || startTime.equals(currentTime)) && ( stopTime.after(currentTime) || stopTime.equals(currentTime)));
    }


    public int getDateHour(Date date) {
        Calendar calendar = newCalender(date);
        return calendar.get(Calendar.HOUR_OF_DAY);        // gets hour in 12h format
    }

    public int getDateWeekDay(Date date) {
        Calendar calendar = newCalender(date);
        return calendar.get(Calendar.DAY_OF_WEEK);  //TODO khouly #SUNDAY=1 update logic
    }

    public int getDateMinutes(Date date) {
        Calendar calendar = newCalender(date);

        // (Calendar.HOUR) gets hour in 12h format
        return calendar.get(Calendar.MINUTE); // gets hour in 24h format
    }

    public int getDateSeconds(Date date) {
        Calendar calendar = newCalender(date);
        // (Calendar.HOUR) gets hour in 12h format
        return calendar.get(Calendar.SECOND);
    }



    public Date addOrSubtractHours(Date date, int hours) {

        Calendar c = newCalender(date);

        c.add(Calendar.HOUR_OF_DAY, hours);
        // Convert calendar back to Date
        return c.getTime();

    }

    public Date addOrSubtractMinutes(Date date, int minutes) {

        Calendar c = newCalender(date);

        c.add(Calendar.MINUTE, minutes);

        // Convert calendar back to Date
        return c.getTime();

    }

    public Time addOrSubtractHours(Time time, int hours) {

        Calendar c = newCalender(time);

        c.add(Calendar.HOUR_OF_DAY, hours);
        // Convert calendar back to Time
        return newTime(c.getTime());

    }

    public Time addOrSubtractMinutes(Time time, int minutes) {

        Calendar c = newCalender(time);

        c.add(Calendar.MINUTE, minutes);

        // Convert calendar back to Time
        return newTime(c.getTime());

    }



    public boolean dateBefore(Date date, Date currentDate, Boolean orEqual) {
        return (currentDate.before(date) || (orEqual != null && orEqual && currentDate.equals(date)));
    }

    public boolean dateAfter(Date date, Date currentDate, Boolean orEqual) {
        return (currentDate.after(date) || (orEqual != null && orEqual && currentDate.equals(date)));
    }

    public boolean timeAfter(Time time, Time currentTime, Boolean orEqual) {



        if (orEqual != null && orEqual) {

            if (currentTime.after(time) || currentTime.equals(time)) {
                System.out.println("after or equal");
                return true;
            }
        } else {

            if (currentTime.after(time)) {
                System.out.println("after");
                return true;
            }
        }

        return false;
    }

    public boolean timeBefore(Time time, Date currentTime, Boolean orEqual) {

        if (orEqual != null && orEqual) {

            if (currentTime.before(time) || currentTime.equals(time)) {
                System.out.println("before or equal");
                return true;
            }
        } else {

            if (currentTime.before(time)) {
                System.out.println("before");
                return true;
            }
        }

        return false;
    }


    public LocalDate convertToLocalDateViaInstant ( Date dateToConvert ) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public LocalDateTime convertToLocalDateViaInstantAtStartOfDate ( Date dateToConvert ) {
        LocalDate localDate = new java.sql.Date(dateToConvert.getTime()).toLocalDate();
        return localDate.atStartOfDay();
    }

    public LocalDate convertToLocalDateViaInstant ( Date dateToConvert, TimeZone timeZone ) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(timeZone.toZoneId())
                .toLocalDate();
    }

    public LocalDateTime convertToLocalDateTimeViaInstant ( Date dateToConvert, TimeZone timeZone ) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(timeZone != null ? timeZone.toZoneId() : TimeZone.getDefault().toZoneId())
                .toLocalDateTime();
    }

    public Date convertLocalDateToDate(LocalDate dateToConvert) {
        return java.sql.Date.valueOf(dateToConvert);
    }

    public Date convertToDateViaInstant(LocalDate dateToConvert, TimeZone timeZone) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(timeZone.toZoneId())
                .toInstant());
    }

    public Date convertToDateViaInstant(LocalDateTime dateToConvert, TimeZone timeZone) {
        return java.util.Date.from(dateToConvert
                .atZone(timeZone.toZoneId())
                .toInstant());
    }

    public Long calculateDaysBetweenTwoDates(Date before, Date after) {
        LocalDate dateBefore = convertToLocalDateViaInstant(before);
        LocalDate dateAfter = convertToLocalDateViaInstant(after);
        return ChronoUnit.DAYS.between(dateBefore, dateAfter);
    }

    public Date copyTimeToDate(Date date, Date time) {
        Calendar t = Calendar.getInstance();
        t.setTime(time);

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, t.get(Calendar.HOUR_OF_DAY));
        c.set(Calendar.MINUTE, t.get(Calendar.MINUTE));
        c.set(Calendar.SECOND, t.get(Calendar.SECOND));
        c.set(Calendar.MILLISECOND, t.get(Calendar.MILLISECOND));
        return c.getTime();
    }


}
