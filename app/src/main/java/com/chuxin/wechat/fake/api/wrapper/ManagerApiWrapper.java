package com.chuxin.wechat.fake.api.wrapper;

import android.support.annotation.NonNull;

import com.chuxin.wechat.fake.api.ManagerApi;
import com.chuxin.wechat.fake.entity.ManagerWrapper;
import com.chuxin.wechat.fake.net.BaseResponse;
import com.chuxin.wechat.fake.net.RetrofitWrapper;
import com.chuxin.wechat.fake.net.RxSchedulers;
import com.chuxin.wechat.fake.net.SimpleObserver;

import java.util.HashMap;

import io.reactivex.Observable;

/**
 *
 * Created by chao on 2018/3/14.
 */

public class ManagerApiWrapper {

    public static void loginWithAccount(@NonNull String account, @NonNull String password, SimpleObserver<ManagerWrapper, ?> observer) {
        HashMap<String, String> params = new HashMap<>();
        params.put("account", account);
        params.put("password", password);

        Observable observable = RetrofitWrapper.instance()
                .create(ManagerApi.class)
                .loginWithAccount(params);

        observable.compose(RxSchedulers.compose()).subscribe(observer);
    }

    public static void loginWithPhone(@NonNull String phone, @NonNull String code, SimpleObserver<ManagerWrapper, ?> observer) {
        HashMap<String, String> params = new HashMap<>();
        params.put("phone", phone);
        params.put("code", code);

        Observable observable = RetrofitWrapper.instance()
                .create(ManagerApi.class)
                .loginWithPhone(params);

        observable.compose(RxSchedulers.compose()).subscribe(observer);
    }

    public static void getVerifyCode(@NonNull String phone, SimpleObserver<String, ?> observer) {
        HashMap<String, String> params = new HashMap<>();
        params.put("phone", phone);

        Observable observable = RetrofitWrapper.instance()
                .create(ManagerApi.class)
                .getVerifyCode(params);

        observable.compose(RxSchedulers.compose()).subscribe(observer);
    }
}
