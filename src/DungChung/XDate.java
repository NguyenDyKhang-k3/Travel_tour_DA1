package DungChung;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author NHUTLQ
 */
public class XDate {

    static SimpleDateFormat format = new SimpleDateFormat();

    public static Date toDate(String date, String patern) {
        try {
            format.applyPattern(patern);
            return format.parse(date);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String toString(Date date, String pattern) {
        format.applyPattern(pattern);
        return format.format(date);
    }

    public static Date addDays(Date date, long days) {
        date.setTime(date.getTime() + days * 24 * 60 * 60 * 1000);
        return date;
    }
}
