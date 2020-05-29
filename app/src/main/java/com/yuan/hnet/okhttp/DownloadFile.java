package com.yuan.hnet.okhttp;

import java.util.HashMap;
import java.util.Map;

/**
 * 下载文件需要先创建对象
 * Created by  on 2017/1/18.
 */

public class DownloadFile {
    //下载url
    private String url;
    //下载文件本地路径
    private String filePath;
    //下载文件参数
    private Map<String, String> downloadParams = new HashMap<>();
    //请求头部
    private Map<String, String> headers = new HashMap<>();
    //0 POST ,1 GET
    private int downloadType = 0;


    public Map<String, String> getDownloadParams() {
        return downloadParams;
    }

    public void setDownloadParams(Map<String, String> downloadParams) {
        this.downloadParams = downloadParams;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getDownloadType() {
        return downloadType;
    }

    public void setDownloadType(int downloadType) {
        this.downloadType = downloadType;
    }
}
