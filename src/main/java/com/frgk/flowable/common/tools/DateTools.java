package com.frgk.flowable.common.tools;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//import org.apache.commons.lang3.StringUtils;

public class DateTools {
    /**
     * MM/dd HH:mm
     */
    public static final String DATE_FORMAT_MM_DD_HH_MM = "MM/dd HH:mm";

    /**
     * 默认时间格式
     */
    public static final String DEFAULT_DATE_PATTERN2 = "yyyy-MM-dd HH:mm:ss";

    /**
     * 默认时间格式
     */
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * 统计时间格式
     */
    public static final String STATIC_DATE_PATTERN = "yyyyMMddHHmmssSSS";

    /**
     * 当天时间，二十四小时制
     */
    public static final String DEFAULT_TODAY_PATTERN = "HH:mm:ss";

    /**
     * 昨天格式
     */
    public static final String DEFUALT_YESTERDAY_PATTERN = "昨天 HH:mm";

    /**
     * 年月格式
     */
    public static final String DEFAULT_YEAR_MONTH_DAY_2 = "yyyy年MM月";
    /**
     * 年月日格式
     */
    public static final String DEFAULT_YEAR_MONTH_DAY_3 = "yyyy年MM月dd日";

    /**
     * 月日格式
     */
    // public static final String DEFAULT_MONTH_AND_DAY = "M月d日 HH:mm:ss";
    public static final String DEFAULT_MONTH_AND_DAY = "M月d日 HH:mm";

    public static final String DEFAULT_MONTH_AND_DAY_NO_SPACE = "M月d日HH:mm";
    /**
     * 年月日格式
     */
    // public static final String DEFAULT_YEAR_MONTH_DAY = "yyyy-MM-dd HH:mm";
    public static final String DEFAULT_YEAR_MONTH_DAY = "yyyy-MM-dd";

    /**
     * 年月格式
     */
    public static final String DEFAULT_YEAR_MONTH = "yyyy-MM";

    /**
     * 不带“-”的时间格式
     */
    public static final String DEFAULT_YEARMONTHDAY = "yyyyMMdd";

    /**
     * 月/日
     */
    public static final String TEMP_MONTH_DAY = "MM/dd";
    /**
     * 时和分
     */
    public static final String TEMP_HOUR_AND_MINUTE = "HH:mm";

    /**
     * MONDAY
     */
    public static final String MONDAY = "星期一";
    /**
     * TUESDAY
     */
    public static final String TUESDAY = "星期二";
    /**
     * WEDNESDAY
     */
    public static final String WEDNESDAY = "星期三";
    /**
     * THURSDAY
     */
    public static final String THURSDAY = "星期四";
    /**
     * FRIDAY
     */
    public static final String FRIDAY = "星期五";
    /**
     * SATURDAY
     */
    public static final String SATURDAY = "星期六";
    /**
     * SUNDAY
     */
    public static final String SUNDAY = "星期日";

    /**
     * 日期不能为空提示信息
     */
    private static final String DATE_NULL_MSG = "The date must not be null";

    /**
     * MM-dd
     */
    public static final String TEMP_MONTH_DAY2 = "MM-dd";

    /**
     * 私有化构造函数
     */
    private DateTools() {
    }

    /**
     * 处理时间戳为格式化字符串<br>
     *
     * @param timestamp
     * @param pattern
     * @return
     */
    public static String format(long timestamp, String pattern) {
        Date date = new Date(timestamp);
        return format(date, pattern);
    }

    /**
     * 处理时间为格式化字符串<br>
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {
        if (null != date) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            return simpleDateFormat.format(date);
        } else {
            return "";
        }
    }

    /**
     * 转换日期格式
     *
     * @Description:
     */
    public static String format(String source, String orinPattern, String retPattern) {
        Date date = parse(source, orinPattern);
        return format(date, retPattern);
    }

    /**
     * 解析时间字符串<br>
     *
     * @param source
     * @param pattern
     * @return
     */
    public static Date parse(String source, String pattern) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            return simpleDateFormat.parse(source);
        } catch (ParseException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 是否同一天<br>
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameday(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException(DATE_NULL_MSG);
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameday(cal1, cal2);
    }

    /**
     * 是否同一天<br>
     *
     * @param cal1
     * @param cal2
     * @return
     */
    public static boolean isSameday(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException(DATE_NULL_MSG);
        }
        return cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 是否昨天<br>
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isYesterday(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException(DATE_NULL_MSG);
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isYesterday(cal1, cal2);
    }

    /**
     * 是否昨天<br>
     *
     * @param cal1
     * @param cal2
     * @return
     */
    public static boolean isYesterday(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException(DATE_NULL_MSG);
        }

        return cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA)
                && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && (cal1.get(Calendar.DAY_OF_YEAR) - 1 == cal2.get(Calendar.DAY_OF_YEAR) || cal1
                .get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) - 1);
    }

    /**
     * 是否本周<br>
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameweek(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException(DATE_NULL_MSG);
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameweek(cal1, cal2);
    }

    /**
     * 是否本周<br>
     *
     * @param cal1
     * @param cal2
     * @return
     */
    public static boolean isSameweek(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException(DATE_NULL_MSG);
        }

        return cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.WEEK_OF_MONTH) == cal2.get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * 是否本年<br>
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameyear(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException(DATE_NULL_MSG);
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameyear(cal1, cal2);
    }

    /**
     * 是否本年<br>
     *
     * @param cal1
     * @param cal2
     * @return
     */
    public static boolean isSameyear(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException(DATE_NULL_MSG);
        }

        return cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
    }

    /**
     * 取一天内的小时数，二十四小时制<br>
     *
     * @return
     */
    public static String toToday(Date date) {
        return format(date, DEFAULT_TODAY_PATTERN);
    }

    /**
     * 昨天时间格式<br>
     *
     * @param date
     * @return
     */
    public static String toYesterday(Date date) {
        return format(date, DEFAULT_MONTH_AND_DAY);
    }

    /**
     * 取星期几<br>
     *
     * @param calendar
     * @return
     */
    public static String toDayOfWeek(Calendar calendar) {
        String when = null;
        if (Calendar.MONDAY == calendar.get(Calendar.DAY_OF_WEEK)) {

            when = MONDAY;
        } else if (Calendar.TUESDAY == calendar.get(Calendar.DAY_OF_WEEK)) {

            when = TUESDAY;
        } else if (Calendar.WEDNESDAY == calendar.get(Calendar.DAY_OF_WEEK)) {

            when = WEDNESDAY;
        } else if (Calendar.THURSDAY == calendar.get(Calendar.DAY_OF_WEEK)) {

            when = THURSDAY;
        } else if (Calendar.FRIDAY == calendar.get(Calendar.DAY_OF_WEEK)) {

            when = FRIDAY;
        } else if (Calendar.SATURDAY == calendar.get(Calendar.DAY_OF_WEEK)) {

            when = SATURDAY;
        } else if (Calendar.SUNDAY == calendar.get(Calendar.DAY_OF_WEEK)) {

            when = SUNDAY;
        }
        return when;
    }

    /**
     * 返回月日<br>
     *
     * @param date
     * @return
     */
    public static String toMonthAndDay(Date date) {
        return format(date, DEFAULT_MONTH_AND_DAY);
    }

    /**
     * 返回年月日<br>
     *
     * @param date
     * @return
     */
    public static String toYearMonthDay(Date date) {
        return format(date, DEFAULT_YEAR_MONTH_DAY);
    }

    /**
     * 格式化时间字符串<br>
     * <p>
     * 今天的：时间 24小时制
     * </p>
     * <p>
     * 昨天：昨天
     * </p>
     * <p>
     * 一周内：显示星期几
     * </p>
     * <p>
     * 大于一周小于一年：4-7
     * </p>
     * <p>
     * 大于一年：2012-4-7
     * </p>
     *
     * @param source
     * @return
     */
    public static String formatDate(String source) {
        if (!source.isEmpty()) {
            if (("null").equals(source)) {
                return "";
            }
            Date date = new Date(Long.parseLong(source));
            Date now = new Date();

            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(now);
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(date);

            String result;

            if (DateTools.isSameday(cal1, cal2)) {

                result = DateTools.toToday(date);
            } else if (DateTools.isYesterday(cal1, cal2)) {

                result = DateTools.toYesterday(date);
            } else if (DateTools.isSameWeekNew(cal1, cal2)) {
                result = DateTools.toYesterday(date);
            } else if (DateTools.isSameyear(cal1, cal2)) {

                result = DateTools.toMonthAndDay(date);
            } else {

                result = DateTools.toYearMonthDay(date);
            }

            return result;
        }

        return "";
    }

    private static DateFormat SimpleDateFormat(String string) {
        return null;
    }

    /**
     * 功能描述:判断是否是同一周 <br>
     * 默认第一个日期大于第二个日期
     *
     * @param cal1
     * @param cal2
     * @return
     */
    public static boolean isSameWeekNew(Calendar cal1, Calendar cal2) {
        if (cal1.before(cal2)) {
            return false;
        }
        cal2.add(Calendar.DAY_OF_MONTH, 7);
        return cal2.after(cal1);
    }

    public static String getMessageTime(String time) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = dateFormat.parse(time);
            long todayTime = System.currentTimeMillis();
            Date todayDate = new Date(todayTime);
            if (isSameday(todayDate, date)) {
                SimpleDateFormat format1 = new SimpleDateFormat("HH:mm");
                return format1.format(date);
            }
            if (isYesterday(todayDate, date)) {
                SimpleDateFormat format1 = new SimpleDateFormat("HH:mm");
                return "昨天" + format1.format(date);
            }
            Calendar todayCal = Calendar.getInstance();
            todayCal.setTime(todayDate);
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(date);
            if (isSameyear(todayCal, cal2)) {
                return toMonthAndDay(date);
            } else {
                if (todayCal.get(Calendar.YEAR) > cal2.get(Calendar.YEAR)) {
                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                    return format1.format(date);
                } else {
                    return time;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    private static String unitFormat(int i) {
        String retStr;
        if (i >= 0 && i < 10) {
            retStr = "0" + Integer.toString(i);
        } else {
            retStr = "" + i;
        }
        return retStr;
    }

    /**
     * @param second 秒
     * @return 00:00小时
     */
    public static String secondToHourMinute(String second) {
        int time;
        if (second.isEmpty()) {
            time = 0;
        } else {
            time = Integer.valueOf(second);
        }

        String retStr;
        if (time <= 0) {
            return "00:00";
        } else {
            int minute = time / 60;
            if (minute < 60) {
                retStr = "00" + ":" + unitFormat(minute);
            } else {
                int hour = minute / 60;
                minute = minute % 60;
                retStr = unitFormat(hour) + ":" + unitFormat(minute);
            }
        }
        return retStr;
    }

    /**
     * 获取当前时间。
     *
     * @return 当前时间
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static long getCurrentTime() {
        return System.currentTimeMillis();
    }

    /**
     * 时间的差值。
     *
     * @param next  第二个时间
     * @param first 第一个时间
     * @return 时间的差值
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static long getTimeMinus(long next, long first) {
        // 兼容参数值为0的情况
        if (first <= 0L || next <= 0L) {
            return 0L;
        }

        if (next < first) {
            return 0L;
        }

        return next - first;
    }

    public static String toMonthDay(Date date) {
        return format(date, "MM月dd日");
    }

    private static int getLY(String data) {
        int leapYear = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        int birthYear = Integer.parseInt(data.substring(0, 4)); // 获取出生日期，解析为Date类型
        int currYear = Integer.parseInt(format.format(new Date())); // 获取当前日期
        for (int year = birthYear; year <= currYear; year++) {
            if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
                leapYear++; // 从出生年到当前年，只有是闰年就+1
            }
        }
        return leapYear;
    }

    public static int getAge(String data) {
        try {
            long leapYear = (long) getLY(data);// 其实会自动转成int
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date birthDate = format.parse(data);
            Date currDate = format.parse(format.format(new Date()));
            return (int) (((currDate.getTime() - birthDate.getTime()) / (24 * 60 * 60 * 1000) - leapYear) / 365);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int getCountdown(String targetDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(targetDate));
            long time1 = cal.getTimeInMillis();

            cal.setTime(new Date());
            long time2 = cal.getTimeInMillis();

            long between_days = (time1 - time2) / (1000 * 3600 * 24);
            return (Integer.parseInt(String.valueOf(between_days)) + 1);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 得到年龄
     *
     * @param targetDate
     * @return
     */
    public static int getMyAge(String targetDate) {
        //设置默认年龄为20
        int age = 20;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(targetDate));
            long time1 = cal.getTimeInMillis();
            age = getAge(new Date(time1));
        } catch (ParseException e) {
            return age;
        }

        return age;
    }

    public static int getAge(Date dateOfBirth) {
        int age = 0;
        Calendar born = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        if (dateOfBirth != null) {
            now.setTime(new Date());
            born.setTime(dateOfBirth);
            age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
            if (now.get(Calendar.DAY_OF_YEAR) < born.get(Calendar.DAY_OF_YEAR)) {
                age -= 1;
            }
        }
        return age;
    }

    public static int getCountdown(String targetDate, String createDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(targetDate));
            long time1 = cal.getTimeInMillis();

            cal.setTime(sdf.parse(createDate));
            long time2 = cal.getTimeInMillis();

            long between_days = (time1 - time2) / (1000 * 3600 * 24);
            return (Integer.parseInt(String.valueOf(between_days)) + 1);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int getWeekOfMonth(String recordDay) {
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(DateTools.parse(recordDay, "yyyy-MM-dd"));
            return c.get(Calendar.WEEK_OF_MONTH);
        } catch (Exception e) {
            return 0;
        }
    }

    public static int getWeekOfYear(String recordDay) {
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(DateTools.parse(recordDay, "yyyy-MM-dd"));
            return c.get(Calendar.WEEK_OF_YEAR);
        } catch (Exception e) {
            return 0;
        }
    }

    public static String formatDate(String source, String pattern) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = format.parse(source);
            return toMonthDay(date).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static Date formatStringDateToDate(String source) {
        Date date;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = format.parse(source);
            return date;
        } catch (Exception e) {
            e.printStackTrace();
            return new Date();
        }
    }

    public static Date formatStringDateToDate(String source, String pattern) {
        Date date;
        try {
            SimpleDateFormat format = new SimpleDateFormat(
                    pattern);
            date = format.parse(source);
            return date;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date formatDateToDate(Date source, String pattern) {
        Date date;
        try {
            String stringDate = format(source, pattern);
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            date = format.parse(stringDate);
            return date;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String formatDate2(long source) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            return format.format(new Date(source));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getMessageTime(long timeMillis) {
        String time;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(timeMillis);
        time = dateFormat.format(date);
        long todayTime = System.currentTimeMillis();
        Date todayDate = new Date(todayTime);
        if (isSameday(todayDate, date)) {// 今天
            SimpleDateFormat format1 = new SimpleDateFormat("HH:mm");
            return format1.format(date);
        }
        if (isYesterday(todayDate, date)) {
            SimpleDateFormat format1 = new SimpleDateFormat("HH:mm");
            return "昨天" + format1.format(date);
        }
        Calendar todayCal = Calendar.getInstance();
        todayCal.setTime(todayDate);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date);
        if (isSameyear(todayCal, cal2)) {
            return toMonthAndDay(date);
        } else {
            if (todayCal.get(Calendar.YEAR) > cal2.get(Calendar.YEAR)) {
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                return format1.format(date);
            } else {
                return time;
            }
        }
    }

    /**
     * 时间转换成long
     *
     * @param date
     * @param format
     * @return
     */
    public static long convert2long(String date, String format) {
        String DATE_FORMAT = "yyyy-MM-dd";
        try {
            if (!date.isEmpty()) {
                if (format.isEmpty()) {
                    format = DATE_FORMAT;
                }
                SimpleDateFormat sf = new SimpleDateFormat(format);
                return sf.parse(date).getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    /**
     * 转换时间，格式为YY-MM-DD HH:mm:ss。 a)服务器日期与本地日期相同，展示服务器时间： HH：MM b)晚于本地日期一天：昨天 HH：MM c)超过一天展示：MM-DD d)超过一年展示： YY-MM-DD
     *
     * @param time 时间
     * @return 转换之后的时间
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String convert(String time) {
        String convertedTime = "";

        if (time.isEmpty()) {
            return convertedTime;
        }

        Calendar now = Calendar.getInstance();

        Calendar calendar = Calendar.getInstance();
        Date date = DateTools.parse(time, DEFAULT_DATE_PATTERN2);

        if (null == date) {
            return convertedTime;
        }

        calendar.setTime(date);

        if (DateTools.isSameday(now, calendar)) {
            convertedTime = format(calendar.getTime(), TEMP_HOUR_AND_MINUTE);
        } else if (DateTools.isYesterday(now, calendar)) {
            convertedTime = format(calendar.getTime(), "昨天" + TEMP_HOUR_AND_MINUTE);
        } else if (DateTools.isSameyear(now, calendar)) {
            convertedTime = format(calendar.getTime(), TEMP_MONTH_DAY2);
        } else {
            convertedTime = format(calendar.getTime(), DEFAULT_YEAR_MONTH_DAY);
        }

        return convertedTime;
    }

    /**
     * 过x月的时间
     *
     * @param month
     * @return
     */
    public static String getMonthAfter(int month) {
        SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATE_PATTERN2);
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, month);
        Date m3 = c.getTime();
        return format.format(m3);
    }

    public static String formatStrDate(String strDate, String pattern) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            Date date = format.parse(strDate);
            strDate = format(date, pattern);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }

    /**
     * 根据年 月 获取对应的月份 天数
     */
    public static int getDaysByYearMonth(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 获取出入日期的年份
     *
     * @param date
     * @return
     */
    public static String getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        return String.valueOf(year);
    }

    public static int getYearByInt(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获取出入日期的月份
     *
     * @param date
     * @return
     */
    public static String getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH) + 1;
        if (month >= 10) {
            return String.valueOf(month);
        } else {
            return "0" + month;
        }
    }

    public static int getMonthByInt(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取出入日期的日
     *
     * @param date
     * @return
     */
    public static String getDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        if (day >= 10) {
            return String.valueOf(day);
        } else {
            return "0" + day;
        }
    }

    public static int getDayByInt(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取出入日期的时
     *
     * @param date
     * @return
     */
    public static int getHour(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取当天的第二天
     */
    public static Date getTomorrow(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, 1);
        Date tomorrow = c.getTime();//end_date后一天
        return tomorrow;

    }

    /**
     * 获取当天的前一天天
     */
    public static Date getYesterday(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, -1);
        Date yesterday = c.getTime();//end_date后一天
        return yesterday;

    }

    /**
     * 计算小时数（2个long相减）
     *
     * @param endDate
     * @param nowDate
     * @return
     */
    public static Long getTwoLong(Long endDate, Long nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        long diff = endDate - nowDate;
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return hour;
    }

}
