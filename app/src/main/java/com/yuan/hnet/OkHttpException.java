package com.yuan.hnet;

public class OkHttpException {



    public static final String NETWORK_ERROR_MSG = "NetworkException";
    public static final String JSON_ERROR_MSG = "JsonException";
    public static final String EMPTY_ERROR_MSG = "DataEmpty";
    public static final String OTHER_ERROR_MSG = "ErrorMsg";

    public static final int NETWORK_ERROR = -1;//the network error
    public static final int JSON_ERROR = -2;//the json error
    public static final int EMPTY_ERROR = -3;//the empty error
    public static final int LOGIN_TIMEOUT_ERROR = -4;//登录超时
    public static final int OTHER_ERROR = -5;//the unknow error

    /**
     * error code
     */
    private int ecode;
    /**
     * error message
     */
    private String emsg;

    public OkHttpException(int ecode, String emsg){
        this.ecode = ecode;
        this.emsg = emsg;
    }

    public int getEcode() {
        return ecode;
    }

    public String getEmsg() {
        return emsg+"";
    }

}
