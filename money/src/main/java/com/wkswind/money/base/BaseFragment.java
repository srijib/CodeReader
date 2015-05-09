package com.wkswind.money.base;

import android.support.v4.app.Fragment;

import com.squareup.leakcanary.RefWatcher;

/**
 * Created by Administrator on 2015/5/9.
 */
public class BaseFragment extends Fragment {
    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MoneyApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }
}
