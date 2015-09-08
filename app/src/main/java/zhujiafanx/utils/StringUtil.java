package zhujiafanx.utils;

/**
 * Created by Administrator on 2015/9/4.
 */
public class StringUtil {
    public static boolean isInt(String value)
    {
        boolean result=true;

        try
        {
            Integer.valueOf(value);
        }
        catch (Exception e)
        {
            result=false;
        }

        return result;
    }
}
