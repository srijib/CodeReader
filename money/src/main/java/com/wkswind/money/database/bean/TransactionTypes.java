package com.wkswind.money.database.bean;

import android.database.Cursor;

import com.wkswind.money.database.schema.TransactionTypeColumns;

/**
 * Created by Administrator on 2015/5/19.
 */
public class TransactionTypes extends Bases {
    private int transactionType;
    private String name;
    private int periodicity;

    public int getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(int transactionType) {
        this.transactionType = transactionType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(int periodicity) {
        this.periodicity = periodicity;
    }

    @Override
    public void cursorToBean(Cursor cursor) {
        super.cursorToBean(cursor);
        if(cursor != null){
            transactionType = cursor.getInt(cursor.getColumnIndex(TransactionTypeColumns.type));
            name = cursor.getString(cursor.getColumnIndex(TransactionTypeColumns.name));
            periodicity = cursor.getInt(cursor.getColumnIndex(TransactionTypeColumns.periodicity));
        }
    }
}
