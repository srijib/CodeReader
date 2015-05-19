package com.wkswind.money.database.bean;

import android.database.Cursor;

import com.wkswind.money.database.schema.BaseColumns;

/**
 * Created by Administrator on 2015/5/19.
 */
public abstract class Bases {
    private int id;
    private int dataStatus;
    private int status;
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(int dataStatus) {
        this.dataStatus = dataStatus;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void cursorToBean(Cursor cursor){
        if(cursor != null){
            id = cursor.getInt(cursor.getColumnIndex(BaseColumns.ID));
            dataStatus = cursor.getInt(cursor.getColumnIndex(BaseColumns.dataStatus));
            status = cursor.getInt(cursor.getColumnIndex(BaseColumns.status));
            remark = cursor.getString(cursor.getColumnIndex(BaseColumns.remark));
        }
    }
}
