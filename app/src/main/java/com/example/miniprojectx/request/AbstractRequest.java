package com.example.miniprojectx.request;

import com.example.miniprojectx.data.NetworkResult;
import com.example.miniprojectx.data.NetworkResultTemp;
import com.example.miniprojectx.manager.NetworkRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2016-08-09.
 */
public abstract class AbstractRequest<T> extends NetworkRequest<T> {

    protected HttpUrl.Builder getBaseUrlBuilder() {
        HttpUrl.Builder builder = new HttpUrl.Builder();
        builder.scheme("https");
        builder.host("myproject-1470719872788.appspot.com");
        return builder;
    }

    @Override
    protected T parse(ResponseBody body) throws IOException {
        String text = body.string();
        Gson gson = new Gson();
        NetworkResultTemp temp = gson.fromJson(text, NetworkResultTemp.class);
        if (temp.getCode() == 1) {
            T result = gson.fromJson(text, getType());
            return result;
        } else if (temp.getCode() == 2) {
            Type type = new TypeToken<NetworkResult<String>>() {
            }.getType();
            NetworkResult<String> result = gson.fromJson(text, type);
            throw new IOException(result.getResult());
        } else {
            T result = gson.fromJson(text, getType(temp.getCode()));
            return result;
        }
    }

    protected Type getType(int code) {
        return getType();
    }

    protected abstract Type getType();
}
