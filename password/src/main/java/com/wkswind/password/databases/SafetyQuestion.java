package com.wkswind.password.databases;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.ForeignKeyReference;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.container.ForeignKeyContainer;

/**
 * Created by Administrator on 2015/6/9.
 */
@Table(databaseName = AppDatabase.NAME)
public class SafetyQuestion extends BaseModel{
    @Column
    @PrimaryKey(autoincrement = true)
    long id;
    @Column
    String question;
    String answer;
    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "password_id",
                    columnType = Long.class,
                    foreignColumnName = "id")},
            saveForeignKeyModel = false)
    ForeignKeyContainer<Password> password;
    @Column
    int status;
}
