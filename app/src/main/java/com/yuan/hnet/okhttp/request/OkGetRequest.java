package com.yuan.hnet.okhttp.request;

import android.net.Uri;

import java.util.Map;

import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * GET请求的封装类
 * Created by xiatian on 16/11/18.
 */

public class OkGetRequest extends OkHttpRequest {

    /*参数不带请求头的构造方法*/
    public OkGetRequest(String url, Map<String, String> params) {
        super(url, params);
    }

    /*参数带请求头的构造方法*/
    public OkGetRequest(String url, Map<String, String> params, Map<String, String> headers) {
        super(url, params, headers);
    }

    /*真正创建get请求*/
    @Override
    protected Request buildRequest(RequestBody requestBody) {
        return builder.url(buildUrl()).get().build();
    }

    @Override
    protected RequestBody buildRequestBody() {
        return null;
    }

    /*把参数放入url*/
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
}
