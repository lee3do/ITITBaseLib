package io.itit.androidlibrary.network.http;

import android.annotation.SuppressLint;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.trinea.android.common.util.StringUtils;
import io.itit.androidlibrary.ITITApplication;
import io.itit.androidlibrary.utils.NetWorkUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by dingzhihu on 15/5/7.
 */
public class RetrofitProvider {
    private static final String FORM_NAME = "content";
    private static final String CHARSET = "UTF-8";

    private static Retrofit retrofit;
    private static AppApis appApis;

    public static boolean needJsonInterceptor = false;
    public static boolean needAuthInterceptor = false;
    public static boolean needCache = true;
    public static boolean needLog = true;
    public static int TIME_OUT = 60;

    private static File httpCacheDirectory = new File(ITITApplication.appContext.getCacheDir(),
            "itCache");
    private static int cacheSize = 10 * 1024 * 1024; // 10 MiB
    private static Cache cache = new Cache(httpCacheDirectory, cacheSize);

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
                    "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + ""
                    + " " + "" + "max-stale=" + maxStale).build();
        }
    };

//    private static final Interceptor TOKEN_INTERCEPTOR = chain -> {
//        Request request = chain.request();
//        if (StringUtils.isEmpty(request.headers().get("TOKEN"))) {
//            return chain.proceed(request);
//        }
//        String name = request.headers().get("TOKEN").trim();
//        HttpUrl httpUrl = request.url().newBuilder().addQueryParameter(name, JSON.toJSONString
//                (ITITApplication.getToken())).build();
//        request = request.newBuilder().url(httpUrl).build();
//
//        return chain.proceed(request);
//    };

    private static final Interceptor JSON_INTERCEPTOR = chain -> {
        Request request = chain.request();
        RequestBody body = request.body();
        if (body instanceof FormBody) {
            FormBody formBody = (FormBody) body;
            Map<String, String> formMap = new HashMap<>();
            // 从 formBody 中拿到请求参数，放入 formMap 中
            for (int i = 0; i < formBody.size(); i++) {
                formMap.put(formBody.name(i), JSON.toJSONString(formBody.value(i)));
            }
            Gson gson = new Gson();
            String jsonParams = gson.toJson(formMap);
            // 重新修改 body 的内容
            body = new FormBody.Builder().add(FORM_NAME, jsonParams).build();
        }
        if (body != null) {
            request = request.newBuilder().post(body).build();
        }
        return chain.proceed(request);
    };


    private RetrofitProvider() {
    }

    public static Retrofit getInstance() {
        if (retrofit == null) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(message -> {
                Log.v("HTTP", message);
            });
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder builder = new OkHttpClient.Builder().
                    connectTimeout(TIME_OUT, TimeUnit.SECONDS).readTimeout(TIME_OUT, TimeUnit
                    .SECONDS).writeTimeout(TIME_OUT, TimeUnit.SECONDS);

            if (needJsonInterceptor) {
                builder.addInterceptor(JSON_INTERCEPTOR);
            }

            if (needAuthInterceptor) {
                builder.addInterceptor(new AuthInterceptor());
            }

            if (needCache) {
                builder.addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR).
                        addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR).
                        cache(cache);
            }

            if (needLog) {
                builder.addInterceptor(httpLoggingInterceptor);
            }

            OkHttpClient client = builder.build();
            retrofit = new Retrofit.Builder().baseUrl(baseUrl).client(client).addConverterFactory
                    (CustomGsonConverterFactory.create()).addCallAdapterFactory
                    (RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())).build();
        }
        return retrofit;
    }

    @SuppressLint("CheckResult")
    public static void get(String url, Map<String,Object> headers, Map<String,Object> parameters, Consumer<ResponseBody> success, Consumer<Throwable> error) {
        if (headers!=null) {
            headers = new HashMap<>();
        }
        if (parameters!=null) {
            parameters = new HashMap<>();
        }
        getApiInstance().httpGet(url, headers, parameters)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success,error);
    }

    @SuppressLint("CheckResult")
    public static void post(String url, Map<String,Object> headers, Map<String,Object> parameters, Consumer<ResponseBody> success, Consumer<Throwable> error) {
        if (headers!=null) {
            headers = new HashMap<>();
        }
        if (parameters!=null) {
            parameters = new HashMap<>();
        }
        getApiInstance().httpPost(url, headers, parameters)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success,error);
    }

    public static AppApis getApiInstance() {
        if (appApis == null) {
            appApis = getInstance().create(AppApis.class);
        }
        return appApis;
    }
}
