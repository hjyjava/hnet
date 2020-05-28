package com.yuan.hnet.okhttp.request;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by xiatian on 16/11/18.
 */

public class OkPostRequest extends OkHttpRequest {
//    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    public OkPostRequest(String url, Map<String, String> params) {
        super(url, params);
    }

    public OkPostRequest(String url, Map<String, String> params, Map<String, String> headers) {
        super(url, params, headers);
    }

    @Override
    protected Request buildRequest(RequestBody requestBody) {
        return builder.url(url).post(requestBody).build();
    }

    @Override
    protected RequestBody buildRequestBody() {
        if(postType==0){
            if (!TextUtils.isEmpty(bodyJsonParams)) {
                return RequestBody.create(MEDIA_TYPE_JSON, bodyJsonParams);
            }
            String params1 = JSON.toJSONString(params);
            return RequestBody.create(MEDIA_TYPE_JSON, params1);
        }else{
            FormBody.Builder builder = new FormBody.Builder();
            if (params != null) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    builder.add(entry.getKey(), entry.getValue());
                }
            }
            return builder.build();
        }
    }
}
