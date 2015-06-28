package com.wkswind.password.databases.migrations;

import android.database.sqlite.SQLiteDatabase;

import com.raizlabs.android.dbflow.annotation.Migration;
import com.raizlabs.android.dbflow.runtime.TransactionManager;
import com.raizlabs.android.dbflow.runtime.transaction.process.ProcessModelInfo;
import com.raizlabs.android.dbflow.runtime.transaction.process.SaveModelTransaction;
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration;
import com.raizlabs.android.dbflow.sql.migration.BaseMigration;
import com.raizlabs.android.dbflow.sql.migration.UpdateTableMigration;
import com.wkswind.password.databases.AppDatabase;
import com.wkswind.password.databases.PasswordType;

import java.util.ArrayList;

/**
 * Created by 南风不竞 on 2015-06-22.
 */
@Migration(version = 0,databaseName = AppDatabase.NAME)
public class InitialContentsMigration extends AlterTableMigration<PasswordType> {
    public InitialContentsMigration(){
        super(PasswordType.class);
    }

    @Override
    public void onPreMigrate() {
        super.onPreMigrate();
        ArrayList<PasswordType> types = new ArrayList<>();
        for(int i=0,j=PasswordTypeConsts.NAMES.size();i<j;i++){
            PasswordType type = new PasswordType();
            type.setName(PasswordTypeConsts.NAMES.get(i));
            type.setRemark(PasswordTypeConsts.REMARKS.get(i));
            type.setExcludeFromEdit(i==0);
            type.setQueryAll(i==0);
//            type.save();
            types.add(type);

        }
        TransactionManager.getInstance().addTransaction(new SaveModelTransaction(ProcessModelInfo.withModels(types)));
    }

}
