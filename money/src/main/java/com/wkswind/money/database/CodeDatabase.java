package com.wkswind.money.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.wkswind.money.database.schema.AccountColumns;
import com.wkswind.money.database.schema.TransactionColumns;
import com.wkswind.money.database.schema.TransactionTypeColumns;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.OnCreate;
import net.simonvt.schematic.annotation.OnUpgrade;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by Administrator on 2015/3/31.
 */
@Database(version = CodeDatabase.VERSION,
    packageName = "com.wkswind.money.provider")
public final class CodeDatabase {
    public static final int VERSION =2;
    private CodeDatabase(){

    }

    public static class Tables {
        @Table(TransactionTypeColumns.class)
        public static final String TRANSACTION_TYPES = "transaction_types";
        @Table(TransactionColumns.class)
        public static final String TRANSACTIONS = "transactions";
        @Table(AccountColumns.class)
        public static final String ACCOUNS = "accounts";
    }
    @OnCreate
    public static void onCreate(Context context, SQLiteDatabase db){
    }
    @OnUpgrade
    public static void onUpgrade(Context context, SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
