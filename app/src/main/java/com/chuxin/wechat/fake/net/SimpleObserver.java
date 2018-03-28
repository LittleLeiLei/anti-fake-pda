package com.chuxin.wechat.fake.net;

import android.support.annotation.Nullable;

import com.chuxin.wechat.fake.BuildConfig;
import com.chuxin.wechat.fake.app.MyApp;
import com.chuxin.wechat.fake.utils.GsonHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 *
 * Created by chao on 2018/3/12.
 */

public abstract class SimpleObserver<T, E> implements Observer<BaseResponse<T>> {

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(BaseResponse<T> t) {
        if (t.isSuccess()) {
            MyApp.get().toast(t.getMessage());
            onSuccess(t.getData());
            if (MyApp.get().isDebug()) {
                Logger.json(GsonHolder.get().toJson(t.getData()));
            }
        } else {
            onFailure(null, t.getMessage());
        }
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof HttpException) {
            ResponseBody body = ((HttpException) e).response().errorBody();
            String bodyStr = "";
            try {
                bodyStr = body.string();
                BaseResponse<E> res = GsonHolder.get().fromJson(bodyStr, new TypeToken<BaseResponse<E>>(){}.getType());
                onFailure(res.getData(), res.getMessage());
            } catch (IOException IOe) {
                IOe.printStackTrace();
            } catch (Exception exception) {
                exception.printStackTrace();
                onFailure(null, "解析错误");
                Logger.e(bodyStr);
            }
        } else {
            Logger.e(e.getMessage());
            onFailure(null, "未知错误");
        }
    }

    @Override
    public void onComplete() {

    }

    public void onSuccess(T data) {}

    public void onFailure(@Nullable E data, String message) {
        MyApp.get().toast(message);
    }
}
