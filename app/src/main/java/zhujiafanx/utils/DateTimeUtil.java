package zhujiafanx.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2015/8/22.
 */
public class DateTimeUtil {
    public static String ConvertToDateAndTimeString(Date dateTime) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(dateTime);
    }

    public static String ConvertToDateAndTimeString(Calendar calendar)
    {
        return ConvertToDateAndTimeString(ConvertFromCalender(calendar));
    }

    public static String ConvertToDateString(Date dateTime)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(dateTime);
    }

    public static String ConvertToDateString(Calendar calendar)
    {
        return ConvertToDateString(ConvertFromCalender(calendar));
    }

    public static String ConvertToTimeString(Date dateTime)
    {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(dateTime);
    }

    public static String ConvertToTimeString(Calendar calendar)
    {
        return ConvertToTimeString(ConvertFromCalender(calendar));
    }

    public static Date ConvertFromString(String dateTime) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return format.parse(dateTime);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date ConvertFromString(String date, String time) {
        return ConvertFromString(date + " " + time);
    }

    public static Date ConvertFromCalender(Calendar calendar)
    {
        return calendar.getTime();
    }

    public static Calendar ConvertToCalender(Date date)
    {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);

        return calendar;
    }
}
