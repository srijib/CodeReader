package com.wkswind.codereader.database;

import android.content.Context;

import com.wkswind.codereader.base.CodeReaderApplication;

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

    public static final Observable<List<Result>> getResultsByType(Context context,final int typeId){
        return Observable.create(new Observable.OnSubscribe<List<Result>>() {
            @Override
            public void call(Subscriber<? super List<Result>> subscriber) {
                try {
                    List<Result> result = CodeReaderApplication.getSession().getResultDao().queryBuilder().where(ResultDao.Properties.DocTypeId.eq(typeId)).list();
                    if(!subscriber.isUnsubscribed()){
                        subscriber.onNext(result);
                    }
                    subscriber.onCompleted();
                }catch(Throwable e) {
                    subscriber.onError(e);
                }

            }
        });
    }
}
