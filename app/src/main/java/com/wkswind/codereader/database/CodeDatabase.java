package com.wkswind.codereader.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.OnCreate;
import net.simonvt.schematic.annotation.OnUpgrade;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by Administrator on 2015/3/31.
 */
@Database(version = CodeDatabase.VERSION,
    packageName = "com.wkswind.codereader.provider")
public final class CodeDatabase {
    public static final int VERSION = 1;
    private CodeDatabase(){

    }

    public static class Tables {
        @Table(StarsColumn.class)
        public static final String STARS = "stars";
        @Table(HistorysColumn.class)
        public static final String HISTORYS = "historys";
    }
    @OnCreate
    public static void onCreate(Context context, SQLiteDatabase db){
    }
    @OnUpgrade
    public static void onUpgrade(Context context, SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
