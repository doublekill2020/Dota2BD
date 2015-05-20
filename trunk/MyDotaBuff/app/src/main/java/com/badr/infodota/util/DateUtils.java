package com.badr.infodota.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * User: Histler
 * Date: 23.04.14
 */
public class DateUtils {
    public static SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("HH:mm  dd.MM.yyyy");
    public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy, EEE");
    public static SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");
    public static SimpleDateFormat VALVE_DATE_TIME_FORMAT = new SimpleDateFormat("HH:mm  dd.MM.yyyy");
    private static SimpleDateFormat FULL_DATE_TIME_FORMAT = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");

    static {
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();
        VALVE_DATE_TIME_FORMAT.setTimeZone(tz);
    }

}
