package io.itit.androidlibrary.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import cn.trinea.android.common.util.StringUtils;

public class AppUtils {

    public static final String APP_FOLDER = Environment.getExternalStorageDirectory() + "/ITIT/";

    private static final String DOWNLOAD_FOLDER_PATH_OFFICIAL = APP_FOLDER + "/downloads/";

    private static final String REC_FOLDER_PATH_OFFICIAL = APP_FOLDER + "/rec/";

    private static final String IMG_FOLDER_PATH = APP_FOLDER + "/PIC/";

    /**
     * 获取指定包名的版本号
     *
     * @param context     本应用程序上下文
     * @param packageName 你想知道版本信息的应用程序的包名
     * @return
     * @throws Exception
     */
    public static String getVersionName(Context context, String packageName) {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(packageName, 0);
            String version = packInfo.versionName;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void openApp(Context context, String appPackageName) {
        try {
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(appPackageName);
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, "应用打开失败", Toast.LENGTH_LONG).show();
        }
    }


    public static void copyAssetDirToFiles(Context context, String dirname) throws IOException {
        File dir = new File(context.getFilesDir() + "/" + dirname);
        dir.mkdir();

        AssetManager assetManager = context.getAssets();
        String[] children = assetManager.list(dirname);
        for (String child : children) {
            child = dirname + '/' + child;
            String[] grandChildren = assetManager.list(child);
            if (0 == grandChildren.length) copyAssetFileToFiles(context, child);
            else copyAssetDirToFiles(context, child);
        }
    }

    public static void copyAssetFileToFiles(Context context, String filename) throws IOException {
        InputStream is = context.getAssets().open(filename);
        byte[] buffer = new byte[is.available()];
        is.read(buffer);
        is.close();

        File of = new File(context.getFilesDir() + "/" + filename);
        if (of.exists()) {
            of.delete();
        }
        of.createNewFile();
        FileOutputStream os = new FileOutputStream(of);
        os.write(buffer);
        os.close();
    }


    /**
     * 获取下载路径
     *
     * @return
     */
    public static File getDownloadsPath() {
        File file = new File(DOWNLOAD_FOLDER_PATH_OFFICIAL);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }


    public static File getRecPath() {
        File file = new File(REC_FOLDER_PATH_OFFICIAL);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    /**
     * 获取图片路径
     *
     * @return
     */
    public static File getImagePath() {
        File file = new File(AppUtils.IMG_FOLDER_PATH);
        if (!file.exists()) {
            file.mkdirs();
            File noMedia = new File(file, ".nomedia");
            try {
                noMedia.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 文件剪切
     *
     * @param srcPath
     * @param context
     * @return
     */
    public static String cutFile(String srcPath, Context context) {
        File srcFile = new File(srcPath);
        if (srcFile.exists()) {
            File destFile = new File(getDownloadsPath(), "IM" + System.currentTimeMillis() + "" +
                    ".jpg");
            boolean flag = false;

            try {
                FileInputStream fis = new FileInputStream(srcPath);
                FileOutputStream fos = new FileOutputStream(destFile);
                byte[] buf = new byte[1024];
                int c;
                while ((c = fis.read(buf)) != -1) {
                    fos.write(buf, 0, c);
                }
                fis.close();
                fos.close();

                flag = true;
            } catch (IOException e) {
                //
            }

            if (flag) {
                srcFile.delete();
                Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                scanIntent.setData(Uri.fromFile(destFile));
                context.sendBroadcast(scanIntent);
                return destFile.getPath();
            }
        }
        return null;
    }


    /**
     * 解压缩功能.
     * 将ZIP_FILENAME文件解压到ZIP_DIR目录下.
     *
     * @throws Exception
     */
    public static int upZipFile(File zipFile, String folderPath) throws IOException {
        Logger.d("unzip file is:" + zipFile + ",folderPath is :" + folderPath);
        ZipFile zfile = new ZipFile(zipFile);
        Enumeration zList = zfile.entries();
        ZipEntry ze = null;
        byte[] buf = new byte[1024];
        while (zList.hasMoreElements()) {
            ze = (ZipEntry) zList.nextElement();
            if (ze.isDirectory()) {
                // Logger.d("ze.getName() = " + ze.getName());
                String dirstr = folderPath + ze.getName();
                //dirstr.trim();
                dirstr = new String(dirstr.getBytes("8859_1"), "GB2312");
                // Log.d("upZipFile", "str = " + dirstr);
                File f = new File(dirstr);
                f.mkdir();
                continue;

            }
            //  Logger.d("ze.getName() = " + ze.getName());
            OutputStream os = new BufferedOutputStream(new FileOutputStream(getRealFileName
                    (folderPath, ze.getName())));
            InputStream is = new BufferedInputStream(zfile.getInputStream(ze));
            int readLen = 0;
            while ((readLen = is.read(buf, 0, 1024)) != -1) {
                os.write(buf, 0, readLen);
            }
            is.close();
            os.close();
        }
        zfile.close();
        return 0;
    }


    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (StringUtils.isEmpty(versionName)) {
                return "";
            }
        } catch (Exception ignored) {
        }
        return versionName;
    }


    public static String getVersionName(Context context) {
        String version = "";
        try {
            PackageInfo packInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * 给定根目录，返回一个相对路径所对应的实际文件名.
     *
     * @param baseDir     指定根目录
     * @param absFileName 相对路径名，来自于ZipEntry中的name
     * @return java.io.File 实际的文件
     */
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
        }

        return ret;
    }
} 