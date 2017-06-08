package com.myself.show.show.net.responceBean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/6/8 0008.
 */

@Entity
public class JoinUserWithSheep {
    @Id
    private Long id;

    private Long uId;

    private Long sId;

    public Long getSId() {
        return this.sId;
    }

    public void setSId(Long sId) {
        this.sId = sId;
    }

    public Long getUId() {
        return this.uId;
    }

    public void setUId(Long uId) {
        this.uId = uId;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Generated(hash = 1026260241)
    public JoinUserWithSheep(Long id, Long uId, Long sId) {
        this.id = id;
        this.uId = uId;
        this.sId = sId;
    }

    @Generated(hash = 297124019)
    public JoinUserWithSheep() {
    }
}