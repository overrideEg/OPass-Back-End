package com.overrideeg.apps.opass.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * User: amr
 * Date: 4/1/2020
 * Time: 12:45 AM
 */
public class DateUtils {


    public boolean onSameDay(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
                && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)
                && calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH);
    }


//    or
//    public static boolean betweenTwoHours(Date startTime, Date endTime, Date validateTime)
//    {
//        boolean validTimeFlag = false;
//
//        if(endTime.compareTo(startTime) <= 0)
//        {
//            if(validateTime.compareTo(endTime) < 0 || validateTime.compareTo(startTime) >= 0)
//            {
//                validTimeFlag = true;
//            }
//        }
//        else if(validateTime.compareTo(endTime) < 0 && validateTime.compareTo(startTime) >= 0)
//        {
//            validTimeFlag = true;
//        }
//
//        return validTimeFlag;
//    }


    public boolean isBetweenTwoTime(Date startTime, Date stopTime, Date currentTime) {
        //Start Time
        Calendar StartTime = Calendar.getInstance();
        StartTime.setTime(startTime);
        //Current Time
        Calendar CurrentTime = Calendar.getInstance();
        CurrentTime.setTime(currentTime);
        //Stop Time
        Calendar StopTime = Calendar.getInstance();
        StopTime.setTime(stopTime);

        if (stopTime.compareTo(startTime) < 0) {
            if (CurrentTime.compareTo(StopTime) < 0) {
                CurrentTime.add(Calendar.DATE, 1);
            }
            StopTime.add(Calendar.DATE, 1);
        }
        return CurrentTime.compareTo(StartTime) >= 0 && CurrentTime.compareTo(StopTime) < 0;
    }


    public int getDateHour(Date date) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);        // gets hour in 12h format
    }


    public int getDateWeekDay(Date date) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);  //TODO khouly #SUNDAY=1 update logic
    }

    public int getDateMinutes(Date date) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        // (Calendar.HOUR) gets hour in 12h format
        return calendar.get(Calendar.MINUTE); // gets hour in 24h format
    }

    public int getDateSeconds(Date date) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        // (Calendar.HOUR) gets hour in 12h format
        return calendar.get(Calendar.SECOND);
    }

    public Date addOrSubtractHours(Date date, int hours) {

        Calendar c = Calendar.getInstance();
        c.setTime(date);

        c.add(Calendar.HOUR, hours);
        // Convert calendar back to Date
        return c.getTime();

    }

    public Date addOrSubtractMinutes(Date date, int minutes) {

        Calendar c = Calendar.getInstance();
        c.setTime(date);

        c.add(Calendar.MINUTE, minutes);

        // Convert calendar back to Date
        return c.getTime();

    }


}
