package com.yuan.hnet;

import android.content.Context;

import com.yuan.hnet.okhttp.DownloadFile;
import com.yuan.hnet.okhttp.OkHttpUtils;
import com.yuan.hnet.okhttp.UploadFile;
import com.yuan.hnet.okhttp.listener.OkFileLisener;
import com.yuan.hnet.okhttp.listener.OkListener;
import com.yuan.hnet.okhttp.request.OkDownloadRequest;
import com.yuan.hnet.okhttp.request.OkUploadRequest;

/**
 * 文件上传、下载
 * Created by xiatian on 16/11/29.
 */

public class FileTransfer {

    protected Context mContext;
    protected OkHttpUtils okHttpUtils;

    public FileTransfer(Context context) {
        this.mContext = context;
        this.okHttpUtils = OkHttpUtils.getInstance();
    }

    /**
     * 用于第三方上传使用
     *
     * @param uploadFile
     * @param okFileLisener
     * @param <T>
     */
    public <T> void upload(UploadFile uploadFile, final OkFileLisener<T> okFileLisener) {
        okHttpUtils.uploadFile(new OkUploadRequest(uploadFile, okFileLisener), new OkListener() {
            @Override
            public void onSuccess(String resultStr) {
                okFileLisener.onSuccess((T) resultStr);
            }

            @Override
            public void onFailure(OkHttpException e) {
                okFileLisener.onFailure(e);
            }
        });
    }

    public void download(DownloadFile downloadFile, OkListener okListener) {
        okHttpUtils.downloadFile(new OkDownloadRequest(downloadFile), okListener);
    }
}
