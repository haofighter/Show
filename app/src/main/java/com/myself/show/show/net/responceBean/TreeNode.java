package com.myself.show.show.net.responceBean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.myself.show.show.sql.DaoSession;
import com.myself.show.show.sql.TreeNodeDao;

/**
 * Created by Administrator on 2017/6/8 0008.
 */

@Entity
public class TreeNode {
    @Id
    private Long id;

    private Long parentId;

    @ToOne(joinProperty = "parentId")
    private TreeNode parent;

    @ToMany(referencedJoinProperty = "parentId")
    private List<TreeNode> children;

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
    @Generated(hash = 1590975152)
    public synchronized void resetChildren() {
        children = null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 807015408)
    public List<TreeNode> getChildren() {
        if (children == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TreeNodeDao targetDao = daoSession.getTreeNodeDao();
            List<TreeNode> childrenNew = targetDao._queryTreeNode_Children(id);
            synchronized (this) {
                if(children == null) {
                    children = childrenNew;
                }
            }
        }
        return children;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1210663804)
    public void setParent(TreeNode parent) {
        synchronized (this) {
            this.parent = parent;
            parentId = parent == null ? null : parent.getId();
            parent__resolvedKey = parentId;
        }
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 849316563)
    public TreeNode getParent() {
        Long __key = this.parentId;
        if (parent__resolvedKey == null || !parent__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TreeNodeDao targetDao = daoSession.getTreeNodeDao();
            TreeNode parentNew = targetDao.load(__key);
            synchronized (this) {
                parent = parentNew;
                parent__resolvedKey = __key;
            }
        }
        return parent;
    }

    @Generated(hash = 1293412156)
    private transient Long parent__resolvedKey;

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 575492216)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTreeNodeDao() : null;
    }

    /** Used for active entity operations. */
    @Generated(hash = 2127305444)
    private transient TreeNodeDao myDao;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    public Long getParentId() {
        return this.parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Generated(hash = 688201735)
    public TreeNode(Long id, Long parentId) {
        this.id = id;
        this.parentId = parentId;
    }

    @Generated(hash = 440943504)
    public TreeNode() {
    }
}