package cn.edu.mydotabuff.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.edu.mydotabuff.DotaApplication;
import cn.edu.mydotabuff.R;

public class TimeHelper {

    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_FORMAT2 = "yyyy-MM-dd";

    /**
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getStringTime() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        return c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-"
                + c.get(Calendar.DAY_OF_MONTH) + " "
                + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE)
                + ":" + c.get(Calendar.SECOND);
    }

    /**
     * 得到校验后的正确时间 格式形如：yyyy-MM-dd HH:mm:ss
     *
     * @param timedifference 带符号位的long 值
     *
     * @return
     */
    public static String getStringTime(long timedifference) {
        String time = null;
        long currtime = System.currentTimeMillis();
        long result = currtime + timedifference;
        time = getLongtoString(result);
        return time;
    }

    /**
     * 将long 的时间值转换为固定格式为:yyyy-MM-dd HH:mm:ss输出
     *
     * @param str
     *
     * @return
     */
    public static String getLongtoString(long time) {
        String str = null;
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT);
        str = sdf.format(date);
        return str;
    }

    /**
     * 得到当前服务器时间与系统时间差值
     *
     * @param serverTime
     *
     * @return
     */
    public static long getTimeDifference(long serverTime) {
        long timedifference = 0L;
        long curr = System.currentTimeMillis();
        timedifference = serverTime - curr;
        return timedifference;
    }

    /**
     * return format time string
     *
     * @return
     */
    public static String getFormatTimeString() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }

    /**
     * @return 例：2014081418
     */
    public static int getFormatTimeInt() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHH");
        return Integer.parseInt(format.format(new Date()));
    }

    /**
     * 将Timestamp 转换为字符串
     *
     * @param time
     *
     * @return
     */
    public static String TimestampToStr(Timestamp time) {
        SimpleDateFormat df = new SimpleDateFormat(DEFAULT_FORMAT);// 定义格式，不显示毫秒
        String str = df.format(time);
        System.out.println(str);
        return str;
    }

    /**
     * 将字符串转换为Timestamp
     *
     * @param str
     *
     * @return
     */
    public static Timestamp StrToTimestamp(String str) {
        Timestamp time = Timestamp.valueOf(str);
        System.out.println(time);
        return time;
    }

    /**
     * 字符串转换成日期
     *
     * @param stringdate 要转换的字符串(字符串格式 yyyy-MM-dd HH:mm:ss)
     *
     * @return
     */
    public static Date getStringtoDate(String stringdate) {
        SimpleDateFormat df = new SimpleDateFormat(DEFAULT_FORMAT);
        Date cDate = null;
        try {
            cDate = df.parse(stringdate);
        } catch (ParseException e1) {
        }
        return cDate;
    }

    public static String getHaos() {
        Calendar c = Calendar.getInstance();
        // 可以对每个时间域单独修改
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DATE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        int haos = c.get(Calendar.MILLISECOND);
        return "" + year + " " + month + " " + date + " " + hour + " " + minute
                + " " + second + " " + haos;
    }

    /**
     * 字符串转换成日期
     *
     * @param stringdate 要转换的字符串(字符串格式 yyyy-MM-dd HH:mm:ss)
     *
     * @return
     */
    public static Date getStringtoDate2(String stringdate) {
        SimpleDateFormat df = new SimpleDateFormat(DEFAULT_FORMAT2);
        Date cDate = null;
        try {
            cDate = df.parse(stringdate);
        } catch (ParseException e1) {
        }
        return cDate;
    }

    /**
     * 得到long类型时间值
     *
     * @param stringdate
     *
     * @return
     */
    public static long getStringtoLong(String stringdate) {
        long time = 0L;
        if (stringdate == null)
            return time;
        Date date = getStringtoDate(stringdate);
        if (date != null)
            time = date.getTime();
        return time;
    }

    /**
     * 获得文件保存名字
     *
     * @return
     */
    public static String getFileStringTime() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        String tempString = String.format("didi%s%s%s%s%s%s",
                c.get(Calendar.YEAR), (c.get(Calendar.MONTH) + 1),
                c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE), c.get(Calendar.SECOND));
        return tempString;
    }

    /**
     * 获得年月
     */
    public static String getStringTimeYYMM() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        String tempString = String.format("%s-%s-%s", c.get(Calendar.YEAR),
                (c.get(Calendar.MONTH) + 1), c.get(Calendar.DAY_OF_MONTH));
        return tempString;
    }

    /**
     * 获得时分
     */
    public static String getStringTimeHHmm() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        String tempString = String.format("%s:%s", c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE));
        return tempString;
    }

    /**
     * 返回下拉刷新时间
     *
     * @return 上午 10:52
     */
    public static String friendly_time() {
        int nowtime = new Date().getHours();
        String time = null;
        if (nowtime <= 6) {
            time = getString(R.string.lingchen)
                    + dateFormater3.get().format(new Date());
        } else if (nowtime <= 11) {
            time = getString(R.string.shangwu)
                    + dateFormater3.get().format(new Date());
        } else if (nowtime <= 13) {
            time = getString(R.string.zhongwu)
                    + dateFormater3.get().format(new Date());
        } else if (nowtime <= 17) {
            time = getString(R.string.xiawu)
                    + dateFormater3.get().format(new Date());
        } else {
            time = getString(R.string.wanshang)
                    + dateFormater3.get().format(new Date());
        }

        return time;
    }

    public static String getString(int Id) {
        return DotaApplication.getApplication().getResources().getString(Id);
    }

    /**
     * 以友好的方式显示时间
     *
     * @param sdate
     *
     * @return
     */
    public static String friendly_time(String sdate) {
        if (sdate == null) {
            return "";
        }
        Date time = toDate(sdate);
        if (time == null) {
            return "";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();

        // 判断是否是同一天
        String curDate = dateFormater2.get().format(cal.getTime());
        String paramDate = dateFormater2.get().format(time);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour < 0) {
                ftime = dateFormater3.get().format(time);
            } else if (hour == 0) {
                long minutes = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1);
                if (minutes < 5)
                    ftime = getString(R.string.ganggang);
                else if (minutes > 5 && minutes < 30) {
                    ftime = minutes + getString(R.string.fenzhongqian);
                } else
                    ftime = dateFormater3.get().format(time);
            } else if (hour > 0 && hour < 2) {
                ftime = hour + getString(R.string.xiaoshiqian);
            } else
                ftime = getString(R.string.jintian)
                        + dateFormater3.get().format(time);

            return ftime;
        }

        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days < 0) {
            ftime = dateFormater2.get().format(time);
        } else if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour < 0) {
                ftime = dateFormater3.get().format(time);
            } else if (hour == 0) {
                long minutes = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1);
                if (minutes < 5)
                    ftime = getString(R.string.ganggang);
                else if (minutes > 5 && minutes < 30) {
                    ftime = minutes + getString(R.string.fenzhongqian);
                } else {
                    ftime = dateFormater3.get().format(time);
                }
            } else if (hour > 0 && hour < 2) {
                ftime = hour + getString(R.string.xiaoshiqian);
            } else {
                ftime = dateFormater3.get().format(time);
            }
        } else if (days == 1) {
            ftime = getString(R.string.zuotian)
                    + dateFormater3.get().format(time);
        } else if (days == 2) {
            ftime = getString(R.string.qiantian)
                    + dateFormater3.get().format(time);
        }
        // else if(days > 2 && days <= 10){
        // ftime = days+"天前";
        // }
        else if (days > 2) {
            ftime = dateFormater2.get().format(time);
        }
        return ftime;
    }

    /**
     * 将字符串转位日期类型
     *
     * @param sdate
     *
     * @return
     */
    public static Date toDate(String sdate) {
        try {
            if (dateFormater.get().parse(sdate) == null) {
                return dateFormaterWithoutSecond.get().parse(sdate);
            } else {
                return dateFormater.get().parse(sdate);
            }
        } catch (ParseException e) {
            try {
                return dateFormaterWithoutSecond.get().parse(sdate);
            } catch (ParseException e1) {
                return null;
            }
        }
    }

    /**
     * 将字符串转位日期类型
     *
     * @param sdate
     *
     * @return
     */
    public static Date toDate2(String sdate) {
        try {
            return dateFormater2.get().parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 将字符串转位日期类型
     *
     * @param sdate
     *
     * @return
     */
    public static Date toDate4(String sdate) {
        try {
            return dateFormater4.get().parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 将字符串转位日期类型
     *
     * @param sdate
     * @return
     */
    /**
     * 获取现在时间
     *
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String toDateYYYYMMDD(String dateString) {
        String result = "";
        try {
            DateFormat formatBefore = new SimpleDateFormat("yyyy-MM-dd");
            Date date;
            date = formatBefore.parse(dateString);
            SimpleDateFormat formatAfter = new SimpleDateFormat("yyyy/MM/dd");
            result = formatAfter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 将字符串转位日期类型
     *
     * @param sdate
     * @return
     */
    /**
     * 获取现在时间
     *
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String toDateYYYYMMDD2(String dateString) {
        String result = TimeHelper.formatToString(dateString,
                "yyyy/MM/dd HH:mm:ss");
        return result;
    }

    /**
     * 获取现在时间
     *
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String toDateYYYYMMDD3(Date date) {
        String result = "";
        SimpleDateFormat temp = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
        result = temp.format(date);
        return result;
    }

    /**
     * 将字符串转位日期类型
     *
     * @param sdate
     * @return
     */
    /**
     * 获取现在时间
     *
     * @return返回字符串格式 yyyy-MM-dd HH:mm
     */
    public static String toDateYYYYMMDD5(String dateString) {
        String result = TimeHelper.formatToString(dateString,
                "yyyy-MM-dd HH:mm");
        return result;
    }

    public static String TimeStamp2Date(String timestampString, String formats) {
        if (timestampString.equals("null")) {
            return "未知";
        }
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = new java.text.SimpleDateFormat(formats)
                .format(new java.util.Date(timestamp));
        return date;
    }

    private final static ThreadLocal<SimpleDateFormat> dateFormater = new
            ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };
    private final static ThreadLocal<SimpleDateFormat> dateFormaterWithoutSecond = new
            ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new
            ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater3 = new
            ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("HH:mm");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater4 = new
            ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        }
    };

    // 将未指定格式的字符串转换成日期类型
    public static Date parseStringToDate(String date) throws ParseException {
        Date result = null;
        String parse = date;
        parse = parse.replaceFirst("^[0-9]{4}([^0-9]?)", "yyyy$1");
        parse = parse.replaceFirst("^[0-9]{2}([^0-9]?)", "yy$1");
        parse = parse.replaceFirst("([^0-9]?)[0-9]{1,2}([^0-9]?)", "$1MM$2");
        parse = parse.replaceFirst("([^0-9]?)[0-9]{1,2}( ?)", "$1dd$2");
        parse = parse.replaceFirst("( )[0-9]{1,2}([^0-9]?)", "$1HH$2");
        parse = parse.replaceFirst("([^0-9]?)[0-9]{1,2}([^0-9]?)", "$1mm$2");
        parse = parse.replaceFirst("([^0-9]?)[0-9]{1,2}([^0-9]?)", "$1ss$2");

        SimpleDateFormat format = new SimpleDateFormat(parse);

        result = format.parse(date);

        return result;
    }

    // 将日期格式的字符串以指定格式输出
    public static String formatToString(String date, String format) {
        try {
            Date dt = parseStringToDate(date);
            java.text.SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(dt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    // private final static ThreadLocal<SimpleDateFormat> dateFormater4 = new
    // ThreadLocal<SimpleDateFormat>() {
    // @Override
    // protected SimpleDateFormat initialValue() {
    // return new SimpleDateFormat("yyyy");
    // }
    // };
    // private final static ThreadLocal<SimpleDateFormat> dateFormater5 = new
    // ThreadLocal<SimpleDateFormat>() {
    // @Override
    // protected SimpleDateFormat initialValue() {
    // return new SimpleDateFormat("yyyy-MM-dd HH:mm");
    // }
    // };

    /**
     * 获取时间
     *
     * @return
     */
    public static MyDate GetMyDate() {
        MyDate mMyDate = new MyDate();
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        mMyDate.setYear(c.get(Calendar.YEAR));
        mMyDate.setMonth(c.get(Calendar.MONTH) + 1);
        mMyDate.setDay(c.get(Calendar.DAY_OF_MONTH));
        mMyDate.setHour(c.get(Calendar.HOUR_OF_DAY));
        mMyDate.setMinute(c.get(Calendar.MINUTE));
        mMyDate.setSecond(c.get(Calendar.SECOND));
        return mMyDate;
    }

    public static class MyDate {

        private int year, month, day, hour, minute, second;

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append(getYearStr());
            builder.append(getMonthStr());
            builder.append(getDayStr());
            builder.append(getHourStr());
            builder.append(getMinStr());
            builder.append(getSecondStr());
            return builder.toString();
        }

        public int getHour() {
            return hour;
        }

        public void setHour(int hour) {
            this.hour = hour;
        }

        public int getMinute() {
            return minute;
        }

        public void setMinute(int minute) {
            this.minute = minute;
        }

        public int getSecond() {
            return second;
        }

        public void setSecond(int second) {
            this.second = second;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public String getYearStr() {
            return String.valueOf(year);
        }

        public String getMonthStr() {
            String s = String.valueOf(month);
            if (s.length() < 2) {
                s = '0' + s;
            }
            return s;
        }

        public String getDayStr() {
            String s = String.valueOf(day);
            if (s.length() < 2) {
                s = '0' + s;
            }
            return s;
        }

        public String getHourStr() {
            String s = String.valueOf(hour);
            if (s.length() < 2) {
                s = '0' + s;
            }
            return s;
        }

        public String getMinStr() {
            String s = String.valueOf(minute);
            if (s.length() < 2) {
                s = '0' + s;
            }
            return s;
        }

        public String getSecondStr() {
            String s = String.valueOf(second);
            if (s.length() < 2) {
                s = '0' + s;
            }
            return s;
        }
    }

    public static String getDateFormatYYYYMMDD(String time) {
        DateFormat format_before = new SimpleDateFormat("yyyy-MM-dd");
        Date dDate = null;
        try {
            dDate = format_before.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        DateFormat format_after = new SimpleDateFormat("yyyy-MM-dd");
        String reTime = format_after.format(dDate);
        return reTime;
    }

    /**
     * 返回unix时间戳 (1970年至今的秒数)
     *
     * @return
     */

    public static long getUnixStamp() {

        return System.currentTimeMillis() / 1000;

    }

    /**
     * 得到昨天的日期
     *
     * @return
     */

    public static String getYestoryDate() {

        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DATE, -1);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String yestoday = sdf.format(calendar.getTime());

        return yestoday;

    }

    /**
     * 得到今天的日期
     *
     * @return
     */

    public static String getTodayDate() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String date = sdf.format(new Date());

        return date;

    }

    /**
     * 时间戳转化为时间格式
     *
     * @param timeStamp
     *
     * @return
     */

    public static String timeStampToStr(long timeStamp) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String date = sdf.format(timeStamp * 1000);

        return date;

    }

    /**
     * 得到日期   yyyy-MM-dd
     *
     * @param timeStamp 时间戳
     *
     * @return
     */

    public static String formatDate(long timeStamp) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String date = sdf.format(timeStamp * 1000);

        return date;

    }

    /**
     * 得到时间  HH:mm:ss
     *
     * @param timeStamp 时间戳
     *
     * @return
     */

    public static String getTime(long timeStamp) {

        String time = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String date = sdf.format(timeStamp * 1000);

        String[] split = date.split("\\s");

        if (split.length > 1) {

            time = split[1];

        }

        return time;

    }

    /**
     * 将一个时间戳转换成提示性时间字符串，如刚刚，1秒前
     *
     * @param timeStamp
     *
     * @return
     */

    public static String convertTimeToFormat(long timeStamp) {

        long curTime = System.currentTimeMillis() / (long) 1000;

        long time = curTime - timeStamp;

        if (time < 60 && time >= 0) {

            return "刚刚";

        } else if (time >= 60 && time < 3600) {

            return time / 60 + "分钟前";

        } else if (time >= 3600 && time < 3600 * 24) {

            return time / 3600 + "小时前";

        } else if (time >= 3600 * 24 && time < 3600 * 24 * 30) {

            return time / 3600 / 24 + "天前";

        } else if (time >= 3600 * 24 * 30 && time < 3600 * 24 * 30 * 12) {

            return time / 3600 / 24 / 30 + "个月前";

        } else if (time >= 3600 * 24 * 30 * 12) {

            return time / 3600 / 24 / 30 / 12 + "年前";

        } else {

            return "刚刚";

        }

    }

    /**
     * 将一个时间戳转换成提示性时间字符串，(多少分钟)
     *
     * @param timeStamp
     *
     * @return
     */

    public static String timeStampToFormat(long timeStamp) {

        long curTime = System.currentTimeMillis() / (long) 1000;

        long time = curTime - timeStamp;

        return time / 60 + "";

    }
}
