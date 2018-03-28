package com.chuxin.wechat.fake.api;

import com.chuxin.wechat.fake.api.body.PackageBody;
import com.chuxin.wechat.fake.entity.Format;
import com.chuxin.wechat.fake.entity.ItemBatch;
import com.chuxin.wechat.fake.entity.ListWrapper;
import com.chuxin.wechat.fake.net.BaseResponse;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 *
 * Created by chao on 2018/3/14.
 */

public interface ProductApi {
    @GET("format")
    Observable<BaseResponse<ListWrapper<Format>>> getFormats(@Query("limit") int limit, @Query("offset") int offset);

    @GET("itembatch")
    Observable<BaseResponse<ListWrapper<ItemBatch>>> getItemBatches(@Query("formatId") String formatId, @Query("limit") int limit, @Query("offset") int offset);
}
