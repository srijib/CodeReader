package com.wkswind.money.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.squareup.leakcanary.RefWatcher;
import com.wkswind.money.MainActivity;

import net.simonvt.schematic.annotation.OnCreate;

/**
 * Created by Administrator on 2015/5/9.
 */
public class BaseFragment extends Fragment {
    protected final static String LABEL = "label";
    protected final static String COLOR_PRIMARY = "color_primary";
    protected final static String COLOR_PRIMARY_DARK = "color_primary_dark";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if(getArguments() != null){
            int colorPrimary = getArguments().getInt(COLOR_PRIMARY);
            int colorPrimaryDark = getArguments().getInt(COLOR_PRIMARY_DARK);
            FragmentActivity activity = getActivity();
            if(activity instanceof ToolbarActivity){
                ((ToolbarActivity) activity).getToolbar().setBackgroundColor(getResources().getColor(colorPrimary));
                if(activity instanceof MainActivity){
                    ((MainActivity)activity).changeStatusBarColor(colorPrimaryDark);
                }
            }
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof MainActivity){
            if(getArguments() != null){
                Bundle args = getArguments();
                if(args.containsKey(LABEL)){
                    if(activity instanceof MainActivity){
                        ((MainActivity) activity).onSectionAttached(args.getString(LABEL));
                    }
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MoneyApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }
}
