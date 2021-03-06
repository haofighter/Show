package com.myself.show.show.net.responceBean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinEntity;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.myself.show.show.sql.DaoSession;
import com.myself.show.show.sql.UserDao;
import com.myself.show.show.sql.SheepDao;

/**
 * Created by Administrator on 2017/6/8 0008.
 */

@Entity
public class Sheep {
    @Id
    private Long id;

    private String name;

    @ToMany
    @JoinEntity(
            entity = JoinUserWithSheep.class,
            sourceProperty = "sId",
            targetProperty = "uId"
    )
    private List<User> users;

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

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1027274768)
    public synchronized void resetUsers() {
        users = null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 2014726554)
    public List<User> getUsers() {
        if (users == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UserDao targetDao = daoSession.getUserDao();
            List<User> usersNew = targetDao._querySheep_Users(id);
            synchronized (this) {
                if(users == null) {
                    users = usersNew;
                }
            }
        }
        return users;
    }

    /** Used for active entity operations. */
    @Generated(hash = 1294021435)
    private transient SheepDao myDao;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

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

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 892965363)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getSheepDao() : null;
    }

    @Generated(hash = 1871150570)
    public Sheep(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Generated(hash = 152960452)
    public Sheep() {
    }
}