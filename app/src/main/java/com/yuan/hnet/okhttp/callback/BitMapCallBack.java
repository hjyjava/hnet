package com.yuan.hnet.okhttp.callback;

import android.os.Handler;
import android.os.Looper;

import com.yuan.hnet.OkHttpException;
import com.yuan.hnet.okhttp.listener.RequestListener;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 描述
 *
 * @author tp on 2017-12-11 16:25:36
 * @version v1.0
 */
public class BitMapCallBack implements Callback {
    private RequestListener listener;
    private Handler handler = new Handler(Looper.getMainLooper());

    public BitMapCallBack(RequestListener listener) {
        this.listener = listener;
    }

    @Override
    public void onFailure(Call call, final IOException e) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                listener.onFailure(new OkHttpException(OkHttpException.OTHER_ERROR, e.getMessage()));
            }
        });
    }

    @Override
    public void onResponse(Call call, final Response response){
        handler.post(new Runnable() {
            @Override
            public void run() {
                listener.onSuccess(response.body().byteStream());
            }
        });
    }
}
