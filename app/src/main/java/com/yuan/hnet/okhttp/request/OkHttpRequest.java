package com.yuan.hnet.okhttp.request;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 用来封装请求的基类
 * Created by xiatian on 16/11/18.
 */

public abstract class OkHttpRequest {

    protected String url;
    protected Map<String, String> params = new HashMap<>();
    protected Map<String, String> headers = new HashMap<>();
    protected String bodyJsonParams;
    //0 RequestBody ,1 FormBody
    protected int postType = 0;
    protected static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    protected Request.Builder builder = new Request.Builder();
    /*只有请求url、请求参数的构造方法*/
    public OkHttpRequest(String url, Map<String, String> params) {
        this(url, params, null);
    }
    /*有请求url、请求参数、请求头的构造方法*/
    public OkHttpRequest(String url, Map<String, String> params, Map<String, String> headers) {
        this.url = url;
        this.params = params;
        this.headers = headers;
        appendHeaders();
    }
    /*添加请求头*/
    protected void appendHeaders() {
        if (headers == null || headers.isEmpty())
            return;
        Headers.Builder headerBuilder = new Headers.Builder();
        for (String key : headers.keySet()) {
            try {
                headerBuilder.add(key, getValueEncoded(headers.get(key)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        builder.headers(headerBuilder.build());
    }
    /*把请求头的值解析成utf-8不换行的字符串*/
    protected static String getValueEncoded(String value) throws Exception {
        if (value == null) return "null";
        String newValue = value.replace("\n", "");
        for (int i = 0, length = newValue.length(); i < length; i++) {
            char c = newValue.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                return URLEncoder.encode(newValue, "UTF-8");
            }
        }
        return newValue;
    }

    protected abstract Request buildRequest(RequestBody requestBody);

    public Request generateRequest() {
        return buildRequest(buildRequestBody());
    }
    /*构建请求体*/
    protected abstract RequestBody buildRequestBody();
    /*设置头方法*/
    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
        appendHeaders();
    }

    public void setBodyJsonParams(String bodyJsonParams) {
        this.bodyJsonParams = bodyJsonParams;
    }

    public void setPostType(int postType) {
        this.postType = postType;
    }
}
