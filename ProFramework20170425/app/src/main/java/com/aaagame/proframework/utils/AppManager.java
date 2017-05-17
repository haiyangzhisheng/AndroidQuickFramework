package com.aaagame.proframework.utils;

import android.app.Activity;
import android.content.Context;

import java.util.Stack;

public class AppManager {
    private static Stack<Activity> mActivityStack;
    private static AppManager mAppManager;

    private AppManager() {

    }

    /**
     * 单一实例
     */
    public static AppManager getInstance() {
        if (mAppManager == null) {
            mAppManager = new AppManager();
        }
        return mAppManager;
    }

    /**
     * 添加Activity到堆
     */
    public void addActivity(Activity activity) {
        if (mActivityStack == null) {
            mActivityStack = new Stack<Activity>();
        }
        mActivityStack.add(activity);
    }

    /**
     * 获取栈顶Activity
     */
    public Activity getTopActivity() {
        Activity activity = mActivityStack.lastElement();
        return activity;
    }

    /**
     * 结束栈顶Activity
     */
    public void killTopActivity() {
        Activity activity = mActivityStack.lastElement();
        killActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void killActivity(Activity activity) {
        if (activity != null) {
            boolean res = mActivityStack.remove(activity);
            System.out.println("删除结果=============" + res);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void killActivity(Class<?> cls) {
        try {
            for (Activity activity : mActivityStack) {
                if (activity.getClass().equals(cls)) {
                    killActivity(activity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 结束所有Activity
     */
    public void killAllActivity() {
        for (int i = 0, size = mActivityStack.size(); i < size; i++) {
            if (null != mActivityStack.get(i)) {
                mActivityStack.get(i).finish();
            }
        }
        mActivityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            killAllActivity();
            System.exit(0);
        } catch (Exception e) {
        }
    }
}
