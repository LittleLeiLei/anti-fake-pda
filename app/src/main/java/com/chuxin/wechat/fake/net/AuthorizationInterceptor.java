package com.chuxin.wechat.fake.net;

import com.chuxin.wechat.fake.app.MyApp;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 * Created by chao on 2018/3/13.
 */

public class AuthorizationInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        if (MyApp.get().isLoggedIn()) {
            builder.addHeader("Authorization", "Bearer " + MyApp.get().getManagerWrapper().getToken());
        }
        return chain.proceed(builder.build());
    }
}
