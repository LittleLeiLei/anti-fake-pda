package com.chuxin.wechat.fake.views.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chuxin.wechat.fake.R;
import com.chuxin.wechat.fake.app.MyApp;

/**
 *
 * Created by chao on 2018/3/21.
 */

public class ConfirmDialog extends Dialog {

    private TextView mMessageText;
    private Button mPositiveBtn;
    private Button mNegativeBtn;

    private OnClickListener mPositiveListener;
    private OnClickListener mNegativeListener;

    private String message = "";

    public ConfirmDialog(@NonNull Context context) {
        this(context, R.style.CustomDialog);
    }

    public ConfirmDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_confirm);
        mMessageText = findViewById(R.id.tv_message);
        mPositiveBtn = findViewById(R.id.btn_confirm);
        mNegativeBtn = findViewById(R.id.btn_cancel);

        mNegativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mNegativeListener != null) {
                    mNegativeListener.onClick();
                } else {
                    dismiss();
                }
            }
        });
        mPositiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPositiveListener != null) {
                    mPositiveListener.onClick();
                }
            }
        });
    }

    @Override
    public void show() {
        super.show();
        mMessageText.setText(message);
    }

    private void setMessage(String text) {
        message = text;
    }

    private void setOnPositiveClickListener(OnClickListener listener) {
        this.mPositiveListener = listener;
    }

    private void setOnNegativeClickListener(OnClickListener listener) {
        this.mNegativeListener = listener;
    }


    public static class Builder {
        private ConfirmDialog mDialog;

        public Builder(Context context) {
            mDialog = new ConfirmDialog(context);
        }

        public Builder message(String content) {
            mDialog.setMessage(content);
            return this;
        }

        public Builder message(@StringRes int resId) {
            mDialog.setMessage(MyApp.get().getString(resId));
            return this;
        }

        public Builder positive(OnClickListener listener) {
            mDialog.setOnPositiveClickListener(listener);
            return this;
        }

        public Builder negative(OnClickListener listener) {
            mDialog.setOnNegativeClickListener(listener);
            return this;
        }

        public ConfirmDialog build() {
            return mDialog;
        }
    }

    public interface OnClickListener {
        public void onClick();
    }
}
