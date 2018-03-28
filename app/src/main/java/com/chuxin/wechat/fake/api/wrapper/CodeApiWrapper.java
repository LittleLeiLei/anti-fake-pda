package com.chuxin.wechat.fake.api.wrapper;

import android.support.annotation.NonNull;

import com.chuxin.wechat.fake.api.CodeApi;
import com.chuxin.wechat.fake.api.ProductApi;
import com.chuxin.wechat.fake.api.body.AppendBody;
import com.chuxin.wechat.fake.api.body.PackageBody;
import com.chuxin.wechat.fake.api.body.SeparateBody;
import com.chuxin.wechat.fake.api.body.UnpackBody;
import com.chuxin.wechat.fake.entity.BoxCode;
import com.chuxin.wechat.fake.entity.Code;
import com.chuxin.wechat.fake.entity.CodeInfo;
import com.chuxin.wechat.fake.entity.ItemCode;
import com.chuxin.wechat.fake.net.BaseResponse;
import com.chuxin.wechat.fake.net.RetrofitWrapper;
import com.chuxin.wechat.fake.net.RxSchedulers;
import com.chuxin.wechat.fake.net.SimpleObserver;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 *
 * Created by chao on 2018/3/15.
 */

public class CodeApiWrapper {

    /**
     * 检查是否正确的箱码
     * @param code      箱码
     * @param observer  回调
     */
    public static void isBoxCode(@NonNull String code, SimpleObserver<Boolean, ?> observer) {
        Observable observable = RetrofitWrapper.instance().create(CodeApi.class).isBoxCode(code);
        observable.compose(RxSchedulers.compose()).subscribe(observer);
    }

    /**
     * 检查是否正确的单码
     * @param code      单码
     * @param observer  回调
     */
    public static void isItemCode(@NonNull String code, SimpleObserver<Boolean, ?> observer) {
        Observable observable = RetrofitWrapper.instance().create(CodeApi.class).isItemCode(code);
        observable.compose(RxSchedulers.compose()).subscribe(observer);
    }

    /**
     * 获取码信息
     * @param code
     * @param observer
     */
    public static void getCodeInfo(@NonNull String code, SimpleObserver<CodeInfo, ?> observer) {
        Observable observable = RetrofitWrapper.instance().create(CodeApi.class).getCodeInfo(code);
        observable.compose(RxSchedulers.compose()).subscribe(observer);
    }

    /**
     * 装箱
     * @param itemBatchId
     * @param boxLogisticsCode
     * @param itemLogisticsCodes
     * @param observer
     */
    public static void doPackage(@NonNull String itemBatchId, @NonNull String boxLogisticsCode, @NonNull List<String> itemLogisticsCodes, SimpleObserver<?, List<String>> observer) {
        PackageBody body = new PackageBody(itemBatchId, boxLogisticsCode, itemLogisticsCodes);

        Observable observable = RetrofitWrapper.instance()
                .create(CodeApi.class)
                .doPackage(body);
        observable.compose(RxSchedulers.compose()).subscribe(observer);
    }

    /**
     * 拆箱
     * @param boxLogisticsCodes
     * @param observer
     */
    public static void doUnpack(@NonNull List<String> boxLogisticsCodes, SimpleObserver observer) {
        UnpackBody body = new UnpackBody(boxLogisticsCodes);

        Observable observable = RetrofitWrapper.instance()
                .create(CodeApi.class)
                .doUnpack(body);
        observable.compose(RxSchedulers.compose()).subscribe(observer);
    }

    /**
     * 分离单标
     * @param itemLogisticsCodes
     * @param observer
     */
    public static void doSeparate(@NonNull List<String> itemLogisticsCodes, SimpleObserver observer) {
        SeparateBody body = new SeparateBody(itemLogisticsCodes);

        Observable observable = RetrofitWrapper.instance()
                .create(CodeApi.class)
                .doSeparate(body);
        observable.compose(RxSchedulers.compose()).subscribe(observer);
    }

    /**
     * 补标
     * @param boxLogisticsCode    箱码
     * @param itemLogisticsCodes  单码
     * @param observer
     */
    public static void doAppend(@NonNull String boxLogisticsCode, @NonNull List<String> itemLogisticsCodes, SimpleObserver<?, List<String>> observer) {
        AppendBody body = new AppendBody(boxLogisticsCode, itemLogisticsCodes);

        Observable observable = RetrofitWrapper.instance()
                .create(CodeApi.class)
                .doAppend(body);
        observable.compose(RxSchedulers.compose()).subscribe(observer);
    }

    /**
     * 箱标查询单标
     * @param logisticsCode    箱码
     * @param observer
     */
    public static void getItemCodeByBoxCode(@NonNull String logisticsCode, SimpleObserver<List<Code>, ?> observer) {
        Observable observable = RetrofitWrapper.instance()
                .create(CodeApi.class)
                .getItemCodeByBoxCode(logisticsCode);
        observable.compose(RxSchedulers.compose()).map(new Function<BaseResponse<List<Code>>, BaseResponse<List<Code>>>() {

            @Override
            public BaseResponse<List<Code>> apply(BaseResponse<List<Code>> res) throws Exception {
                for (Code code : res.getData()) {
                    code.setType(Code.TYPE_ITEM_CODE);
                }
                return res;
            }
        }).subscribe(observer);
    }

    /**
     * 单标查询箱标
     * @param logisticsCode    单码
     * @param observer
     */
    public static void getBoxCodeByItemCode(@NonNull String logisticsCode, SimpleObserver<BoxCode, ?> observer) {
        Observable observable = RetrofitWrapper.instance()
                .create(CodeApi.class)
                .getBoxCodeByItemCode(logisticsCode);
        observable.compose(RxSchedulers.compose()).subscribe(observer);
    }
}
