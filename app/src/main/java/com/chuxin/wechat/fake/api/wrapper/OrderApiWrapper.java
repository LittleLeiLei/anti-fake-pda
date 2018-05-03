package com.chuxin.wechat.fake.api.wrapper;

import android.support.annotation.NonNull;

import com.chuxin.wechat.fake.api.OrderApi;
import com.chuxin.wechat.fake.api.body.DeliverBody;
import com.chuxin.wechat.fake.entity.DeliverResult;
import com.chuxin.wechat.fake.entity.Format;
import com.chuxin.wechat.fake.entity.Order;
import com.chuxin.wechat.fake.entity.Product;
import com.chuxin.wechat.fake.net.BaseResponse;
import com.chuxin.wechat.fake.net.RetrofitWrapper;
import com.chuxin.wechat.fake.net.RxSchedulers;
import com.chuxin.wechat.fake.net.SimpleObserver;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * 订单
 * Created by chao on 2018/3/16.
 */

public class OrderApiWrapper {

    /**
     * 获取订单详情
     * @param code      订单编码
     * @param observer  回调
     */
    public static void getOrderDetail(@NonNull String code, SimpleObserver<Order, ?> observer) {
        Observable observable = RetrofitWrapper.instance().create(OrderApi.class).getOrderDetail(code);
        observable.compose(RxSchedulers.compose()).map(new Function<BaseResponse<Order>, BaseResponse<Order>>() {

            @Override
            public BaseResponse<Order> apply(BaseResponse<Order> res) throws Exception {
                List<Format> formats = new ArrayList<>();
                for (Product product : res.getData().getProducts()) {
                    Product cloneProduct = new Product();
                    cloneProduct.setCode(product.getCode());
                    cloneProduct.setName(product.getName());
                    cloneProduct.setMinUnit(product.getMinUnit());
                    cloneProduct.setSoldUnit(product.getSoldUnit());
                    for (Format format : product.getFormats()) {
                        format.setCount(format.getCount() * cloneProduct.getSoldUnit().getNumber());
                        // 若订单为已发货状态，则设置packageCount为count的值
                        format.setPackageCount(res.getData().isDelivered() || !product.isAntiFake() ? format.getCount() : 0);
                        format.setProduct(cloneProduct);
                        formats.add(format);
                    }
                }
                // 将相同规格商品进行合并
                res.getData().setFormats(Order.compact(formats));
                return res;
            }
        }).subscribe(observer);
    }

    /**
     * 发货
     * @param orderId
     * @param warehouseId
     * @param boxCodes
     * @param itemCodes
     * @param observer
     */
    public static void doDeliver(@NonNull String orderId, @NonNull String warehouseId, List<String> boxCodes, List<String> itemCodes, SimpleObserver<?, DeliverResult> observer) {
        DeliverBody body = new DeliverBody(warehouseId, itemCodes, boxCodes);
        Observable observable = RetrofitWrapper.instance().create(OrderApi.class).doDeliver(orderId, body);
        observable.compose(RxSchedulers.compose()).subscribe(observer);
    }
}
