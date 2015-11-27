package com.wkswind.codereader.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.wkswind.codereader.R;

/**
 * Created by Administrator on 2015-11-16.
 */
public class SqlHelper extends DaoMaster.OpenHelper {
    private static final String DATABASE_NAME = "codereader.db";
    private Context context;
    private String defaultScanRoot;
    public SqlHelper(Context context) {
        super(context, DATABASE_NAME, null);
        this.context = context;
        if(Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED){
            defaultScanRoot = Environment.getExternalStorageDirectory().getAbsolutePath();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        super.onCreate(db);

        db.beginTransaction();
        try {
            String[] typeNames = context.getResources().getStringArray(R.array.doc_types);
            String[] typeExtensions = context.getResources().getStringArray(R.array.doc_type_extensions);
            for(int i=0,j=typeNames.length;i<j;i++){
                ContentValues values = new ContentValues();
                values.put(DocTypeDao.Properties.Name.columnName, typeNames[i]);
                values.put(DocTypeDao.Properties.Extensions.columnName, typeExtensions[i]);
                values.put(DocTypeDao.Properties.ScanRoot.columnName, defaultScanRoot);
                db.insert(DocTypeDao.TABLENAME,null,values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

//        DocTypeDao dao = CodeReaderApplication.getSession().getDocTypeDao();
//        dao.insertInTx(types);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        DaoMaster.dropAllTables(db, true);
        onCreate(db);
    }

}
