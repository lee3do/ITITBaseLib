package io.itit.androidlibrary.utils;

import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;

public class FileUtils extends cn.trinea.android.common.util.FileUtils{
    private FileUtils() {
    }
    /**
     * 将文件转换成Base64字符串
     *
     * @return
     */
    public static String readImageAsBase64(String path) {

        try {
            FileInputStream fis = new FileInputStream(path);//转换成输入流
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = fis.read(buffer)) >= 0) {
                baos.write(buffer, 0, count);//读取输入流并写入输出字节流中
            }
            fis.close();//关闭文件输入流
            return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        } catch (Exception e) {
            return "";
        }
    }

    public static File getRealFileName(String baseDir, String absFileName) {
        String[] dirs = absFileName.split("/");
        File ret = new File(baseDir);
        String substr = null;
        if (dirs.length > 1) {
            for (int i = 0; i < dirs.length - 1; i++) {
                substr = dirs[i];
                try {
                    //substr.trim();
                    substr = new String(substr.getBytes("8859_1"), "GB2312");

                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                ret = new File(ret, substr);

            }
            // Logger.d("1ret = " + ret);
            if (!ret.exists()) ret.mkdirs();
            substr = dirs[dirs.length - 1];
            try {
                //substr.trim();
                substr = new String(substr.getBytes("8859_1"), "GB2312");
                // Logger.d("substr = " + substr);
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            ret = new File(ret, substr);
            // Log.d("upZipFile", "2ret = " + ret);
            return ret;
        } else {
            ret = new File(baseDir, absFileName);
        }

        return ret;
    }
}
