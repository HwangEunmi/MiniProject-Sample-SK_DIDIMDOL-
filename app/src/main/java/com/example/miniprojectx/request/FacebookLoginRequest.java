package com.example.miniprojectx.request;

import android.content.Context;

import com.example.miniprojectx.data.FacebookUser;
import com.example.miniprojectx.data.NetworkResult;
import com.example.miniprojectx.data.User;
import com.example.miniprojectx.manager.NetworkRequest;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Tacademy on 2016-08-22.
 */
public class FacebookLoginRequest extends AbstractRequest<NetworkResult<Object>> {
    // FacebookLogin을 위한 request
    Request mRequest;

    public FacebookLoginRequest(Context context, String token, String regId) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("facebooksignin")
                .build();

        RequestBody body = new FormBody.Builder()
                .add("access_token", token)
                .add("registrationId", regId)
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

    // (자세한건 서버개발서 참고)
    // 경우가 로그인 됨, 로그인 안됨, 그리고 회원가입(아마)
    // 그렇게 세가지 경우라서 getType을 한번 더 만들어줌 (code가 3개 들어가야되니까)
    // facebook login 용
    protected Type getType(int code) {
        if (code == 3) {
            return new TypeToken<NetworkResult<FacebookUser>>(){}.getType();
        }
        return null;
    }


    @Override
    public Request getRequest() {
        return mRequest;
    }
}
