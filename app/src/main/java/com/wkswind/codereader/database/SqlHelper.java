package com.wkswind.codereader.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Administrator on 2015-11-16.
 */
public class SqlHelper extends DaoMaster.OpenHelper {
    private static final String DATABASE_NAME = "codereader.db";
    public SqlHelper(Context context) {
        super(context, DATABASE_NAME, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        super.onCreate(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
