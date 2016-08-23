package com.example.miniprojectx.request;

import android.content.Context;

import com.example.miniprojectx.data.NetworkResult;
import com.example.miniprojectx.data.User;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Administrator on 2016-08-10.
 */
public class ProfileRequest extends AbstractRequest<NetworkResult<User>> {
    Request request;
    public ProfileRequest(Context context) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("profile")
                .build();
        // HttpUrl을 작성함

        request = new Request.Builder()
                .url(url)
                .tag(context)
                .build();
        // 위의 url과 tag를 추가해서 Request로 빌드시킴
    }
    @Override
    protected Type getType() {
        return new TypeToken<NetworkResult<User>>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
