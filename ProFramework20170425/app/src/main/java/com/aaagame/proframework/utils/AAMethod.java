package com.aaagame.proframework.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class AAMethod {
    /**
     * 判断当前线程是否是本地程序启动的，而不是第三方启动的
     *
     * @param context
     * @return
     */
    public static boolean isfromMyProcess(Context context) {
        String processName = null;
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = context.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i
                    .next());
            try {
                if (info.pid == android.os.Process.myPid()) {
                    CharSequence c = pm.getApplicationLabel(pm
                            .getApplicationInfo(info.processName,
                                    PackageManager.GET_META_DATA));
                    // Log.d("Process", "Id: "+ info.pid +" ProcessName: "+
                    // info.processName +"  Label: "+c.toString());
                    // processName = c.toString();
                    processName = info.processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        if (processName == null || processName.equals(context.getPackageName())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 设置手机保持唤醒时间,sec单位秒
     *
     * @param context
     * @param sec
     */
    public static void keepWake(Context context, int sec) {
        try {
            PowerManager pm = (PowerManager) context
                    .getSystemService(Context.POWER_SERVICE);
            WakeLock wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    context.getClass().getName());
            wakeLock.acquire(1000 * sec);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 清空通知栏通知
     *
     * @param context
     */
    public static void cancleNotifi(Context context) {
        try {
            NotificationManager ntmana = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            ntmana.cancelAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static boolean deleteFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
            return true;
        }
        return false;
    }

    /**
     * 刷新图库照片
     *
     * @param context
     * @param filenames
     */
    public static void updateGallery(Context context, String[] filenames)// filename是我们的文件全名，包括后缀哦
    {
        try {
            MediaScannerConnection.scanFile(context, filenames, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                            System.out.println("扫描成功---------------");
                        }
                    });
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 复制文件
     *
     * @param s
     * @param t
     */
    public static void fileChannelCopy(File s, File t) {

        FileInputStream fi = null;

        FileOutputStream fo = null;

        FileChannel in = null;

        FileChannel out = null;

        try {

            fi = new FileInputStream(s);

            fo = new FileOutputStream(t);

            in = fi.getChannel();// 得到对应的文件通道

            out = fo.getChannel();// 得到对应的文件通道

            in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                fi.close();

                in.close();

                fo.close();

                out.close();

            } catch (IOException e) {

                e.printStackTrace();

            }

        }

    }

    /**
     * 获取软件版本名称
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            try {
                PackageInfo packinfo = pm.getPackageInfo(
                        context.getPackageName(), 0);
                String version = packinfo.versionName;
                return version;
            } catch (NameNotFoundException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取软件版本号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            try {
                PackageInfo packinfo = pm.getPackageInfo(
                        context.getPackageName(), 0);
                int version = packinfo.versionCode;
                return version;
            } catch (NameNotFoundException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 获取屏幕宽度
     *
     * @param activity
     * @return
     */
    public static int getScreenWidth(Activity activity) {
        WindowManager manager = activity.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @param activity
     * @return
     */
    public static int getScreenHeight(Activity activity) {
        WindowManager manager = activity.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 获取图片名称获取图片的资源id的方法
     *
     * @param imageName
     * @return
     */
    public static int getResource(Context context, String imageName) {
        int resId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        return resId;
    }

    /**
     * 读取Assert文件夹下txt文本并变为String的方法
     *
     * @param context
     * @param strAssertFileName
     * @return
     */
    public static String readAssertResource(Context context, String strAssertFileName) {
        AssetManager assetManager = context.getAssets();
        String strResponse = "";
        try {
            InputStream ims = assetManager.open(strAssertFileName);
            strResponse = getStringFromInputStream(ims);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strResponse;
    }

    // 读取Assert文件夹下txt文本并变为String的方法
    private static String getStringFromInputStream(InputStream a_is) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(a_is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
        return sb.toString();
    }

    /**
     * 文件是否存在
     *
     * @param path
     * @return
     */
    public static boolean isFileExist(String path) {
        try {
            return new File(path).exists() && new File(path).length() > 0;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    public static String getCloudPhotoPath() {
        return "/photos/" + UUID.randomUUID().toString() + ".JPG";
    }

    /**
     * 跳转到QQ聊天界面
     *
     * @param activity
     * @param qq
     */
    public static void startQQChat(Activity activity, String qq) {
        try {
            String url = "mqqwpa://im/chat?chat_type=wpa&uin=" + qq;
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } catch (Exception e) {
            AAToast.toastShow(activity, "请安装QQ客户端");
            e.printStackTrace();
        }
    }

    /**
     * 选择的图片另存一份
     *
     * @param path
     * @param name
     * @param bitmap
     * @return
     */
    public static String saveChoiceImage(String path, String name, Bitmap bitmap) {
        FileOutputStream fout = null;
        File fpath = new File(path);
        if (!fpath.exists()) {
            fpath.mkdirs();
        }
        try {
            fout = new FileOutputStream(path + File.separator + name);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fout);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fout != null) {
                    fout.flush();
                    fout.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return path + File.separator + name;
    }


    /**
     * 根据文件后缀名获得对应的MIME类型。
     *
     * @param file
     */
    public static String getMIMEType(File file) {

        String type = "*/*";
        String fName = file.getName();
        // 获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }
        /* 获取文件的后缀名 */
        String end = fName.substring(dotIndex, fName.length()).toLowerCase();
        if (end == "")
            return type;
        // 在MIME和文件类型的匹配表中找到对应的MIME类型。
        for (int i = 0; i < MIME_MapTable.length; i++) { // MIME_MapTable??在这里你一定有疑问，这个MIME_MapTable是什么？
            if (end.equals(MIME_MapTable[i][0])) {
                type = MIME_MapTable[i][1];
                break;
            }
        }
        return type;
    }

    private static final String[][] MIME_MapTable = {
            // {后缀名，MIME类型}
            {".jpg", "image/jpeg"},
            {".txt", "text/plain"},
            {".jpeg", "image/jpeg"},
            {".pdf", "application/pdf"},
            {".png", "image/png"},
            {".doc", "application/msword"},
            {".docx",
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {".pps", "application/vnd.ms-powerpoint"},
            {".ppt", "application/vnd.ms-powerpoint"},
            {".3gp", "video/3gpp"},
            {".apk", "application/vnd.android.package-archive"},
            {".asf", "video/x-ms-asf"},
            {".avi", "video/x-msvideo"},
            {".bin", "application/octet-stream"},
            {".bmp", "image/bmp"},
            {".c", "text/plain"},
            {".class", "application/octet-stream"},
            {".conf", "text/plain"},
            {".cpp", "text/plain"},
            {".xls", "application/vnd.ms-excel"},
            {".xlsx",
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {".exe", "application/octet-stream"},
            {".gif", "image/gif"},
            {".gtar", "application/x-gtar"},
            {".gz", "application/x-gzip"},
            {".h", "text/plain"},
            {".htm", "text/html"},
            {".html", "text/html"},
            {".jar", "application/java-archive"},
            {".java", "text/plain"},
            {".js", "application/x-javascript"},
            {".log", "text/plain"},
            {".m3u", "audio/x-mpegurl"},
            {".m4a", "audio/mp4a-latm"},
            {".m4b", "audio/mp4a-latm"},
            {".m4p", "audio/mp4a-latm"},
            {".m4u", "video/vnd.mpegurl"},
            {".m4v", "video/x-m4v"},
            {".mov", "video/quicktime"},
            {".mp2", "audio/x-mpeg"},
            {".mp3", "audio/x-mpeg"},
            {".mp4", "video/mp4"},
            {".mpc", "application/vnd.mpohun.certificate"},
            {".mpe", "video/mpeg"},
            {".mpeg", "video/mpeg"},
            {".mpg", "video/mpeg"},
            {".mpg4", "video/mp4"},
            {".mpga", "audio/mpeg"},
            {".msg", "application/vnd.ms-outlook"},
            {".ogg", "audio/ogg"},
            {".pptx",
                    "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
            {".prop", "text/plain"}, {".rc", "text/plain"},
            {".rmvb", "audio/x-pn-realaudio"}, {".rtf", "application/rtf"},
            {".sh", "text/plain"}, {".tar", "application/x-tar"},
            {".tgz", "application/x-compressed"}, {".wav", "audio/x-wav"},
            {".wma", "audio/x-ms-wma"}, {".wmv", "audio/x-ms-wmv"},
            {".wps", "application/vnd.ms-works"}, {".xml", "text/plain"},
            {".z", "application/x-compress"},
            {".zip", "application/x-zip-compressed"}, {"", "*/*"}};
}
