package com.aaagame.proframework.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class AACamera {
    // 照片大小
    public static final int qualityNum = 90;
    public static final int photoWidth = 520;
    public static final int photoSmallHeight = 325;

    public static void saveFileTest(String str) {
        String filePath = null;
        boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (hasSDCard) {
            filePath = Environment.getExternalStorageDirectory().toString() + File.separator
                    + "message_2020_xzqhmodel.txt";
        } else
            filePath = Environment.getDownloadCacheDirectory().toString() + File.separator
                    + "message_2020_xzqhmodel.txt";

        try {
            File file = new File(filePath);
            if (!file.exists()) {
                File dir = new File(file.getParent());
                dir.mkdirs();
                file.createNewFile();
            }
            FileOutputStream outStream = new FileOutputStream(file);
            outStream.write(str.getBytes());
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final int Req_Camera = 5;

    /**
     * 请求拍照权限
     *
     * @param myActivity
     * @return
     */
    public static boolean checkCameraPer(Activity myActivity) {
        try {
            if (ContextCompat.checkSelfPermission(myActivity,
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                // 申请WRITE_EXTERNAL_STORAGE权限
                ActivityCompat.requestPermissions(myActivity, new String[]{Manifest.permission.CAMERA}, Req_Camera);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 照相功能
     */
    public static void cameraMethod(Activity activity, int RESULT_CAPTURE_IMAGE, String imgPath) {
        if (checkCameraPer(activity)) {
            try {
                SpUtils.setStr(activity, imgPath, "");
                Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File out = new File(imgPath);
                Uri uri = Uri.fromFile(out);
                imageCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                imageCaptureIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                activity.startActivityForResult(imageCaptureIntent, RESULT_CAPTURE_IMAGE);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    // 缩放照片（通过文件路径）
    public static void zoomCutPhoto(String strImgPath, Activity context) {
        SpUtils.getStr(context, strImgPath);
        int twidth = photoWidth;
        int quality = qualityNum;
        File f = new File(strImgPath);
        if (!f.exists()) {
            if (context != null) {
                Toast.makeText(context, "未能获取照片，请检查内存卡是否插好", Toast.LENGTH_LONG).show();
            }
        } else {
            try {
                int qualityNum = quality;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(strImgPath, options);
                if (twidth == (options.outWidth < options.outHeight ? options.outWidth : options.outHeight)
                        && f.length() / 1024 < 200) {
                    return;
                }
                float iss = 1;
                try {
                    iss = options.outHeight < options.outWidth ? (float) options.outHeight / (float) twidth
                            : (float) options.outWidth / (float) twidth;
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                if (iss < 1) {
                    iss = 1;
                }
                options.inSampleSize = (int) iss;
                options.inJustDecodeBounds = false;
                Bitmap bitmap = BitmapFactory.decodeFile(strImgPath, options);
                int bmpWidth = bitmap.getWidth();
                int bmpHeight = bitmap.getHeight();
                float scaleWidth = 0;
                if (bmpWidth > bmpHeight) {
                    scaleWidth = (float) twidth / bmpHeight;
                } else {
                    scaleWidth = (float) twidth / bmpWidth;
                }
                int degree = readPictureDegree(strImgPath);
                Matrix matrix = new Matrix();
                matrix.postScale(scaleWidth, scaleWidth);
                if (degree != 0) {
                    matrix.postRotate(degree);
                }
                Bitmap resizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, bmpWidth, bmpHeight, matrix, false);
                File file = new File(strImgPath);
                if (file.exists()) {
                    file.delete();
                }
                FileOutputStream out = new FileOutputStream(file);
                if (resizeBitmap.compress(Bitmap.CompressFormat.JPEG, qualityNum, out)) {
                    out.flush();
                    out.close();
                }
                try {
                    bitmap.recycle();
                    resizeBitmap.recycle();
                    bitmap = null;
                    resizeBitmap = null;
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    // 缩放照片（通过文件路径）
    public static void zoomPhoto_Mosaic(String strImgPath, Activity context) {
        SpUtils.getStr(context, strImgPath);
        int twidth = 1080;
        int quality = 90;
        File f = new File(strImgPath);
        if (!f.exists()) {
            if (context != null) {
                Toast.makeText(context, "未能获取照片，请检查内存卡是否插好", Toast.LENGTH_LONG).show();
            }
        } else {
            try {
                int qualityNum = quality;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(strImgPath, options);
                if (twidth == (options.outWidth < options.outHeight ? options.outWidth : options.outHeight)
                        && f.length() / 1024 < 200) {
                    return;
                }
                float iss = 1;
                try {
                    iss = options.outHeight < options.outWidth ? (float) options.outHeight / (float) twidth
                            : (float) options.outWidth / (float) twidth;
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                if (iss < 1) {
                    iss = 1;
                }
                options.inSampleSize = (int) iss;
                options.inJustDecodeBounds = false;
                Bitmap bitmap = BitmapFactory.decodeFile(strImgPath, options);
                int bmpWidth = bitmap.getWidth();
                int bmpHeight = bitmap.getHeight();
                float scaleWidth = 0;
                if (bmpWidth > bmpHeight) {
                    scaleWidth = (float) twidth / bmpHeight;
                } else {
                    scaleWidth = (float) twidth / bmpWidth;
                }
                int degree = readPictureDegree(strImgPath);
                Matrix matrix = new Matrix();
                matrix.postScale(scaleWidth, scaleWidth);
                if (degree != 0) {
                    matrix.postRotate(degree);
                }
                Bitmap resizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, bmpWidth, bmpHeight, matrix, false);
                // resizeBitmap = watermarkBitmap(resizeBitmap,
                // AADate.getTime());
                File file = new File(strImgPath);
                if (file.exists()) {
                    file.delete();
                }
                FileOutputStream out = new FileOutputStream(file);
                if (resizeBitmap.compress(Bitmap.CompressFormat.JPEG, qualityNum, out)) {
                    out.flush();
                    out.close();
                }
                try {
                    bitmap.recycle();
                    resizeBitmap.recycle();
                    bitmap = null;
                    resizeBitmap = null;
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    // 缩放照片（通过文件路径）
    public static void zoomPhoto(String strImgPath, Activity context) {
        SpUtils.getStr(context, strImgPath);
        int twidth = photoWidth;
        int quality = qualityNum;
        File f = new File(strImgPath);
        if (!f.exists()) {
            if (context != null) {
                Toast.makeText(context, "未能获取照片，请检查内存卡是否插好", Toast.LENGTH_LONG).show();
            }
        } else {
            try {
                int qualityNum = quality;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(strImgPath, options);
                if (twidth == (options.outWidth < options.outHeight ? options.outWidth : options.outHeight)
                        && f.length() / 1024 < 200) {
                    return;
                }
                float iss = 1;
                try {
                    iss = options.outHeight < options.outWidth ? (float) options.outHeight / (float) twidth
                            : (float) options.outWidth / (float) twidth;
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                if (iss < 1) {
                    iss = 1;
                }
                options.inSampleSize = (int) iss;
                options.inJustDecodeBounds = false;
                Bitmap bitmap = BitmapFactory.decodeFile(strImgPath, options);
                int bmpWidth = bitmap.getWidth();
                int bmpHeight = bitmap.getHeight();
                float scaleWidth = 0;
                if (bmpWidth > bmpHeight) {
                    scaleWidth = (float) twidth / bmpHeight;
                } else {
                    scaleWidth = (float) twidth / bmpWidth;
                }
                int degree = readPictureDegree(strImgPath);
                Matrix matrix = new Matrix();
                matrix.postScale(scaleWidth, scaleWidth);
                if (degree != 0) {
                    matrix.postRotate(degree);
                }
                Bitmap resizeBitmap = Bitmap.createBitmap(bitmap, 0, 0, bmpWidth, bmpHeight, matrix, false);
                // resizeBitmap = watermarkBitmap(resizeBitmap,
                // AADate.getTime());
                File file = new File(strImgPath);
                if (file.exists()) {
                    file.delete();
                }
                FileOutputStream out = new FileOutputStream(file);
                if (resizeBitmap.compress(Bitmap.CompressFormat.JPEG, qualityNum, out)) {
                    out.flush();
                    out.close();
                }
                try {
                    bitmap.recycle();
                    resizeBitmap.recycle();
                    bitmap = null;
                    resizeBitmap = null;
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static Bitmap watermarkBitmapTT(Bitmap src, String title) {
        if (src == null) {
            return null;
        }
        int w = src.getWidth();
        int h = src.getHeight();
        // 需要处理图片太大造成的内存超过的问题,这里我的图片很小所以不写相应代码了
        Bitmap newb = Bitmap.createBitmap(w, h, Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
        Canvas cv = new Canvas(newb);
        cv.drawBitmap(src, 0, 0, null);// 在 0，0坐标开始画入src
        if (title != null) {
            String familyName = "宋体";
            Typeface font = Typeface.create(familyName, Typeface.NORMAL);
            TextPaint textPaint = new TextPaint();
            textPaint.setColor(Color.RED);
            textPaint.setTypeface(font);
            textPaint.setTextSize(22);
            // 这里是自动换行的
            StaticLayout layout = new StaticLayout(title, textPaint, w, Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
            layout.draw(cv);
        }
        cv.save(Canvas.ALL_SAVE_FLAG);// 保存
        cv.restore();// 存储
        return newb;
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    // 从文件获取二进制
    public static byte[] readImage(String imagePath, Activity activity) {
        try {
            zoomPhoto(imagePath, activity);
            File file = new File(imagePath);
            FileInputStream stream = new FileInputStream(file);
            ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = stream.read(b)) != -1)
                out.write(b, 0, n);
            stream.close();
            out.close();
            b = null;
            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    /**
     * bitmap存到本地
     *
     * @param bitmap
     * @param path
     */
    public static void saveBitmapToFile(Bitmap bitmap, String path) {
        try {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
                out.flush();
                out.close();
            }
            bitmap.recycle();
            bitmap = null;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 请求存储权限，在华为等6.0手机适配
     *
     * @param activity
     */
    public static void checkStoragePer(Activity activity) {
        try {
            final int REQUEST_EXTERNAL_STORAGE = 1;
            String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE};
            // Check if we have write permission
            int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // We don't have permission so prompt the user
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
}
