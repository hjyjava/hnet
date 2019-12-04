package com.yuan.hnet.okhttp.request;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.yuan.hnet.OkHttpException;
import com.yuan.hnet.okhttp.UploadFile;
import com.yuan.hnet.okhttp.listener.OkFileLisener;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/**
 * Created by xiatian on 16/11/18.
 */

public class OkUploadRequest extends OkHttpRequest {

    private UploadFile mUploadFile;
    private OkFileLisener mOkFileLisener;
    protected Handler mDelieverHandler;

    public OkUploadRequest(UploadFile uploadFile, OkFileLisener okFileLisener) {
        super(uploadFile.getUrl(), uploadFile.getUploadParams(), uploadFile.getHeaders());
        this.mUploadFile = uploadFile;
        this.mOkFileLisener = okFileLisener;
        this.mDelieverHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    protected Request buildRequest(RequestBody requestBody) {
        return builder.url(mUploadFile.getUrl()).post(buildRequestBody()).build();
    }

    @Override
    protected RequestBody buildRequestBody() {
        if (mUploadFile == null)
            return null;
        MultipartBody.Builder uploadBuilder = new MultipartBody.Builder();
        uploadBuilder.setType(MultipartBody.FORM);
        try {
            addUploadParams(uploadBuilder, mUploadFile.getUploadParams());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mUploadFile.getType() == 0) {
            if (!TextUtils.isEmpty(mUploadFile.getFilePath())) {
                final File file = new File(mUploadFile.getFilePath());
                if (file.exists()) {
                    uploadBuilder.addFormDataPart(mUploadFile.getKeyName(), mUploadFile.getServerFileName(),
                            buildFileRequestBody(file));
                }
            }

        } else if (mUploadFile.getType() == 1) {
            Map<String, String> filePathMap = mUploadFile.getFilePathMap();
            for (Map.Entry<String, String> entry : filePathMap.entrySet()) {
                if (!TextUtils.isEmpty(entry.getValue())) {
                    final File file = new File(entry.getValue());
                    if (file.exists()) {
                        uploadBuilder.addFormDataPart(mUploadFile.getKeyName(), String.valueOf(entry),
                                buildFileRequestBody(file));
                    }
                }
            }
        }
        return uploadBuilder.build();
    }

    private void addUploadParams(MultipartBody.Builder uploadBuilder, Map<String, String> params) throws Exception {
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                uploadBuilder.addFormDataPart(entry.getKey(), getValueEncoded(entry.getValue()));
            }
        }
    }

    private RequestBody buildFileRequestBody(final File file) {
        return new RequestBody() {
            @Override
            public MediaType contentType() {
//                return MediaType.parse("image/jpeg");
                return MediaType.parse("multipart/form-data");
            }

            @Override
            public long contentLength() throws IOException {
                return file.length();
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                try {
                    Source source = Okio.source(file);
                    Buffer buffer = new Buffer();
                    long writedBytes = 0;
                    final long totalBytes = contentLength();
                    for (long readCount; (readCount = source.read(buffer, 2048)) != -1; ) {
                        sink.write(buffer, readCount);

                        writedBytes += readCount;
                        final long finalWritedBytes = writedBytes;
                        mDelieverHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mOkFileLisener.progress(finalWritedBytes, totalBytes);
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    mOkFileLisener.onFailure(new OkHttpException(OkHttpException.OTHER_ERROR, e.getMessage()));
                }
            }
        };
    }
}
