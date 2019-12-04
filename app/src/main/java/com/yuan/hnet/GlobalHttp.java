package com.yuan.hnet;

import android.app.ProgressDialog;
import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.yuan.hnet.okhttp.OkHttpUtils;
import com.yuan.hnet.okhttp.UploadFile;
import com.yuan.hnet.okhttp.listener.OkFileLisener;
import com.yuan.hnet.okhttp.listener.OkListener;
import com.yuan.hnet.okhttp.listener.RequestListener;
import com.yuan.hnet.okhttp.request.OkGetRequest;
import com.yuan.hnet.okhttp.request.OkHttpRequest;
import com.yuan.hnet.okhttp.request.OkPostRequest;
import com.yuan.hnet.okhttp.request.OkUploadRequest;
import com.yuan.hnet.utils.ClassUtils;
import com.yuan.hnet.utils.NetUtils;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * http 请求入口封装
 * Created by xiatian on 16/11/29.
 */

public abstract class GlobalHttp {

    protected Context mContext;
    protected OkHttpUtils okHttpUtils;
    protected OkHttpRequest okHttpRequest;
    protected Map<String, String> mParams = new HashMap<>();
    protected Map<String, String> mHeaders = new HashMap<>();
    protected String bodyJsonParams;

    private boolean mShowProgress = true;
    protected ProgressDialog progressDialog;
    private RequestType mRequestType = RequestType.POST;

    public GlobalHttp(Context context) {
        this.mContext = context;
        this.okHttpUtils = OkHttpUtils.getInstance();
        this.progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("请您耐心等待");
    }

    /**
     * 请求入口,默认显示加载圈
     *
     * @param requestListener
     * @param url
     * @param <T>
     */
    protected <T> void request(final RequestListener<T> requestListener, final String url) {
        if (frontCheck(requestListener)) return;
        buildRequest(url);
        okHttpUtils.request(okHttpRequest, new OkListener() {
            @Override
            public void onSuccess(String resultStr) {
                loadingProgress(false);
                mShowProgress = true;
//                Log.e("网络请求", "请求地址:"+url+"\n"+"请求结果:" + resultStr);
                analyJson(resultStr, requestListener);
            }

            @Override
            public void onFailure(OkHttpException e) {
                loadingProgress(false);
                mShowProgress = true;
                requestListener.onFailure(e);
            }
        });
    }


    /**
     * 文件上传入口
     *
     * @param uploadFile
     * @param okFileLisener
     * @param <T>
     */
    public <T> void upload(UploadFile uploadFile, final OkFileLisener<T> okFileLisener) {
        if (frontCheck(okFileLisener)) return;
        okHttpUtils.uploadFile(new OkUploadRequest(uploadFile, okFileLisener), new OkListener() {
            @Override
            public void onSuccess(String resultStr) {
                loadingProgress(false);
                mShowProgress = true;
                analyJson(resultStr, okFileLisener);
            }

            @Override
            public void onFailure(OkHttpException e) {
                loadingProgress(false);
                mShowProgress = true;
                okFileLisener.onFailure(e);
            }
        });
    }

    protected abstract <T> void analyJson(String result, final RequestListener<T> requestListener);

    protected <T> void backResult(String resultStr, RequestListener<T> requestListener) {
        try {
            backWhenSuccess(requestListener, resultStr);
        } catch (Exception e) {
            requestListener.onFailure(new OkHttpException(OkHttpException.JSON_ERROR, e.getMessage()));
        }
    }


    private void buildRequest(String url) {
        Map<String, String> headers = new HashMap<>();
        headers.putAll(mHeaders);
        mHeaders.clear();
        switch (mRequestType) {
            case GET:
                HashMap<String, String> getMap = new HashMap<>();
                getMap.putAll(mParams);
                mParams.clear();
                okHttpRequest = new OkGetRequest(url, getMap, headers);
                break;
            case POST:
                HashMap<String, String> postMap = new HashMap<>();
                postMap.putAll(mParams);
                mParams.clear();
                okHttpRequest = new OkPostRequest(url, postMap, headers);
                okHttpRequest.setBodyJsonParams(bodyJsonParams);
                bodyJsonParams="";
                break;
            default:
                break;
        }
    }

    private <T> boolean frontCheck(RequestListener<T> requestListener) {
        if (!NetUtils.isConnected(mContext)) {
            requestListener.onFailure(new OkHttpException(OkHttpException.NETWORK_ERROR, OkHttpException.NETWORK_ERROR_MSG));
            return true;
        }
        if (mShowProgress) {
            loadingProgress(true);
        }
        return false;
    }

    /**
     * 自定义显示加载圈入口
     *
     * @param requestListener
     * @param url
     * @param showProgress
     * @param <T>
     */
    protected <T> void request(final RequestListener<T> requestListener, String url, boolean showProgress) {
        this.mShowProgress = showProgress;
        this.mRequestType = RequestType.POST;
        request(requestListener, url);
    }

    protected <T> void requestPost(final RequestListener<T> requestListener, String url) {
        this.mShowProgress = true;
        this.mRequestType = RequestType.POST;
        request(requestListener, url);
    }

    protected <T> void requestGet(final RequestListener<T> requestListener, String url) {
        this.mShowProgress = true;
        this.mRequestType = RequestType.GET;
        request(requestListener, url);
    }

    protected <T> void requestGet(final RequestListener<T> requestListener, String url, boolean showProgress) {
        this.mShowProgress = showProgress;
        this.mRequestType = RequestType.GET;
        request(requestListener, url);
    }

    /**
     * 自定显示加载圈及请求方式入口
     *
     * @param requestListener
     * @param url
     * @param requestType
     * @param showProgress
     * @param <T>
     */
    protected <T> void request(final RequestListener<T> requestListener, String url, RequestType requestType, boolean showProgress) {
        this.mShowProgress = showProgress;
        this.mRequestType = requestType;
        request(requestListener, url);
    }

    /**
     * 请求成功时，根据请求类型返回数据
     *
     * @param requestListener
     * @param resultStr
     * @param <T>
     */
    private <T> void backWhenSuccess(RequestListener<T> requestListener, String resultStr) {
        //获取接口上泛型类型
        final Type type = requestListener.getClass().getGenericInterfaces()[0];
        //获取之上泛型类类型
        final Class clz = ClassUtils.getClass(type);
        if (clz == List.class) {//class为list.class，则获取内泛型类型
            requestListener.onSuccess((T) JSON.parseArray(resultStr, ClassUtils.getActualClass(type)));
        } else if (clz == String.class) {//请求若为String.class,直接返回
            requestListener.onSuccess((T) resultStr);
        } else {//非list.class,非string.class,返回fastjson转译类型
            requestListener.onSuccess((T) JSON.parseObject(resultStr, clz));
        }
    }

    /**
     * 加载progress控制
     *
     * @param show
     */
    private void loadingProgress(boolean show) {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                if (!show) {
                    progressDialog.dismiss();
                }
            } else {
                if (show) {
                    progressDialog.show();
                }
            }
        }
    }

    /**
     * params加参数，key位于偶数index，对应value位于index+1
     *
     * @param params
     */
    protected void addMapParams(String... params) {
        String[] paramsArray = params.clone();
        //mParams.clear();
        for (int i = 0; i < paramsArray.length; i += 2) {
            mParams.put(paramsArray[i] + "", paramsArray[i + 1] + "");
        }
        addGlobalParams();
    }

    protected abstract void addGlobalParams();

    protected void addJsonParams(String jsonParams){
        bodyJsonParams = jsonParams;
        addGlobalParams();
    }

    public enum RequestType {
        POST,
        GET
    }
}
