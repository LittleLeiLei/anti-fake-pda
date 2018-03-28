package com.chuxin.wechat.fake.views.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chuxin.wechat.fake.R;
import com.chuxin.wechat.fake.api.wrapper.ManagerApiWrapper;
import com.chuxin.wechat.fake.app.MyApp;
import com.chuxin.wechat.fake.constants.Constant;
import com.chuxin.wechat.fake.constants.IntentAction;
import com.chuxin.wechat.fake.entity.ManagerWrapper;
import com.chuxin.wechat.fake.net.BaseResponse;
import com.chuxin.wechat.fake.net.SimpleObserver;
import com.chuxin.wechat.fake.utils.ACache;
import com.chuxin.wechat.fake.views.base.BaseActivity;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 * Created by chao on 2018/3/12.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.btn_switch_login_way)
    TextView mSwitchBtn;

    @BindView(R.id.btn_login)
    Button mLoginBtn;

    @BindView(R.id.ll_login_by_account)
    LinearLayout mAccountLayout;

    @BindView(R.id.ll_login_by_phone)
    LinearLayout mPhoneLayout;

    @BindView(R.id.et_account)
    EditText mAccount;

    @BindView(R.id.et_password)
    EditText mPassword;

    @BindView(R.id.et_phone)
    EditText mPhone;

    @BindView(R.id.et_verify_code)
    EditText mCode;

    @BindView(R.id.btn_obtain_code)
    TextView mCodeBtn;

    final int COUNT_DOWN_MILLIS = 60 * 1000;
    final int COUNT_DOWN_INTERVAL = 1000;
    final int LOGIN_BY_PHONE = 1;
    final int LOGIN_BY_ACCOUNT = 0;
    private int currentLoginWay = LOGIN_BY_ACCOUNT;

    private CountDownTimer countDownTimer = new CountDownTimer(COUNT_DOWN_MILLIS, COUNT_DOWN_INTERVAL) {
        @Override
        public void onTick(long l) {
            mCodeBtn.setText(String.format("%s s", l / 1000));
        }

        @Override
        public void onFinish() {
            mCodeBtn.setEnabled(true);
            mCodeBtn.setText(R.string.text_obtain_verify_code);
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        if (MyApp.get().isLoggedIn()) {
            redirect2Main();
        }
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {

    }

    @Override
    protected boolean showToolbar() {
        return false;
    }

    @OnClick(R.id.btn_login)
    public void doLogin () {
        if (currentLoginWay == LOGIN_BY_ACCOUNT) {
            if (TextUtils.isEmpty(mAccount.getText().toString().trim()) || TextUtils.isEmpty(mPassword.getText().toString().trim())) {
                toast("账号或密码不能为空");
                return ;
            }

            ManagerApiWrapper.loginWithAccount(mAccount.getText().toString(), mPassword.getText().toString(), new SimpleObserver<ManagerWrapper, Object>() {
                @Override
                public void onSuccess(ManagerWrapper data) {
                    MyApp.get().login(data);
                    redirect2Main();
                }
            });

        } else if (currentLoginWay == LOGIN_BY_PHONE) {

            if (TextUtils.isEmpty(mPhone.getText().toString().trim()) || TextUtils.isEmpty(mCode.getText().toString().trim())) {
                toast("手机号或验证码不能为空");
                return ;
            }

            ManagerApiWrapper.loginWithPhone(mPhone.getText().toString(), mCode.getText().toString(), new SimpleObserver<ManagerWrapper, Object>() {
                @Override
                public void onSuccess(ManagerWrapper data) {
                    MyApp.get().login(data);
                    redirect2Main();
                }
            });

        }
    }

    @OnClick(R.id.btn_switch_login_way)
    public void switchLoginWay () {
        currentLoginWay = (currentLoginWay + 1) % 2;
        switch (currentLoginWay) {
            case LOGIN_BY_ACCOUNT:
                mSwitchBtn.setText(R.string.text_login_by_phone);
                mAccountLayout.setVisibility(View.VISIBLE);
                mPhoneLayout.setVisibility(View.GONE);
                break;
            case LOGIN_BY_PHONE:
                mSwitchBtn.setText(R.string.text_login_by_account);
                mAccountLayout.setVisibility(View.GONE);
                mPhoneLayout.setVisibility(View.VISIBLE);
                break;
        }
    }

    @OnClick(R.id.btn_obtain_code)
    public void getVerifyCode() {
        if (TextUtils.isEmpty(mPhone.getText().toString().trim())) {
            toast("手机号不能为空");
            return ;
        }
        ManagerApiWrapper.getVerifyCode(mPhone.getText().toString(), new SimpleObserver<String, Object>() {
            @Override
            public void onSuccess(String data) {
                countDownTimer.start();
                mCodeBtn.setEnabled(false);
            }
        });
    }

    private void redirect2Main () {
        countDownTimer.cancel();
        Intent intent = new Intent(IntentAction.PAGE_HOME);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        resolveIntent(intent);
    }
}
