package com.wkswind.password.databases;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by Administrator on 2015/6/9.
 */
@Table(databaseName = AppDatabase.NAME)
public class PasswordType extends BaseModel {
    @Column
    @PrimaryKey(autoincrement = true)
    long id;
    @Column
    String name;
    @Column
    String remark;
    @Column
    int colorBackground;
    @Column
    int status;
}
