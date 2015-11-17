package com.wkswind.codereader;

import android.os.Bundle;

import com.wkswind.codereader.base.CodeReaderApplication;
import com.wkswind.codereader.base.ToolbarActivity;
import com.wkswind.codereader.database.DocType;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2015-11-17.
 */
public class HomeActivity extends ToolbarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Observable.create(new Observable.OnSubscribe<List<DocType>>() {
            @Override
            public void call(Subscriber<? super List<DocType>> subscriber) {
                try {
                    List<DocType> result = queryDocType();
                    subscriber.onNext(result);
                    subscriber.onCompleted();
                }catch (Throwable e){
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<DocType>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<DocType> docTypes) {

            }
        });
//        Observable.
    }

    private List<DocType> queryDocType(){
        List<DocType> result = CodeReaderApplication.getSession().getDocTypeDao().queryBuilder().list();
        return result;
    }
}
