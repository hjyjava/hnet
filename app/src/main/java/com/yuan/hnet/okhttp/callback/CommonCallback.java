package com.yuan.hnet.okhttp.callback;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.yuan.hnet.OkHttpException;
import com.yuan.hnet.okhttp.listener.OkListener;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;

/**
 * 通用的回调接口，把数据回调给主线程OkListener
 * Created by xiatian on 16/11/18.
 */

public abstract class CommonCallback implements Callback {


    protected OkListener mListener;
    protected Handler mDelieverHandler;

    public CommonCallback(OkListener listener) {
        this.mListener = listener;
        this.mDelieverHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void onFailure(Call call, final IOException e) {
        mDelieverHandler.post(new Runnable() {
            @Override
            public void run() {
                mListener.onFailure(new OkHttpException(OkHttpException.OTHER_ERROR, e.getMessage()));
            }
        });
    }

    protected void analyResult(final String result) {
        mDelieverHandler.post(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.isEmpty(result)) {
                    mListener.onFailure(new OkHttpException(OkHttpException.EMPTY_ERROR, OkHttpException.EMPTY_ERROR_MSG));
                    return;
                }
                mListener.onSuccess(result);
            }
        });
    }


}
