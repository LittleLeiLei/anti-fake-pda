package com.chuxin.wechat.fake.api;

import com.chuxin.wechat.fake.entity.ManagerWrapper;
import com.chuxin.wechat.fake.net.BaseResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 *
 * Created by chao on 2018/3/12.
 */

public interface ManagerApi {

    /**
     * 手机验证码登录
     * @param params phone & code
     * @return
     */
    @POST("user/login/phone")
    Observable<BaseResponse<ManagerWrapper>> loginWithPhone(@Body HashMap<String, String> params);

    /**
     * 账号密码登录
     * @param params account & password
     * @return
     */
    @POST("user/login/account")
    Observable<BaseResponse<ManagerWrapper>> loginWithAccount(@Body HashMap<String, String> params);

    /**
     * 获取验证码
     * @param params phone
     * @return
     */
    @POST("user/login/code")
    Observable<BaseResponse<String>> getVerifyCode(@Body HashMap<String, String> params);
}
