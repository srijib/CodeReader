package com.wkswind.diary.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.wkswind.diary.R;

/**
 * Created by Administrator on 2015/4/28.
 */
public class BaseFragment  extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    protected Toolbar getToolbar(){
        return (Toolbar) getView().findViewById(R.id.toolbar_actionbar);
    }
}
