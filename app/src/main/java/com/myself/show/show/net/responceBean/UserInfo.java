package com.myself.show.show.net.responceBean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/6/8 0008.
 */


@Entity
public class UserInfo {
    /**
     * user_id : 24efd8ac-8e9b-1755-ca9e-f7c7b45a71b5
     * username : app_test
     * type : 4
     */

    @Id
    private Long id;

    private String user_id;
    private String username;
    private String type;
    @Generated(hash = 352235654)
    public UserInfo(Long id, String user_id, String username, String type) {
        this.id = id;
        this.user_id = user_id;
        this.username = username;
        this.type = type;
    }
    @Generated(hash = 1279772520)
    public UserInfo() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUser_id() {
        return this.user_id;
    }
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }


}

