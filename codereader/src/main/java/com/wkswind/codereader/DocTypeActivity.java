package com.wkswind.codereader;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.wkswind.codereader.adapter.DocAdapter;
import com.wkswind.codereader.base.ToolbarActivity;
import com.wkswind.codereader.database.DocType;
import com.wkswind.codereader.database.DatabaseUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2015-11-24.
 */
public class DocTypeActivity extends ToolbarActivity {
    @Bind(R.id.content)
    RecyclerView content;
    Toolbar toolbar;
    @Bind(R.id.add)
    FloatingActionButton btnAdd;
    DocAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_type);
        ButterKnife.bind(this);
        toolbar = getToolbar();
        toolbar.setNavigationIcon(R.drawable.ic_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(),"add",Toast.LENGTH_SHORT).show();
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        content.setLayoutManager(manager);
        content.setItemAnimator(new DefaultItemAnimator());
        Observable<List<DocType>> observable = DatabaseUtils.getAllDocTypes(getApplication());
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<List<DocType>>() {
            @Override
            public void call(List<DocType> docTypes) {
//                adapter = new DocAdapter(DocTypeActivity.this, docTypes);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }
}
