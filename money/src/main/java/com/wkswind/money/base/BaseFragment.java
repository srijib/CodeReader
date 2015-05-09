package com.wkswind.money.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.squareup.leakcanary.RefWatcher;

import net.simonvt.schematic.annotation.OnCreate;

/**
 * Created by Administrator on 2015/5/9.
 */
public class BaseFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MoneyApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }
}
