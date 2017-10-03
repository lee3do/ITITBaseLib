package io.itit.androidlibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.itit.androidlibrary.ITITApplication;


/**
 * Created by Lee_3do on 2017/9/16.
 */

public class CommonUtil {
    public static final String APPLICATION_FOLDER = "ITIT";

    public static void Call(Activity a,String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        a.startActivity(intent);
    }

    public static String md5(String key) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md5.update(key.getBytes());
        //important: use Base64.URL_SAFE flag to avoid "+" and "/"
        return new String(Base64.encode(md5.digest(), Base64.URL_SAFE));
    }

    private static float sDensity = 0;

    public static int dipToPixel( int nDip) {
        if (sDensity == 0) {
            final WindowManager wm = (WindowManager) ITITApplication.appContext
                    .getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics dm = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(dm);
            sDensity = dm.density;
        }
        return (int) (sDensity * nDip);
    }


    public static int pixelToDip( int px) {
        if (sDensity == 0) {
            final WindowManager wm = (WindowManager) ITITApplication.appContext
                    .getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics dm = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(dm);
            sDensity = dm.density;
        }
        return (int) (px/sDensity );
    }

    public static BitmapFactory.Options getBitmapOpt(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        //设置为true,表示解析Bitmap对象，该对象不占内存
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        return  options;
    }

}
