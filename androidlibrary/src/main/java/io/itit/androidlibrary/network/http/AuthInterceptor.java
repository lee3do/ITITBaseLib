package io.itit.androidlibrary.network.http;

import androidx.annotation.VisibleForTesting;

import com.alibaba.fastjson.JSON;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import cn.trinea.android.common.util.StringUtils;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {


    private static volatile String[] AuthFields;                             // 1

    public AuthInterceptor() {
    }

    public static void setAuth(String[] fields) {          // 3
        AuthFields = fields;
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Headers originHeaders = request.headers();
        Headers.Builder newHeaders = new Headers.Builder();                     // 1
        if (StringUtils.isEmpty(originHeaders.get("Auth-Fields"))) {
            return chain.proceed(request);
        }
        String[] names = originHeaders.get("Auth-Fields").trim().split(",");
        Request.Builder newRequest = request.newBuilder().headers(newHeaders.build());
        tokenAuth(newRequest, request.url(), names);

        return chain.proceed(newRequest.build());
    }


    @VisibleForTesting
    void tokenAuth(Request.Builder newRequest, HttpUrl url, String[] names) {
        if (AuthFields == null || AuthFields.length != names.length) {
            Logger.d(url);
            throw new NeedLoginException();
        }

        HttpUrl.Builder newUrl = url.newBuilder();
        for (int i = 0; i < names.length; i++) {
            newUrl.addQueryParameter(names[i],JSON.toJSONString(AuthFields[i]));
        }

        newRequest.url(newUrl.build());
    }

    @VisibleForTesting
    void basicAuth(Request.Builder newRequest, HttpUrl url) {
        // ...
    }
}