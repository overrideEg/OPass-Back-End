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


    private Calendar newCalender(Date date) {
        Calendar newCalender = Calendar.getInstance();
        newCalender.setTime(date);
        return newCalender;
    }


    public boolean onSameDay(Date date1, Date date2) {
        Calendar calendar1 = newCalender(date1);

        Calendar calendar2 = newCalender(date2);

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
        Calendar StartTime = newCalender(startTime);

        //Current Time
        Calendar CurrentTime = newCalender(currentTime);

        //Stop Time
        Calendar StopTime = newCalender(stopTime);

        if (stopTime.compareTo(startTime) < 0) {
            if (CurrentTime.compareTo(StopTime) < 0) {
                CurrentTime.add(Calendar.DATE, 1);
            }
            StopTime.add(Calendar.DATE, 1);
        }
        return CurrentTime.compareTo(StartTime) >= 0 && CurrentTime.compareTo(StopTime) < 0;
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

    public boolean before(Date time, Date currentTime,Boolean onlyHours,Boolean orEqual) {

        Calendar startCalendar = newCalender(time);
        Calendar currentTimeCalendar = newCalender(currentTime);

        int timeStart;
        int startHour = startCalendar.get(Calendar.HOUR_OF_DAY);

        if(onlyHours!=null &&onlyHours){

            int startMin = startCalendar.get(Calendar.MINUTE);
            timeStart=startHour * 60 + startMin;  //this

        }else{
            timeStart=startHour;
        }

        int timeCurrent;
        int currentHour = currentTimeCalendar.get(Calendar.HOUR_OF_DAY);

        if(onlyHours!=null &&onlyHours){

            int currentMin = currentTimeCalendar.get(Calendar.MINUTE);
            timeCurrent=currentHour * 60 + currentMin;  //this

        }else{
            timeCurrent=currentHour;
        }

        if(orEqual!=null && orEqual){

            return timeStart >= timeCurrent;

        }else {

            return timeStart > timeCurrent;

        }

    }

    public boolean after(Date time, Date currentTime,Boolean onlyHours,Boolean orEqual) {

        Calendar startCalendar = newCalender(time);
        Calendar currentTimeCalendar = newCalender(currentTime);

        int timeStart;
        int startHour = startCalendar.get(Calendar.HOUR_OF_DAY);

        if(onlyHours!=null &&onlyHours){

            int startMin = startCalendar.get(Calendar.MINUTE);
            timeStart=startHour * 60 + startMin;  //this

        }else{
            timeStart=startHour;
        }

        int timeCurrent;
        int currentHour = currentTimeCalendar.get(Calendar.HOUR_OF_DAY);

        if(onlyHours!=null &&onlyHours){

            int currentMin = currentTimeCalendar.get(Calendar.MINUTE);
            timeCurrent=currentHour * 60 + currentMin;  //this

        }else{
            timeCurrent=currentHour;
        }
        if(orEqual!=null && orEqual){

            return timeStart <= timeCurrent;
        }else {
            return timeStart < timeCurrent;

        }

    }


//    public boolean beforeOrEqual(Date date, int minutes) {
//
//        Calendar startSHCalendar = Calendar.getInstance();
//        startSHCalendar.setTime(date);
//
//        Calendar nowCalendar = Calendar.getInstance();
//        nowCalendar.setTime(date);
//
//        Calendar stopSHCalendar = Calendar.getInstance();
//        stopSHCalendar.setTime(date);
//
//        int startSHhour = startSHCalendar.get(Calendar.HOUR_OF_DAY);
//        int startSHmin = startSHCalendar.get(Calendar.MINUTE);
//        int timeStart = startSHhour*60 + startSHmin;  //this
//
//        int nowHour = nowCalendar.get(Calendar.HOUR_OF_DAY);
//        int nowMin = nowCalendar.get(Calendar.MINUTE);
//        int timeNow = nowHour*60 + nowMin;  //this
//
//        int stopSHhour = stopSHCalendar.get(Calendar.HOUR_OF_DAY);
//        int stopSHmin = stopSHCalendar.get(Calendar.MINUTE);
//        int timeStop = stopSHhour*60 + stopSHmin;  //this
//
//        if( timeStart <= timeNow  && timeNow <= timeStop ){
//            //between
//        }else{
//            //not betwwen
//        }
//
//
//    }


}
