package com.example.miniprojectx.request;

import android.content.Context;

import com.example.miniprojectx.data.ContentData;
import com.example.miniprojectx.data.NetworkResult;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2016-08-12.
 */
public class UploadRequest extends AbstractRequest<NetworkResult<ContentData>> {
    MediaType jpeg = MediaType.parse("image/jpeg");
    // jpeg형식의 이미지(again)
    Request request;
    public UploadRequest(Context context, String content, File file) {
        HttpUrl url = getBaseUrlBuilder()
                .addPathSegment("upload")
                .build();
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("content", content);
        // (again)
        if (file != null) {
            builder.addFormDataPart("myFile", file.getName(),
                    RequestBody.create(jpeg, file));
        }
                // 키 값(이름)은 myFile로 하고, file의 이름도 같이 올림
                // jpeg형식인 file을 만들어라 => 그것이 myFile

        RequestBody body = builder.build();
        // Post형식이니까 RequestBody가 필요함

        request = new Request.Builder()
                .url(url)
                .post(body)
                .tag(context)
                .build();
    }
    @Override
    protected Type getType() {
        return new TypeToken<NetworkResult<ContentData>>(){}.getType();
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
