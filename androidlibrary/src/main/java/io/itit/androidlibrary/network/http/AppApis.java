package io.itit.androidlibrary.network.http;

import java.util.Map;

import io.itit.androidlibrary.network.domain.UploadData;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface AppApis {

    @GET()
    Call<ResponseBody> httpGet(@Url String url);

    @FormUrlEncoded
    @POST()
    Observable<ResponseBody> httpPost(@Url String url, @FieldMap Map<String,Object> data, @HeaderMap Map<String,Object> header);

    @POST()
    Observable<ResponseBody> httpPostWithBody(@Url String url, @HeaderMap Map<String,Object> header,@Body RequestBody body);

    @GET()
    Observable<ResponseBody> httpGet(@Url String url, @FieldMap Map<String,Object> data, @HeaderMap Map<String,Object> header);


    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);

    @Multipart
    @POST()
    @Streaming
    Call<UploadData> uploadFile(@Part MultipartBody.Part file, @Url String url);
}