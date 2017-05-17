package com.aaagame.proframework.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

/**
 * 数据存储类
 */
public class SpUtils {
    /**
     * Preferences记录名称
     */
    private static final String NAME = "hengzhiyuan";

    public static void setStr(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getStr(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }

    public static void setInt(Context context, String key, int value) {
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static int getInt(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return preferences.getInt(key, 0);
    }

    public static void setFloat(Context context, String key, Float value) {
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    public static Float getFloat(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return preferences.getFloat(key, 0);
    }

    public static void setLong(Context context, String key, long value) {
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public static long getLong(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return preferences.getLong(key, 0L);
    }


    public static void setBool(Context context, String key, boolean value) {
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getBool(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }

    /**
     * 获取登录状态
     *
     * @return
     */
    public static boolean isLogin(Context myActivity) {
        if (TextUtils.isEmpty(SpUtils.getStr(myActivity, Contants.USER_INFO))) {
            return false;
        }
        return true;
    }
    /**
     * 获取用户信息
     *
     * @return
     */
//    public static UserBean getUserInfo(Activity myActivity) {
//        UserBean userBean = null;
//        if (!TextUtils.isEmpty(SpUtils.getStr(myActivity, Contants.USER_INFO))) {
//            userBean = new UserBean();
//            userBean = new Gson().fromJson(SpUtils.getStr(myActivity, Contants.USER_INFO), UserBean.class);
//        }
//        return userBean;
//    }

    /**
     * 获取UserToken
     *
     * @return
     */
//    public static String getUserToken(Context myActivity) {
//        UserBean userBean = null;
//        if (!TextUtils.isEmpty(SpUtils.getStr(myActivity, Contants.USER_INFO))) {
//            userBean = new UserBean();
//            userBean = new Gson().fromJson(SpUtils.getStr(myActivity, Contants.USER_INFO), UserBean.class);
//        }
//        return userBean.getUserToken();
//    }

}
