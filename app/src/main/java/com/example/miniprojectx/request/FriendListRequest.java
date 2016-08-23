package com.example.miniprojectx.request;

import android.content.Context;

import com.example.miniprojectx.data.NetworkResult;
import com.example.miniprojectx.data.User;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by Administrator on 2016-08-09.
 */
public class FriendListRequest extends AbstractRequest<NetworkResult<List<User>>> {
    Request request;
    public FriendListRequest(Context context) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("friendlist")
                .build();
        request = new Request.Builder()
                .url(url)
                .tag(context)
                .build();
    }
    @Override
    public Request getRequest() {
        return request;
    }

    @Override
    protected Type getType() {
        return new TypeToken<NetworkResult<List<User>>>(){}.getType();
    }
}
