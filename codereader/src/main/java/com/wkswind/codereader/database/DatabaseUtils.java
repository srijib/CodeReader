package com.wkswind.codereader.database;

import android.content.Context;

import com.wkswind.codereader.base.CodeReaderApplication;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2015-12-1.
 */
public class DatabaseUtils {
    public static final Observable<List<DocType>> getAllDocTypes(Context context){
        return Observable.create(new Observable.OnSubscribe<List<DocType>>() {
            @Override
            public void call(Subscriber<? super List<DocType>> subscriber) {
                try {
                    List<DocType> result = CodeReaderApplication.getSession().getDocTypeDao().queryBuilder().list();
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(result);
                    }
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onCompleted();
                    }
                } catch (Throwable e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    /**
     * 根据文件类型查询，优先查数据库缓存，接着遍历文件并更新缓存记录
     * @param context
     * @param docType
     * @return
     */
    public static final Observable<List<Result>> getResultsByType(Context context,final DocType docType){
        final long typeId = docType.getId();

        return Observable.create(new Observable.OnSubscribe<List<Result>>() {
            @Override
            public void call(Subscriber<? super List<Result>> subscriber) {
                try {
                    /**
                     * 先查询数据库缓存
                     */
                    List<Result> cache = CodeReaderApplication.getSession().getResultDao().queryBuilder().where(ResultDao.Properties.DocTypeId.eq(typeId)).list();
                    if(!subscriber.isUnsubscribed()){
                        subscriber.onNext(cache);
                    }
                    /**
                     * 查询ScanRoot下对应的文件，并保存至数据库
                     */
                    final File directory = new File(docType.getScanRoot());
                    Collection<File> files = FileUtils.listFiles(directory, docType.getExtensions().split(","), true);
                    ArrayList<Result> result = null;
                    if(!files.isEmpty()){
                        result = new ArrayList<Result>(files.size());
                        for (File file : files) {
                            Result item = new Result();
                            item.setAbsolutePath(file.getAbsolutePath());
                            item.setDocTypeId(typeId);
                            item.setExtension(FilenameUtils.getExtension(file.getName()));
                            result.add(item);
                        }
                        CodeReaderApplication.getSession().getResultDao().deleteAll();
                        CodeReaderApplication.getSession().getResultDao().insertInTx(result);
                    }
                    if(!subscriber.isUnsubscribed()){
                        subscriber.onNext(result) ;
                        subscriber.onCompleted();
                    }
                }catch(Throwable e) {
                    if(!subscriber.isUnsubscribed()){
                        subscriber.onError(e);
                    }
                }

            }
        });
    }

    /**
     * 查询
     * @param context
     * @return
     */
    public static Observable<List<History>> getHistory(Context context){
        return Observable.create(new Observable.OnSubscribe<List<History>>() {
            @Override
            public void call(Subscriber<? super List<History>> subscriber) {
                try {
                    List<History> history = CodeReaderApplication.getSession().getHistoryDao().queryBuilder().orderDesc(HistoryDao.Properties.LastReadTime).list();
                    if(!subscriber.isUnsubscribed()){
                        subscriber.onNext(history);
                        subscriber.onCompleted();
                    }
                } catch (Throwable e){
                    if(!subscriber.isUnsubscribed()){
                        subscriber.onError(e);
                    }
                }

            }
        });
    }

    /**
     * 保存浏览记录
     * @param context
     * @param resultId
     * @return
     */
    public static void saveHistory(Context context, long resultId){

        History history = CodeReaderApplication.getSession().getHistoryDao().queryBuilder().where(HistoryDao.Properties.ResultId.eq(resultId)).unique();
        if(history != null){
            history.setLastReadTime(new Date());
            CodeReaderApplication.getSession().getHistoryDao().update(history);
        }else{
            history = new History();
            history.setResultId(resultId);
            history.setLastReadTime(new Date());
            CodeReaderApplication.getSession().getHistoryDao().insert(history);
        }

    }


}
