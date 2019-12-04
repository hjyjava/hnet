package com.yuan.hnet.okhttp;

import java.util.HashMap;
import java.util.Map;

/**
 * 上传文件需要创建的对象
 * Created by  on 2017/1/17.
 */

public class UploadFile {
    //上传url
    private String url;
    //上传类型 0:单文件上传，1:批量上传
    private int type;
    //上传文件本地路径
    private String filePath;
//    private List<String> filePathList = new ArrayList<>();
    //批量上传map key为文件名称，value为文件本地路径
    private Map<String, String> filePathMap = new HashMap<>();
    //上传文件对应key名称
    private String keyName;
    //上传文件服务器获取名称
    private String serverFileName;
    //上传文件参数
    private Map<String, String> uploadParams = new HashMap<>();
    //请求头部
    private Map<String, String> headers = new HashMap<>();

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

//    public List<String> getFilePathList() {
//        return filePathList;
//    }
//
//    public void setFilePathList(List<String> filePathList) {
//        this.filePathList = filePathList;
//    }


    public Map<String, String> getFilePathMap() {
        return filePathMap;
    }

    public void setFilePathMap(Map<String, String> filePathMap) {
        this.filePathMap = filePathMap;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getServerFileName() {
        return serverFileName;
    }

    public void setServerFileName(String serverFileName) {
        this.serverFileName = serverFileName;
    }

    public Map<String, String> getUploadParams() {
        return uploadParams;
    }

    public void setUploadParams(Map<String, String> uploadParams) {
        this.uploadParams = uploadParams;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

}
