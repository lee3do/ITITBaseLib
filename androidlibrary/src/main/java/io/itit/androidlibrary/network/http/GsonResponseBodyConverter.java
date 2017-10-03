package io.itit.androidlibrary.network.http;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;


final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final TypeAdapter<T> adapter;

    GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.adapter = adapter;
    }


    @Override
    public T convert(ResponseBody value) throws IOException {
        try {
            JsonParser parser = new JsonParser();
            JsonElement root = parser.parse(new JsonReader(value.charStream()));
            if (root instanceof JsonObject) {
                JsonElement resultCodeEle = ((JsonObject) root).get("errorCode");
                JsonElement errorMessage = ((JsonObject) root).get("errorMessage");
                if (resultCodeEle instanceof JsonPrimitive) {
                    int resultCode = resultCodeEle.getAsInt();
                    //如果responseCode 不为 “0”，抛出 RuntimeException，或者自定义的异常类型
                    if(resultCode == 1001){
                        throw new NeedLoginException();
                    }else if(resultCode != 0) {
                        if (errorMessage.getAsString().startsWith("$")) {
                            throw new RuntimeException(errorMessage.getAsString().replace("$",""));
                        }else{
                            throw new RuntimeException("系统错误!");
                        }


                    }
                }
            }
            return adapter.fromJsonTree(root);
        } finally {
            value.close();
        }
    }
}