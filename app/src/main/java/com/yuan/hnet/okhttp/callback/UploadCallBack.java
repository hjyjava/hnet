package com.yuan.hnet.okhttp.callback;

import com.yuan.hnet.okhttp.listener.OkListener;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 没实现逻辑
 * Created by xiatian on 16/11/18.
 */

public class UploadCallBack extends CommonCallback {

    public UploadCallBack(OkListener listener) {
        super(listener);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        final String result = response.body().string();
        analyResult(result);
    }
}
