package zhujiafanx.utils;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

/**
 * Created by Administrator on 2015/7/16.
 */
public class IOExtesion {

    public static String StreamToString(InputStream inputStream) throws IOException
    {
        String result="";

        StringWriter stringWriter=new StringWriter();

        IOUtils.copy(inputStream,stringWriter);

        result=stringWriter.toString();

        stringWriter.close();

        return result;
    }
}
