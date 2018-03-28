package com.chuxin.wechat.fake.app;

import android.app.Application;
import android.text.TextUtils;
import android.widget.Toast;

import com.chuxin.wechat.fake.BuildConfig;
import com.chuxin.wechat.fake.constants.Constant;
import com.chuxin.wechat.fake.entity.ManagerWrapper;
import com.chuxin.wechat.fake.sound.SoundWrapper;
import com.chuxin.wechat.fake.utils.ACache;
import com.chuxin.wechat.fake.utils.GsonHolder;
import com.google.gson.Gson;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 *
 * Created by chao on 2018/3/11.
 */

public class MyApp extends Application {

    private ManagerWrapper mManagerWrapper;

    private static MyApp app;

    public static MyApp get() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        this.init();
    }

    private void init() {
        mManagerWrapper = GsonHolder.get().fromJson(ACache.get(this).getAsString(Constant.Key.KEY_CACHE_USER), ManagerWrapper.class);
        Logger.addLogAdapter(new AndroidLogAdapter());
        SoundWrapper.get().init();
    }

    public void login(ManagerWrapper wrapper) {
        mManagerWrapper = wrapper;
        Gson gson = new Gson();
        ACache.get(MyApp.get()).put(Constant.Key.KEY_CACHE_USER, gson.toJson(wrapper));
    }

    public void logout() {
        mManagerWrapper = null;
        ACache.get(this).put(Constant.Key.KEY_CACHE_USER, "");
    }

    public boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    public boolean isLoggedIn () {
        return getManagerWrapper() != null;
    }

    public ManagerWrapper getManagerWrapper() {
        return mManagerWrapper;
    }

    public void toast (String content) {
        if (!TextUtils.isEmpty(content.trim())) {
            Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
        }
    }
}
