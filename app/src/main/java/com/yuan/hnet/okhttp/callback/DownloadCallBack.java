package com.yuan.hnet.okhttp.callback;

import com.yuan.hnet.OkHttpException;
import com.yuan.hnet.okhttp.DownloadFile;
import com.yuan.hnet.okhttp.listener.OkFileLisener;
import com.yuan.hnet.okhttp.listener.OkListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 下载文件回调
 * Created by xiatian on 16/11/18.
 */

public class DownloadCallBack extends CommonCallback {

    private DownloadFile mDownloadFile;

    public DownloadCallBack(DownloadFile downloadFile, OkListener listener) {
        super(listener);
        this.mDownloadFile = downloadFile;
    }

    @Override
    public void onResponse(Call call, Response response) {
        try {
            InputStream is = response.body().byteStream();
            final long totalBytes = response.body().contentLength();
            long writedBytes = 0;
            int len = 0;
            File file = new File(mDownloadFile.getFilePath());
            byte[] buf = new byte[1024];
            FileOutputStream fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                fos.write(buf,0,len);
                writedBytes+=len;
                final long finalWritedBytes = writedBytes;
                mDelieverHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        ((OkFileLisener)mListener).progress(finalWritedBytes,totalBytes);
                    }
                });
            }
            mDelieverHandler.post(new Runnable() {
                @Override
                public void run() {
                    mListener.onSuccess("Success");
                }
            });
            fos.flush();
            fos.close();
            is.close();
        } catch (final Exception e) {
            mDelieverHandler.post(new Runnable() {
                @Override
                public void run() {
                    mListener.onFailure(new OkHttpException(OkHttpException.OTHER_ERROR, e.getMessage()));
                }
            });
        }

    }
}
