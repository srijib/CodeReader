package com.wkswind.money.database.bean;

import android.database.Cursor;

/**
 * Created by Administrator on 2015/5/19.
 */
public class Transactions extends Bases {
    private String account;
    private String description;
    private long timeInMs;
    private String details;
    private int type;
    private int transactionStatus;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getTimeInMs() {
        return timeInMs;
    }

    public void setTimeInMs(long timeInMs) {
        this.timeInMs = timeInMs;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(int transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    @Override
    public void cursorToBean(Cursor cursor) {
        super.cursorToBean(cursor);
        if(cursor != null ){

        }
    }
}
