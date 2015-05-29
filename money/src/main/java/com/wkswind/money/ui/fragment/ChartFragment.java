package com.wkswind.money.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wkswind.money.base.BaseFragment;

/**
 * Created by Administrator on 2015/5/9.
 */
public class ChartFragment extends BaseFragment {
    public static final int TYPE_DAY_CHART = 0;
    public static final int TYPE_WEEK_CHART = 0;
    public static final int TYPE_MONTH_CHART = 0;
    public static final int TYPE_YEAR_CHART = 0;
    private static final String TYPE = "com.wkswind.money.ui.fragment.ChartFragment.TYPE";
    private int chartType = TYPE_DAY_CHART;
    public static final ChartFragment newInstance(int chartType){
        ChartFragment fragment = new ChartFragment();
        Bundle args = new Bundle();
        args.putInt(TYPE, chartType);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            chartType = getArguments().getInt(TYPE, TYPE_DAY_CHART);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
