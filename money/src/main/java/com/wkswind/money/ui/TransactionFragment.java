package com.wkswind.money.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wkswind.money.R;
import com.wkswind.money.base.BaseFragment;

/**
 * Created by Administrator on 2015/5/11.
 */
public class TransactionFragment extends BaseFragment {
    public static final TransactionFragment newInstance(){
        TransactionFragment fragment = new TransactionFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_transaction, container, false);
//        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
