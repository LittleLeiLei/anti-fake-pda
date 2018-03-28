package com.chuxin.wechat.fake.views.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuxin.wechat.fake.R;
import com.chuxin.wechat.fake.app.MyApp;
import com.chuxin.wechat.fake.views.ui.dialog.LoadingDialog;

import butterknife.ButterKnife;

/**
 * activity基类
 * Created by chao on 2018/3/11.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    protected LoadingDialog mLoadingDialog;
    private final Handler handler = new Handler();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        ButterKnife.bind(this);

        if (showToolbar()) {
            initToolbar();
        }

        init(savedInstanceState);
        initView();
        initData();

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }

    @Override
    public void onClick (View view) {
        switch (view.getId()) {
            case R.id.tv_toolbar_secondary_button:
                onSecondaryButtonClicked(view);
                break;
            case R.id.iv_back:
                onBackClicked(view);
                break;
        }
    }

    protected abstract int getLayoutId ();

    protected abstract void init(Bundle savedInstanceState);

    protected abstract void initView();

    protected abstract void initData();

    protected boolean showToolbar () {
        return false;
    }

    @StringRes protected int getToolbarTitle () {
        return R.string.app_name;
    }

    @StringRes
    protected int getSecondaryButtonText () {
        return R.string.app_name;
    }

    protected boolean showBackButton () {
        return false;
    }

    protected boolean showSecondaryButtonText () {
        return false;
    }

    protected void setSecondaryButtonVisibility (int visible) {
        TextView secondaryButton = findViewById(R.id.tv_toolbar_secondary_button);
        if (secondaryButton != null) {
            secondaryButton.setVisibility(visible);
        }
    }

    protected void onBackClicked (View view) {}

    protected void onSecondaryButtonClicked (View view) {
        toast(getString(R.string.toast_reset_already));
    }

    protected void setToolbarTitle (String text) {
        TextView title = findViewById(R.id.tv_toolbar_title);
        if (title != null) {
            title.setText(text);
        }
    }

    protected void initToolbar () {
        setToolbarTitle(getString(getToolbarTitle()));

        TextView secondaryButton = findViewById(R.id.tv_toolbar_secondary_button);
        if (secondaryButton != null) {
            secondaryButton.setText(getSecondaryButtonText());
            secondaryButton.setVisibility(showSecondaryButtonText() ? View.VISIBLE : View.GONE);
            secondaryButton.setOnClickListener(this);
        }

        ImageView backBtn = findViewById(R.id.iv_back);
        if (backBtn != null) {
            backBtn.setVisibility(showBackButton() ? View.VISIBLE : View.GONE);
            backBtn.setOnClickListener(this);
        }
    }

    protected void toast (String content) {
        MyApp.get().toast(content);
    }

    protected boolean resolveIntent (Intent intent) {
        boolean flag = true;
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        } else {
            flag = false;
            toast("未找到对应组件");
        }
        return flag;
    }

    protected boolean resolveIntent4Result (Intent intent, int requestCode) {
        boolean flag = true;
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, requestCode);
            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        } else {
            flag = false;
            toast("未找到对应组件");
        }
        return flag;
    }

    protected void showLoadingDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
        }
        mLoadingDialog.show();
    }

    protected void hideLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mLoadingDialog.dismiss();
                }
            }, 750);
        }
    }
}
