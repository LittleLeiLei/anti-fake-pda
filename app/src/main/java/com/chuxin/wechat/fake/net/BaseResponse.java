package com.chuxin.wechat.fake.net;

/**
 *
 * Created by chao on 2018/3/12.
 */

public class BaseResponse<T> {
    public boolean success = false;
    public T data = null;
    public String message = "";

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
