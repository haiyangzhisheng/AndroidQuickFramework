package com.aaagame.proframework.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AADate {
    /**
     * 获取当前时间（含时分秒）
     *
     * @return
     */
    public static String getTime() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        return sDateFormat.format(new java.util.Date());
    }

    public static String getStrTime(Date date) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        return sDateFormat.format(date);
    }

    /**
     * 获取订单时间选择
     *
     * @return
     */
    public static String getDdChoiceTime() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        return sDateFormat.format(new java.util.Date());
    }

    public static String getTimeFromDdChoiceTime(String time) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.format(sDateFormat.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前时间（含时分）yyyy-MM-dd HH:mm
     *
     * @return
     */
    public static String getMinTime() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sDateFormat.format(new java.util.Date());
    }

    /**
     * 获取当前日期（无时分秒）yyyy-MM-dd
     *
     * @return
     */
    public static String getDate() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return sDateFormat.format(new java.util.Date());
    }

    /**
     * 获取当前日期（无时分秒）yyyyMMddHHmm
     *
     * @return
     */
    public static String getDateStrName() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmm");
        return sDateFormat.format(new java.util.Date());
    }

    /**
     * 从字符串日期转为Java日期
     *
     * @param strdate
     * @return
     */
    public static Date getShotDateFromStr(String strdate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(strdate);
            return date;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从字符串日期时间转为Java日期时间yyyy-MM-dd HH:mm
     *
     * @param strdate
     * @return
     */
    public static Date getMinShotTimeFromStr(String strdate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = sdf.parse(strdate);
            return date;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取几天后或几天前的时间，参数为正整数或负整数
     *
     * @param count
     * @return
     */
    public static String getDateForward(int count) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        calendar.add(Calendar.DAY_OF_YEAR, count);
        Date date = calendar.getTime();
        return sdf.format(date);
    }

    /**
     * 获取几月前或后的时间，参数为正整数或负整数
     *
     * @param count
     * @return
     */
    public static String getDateForwardMon(int count) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        calendar.add(Calendar.MONTH, count);
        Date date = calendar.getTime();
        return sdf.format(date);
    }

    /**
     * 获取几月前或后的时间，参数为正整数或负整数
     *
     * @param count
     * @return
     */
    public static String getDateForwardMonShow(int count) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yy年M月");
        calendar.add(Calendar.MONTH, count);
        Date date = calendar.getTime();
        return sdf.format(date);
    }

    /**
     * 获取两个日期间隔几天 yyyy-MM-dd,time1减去time2
     *
     * @param time1
     * @param time2
     * @return
     */
    public static int getDateBetw(String time1, String time2) {
        SimpleDateFormat sdf;
        int days = 0;
        try {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
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
     * 获取距离现在过了几秒
     *
     * @return
     */
    public static int getTimeToNowSec(String time) {
        int sec = 0;
        try {
            sec = (int) (System.currentTimeMillis() - Long.parseLong(time));
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return sec / 1000;
    }

    /**
     * 获取两个时间间隔几分钟,返回整数,time1减去time2
     *
     * @param time1
     * @param time2
     * @return
     */
    public static int getTimeBetw(String time1, String time2) {
        SimpleDateFormat sdf;
        int mins = 0;
        try {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
     * 获取两个时间间隔毫秒数,返回整数,time1减去time2
     *
     * @param time1
     * @param time2
     * @return
     */
    public static int getTimeBetwMiSec(String time1, String time2) {
        int sec = 0;
        try {
            sec = (int) (Long.parseLong(time1) - Long.parseLong(time2));
        } catch (Exception e) {
            e.printStackTrace();
            return 10000;
        }
        return sec;
    }

    /**
     * 获取当前日期星期数1~7
     *
     * @param time
     * @return
     */
    public static String getWeekNum(String time) {
        SimpleDateFormat sdf;
        try {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(time);
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
     * 将字符串时间转换为date yyyy-MM-dd HH:mm:ss
     *
     * @param time
     * @return
     */
    public static Date getDateFromStr(String time) {
        SimpleDateFormat sdf;
        try {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(time);
            return date;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取星期 yyyy年M月d日 E
     *
     * @param time
     * @return
     */
    public static String getWeekTime(String time) {
        SimpleDateFormat sdf;
        try {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(time);
            sdf = new SimpleDateFormat("yyyy年M月d日 E", Locale.CHINA);
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isCommonMon(String time1, String time2) {
        SimpleDateFormat sdf;
        try {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
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

    public static Date getShotMonFromStr(String time) {
        SimpleDateFormat sdf;
        try {
            sdf = new SimpleDateFormat("yyyy-MM");
            Date date = sdf.parse(time);
            return date;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getShotStrMon() {
        SimpleDateFormat sdf;
        try {
            sdf = new SimpleDateFormat("yyyy-MM");
            String strdate = sdf.format(new java.util.Date());
            return strdate;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取显示日期
     *
     * @param strDate
     * @return
     */
    public static String getStrFroDate(String strDate) {
        try {
            long time = Long.parseLong(strDate);
            Date date = new Date(time);
            SimpleDateFormat sdf;
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

    /**
     * 获取显示日期
     *
     * @param strDate
     * @return
     */
    public static String getStrFroDateYmd(String strDate) {
        try {
            long time = Long.parseLong(strDate);
            Date date = new Date(time);
            SimpleDateFormat sdf;
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

    /**
     * 获取显示日期yyyy.MM.dd
     *
     * @param strDate
     * @return
     */
    public static String getPointStrFroLongData(String strDate) {
        try {
            long time = Long.parseLong(strDate);
            Date date = new Date(time);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

    /**
     * 获取显示日期yyyy.MM.dd hh:mm
     *
     * @param strDate
     * @return
     */
    public static String getPointStrFroLongDataHm(String strDate) {
        try {
            long time = Long.parseLong(strDate);
            Date date = new Date(time);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd hh:mm");
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

    /**
     * 获取显示日期yyyy-MM-dd HH:mm
     *
     * @param strDate
     * @return
     */
    public static String getStrFroDateYmdhm(String strDate) {
        try {
            long time = Long.parseLong(strDate);
            Date date = new Date(time);
            SimpleDateFormat sdf;
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

    /**
     * 获取显示日期，月日
     *
     * @param strDate
     * @return
     */
    public static String getStrFroDate_Mon_Day(String strDate) {
        long time = Long.parseLong(strDate);
        Date date = new Date(time);
        SimpleDateFormat sdf;
        try {
            sdf = new SimpleDateFormat("MM-dd");
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 获取显示日期,时分
     *
     * @param strDate
     * @return
     */
    public static String getStrFroDate_Hour_Min(String strDate) {
        long time = Long.parseLong(strDate);
        Date date = new Date(time);
        SimpleDateFormat sdf;
        try {
            sdf = new SimpleDateFormat("HH:mm");
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 显示时间距离当前多久，包含描述
     *
     * @param lontime
     * @return
     */
    public static String getShowTimeAgo(String lontime) {
        String show = "";
        try {
            long secs = AADate.getTimeToNowSec(lontime);

            if (secs / 60l / 60l / 24l >= 1) {
                show = (int) secs / 60l / 60l / 24l + "天前";

                if ((int) secs / 60l / 60l / 24l > 3) {
                    show = AADate.getStrFroDateYmd(lontime);
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
