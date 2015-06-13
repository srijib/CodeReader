package com.wkswind.money.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wkswind.money.R;
import com.wkswind.money.base.BaseFragment;

/**
 * Created by Administrator on 2015/5/27.
 */
public class TypeViewerFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_type_viewer, container ,false);
        return view;
    }
}
