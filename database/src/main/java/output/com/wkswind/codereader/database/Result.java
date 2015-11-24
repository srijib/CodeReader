package com.wkswind.codereader.database;

import com.wkswind.codereader.database.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "RESULT".
 */
public class Result {

    private String absolutePath;
    private String extension;
    private Long docTypeId;
    private Long id;
    private Integer status;
    private String remark;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient ResultDao myDao;

    private DocType docType;
    private Long docType__resolvedKey;


    public Result() {
    }

    public Result(Long id) {
        this.id = id;
    }

    public Result(String absolutePath, String extension, Long docTypeId, Long id, Integer status, String remark) {
        this.absolutePath = absolutePath;
        this.extension = extension;
        this.docTypeId = docTypeId;
        this.id = id;
        this.status = status;
        this.remark = remark;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getResultDao() : null;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Long getDocTypeId() {
        return docTypeId;
    }

    public void setDocTypeId(Long docTypeId) {
        this.docTypeId = docTypeId;
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
    public DocType getDocType() {
        Long __key = this.docTypeId;
        if (docType__resolvedKey == null || !docType__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            DocTypeDao targetDao = daoSession.getDocTypeDao();
            DocType docTypeNew = targetDao.load(__key);
            synchronized (this) {
                docType = docTypeNew;
            	docType__resolvedKey = __key;
            }
        }
        return docType;
    }

    public void setDocType(DocType docType) {
        synchronized (this) {
            this.docType = docType;
            docTypeId = docType == null ? null : docType.getId();
            docType__resolvedKey = docTypeId;
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
