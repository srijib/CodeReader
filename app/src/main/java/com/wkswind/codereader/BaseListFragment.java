package com.wkswind.codereader;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

/**
 * Created by Administrator on 2015/4/14.
 */
public class BaseListFragment extends ListFragment {
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setEmptyView(getListView());
    }

    public static void setEmptyView(AbsListView target) {
        View old = target.getEmptyView();
        ViewGroup vg = (ViewGroup) target.getParent();
        Context ctx = target.getContext();
        View emptyView = LayoutInflater.from(ctx).inflate(R.layout.empty_view,
                null);
        if (vg != null) {
            if(old != null){
                vg.removeView(old);
            }
            vg.addView(emptyView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            target.setEmptyView(emptyView);
        }
    }
}
