package com.aaagame.proframework.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.Log;

/**
 * 将图片转化为二进制
 * @author nsz
 * 2015年1月30日
 */
public class T_PicFromPrintUtils {
     
     
    /*************************************************************************
     * 我们的热敏打印机是RP-POS80S或RP-POS80P或RP-POS80CS或RP-POS80CP打印机
     * 360*360的图片，8个字节（8个像素点）是一个二进制，将二进制转化为十进制数值
     * y轴：24个像素点为一组，即360就是15组（0-14）
     * x轴：360个像素点（0-359）
     * 里面的每一组（24*360），每8个像素点为一个二进制，（每组有3个，3*8=24）
     **************************************************************************/
    /**
     * 把一张Bitmap图片转化为打印机可以打印的bit(将图片压缩为360*360)
     * 效率很高（相对于下面）
     * @param bit
     * @return
     */
    public static byte[] draw2PxPoint(Bitmap bit) {    
        byte[] data = new byte[16290];
        int k = 0;
        for (int j = 0; j < 15; j++) {
            data[k++] = 0x1B;
            data[k++] = 0x2A;
            data[k++] = 33; // m=33时，选择24点双密度打印，分辨率达到200DPI。
            data[k++] = 0x68;
            data[k++] = 0x01;
            for (int i = 0; i < 360; i++) {
                for (int m = 0; m < 3; m++) {
                    for (int n = 0; n < 8; n++) {
                        byte b = px2Byte(i, j * 24 + m * 8 + n, bit);
                        data[k] += data[k] + b;
                    }
                    k++;
                }
            }
            data[k++] = 10;
        }
        return data;
    }
     
    /**
     * 把一张Bitmap图片转化为打印机可以打印的bit
     * @param bit
     * @return
     */
    public static byte[] pic2PxPoint(Bitmap bit){
        long start = System.currentTimeMillis();
        byte[] data = new byte[16290];
        int k = 0;
        for (int i = 0; i < 15; i++) {
            data[k++] = 0x1B;
            data[k++] = 0x2A;
            data[k++] = 33; // m=33时，选择24点双密度打印，分辨率达到200DPI。
            data[k++] = 0x68;
            data[k++] = 0x01;
            for (int x = 0; x < 360; x++) {
                for (int m = 0; m < 3; m++) {
                    byte[]  by = new byte[8];
                    for (int n = 0; n < 8; n++) {
                        byte b = px2Byte(x, i * 24 + m * 8 +7-n, bit);
                        by[n] = b;
                    }
                    data[k] = (byte) changePointPx1(by);
                    k++;
                }
            }
            data[k++] = 10;
        }
        long end = System.currentTimeMillis();
        long str = end - start;
        Log.i("TAG", "str:" + str);
        return data;
    }
     
    /**
     * 图片二值化，黑色是1，白色是0
     * @param x  横坐标
     * @param y     纵坐标           
     * @param bit 位图
     * @return
     */
    public static byte px2Byte(int x, int y, Bitmap bit) {
        byte b;
        int pixel = bit.getPixel(x, y);
        int red = (pixel & 0x00ff0000) >> 16; // 取高两位
        int green = (pixel & 0x0000ff00) >> 8; // 取中两位
        int blue = pixel & 0x000000ff; // 取低两位
        int gray = RGB2Gray(red, green, blue);
        if ( gray < 128 ){
            b = 1;
        } else {
            b = 0;
        }
        return b;
    }
     
    /**
     * 图片灰度的转化
     * @param r  
     * @param g
     * @param b
     * @return
     */
    private static int RGB2Gray(int r, int g, int b){
        int gray = (int) (0.29900 * r + 0.58700 * g + 0.11400 * b);  //灰度转化公式
        return  gray;
    }
     
    /**
     * 对图片进行压缩（去除透明度）
     * @param bitmapOrg
     */
    public static Bitmap compressPic(Bitmap bitmapOrg) {
        // 获取这个图片的宽和高
        int width = bitmapOrg.getWidth();
        int height = bitmapOrg.getHeight();
        // 定义预转换成的图片的宽度和高度
        int newWidth = 360;
        int newHeight = 360;
        Bitmap targetBmp = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);  
        Canvas targetCanvas = new Canvas(targetBmp);
        targetCanvas.drawColor(0xffffffff);
        targetCanvas.drawBitmap(bitmapOrg, new Rect(0, 0, width, height), new Rect(0, 0, newWidth, newHeight), null);
        return targetBmp;
    }
     
     
    /**
     * 对图片进行压缩(不去除透明度)
     * @param bitmapOrg
     */
    public static Bitmap compressBitmap(Bitmap bitmapOrg) {
        // 加载需要操作的图片，这里是一张图片
//        Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(),R.drawable.alipay);
        // 获取这个图片的宽和高
        int width = bitmapOrg.getWidth();
        int height = bitmapOrg.getHeight();
        // 定义预转换成的图片的宽度和高度
        int newWidth = 360;
        int newHeight = 360;
        // 计算缩放率，新尺寸除原始尺寸
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0, width,height, matrix, true);
        // 将上面创建的Bitmap转换成Drawable对象，使得其可以使用在ImageView, ImageButton中
//        BitmapDrawable bmd = new BitmapDrawable(resizedBitmap);
        return resizedBitmap;
    }
     
    /**
     * 将[1,0,0,1,0,0,0,1]这样的二进制转为化十进制的数值（效率更高）
     * @param arry
     * @return
     */
    public static int changePointPx1(byte[] arry){
        int v = 0;
        for (int j = 0; j <arry.length; j++) {
            if( arry[j] == 1) {
                v = v | 1 << j;
            }
        }
        return v;
    }
     
    /**
     * 将[1,0,0,1,0,0,0,1]这样的二进制转为化十进制的数值
     * @param arry
     * @return
     */
    public byte changePointPx(byte[] arry){
        byte v = 0;
        for (int i = 0; i < 8; i++) {
            v += v + arry[i];
        }
        return v;
    }
     
    /**
     * 得到位图的某个点的像素值
     * @param bitmap
     * @return
     */
    public byte[] getPicPx(Bitmap bitmap){
        int[] pixels = new int[bitmap.getWidth() * bitmap.getHeight()];// 保存所有的像素的数组，图片宽×高
        bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        for (int i = 0; i < pixels.length; i++) {
            int clr = pixels[i];
            int red = (clr & 0x00ff0000) >> 16; // 取高两位
        int green = (clr & 0x0000ff00) >> 8; // 取中两位
                int blue = clr & 0x000000ff; // 取低两位
                System.out.println("r=" + red + ",g=" + green + ",b=" + blue);
        }
        return null;
    }
     
}