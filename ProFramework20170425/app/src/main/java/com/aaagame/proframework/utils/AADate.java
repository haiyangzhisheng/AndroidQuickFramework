package com.aaagame.proframework.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AADate {
    //日期格式
    public static final String ymdHms = "yyyy-MM-dd HH:mm:ss";
    public static final String ymd_zh_Hm = "yyyy年MM月dd日 HH:mm";
    public static final String ymdHm = "yyyy-MM-dd HH:mm";
    public static final String ymd = "yyyy-MM-dd";
    public static final String ymdhms_name = "yyyyMMddHHmm";
    public static final String ym = "yyyy-MM";
    public static final String ym_zh = "yy年M月";
    //E代表星期
    public static final String ymd_E = "yyyy年M月d日 E";
    public static final String md = "MM-dd";
    public static final String hm = "HH:mm";
    public static final String ymd_point = "yyyy.MM.dd";
    public static final String ymdhm_point = "yyyy.MM.dd hh:mm";

    /**
     * 获取当前时间
     *
     * @param myform
     * @return
     */
    public static String getCurrentTime(String myform) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(myform);
        return sDateFormat.format(new java.util.Date());
    }

    /**
     * 获取格式化日期
     *
     * @param myform
     * @param date
     * @return
     */
    public static String getFormatTime(String myform, Date date) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(myform);
        return sDateFormat.format(date);
    }

    /**
     * 时间格式转换
     *
     * @param sourceForm
     * @param desForm
     * @param mytime
     * @return
     */
    public static String getTimeFromConvert(String sourceForm, String desForm, String mytime) {
        SimpleDateFormat sourceFormat = new SimpleDateFormat(sourceForm);
        SimpleDateFormat desFormat = new SimpleDateFormat(desForm);
        try {
            return desFormat.format(sourceFormat.parse(mytime));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将指定格式的日期转换为Date类型
     *
     * @param myform
     * @param mytime
     * @return
     */
    public static Date getDateFromStr(String myform, String mytime) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(myform);
            Date date = sdf.parse(mytime);
            return date;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取几天后或几天前的时间，参数为正整数或负整数
     *
     * @param myform
     * @param days
     * @return
     */
    public static String getDateAddDay(String myform, int days) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(myform);
        calendar.add(Calendar.DAY_OF_YEAR, days);
        Date date = calendar.getTime();
        return sdf.format(date);
    }

    /**
     * 获取几月前或后的时间，参数为正整数或负整数
     *
     * @param myform
     * @param mons
     * @return
     */
    public static String getDateAddMon(String myform, int mons) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(myform);
        calendar.add(Calendar.MONTH, mons);
        Date date = calendar.getTime();
        return sdf.format(date);
    }

    /**
     * 获取两个日期间隔几天,time1减去time2
     *
     * @param myform
     * @param time1
     * @param time2
     * @return
     */
    public static int getDateBetwDays(String myform, String time1, String time2) {
        SimpleDateFormat sdf;
        int days = 0;
        try {
            sdf = new SimpleDateFormat(myform);
            Date date1 = sdf.parse(time1);
            Date date2 = sdf.parse(time2);
            long diff = date1.getTime() - date2.getTime();
            days = (int) (diff / (1000 * 60 * 60 * 24));

        } catch (Exception e) {
            e.printStackTrace();
            return -11;
        }
        return days;
    }


    /**
     * 获取两个日期间隔几分钟,time1减去time2
     *
     * @param myform
     * @param time1
     * @param time2
     * @return
     */
    public static int getDateBetwMins(String myform, String time1, String time2) {
        SimpleDateFormat sdf;
        int mins = 0;
        try {
            sdf = new SimpleDateFormat(myform);
            Date date1 = sdf.parse(time1);
            Date date2 = sdf.parse(time2);
            long diff = date1.getTime() - date2.getTime();
            mins = (int) (diff / (1000 * 60));

        } catch (Exception e) {
            e.printStackTrace();
            return -11;
        }
        return mins;
    }

    /**
     * 获取两个日期间隔几秒,time1减去time2
     *
     * @param myform
     * @param time1
     * @param time2
     * @return
     */
    public static int getDateBetwSecs(String myform, String time1, String time2) {
        SimpleDateFormat sdf;
        int secs = 0;
        try {
            sdf = new SimpleDateFormat(myform);
            Date date1 = sdf.parse(time1);
            Date date2 = sdf.parse(time2);
            long diff = date1.getTime() - date2.getTime();
            secs = (int) (diff / 1000);

        } catch (Exception e) {
            e.printStackTrace();
            return -11;
        }
        return secs;
    }

    /**
     * 获取两个日期间隔几毫秒,time1减去time2
     *
     * @param myform
     * @param time1
     * @param time2
     * @return
     */
    public static int getDateBetwMiSecs(String myform, String time1, String time2) {
        SimpleDateFormat sdf;
        int misecs = 0;
        try {
            sdf = new SimpleDateFormat(myform);
            Date date1 = sdf.parse(time1);
            Date date2 = sdf.parse(time2);
            long diff = date1.getTime() - date2.getTime();
            misecs = (int) diff;

        } catch (Exception e) {
            e.printStackTrace();
            return -11;
        }
        return misecs;
    }

    /**
     * 获取当前日期星期数1~7
     *
     * @param myform
     * @param mytime
     * @return
     */
    public static String getWeekNum(String myform, String mytime) {
        SimpleDateFormat sdf;
        try {
            sdf = new SimpleDateFormat(myform);
            Date date = sdf.parse(myform);
            java.util.Calendar cal = java.util.Calendar.getInstance();
            cal.setTime(date);
            int w = cal.get(java.util.Calendar.DAY_OF_WEEK) - 1;
            if (w == 0)
                w = 7;
            return String.valueOf(w);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取星期 yyyy年M月d日 E
     *
     * @param myform
     * @param time
     * @return
     */
    public static String getWeekTime(String myform, String time) {
        SimpleDateFormat sdf;
        try {
            sdf = new SimpleDateFormat(myform);
            Date date = sdf.parse(time);
            sdf = new SimpleDateFormat("yyyy年M月d日 E", Locale.CHINA);
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 检查年月是否相等
     *
     * @param myform
     * @param time1
     * @param time2
     * @return
     */
    public static boolean checkYmSame(String myform, String time1, String time2) {
        SimpleDateFormat sdf;
        try {
            sdf = new SimpleDateFormat(myform);
            Date date1 = sdf.parse(time1);
            Date date2 = sdf.parse(time2);
            if (date1.getYear() == date2.getYear()
                    && date1.getMonth() == date2.getMonth()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * 将long类型日期转换为指定格式
     *
     * @param myform
     * @param mytime
     * @return
     */
    public static String getStrFroDate(String myform, long mytime) {
        try {
            Date date = new Date(mytime);
            SimpleDateFormat sdf = new SimpleDateFormat(myform);
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

    /**
     * 将String类型long格式日期转换为指定格式
     *
     * @param myform
     * @param mytime
     * @return
     */
    public static String getStrFroDate(String myform, String mytime) {
        try {
            Date date = new Date(Long.parseLong(mytime));
            SimpleDateFormat sdf = new SimpleDateFormat(myform);
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

    /**
     * 显示时间距离当前多久，包含描述
     *
     * @param lontime
     * @return
     */
    public static String getShowTimeAgo(String myform, String lontime) {
        String show = "";
        try {
            long secs = AADate.getDateBetwMiSecs(myform, getCurrentTime(myform), lontime);

            if (secs / 60l / 60l / 24l >= 1) {
                show = (int) secs / 60l / 60l / 24l + "天前";

                if ((int) secs / 60l / 60l / 24l > 3) {
                    show = AADate.getTimeFromConvert(myform, ymd, lontime);
                }
            } else {
                if (secs / 60l / 60l >= 1) {
                    show = (int) secs / 60l / 60l + "小时前";
                } else {
                    int mins = (int) secs / 60;
                    mins = (mins < 1 ? 1 : mins);
                    show = mins + "分钟前";
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return show;
    }
}
