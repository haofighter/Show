package com.myself.show.show.net.responceBean;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;
import com.myself.show.show.sql.DaoSession;
import com.myself.show.show.sql.UserInfoDao;
import com.myself.show.show.sql.LoginResponseDao;

/**
 * Created by Administrator on 2017/6/8 0008.
 */
@Entity
public class LoginResponse {

    /**
     * suc : y
     * msg : 登录成功
     * data : {"user_id":"24efd8ac-8e9b-1755-ca9e-f7c7b45a71b5","username":"app_test","type":"4"}
     */

    private String suc;
    private String msg;

    /**
     * 一对一 表关联   只需要定义一个id 用于关联下一个model  无需在下级model中设置任何东西
     */
    private Long userId;
    @ToOne(joinProperty = "userId")
    private UserInfo data;




    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1624444268)
    private transient LoginResponseDao myDao;
    @Generated(hash = 1814024313)
    public LoginResponse(String suc, String msg, Long userId) {
        this.suc = suc;
        this.msg = msg;
        this.userId = userId;
    }
    @Generated(hash = 1521785954)
    public LoginResponse() {
    }
    public String getSuc() {
        return this.suc;
    }
    public void setSuc(String suc) {
        this.suc = suc;
    }
    public String getMsg() {
        return this.msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public Long getUserId() {
        return this.userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    @Generated(hash = 998359)
    private transient Long data__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1693240403)
    public UserInfo getData() {
        Long __key = this.userId;
        if (data__resolvedKey == null || !data__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UserInfoDao targetDao = daoSession.getUserInfoDao();
            UserInfo dataNew = targetDao.load(__key);
            synchronized (this) {
                data = dataNew;
                data__resolvedKey = __key;
            }
        }
        return data;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1898727427)
    public void setData(UserInfo data) {
        synchronized (this) {
            this.data = data;
            userId = data == null ? null : data.getId();
            data__resolvedKey = userId;
        }
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1361946302)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getLoginResponseDao() : null;
    }

}
