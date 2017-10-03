package io.itit.androidlibrary.network.http;

import android.util.Log;

import java.io.File;
import java.util.concurrent.TimeUnit;

import cn.trinea.android.common.util.StringUtils;
import io.itit.androidlibrary.ITITApplication;
import io.itit.androidlibrary.utils.NetWorkUtil;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by dingzhihu on 15/5/7.
 */
public class RetrofitProvider {

    private static Retrofit retrofit;
    private static AppApis appApis;
    public static String baseUrl = "http://uat3.itit.io:9203/p/";
    public static String baseImgUrl = baseUrl + "public/download_file/";

    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = chain -> {

        Response originalResponse = chain.proceed(chain.request());
        if (StringUtils.isEmpty(chain.request().headers().get("Cache-Control"))) {
            return originalResponse;
        }
        if (NetWorkUtil.isNetWorkAvailable(ITITApplication.appContext)) {
            int maxAge = 60 * 5; // 在线缓存在5分钟内可读取
            return originalResponse.newBuilder().header("Cache-Control", "public, max-age=" +
                    maxAge).build();
        } else {
            int maxStale = 60 * 60 * 60; // 离线时缓存保存1小时
            Log.i("CACHE", "离线缓存");
            return originalResponse.newBuilder().header("Cache-Control", "public, only-if-cached," +
                    "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + " max-stale=" +
                    maxStale).build();
        }
    };

    private static File httpCacheDirectory = new File(ITITApplication.appContext.getCacheDir(),
            "itCache");
    private static int cacheSize = 10 * 1024 * 1024; // 10 MiB
    private static Cache cache = new Cache(httpCacheDirectory, cacheSize);

    private RetrofitProvider() {
    }

    private static Retrofit getInstance() {
        if (retrofit == null) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(message -> {
                Log.v("HTTP", message);
            });
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().
//                    addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR).
//                    addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR).
//                    cache(cache).
        addInterceptor(httpLoggingInterceptor).connectTimeout(5, TimeUnit.MINUTES).build();
            retrofit = new Retrofit.Builder().baseUrl(baseUrl).client(client).addConverterFactory
                    (CustomGsonConverterFactory.create()).addCallAdapterFactory
                    (RxJava2CallAdapterFactory.create()).build();
        }
        return retrofit;
    }

    public static AppApis getApiInstance() {
        if (appApis == null) {
            appApis = getInstance().create(AppApis.class);
        }
        return appApis;
    }
}
