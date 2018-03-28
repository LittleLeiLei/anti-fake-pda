package com.chuxin.wechat.fake.net;

import com.chuxin.wechat.fake.BuildConfig;
import com.chuxin.wechat.fake.api.CodeApi;
import com.chuxin.wechat.fake.api.ManagerApi;
import com.chuxin.wechat.fake.api.OrderApi;
import com.chuxin.wechat.fake.api.ProductApi;
import com.chuxin.wechat.fake.api.WarehouseApi;
import com.chuxin.wechat.fake.app.MyApp;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 * Created by chao on 2018/3/12.
 */

public class RetrofitWrapper {

    private Retrofit mRetrofit;

    private final long TIME_OUT = 30;

    private HashMap<String, Object> mApi = new HashMap<>();

    private RetrofitWrapper () {
        OkHttpClient mClient = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(new AuthorizationInterceptor())
                .build();

        mRetrofit = new Retrofit.Builder()
                .client(mClient)
                .baseUrl(BuildConfig.API_ROOT)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        createApis();
    }

    private static class RetrofitHolder {
        private static RetrofitWrapper INSTANCE = new RetrofitWrapper();
    }

    private void createApis () {
        mApi.put(ManagerApi.class.getSimpleName(), mRetrofit.create(ManagerApi.class));
        mApi.put(ProductApi.class.getSimpleName(), mRetrofit.create(ProductApi.class));
        mApi.put(CodeApi.class.getSimpleName(), mRetrofit.create(CodeApi.class));
        mApi.put(WarehouseApi.class.getSimpleName(), mRetrofit.create(WarehouseApi.class));
        mApi.put(OrderApi.class.getSimpleName(), mRetrofit.create(OrderApi.class));
    }

    public static RetrofitWrapper instance() {
        return RetrofitHolder.INSTANCE;
    }

    public <T> T create (Class<T> clazz) {
        return (T)mApi.get(clazz.getSimpleName());
    }

}
