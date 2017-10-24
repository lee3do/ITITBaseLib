package io.itit.androidlibrary;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.hwangjr.rxbus.RxBus;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.squareup.leakcanary.LeakCanary;

import java.util.UUID;

import cn.trinea.android.common.util.PreferencesUtils;
import cn.trinea.android.common.util.StringUtils;
import me.yokeyword.fragmentation.Fragmentation;


/**
 * Created by Lee_3do on 2017/9/11.
 */
public class ITITApplication extends Application {
    public static boolean hasLogin = false;
    public static String token = "";
    public static String host = "";
    public static int port = 9202;
    public static Context appContext;
    public static Activity currActivity;


    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        Fresco.initialize(this);
        Fragmentation.builder().handleException(e -> {
            e.printStackTrace();
            Logger.e(e, "");
        }).install();
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder().tag(Consts.LOG_TAG)
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));

        if (StringUtils.isEmpty(PreferencesUtils.getString(this, Consts.Pref.DEVICE_ID))) {
            PreferencesUtils.putString(this, Consts.Pref.DEVICE_ID, UUID.randomUUID().toString());
        }
        appContext = this;
    }



    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值
            getResources();
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }

    public static String getToken() {
        if (StringUtils.isEmpty(token)) {
            token = PreferencesUtils.getString(appContext, Consts.Pref.TOKEN, "");
        }
        return token;
    }

    public static void saveToken(String tokenStr) {
        ITITApplication.token = tokenStr;
        PreferencesUtils.putString(appContext, Consts.Pref.TOKEN, tokenStr);
    }

    public static void clearToken() {
        ITITApplication.token = "";
        PreferencesUtils.putString(appContext, Consts.Pref.TOKEN, "");
    }

    public static void showLoading(boolean show){
        RxBus.get().post(Consts.BusAction.SHOW_LOADING,show);
    }
}
