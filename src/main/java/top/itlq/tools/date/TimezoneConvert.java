package top.itlq.tools.date;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 日期格式化（包含时区）
 */
public class TimezoneConvert {
    @Test
    public void test1() throws Exception{
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        System.out.println(sdf.format(d));
    }
}
