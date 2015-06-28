package com.wkswind.password.databases;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by Administrator on 2015/6/9.
 */
@Database(name=AppDatabase.NAME, version = AppDatabase.VERSION)
public final class AppDatabase {
    public static final String NAME = "password";
    public static final int VERSION = 7;

    public static final int STATUS_NORMAL = 0;
    public static final int STATUS_DELETED = 65536;
}
