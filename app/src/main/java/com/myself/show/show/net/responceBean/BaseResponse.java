package com.myself.show.show.net.responceBean;

/**
 * Created by Administrator on 2017/7/25.
 */

public class BaseResponse {

    /**
     * suc : n
     * msg : 没有上传的文件！
     */

    private String suc;
    private String msg;

    public String getSuc() {
        return suc;
    }

    public void setSuc(String suc) {
        this.suc = suc;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
