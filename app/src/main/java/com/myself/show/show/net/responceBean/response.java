package com.myself.show.show.net.responceBean;

/**
 * Created by ljx on 2016/6/13.
 */
public class response {
    String ErrCode;
    String ErrMsg;
    String Response;

    public String getErrCode() {
        return ErrCode;
    }

    public void setErrCode(String errCode) {
        ErrCode = errCode;
    }

    public String getErrMsg() {
        return ErrMsg;
    }

    public void setErrMsg(String errMsg) {
        ErrMsg = errMsg;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }

    @Override
    public String toString() {
        return "response{" +
                "ErrCode='" + ErrCode + '\'' +
                ", ErrMsg='" + ErrMsg + '\'' +
                ", Response='" + Response + '\'' +
                '}';
    }
}
