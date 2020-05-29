package com.yuan.hnet.okhttp.listener;

public interface OkDownLoadLisener extends OkListener {
    void progress(long writedBytes, long totalBytes);
}
