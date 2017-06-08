package com.myself.show.show.net.responceBean;

/**
 * Created by Administrator on 2017/6/8 0008.
 */

public class LoginResponse {

    /**
     * suc : y
     * msg : 登录成功
     * data : {"user_id":"24efd8ac-8e9b-1755-ca9e-f7c7b45a71b5","username":"app_test","type":"4"}
     */

    private String suc;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * user_id : 24efd8ac-8e9b-1755-ca9e-f7c7b45a71b5
         * username : app_test
         * type : 4
         */

        private String user_id;
        private String username;
        private String type;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
