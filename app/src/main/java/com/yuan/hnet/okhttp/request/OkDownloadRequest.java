package com.yuan.hnet.okhttp.request;


import android.net.Uri;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.yuan.hnet.okhttp.DownloadFile;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by xiatian on 16/11/18.
 */

public class OkDownloadRequest extends OkHttpRequest {

    private DownloadFile mDownloadFile;

    public OkDownloadRequest(DownloadFile downloadFile) {
        super(downloadFile.getUrl(), downloadFile.getDownloadParams(), downloadFile.getHeaders());
        this.mDownloadFile = downloadFile;
    }

    @Override
    protected Request buildRequest(RequestBody requestBody) {
        if (downloadType == 0) {
            return builder.url(url).post(requestBody).build();
        } else {
            return builder.url(buildUrl()).get().build();
        }
    }

    private String buildUrl() {
        if (url == null || params == null || params.isEmpty()) {
            return url;
        }
        Uri.Builder builder = Uri.parse(url).buildUpon();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.appendQueryParameter(entry.getKey(), entry.getValue());
        }
        String s = builder.build().toString();
        return s;
    }


    @Override
    protected RequestBody buildRequestBody() {
        if (downloadType == 0) {
            if (postType == 0) {
                if (!TextUtils.isEmpty(bodyJsonParams)) {
                    return RequestBody.create(MEDIA_TYPE_JSON, bodyJsonParams);
                }
                String params1 = JSON.toJSONString(params);
                return RequestBody.create(MEDIA_TYPE_JSON, params1);
            } else {
                FormBody.Builder builder = new FormBody.Builder();
                if (params != null) {
                    for (Map.Entry<String, String> entry : params.entrySet()) {
                        builder.add(entry.getKey(), entry.getValue());
                    }
                }
                return builder.build();
            }
        } else {
            return null;
        }
    }

    public DownloadFile getmDownloadFile() {
        return mDownloadFile;
    }
}
