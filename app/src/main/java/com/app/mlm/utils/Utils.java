package com.app.mlm.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.v4.app.ActivityCompat;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

/**
 * @version : 1.0.0
 * @package : com.app.mlm.utils
 * @fileName : Utils
 * @date : 2018/12/29  13:25
 * @describe : 其他工具类
 */
public class Utils {

    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime;

    /**
     * 检查权限
     *
     * @param context
     * @param permission
     * @return
     */
    public static boolean checkPermission(Context context, String permission) {
        return ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 传入一个字符串，生成一个二维码
     *
     * @param str
     * @return
     */
    public static Bitmap encodeAsBitmap(String str, int width, int height) {
        Bitmap bitmap = null;
        BitMatrix result;
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try { //可以指定生成二维码Bitmap的大小
            result = multiFormatWriter.encode(str, BarcodeFormat.QR_CODE, width, height);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.createBitmap(result);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException iae) {
            return null;
        }
        return bitmap;
    }

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }

}
