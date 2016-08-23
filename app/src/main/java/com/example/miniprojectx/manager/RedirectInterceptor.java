package com.example.miniprojectx.manager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016-08-12.
 */
public class RedirectInterceptor implements Interceptor {
    // 이거 만들어준 이유가
    // 저렇게 채팅에 관련된거 다 만들고 그대로 빌드하면 307이라는 오류가 생김
    // 그래서 이렇게 만들어 준 것임(Interceptor를 implements시켜서)
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        if (response.code() == 307) {
            String url = response.header("Location");
            Request nRequest = request.newBuilder()
                    .url(url)
                    .build();
            response = chain.proceed(nRequest);
        }
        return response;
    }
}
