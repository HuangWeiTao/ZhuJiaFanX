package zhujiafanx.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2015/8/22.
 */
public class DateExtension {
    public static String ConvertToString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

        return format.format(date);
    }

    public static Date ConvertFromString(String date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd HH:mm");
            return format.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
}
