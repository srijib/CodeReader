package com.wkswind.codereader.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wkswind.codereader.R;
import com.wkswind.codereader.adapter.HistoryAdapter;
import com.wkswind.codereader.base.BaseFragment;
import com.wkswind.codereader.database.DataUtils;
import com.wkswind.codereader.database.History;
import com.wkswind.codereader.utils.SimpleListDividerDecorator;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * browse history
 * Created by Administrator on 2015-12-7.
 */
public class HistoryFragment extends BaseFragment {
    @Bind(R.id.toolbar_actionbar)
    Toolbar toolbar;
    @Bind(R.id.content)
    RecyclerView content;
    private Observable<List<History>> observable;
    private HistoryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        content.setItemAnimator(new DefaultItemAnimator());
        content.addItemDecoration(new SimpleListDividerDecorator(getResources().getDrawable(R.drawable.list_divider),false));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        observable = DataUtils.getHistory(getActivity().getApplication());
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<List<History>>() {
            @Override
            public void call(List<History> histories) {
                if(adapter == null){
                    adapter = new HistoryAdapter(getActivity(), histories);
                    content.setAdapter(adapter);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
