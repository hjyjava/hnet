package com.yuan.hnet.okhttp.request;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import java.util.Map;

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
        if (!TextUtils.isEmpty(bodyJsonParams)) {
            return RequestBody.create(MEDIA_TYPE_JSON, bodyJsonParams);
        }
        String params1 = JSON.toJSONString(params);
        return RequestBody.create(MEDIA_TYPE_JSON, params1);

//        StringBuilder tempParams = new StringBuilder();
//        int pos = 0;
//        for (String key : params.keySet()) {
//            if (pos > 0 && pos < params.size()) {
//                tempParams.append("&");
//            }
//            try {
//                if (!TextUtils.isEmpty(params.get(key))) {
//                    String encode = URLEncoder.encode(params.get(key), "utf-8");
//                    tempParams.append(String.format("%s=%s", key, encode));
//                }
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//            pos++;
//        }
//        String params = tempParams.toString();
////        Log.e("网络请求:", "post请求体:" + params);
//        return RequestBody.create(MEDIA_TYPE_JSON, params);


//        FormBody.Builder builder = new FormBody.Builder();
//        MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
//        if (params != null) {
//            for (Map.Entry<String, String> entry : params.entrySet()) {
//                if(!TextUtils.isEmpty(entry.getValue())&&!TextUtils.isEmpty(entry.getKey())){
//                   builder.add(entry.getKey(), entry.getValue());
////                    try {
////                    builder.addEncoded(entry.getKey(),URLEncoder.encode(entry.getValue(),"utf-8"));
//////                   builder.add(entry.getKey(), URLEncoder.encode(entry.getValue(),"utf-8"));
////                    } catch (Exception e) {
////                        e.printStackTrace();
////                    }
//                }
//            }
//        }
//        return builder.build();
    }
}
