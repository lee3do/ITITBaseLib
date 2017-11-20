package io.itit.androidlibrary.network.http;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import cn.trinea.android.common.util.StringUtils;
import io.itit.androidlibrary.network.domain.BaseMessage;
import okhttp3.ResponseBody;
import retrofit2.Converter;


final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final TypeAdapter<T> adapter;

    GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.adapter = adapter;
    }


    @Override
    public T convert(ResponseBody value) throws IOException {

        String str = value.string();
        if (StringUtils.isEmpty(str.trim())) {
            Logger.d("return void");
            T msg = null;
            try {
                msg = (T) BaseMessage.class.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                Logger.e(e,"");
            }
            return msg;
        }
        Logger.d(str);
        try {
            JsonParser parser = new JsonParser();
            JsonElement root = parser.parse(str);
            if (root instanceof JsonObject) {
                JsonElement resultCodeEle = ((JsonObject) root).get("errorCode");
                JsonElement errorMessage = ((JsonObject) root).get("errorMessage");
                if (resultCodeEle instanceof JsonPrimitive) {
                    int resultCode = resultCodeEle.getAsInt();
                    //如果responseCode 不为 “0”，抛出 RuntimeException，或者自定义的异常类型
                    if (resultCode == 1001) {
                        throw new NeedLoginException();
                    } else if (resultCode != 0) {
                        if (errorMessage.getAsString().startsWith("$")) {
                            throw new RuntimeException(errorMessage.getAsString().replace("$", ""));
                        } else {
                            throw new RuntimeException("系统错误!");
                        }
                    }
                }
            }
            T data = adapter.fromJsonTree(root);
            return data;
        } catch (JsonSyntaxException e) {
            Logger.e(e, "JsonSyntaxException");
            //兼容另外一种格式
            String resultCode = str.split(",")[1];
            String resultMessage = str.split(",")[2];
            if (resultCode.equals("1001")) {
                throw new NeedLoginException();
            } else {
                throw new RuntimeException(resultMessage);
            }
        } finally {
            value.close();
        }
    }
}