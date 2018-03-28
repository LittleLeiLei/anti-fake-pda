package com.chuxin.wechat.fake.api.wrapper;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.chuxin.wechat.fake.api.ProductApi;
import com.chuxin.wechat.fake.api.body.PackageBody;
import com.chuxin.wechat.fake.entity.Format;
import com.chuxin.wechat.fake.entity.ItemBatch;
import com.chuxin.wechat.fake.entity.ListWrapper;
import com.chuxin.wechat.fake.net.RetrofitWrapper;
import com.chuxin.wechat.fake.net.RxSchedulers;
import com.chuxin.wechat.fake.net.SimpleObserver;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;

/**
 *
 * Created by chao on 2018/3/14.
 */

public class ProductApiWrapper {

    public static void getFormats(int limit, int offset, SimpleObserver<ListWrapper<Format>, ?> observer) {
        Observable observable = RetrofitWrapper.instance()
                .create(ProductApi.class)
                .getFormats(limit < 0 ? 999999 : limit, offset);
        observable.compose(RxSchedulers.compose()).subscribe(observer);
    }

    public static void getItemBatches(@NonNull String formatId, int limit, int offset, SimpleObserver<ListWrapper<ItemBatch>, ?> observer) {
        Observable observable = RetrofitWrapper.instance()
                .create(ProductApi.class)
                .getItemBatches(formatId, limit < 0 ? 999999 : limit, offset);
        observable.compose(RxSchedulers.compose()).subscribe(observer);
    }
}
