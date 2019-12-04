package com.yuan.hnet.okhttp.listener;

/**
 * Created by xiatian on 16/11/18.
 */

public interface OkFileLisener<T> extends RequestListener<T>{

    void progress(long writedBytes, long totalBytes);
}
