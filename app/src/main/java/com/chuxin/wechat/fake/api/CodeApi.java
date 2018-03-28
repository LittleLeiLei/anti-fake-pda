package com.chuxin.wechat.fake.api;

import com.chuxin.wechat.fake.api.body.AppendBody;
import com.chuxin.wechat.fake.api.body.PackageBody;
import com.chuxin.wechat.fake.api.body.SeparateBody;
import com.chuxin.wechat.fake.api.body.UnpackBody;
import com.chuxin.wechat.fake.entity.BoxCode;
import com.chuxin.wechat.fake.entity.CodeInfo;
import com.chuxin.wechat.fake.entity.ItemCode;
import com.chuxin.wechat.fake.net.BaseResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 码相关接口
 * Created by chao on 2018/3/15.
 */

public interface CodeApi {

    @GET("code/box/check/logistics/{logisticsCode}")
    Observable<BaseResponse<Boolean>> isBoxCode(@Path("logisticsCode") String code);

    @GET("code/item/check/logistics/{logisticsCode}")
    Observable<BaseResponse<Boolean>> isItemCode(@Path("logisticsCode") String code);

    @GET("code/info/{logisticsCode}")
    Observable<BaseResponse<CodeInfo>> getCodeInfo(@Path("logisticsCode") String code);

    @POST("code/link/packing")
    Observable<BaseResponse<List<String>>> doPackage(@Body PackageBody params);

    @POST("code/link/unpacking")
    Observable<BaseResponse> doUnpack(@Body UnpackBody params);

    @POST("code/link/unlink")
    Observable<BaseResponse> doSeparate(@Body SeparateBody params);

    @POST("code/link/link")
    Observable<BaseResponse<List<String>>> doAppend(@Body AppendBody params);

    @GET("code/item/search/box")
    Observable<BaseResponse<BoxCode>> getBoxCodeByItemCode(@Query("logisticsCode") String logisticsCode);

    @GET("code/box/search/items")
    Observable<BaseResponse<List<ItemCode>>> getItemCodeByBoxCode(@Query("logisticsCode") String logisticsCode);
}
