package com.yuan.hnet.okhttp.callback;

import com.yuan.hnet.OkHttpException;
import com.yuan.hnet.okhttp.listener.OkListener;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 没实现逻辑
 * Created by xiatian on 16/11/18.
 */

public class JsonCallBack extends CommonCallback {

    public JsonCallBack(OkListener listener) {
        super(listener);
    }

    @Override
    public void onResponse(Call call, Response response) {
        try {
            String result = response.body().string();
            analyResult(result);
        } catch (final IOException e) {
            mDelieverHandler.post(new Runnable() {
                @Override
                public void run() {
                    mListener.onFailure(new OkHttpException(OkHttpException.OTHER_ERROR, e.getMessage()));
                }
            });
        }
    }

}
