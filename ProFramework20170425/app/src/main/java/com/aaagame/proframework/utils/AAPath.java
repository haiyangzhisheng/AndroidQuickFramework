package com.aaagame.proframework.utils;

import android.os.Environment;

import java.io.File;

public class AAPath {
    public static final String RootPathName = "HengZhiYuan";

    /**
     * 根目录
     *
     * @return
     */
    public static String getRootPath() {
        String path = Environment.getExternalStorageDirectory().getPath() + File.separator + RootPathName;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    /**
     * 拍照存储路径
     *
     * @return
     */
    public static String getPhotoPath() {
        String path = getRootPath() + File.separator + "photo";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    /**
     * 照片路径
     *
     * @return
     */
    public static String getPathPhoto(String photoName) {
        return getPhotoPath() + File.separator + photoName;
    }

    /**
     * 照片本地上传头像路径
     *
     * @return
     */
    public static String getPathTxPhoto() {
        return getPhotoPath() + File.separator + "update_tx.jpg";
    }
    /**
     * 照片本地上传头像路径
     *
     * @return
     */
    public static String getPathTxPhotoCatch() {
        return getPhotoPath() + File.separator + "update_tx_catch.jpg";
    }

    /**
     * 照片本地上传头像路径
     *
     * @return
     */
    public static String getPathTxPhotoCamera() {
        return getPhotoPath() + File.separator + "update_tx_camera.jpg";
    }

    /**
     * 照片本地营业执照路径
     *
     * @return
     */
    public static String getPathLicensePhoto() {
        return getPhotoPath() + File.separator + "license.jpg";
    }

    /**
     * 照片需求Logo路径
     *
     * @return
     */
    public static String getPathNewDemandLogoPhoto() {
        return getPhotoPath() + File.separator + "newdemandlogo.jpg";
    }

    /**
     * 照片定制Logo路径
     *
     * @return
     */
    public static String getPathCustomMadeLogoPhoto() {
        return getPhotoPath() + File.separator + "custommadelogo.jpg";
    }

    /**
     * 照片1路径
     *
     * @return
     */
    public static String getPathPhoto1() {
        return getPhotoPath() + File.separator + "photo1.jpg";
    }

    /**
     * 照片2路径
     *
     * @return
     */
    public static String getPathPhoto2() {
        return getPhotoPath() + File.separator + "photo2.jpg";
    }

    /**
     * 照片3路径
     *
     * @return
     */
    public static String getPathPhoto3() {
        return getPhotoPath() + File.separator + "photo3.jpg";
    }

    /**
     * 录音存储路径
     *
     * @return
     */
    public static String getVoicePath() {
        String path = getRootPath() + File.separator + "voice";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    /**
     * 录音路径
     *
     * @return
     */
    public static String getPathVoice(int mark) {
        return getVoicePath() + File.separator + "voice" + mark + ".mp3";
    }

    /**
     * 录音路径
     *
     * @return
     */
    public static String getPathVoice(String mark) {
        return getVoicePath() + File.separator + "voice" + mark + ".mp3";
    }

    /**
     * apk下载路径
     *
     * @return
     */
    public static String getApkPath() {
        String path = getRootPath() + File.separator + "APK";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    /**
     * 缓存文件路径
     *
     * @return
     */
    public static String getCacheFilesPath() {
        String path = getRootPath() + File.separator + "cachefiles";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    /**
     * 永久保留文件路径
     *
     * @return
     */
    public static String getUseFilesPath() {
        String path = getRootPath() + File.separator + "usefiles";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

}
