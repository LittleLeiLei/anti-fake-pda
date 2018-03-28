package com.chuxin.wechat.fake.api;

import com.chuxin.wechat.fake.api.body.DeliverBody;
import com.chuxin.wechat.fake.entity.DeliverResult;
import com.chuxin.wechat.fake.entity.Order;
import com.chuxin.wechat.fake.net.BaseResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 *
 * Created by chao on 2018/3/16.
 */

public interface OrderApi {

    @GET("order/{code}")
    Observable<BaseResponse<Order>> getOrderDetail(@Path("code") String code);

    @POST("order/{id}/deliver")
    Observable<BaseResponse<DeliverResult>> doDeliver(@Path("id") String orderId, @Body DeliverBody params);
}
