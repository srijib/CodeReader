package com.wkswind.money.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wkswind.money.BuildConfig;
import com.wkswind.money.R;
import com.wkswind.money.base.BaseFragment;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/5/11.
 */
public class TransactionFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout refreshLayout;
    private ListView content;

    public static final TransactionFragment newInstance(){
        TransactionFragment fragment = new TransactionFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction, container, false);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_container);
        content = (ListView) view.findViewById(R.id.content);
        refreshLayout.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        makeFakeDatas();
    }

    private void makeFakeDatas() {
        if(BuildConfig.DEBUG){
            ArrayList<String> datas = new ArrayList<>();
            for(int i=0,j=500;i<j;i++){
                datas.add("item # " + i);
            }
            ArrayAdapter<String> fakeAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,android.R.id.text1, datas);
            content.setAdapter(fakeAdapter);
        }
    }

    @Override
    public void onRefresh() {
        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(refreshLayout.isRefreshing()){
                    refreshLayout.setRefreshing(false);
                }
            }
        }, 2 * 1000);
    }
}
