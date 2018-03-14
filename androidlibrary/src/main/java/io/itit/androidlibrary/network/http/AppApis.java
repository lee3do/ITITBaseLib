package io.itit.androidlibrary.network.http;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface AppApis {
    @GET()
    Call<ResponseBody> httpGet(@Url String url);


    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);


}