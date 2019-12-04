package com.yuan.hnet.okhttp;

import com.yuan.hnet.okhttp.callback.BitMapCallBack;
import com.yuan.hnet.okhttp.callback.DownloadCallBack;
import com.yuan.hnet.okhttp.callback.JsonCallBack;
import com.yuan.hnet.okhttp.callback.UploadCallBack;
import com.yuan.hnet.okhttp.listener.OkListener;
import com.yuan.hnet.okhttp.listener.RequestListener;
import com.yuan.hnet.okhttp.request.OkDownloadRequest;
import com.yuan.hnet.okhttp.request.OkHttpRequest;
import com.yuan.hnet.utils.HttpsUtils;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;

/**
 * 真正用来发送请求的类
 * Created by xiatian on 16/11/18.
 */

public class OkHttpUtils {
    private static final int TIME_OUT = 10;
    private static OkHttpClient mOkHttpClient;
    private static OkHttpUtils mInstance;

    private OkHttpUtils()
    {
        init();
    }

    public static OkHttpUtils getInstance()
    {
        if (mInstance == null)
        {
            synchronized (OkHttpUtils.class)
            {
                if (mInstance == null)
                {
                    mInstance = new OkHttpUtils();
                }
            }
        }
        return mInstance;
    }

    private void init(){
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        okHttpBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpBuilder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpBuilder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpBuilder.followRedirects(true);//允许被重定向
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null,null,null);
        okHttpBuilder.sslSocketFactory(sslParams.sSLSocketFactory,sslParams.trustManager);
        mOkHttpClient = okHttpBuilder.build();
    }

    public void request(OkHttpRequest request, OkListener listener){
        Call call = mOkHttpClient.newCall(request.generateRequest());
        call.enqueue(new JsonCallBack(listener));
    }

    public void uploadFile(OkHttpRequest request, OkListener listener){
        Call call = mOkHttpClient.newCall(request.generateRequest());
        call.enqueue(new UploadCallBack(listener));
    }

    public void downloadFile(OkDownloadRequest request, OkListener listener){
        Call call = mOkHttpClient.newCall(request.generateRequest());
        call.enqueue(new DownloadCallBack(request.getmDownloadFile(),listener));
    }
    public void getPicture(OkHttpRequest request, RequestListener listener){
        Call call = mOkHttpClient.newCall(request.generateRequest());
        call.enqueue(new BitMapCallBack(listener));
    }
    public void getResult(OkHttpRequest request, Callback callback){
        Call call = mOkHttpClient.newCall(request.generateRequest());
        call.enqueue(callback);
    }
}
