package com.wkswind.codereader.database;

import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 

/**
 * Entity mapped to table "HISTORY".
 */
public class History {

    private java.util.Date lastReadTime;
    private Long resultId;
    private Long id;
    private Integer status;
    private String remark;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient HistoryDao myDao;

    private Result result;
    private Long result__resolvedKey;


    public History() {
    }

    public History(Long id) {
        this.id = id;
    }

    public History(java.util.Date lastReadTime, Long resultId, Long id, Integer status, String remark) {
        this.lastReadTime = lastReadTime;
        this.resultId = resultId;
        this.id = id;
        this.status = status;
        this.remark = remark;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getHistoryDao() : null;
    }

    public java.util.Date getLastReadTime() {
        return lastReadTime;
    }

    public void setLastReadTime(java.util.Date lastReadTime) {
        this.lastReadTime = lastReadTime;
    }

    public Long getResultId() {
        return resultId;
    }

    public void setResultId(Long resultId) {
        this.resultId = resultId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    /** To-one relationship, resolved on first access. */
    public Result getResult() {
        Long __key = this.resultId;
        if (result__resolvedKey == null || !result__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ResultDao targetDao = daoSession.getResultDao();
            Result resultNew = targetDao.load(__key);
            synchronized (this) {
                result = resultNew;
            	result__resolvedKey = __key;
            }
        }
        return result;
    }

    public void setResult(Result result) {
        synchronized (this) {
            this.result = result;
            resultId = result == null ? null : result.getId();
            result__resolvedKey = resultId;
        }
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

}