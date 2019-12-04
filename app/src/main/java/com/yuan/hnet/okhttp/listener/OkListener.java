package com.yuan.hnet.okhttp.listener;


import com.yuan.hnet.OkHttpException;

/**
 * Created by xiatian on 16/11/18.
 */

public interface OkListener {
    void onSuccess(String resultStr);
    void onFailure(OkHttpException e);
}
