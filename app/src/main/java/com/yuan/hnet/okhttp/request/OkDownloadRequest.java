package com.yuan.hnet.okhttp.request;



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
        super(downloadFile.getUrl(), downloadFile.getDownloadParams(),downloadFile.getHeaders());
        this.mDownloadFile = downloadFile;
    }

    @Override
    protected Request buildRequest(RequestBody requestBody) {
        return builder.url(url).post(requestBody).build();
    }

    @Override
    protected RequestBody buildRequestBody() {
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        return builder.build();
    }

    public DownloadFile getmDownloadFile() {
        return mDownloadFile;
    }
}
