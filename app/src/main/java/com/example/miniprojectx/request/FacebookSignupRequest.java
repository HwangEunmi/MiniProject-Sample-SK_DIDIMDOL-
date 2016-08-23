package com.example.miniprojectx.request;

import android.content.Context;

import com.example.miniprojectx.data.NetworkResult;
import com.example.miniprojectx.data.User;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by 은미 on 2016-08-23.
 */
public class FacebookSignupRequest extends AbstractRequest<NetworkResult<User>> {

    Request mRequest;
    public FacebookSignupRequest(Context context, String username, String email) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("facebooksignup")
                .build();
        RequestBody body = new FormBody.Builder()
                .add("username", username)
                .add("email", email)
                .build();
        mRequest = new Request.Builder()
                .url(url)
                .post(body)
                .tag(context)
                .build();
    }
    @Override
    protected Type getType() {
        return new TypeToken<NetworkResult<User>>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return mRequest;
    }
}