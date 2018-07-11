package io.itit.androidlibrary.utils;

import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

public class FileUtils extends cn.trinea.android.common.util.FileUtils{
    private FileUtils() {
    }
    /**
     * 将文件转换成Base64字符串
     *
     * @return
     */
    public static String readFileAsBase64(String path) {

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
}
