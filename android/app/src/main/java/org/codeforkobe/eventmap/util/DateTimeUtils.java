package org.codeforkobe.eventmap.util;

import android.content.Context;
import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author ISHIMARU Sohei on 2016/09/01.
 */
public class DateTimeUtils {
    public static String formatDateTime(Context context, String source) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmss", Locale.JAPAN);
        try {
            Date date = sdf.parse(source);
            java.text.DateFormat dateFormat = DateFormat.getLongDateFormat(context);
            java.text.DateFormat timeFormat = DateFormat.getTimeFormat(context);
            return dateFormat.format(date) + " " + timeFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return source;
    }
}
