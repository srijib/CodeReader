package com.wkswind.codereader.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.wkswind.codereader.base.CodeReaderApplication;
import com.wkswind.codereader.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015-11-16.
 */
public class SqlHelper extends DaoMaster.OpenHelper {
    private static final String DATABASE_NAME = "codereader.db";
    private Context context;
    public SqlHelper(Context context) {
        super(context, DATABASE_NAME, null);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        super.onCreate(db);
        String[] typeNames = context.getResources().getStringArray(R.array.doc_types);
        String[] typeExtensions = context.getResources().getStringArray(R.array.doc_type_extensions);
        ArrayList<DocType> types = new ArrayList<>(typeNames.length);
        for(int i=0,j=typeNames.length;i<j;i++){
            DocType type = new DocType();
            type.setName(typeNames[i]);
            if(typeExtensions.length > i){
                type.setExtensions(typeExtensions[i]);
                types.add(type);
            }
        }

        DocTypeDao dao = CodeReaderApplication.getSession().getDocTypeDao();
        dao.insertInTx(types);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
