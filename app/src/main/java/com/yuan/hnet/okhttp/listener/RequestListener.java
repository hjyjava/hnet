package com.yuan.hnet.okhttp.listener;


import com.yuan.hnet.OkHttpException;

/**
 *
 * Created by xiatian on 16/11/29.
 */

public interface RequestListener<T> {
    void onSuccess(T t);
    void onFailure(OkHttpException e);
}
