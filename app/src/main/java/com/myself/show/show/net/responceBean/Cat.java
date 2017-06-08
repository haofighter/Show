package com.myself.show.show.net.responceBean;

import com.myself.show.show.sql.CatDao;
import com.myself.show.show.sql.DaoSession;
import com.myself.show.show.sql.UserDao;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;

/**
 * Created by Administrator on 2017/6/8 0008.
 */

@Entity
public class Cat {
    @Id
    private Long id;

    private String name;

    private long fk_userId;

    @ToOne(joinProperty = "fk_userId")
    private User user;

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

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1320727664)
    public void setUser(@NotNull User user) {
        if (user == null) {
            throw new DaoException(
                    "To-one property 'fk_userId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.user = user;
            fk_userId = user.getId();
            user__resolvedKey = fk_userId;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 2013157836)
    public User getUser() {
        long __key = this.fk_userId;
        if (user__resolvedKey == null || !user__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UserDao targetDao = daoSession.getUserDao();
            User userNew = targetDao.load(__key);
            synchronized (this) {
                user = userNew;
                user__resolvedKey = __key;
            }
        }
        return user;
    }

    @Generated(hash = 251390918)
    private transient Long user__resolvedKey;

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1787171786)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCatDao() : null;
    }

    /** Used for active entity operations. */
    @Generated(hash = 1725686194)
    private transient CatDao myDao;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    public long getFk_userId() {
        return this.fk_userId;
    }

    public void setFk_userId(long fk_userId) {
        this.fk_userId = fk_userId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Generated(hash = 1225191413)
    public Cat(Long id, String name, long fk_userId) {
        this.id = id;
        this.name = name;
        this.fk_userId = fk_userId;
    }

    @Generated(hash = 205319056)
    public Cat() {
    }
}
