package com.wkswind.codereader;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.jakewharton.rxbinding.support.v7.widget.RxToolbar;
import com.jakewharton.rxbinding.view.RxView;
import com.wkswind.codereader.adapter.DocTypeAdapter;
import com.wkswind.codereader.base.ToolbarActivity;
import com.wkswind.codereader.database.DataUtils;
import com.wkswind.codereader.database.DocType;
import com.wkswind.codereader.utils.Constants;

import java.util.List;
import java.util.concurrent.TimeUnit;

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
    DocTypeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_type);
        ButterKnife.bind(this);
        toolbar = getToolbar();
        toolbar.setNavigationIcon(R.drawable.ic_back_24dp);
        RxToolbar.navigationClicks(toolbar).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                finish();
            }
        });
        RxView.clicks(btnAdd).throttleFirst(Constants.DOUBLE_CLICK_DURATION, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
//                Toast.makeText(getApplication(),"add",Toast.LENGTH_SHORT).show();
                DocType type = new DocType();
                type.setName("ABC");
                type.setExtensions("bak");
                type.setScanRoot(Constants.DEFAULT_SCAN_ROOT.getAbsolutePath());
                adapter.addDocType(type);
            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        manager.supportsPredictiveItemAnimations();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                return makeFlag(ItemTouchHelper.ACTION_STATE_IDLE,ItemTouchHelper.RIGHT) | makeFlag(ItemTouchHelper.ACTION_STATE_SWIPE, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                adapter.delete(position);
            }
        });
        itemTouchHelper.attachToRecyclerView(content);
        content.setLayoutManager(manager);
        content.setItemAnimator(new DefaultItemAnimator());
        content.addItemDecoration(new Divider);

        Observable<List<DocType>> observable = DataUtils.getAllDocTypes(getApplication());
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<List<DocType>>() {
            @Override
            public void call(List<DocType> docTypes) {
                adapter = new DocTypeAdapter(DocTypeActivity.this, docTypes);
                content.setAdapter(adapter);
            }
        });
    }
}
