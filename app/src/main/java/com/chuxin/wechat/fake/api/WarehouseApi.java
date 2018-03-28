package com.chuxin.wechat.fake.api;

import com.chuxin.wechat.fake.entity.Warehouse;
import com.chuxin.wechat.fake.net.BaseResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 *
 * Created by chao on 2018/3/15.
 */

public interface WarehouseApi {

    @GET("warehouse")
    Observable<BaseResponse<List<Warehouse>>> getWarehouses();
}
