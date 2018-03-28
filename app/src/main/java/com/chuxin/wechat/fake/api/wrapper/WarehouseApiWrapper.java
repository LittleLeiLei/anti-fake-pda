package com.chuxin.wechat.fake.api.wrapper;

import com.chuxin.wechat.fake.api.WarehouseApi;
import com.chuxin.wechat.fake.entity.Warehouse;
import com.chuxin.wechat.fake.net.RetrofitWrapper;
import com.chuxin.wechat.fake.net.RxSchedulers;
import com.chuxin.wechat.fake.net.SimpleObserver;

import java.util.List;

import io.reactivex.Observable;

/**
 *
 * Created by chao on 2018/3/15.
 */

public class WarehouseApiWrapper {


    public static void getWarehouses(SimpleObserver<List<Warehouse>, ?> observer) {
        Observable observable = RetrofitWrapper.instance().create(WarehouseApi.class).getWarehouses();
        observable.compose(RxSchedulers.compose()).subscribe(observer);
    }

}
