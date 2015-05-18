package com.wkswind.money.ui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wkswind.money.BuildConfig;
import com.wkswind.money.MainActivity;
import com.wkswind.money.R;
import com.wkswind.money.base.BaseFragment;
import com.wkswind.money.base.ToolbarActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/5/11.
 */
public class TransactionFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout refreshLayout;
    private ListView content;
    private final static String LABEL = "label";
    private final static String COLOR_PRIMARY = "color_primary";
    private final static String COLOR_PRIMARY_DARK = "color_primary_dark";
    public static final TransactionFragment newInstance(String label, int colorPrimary, int colorPrimaryDark){
        TransactionFragment fragment = new TransactionFragment();
        Bundle args = new Bundle();
        args.putString(LABEL, label);
        args.putInt(COLOR_PRIMARY, colorPrimary);
        args.putInt(COLOR_PRIMARY_DARK, colorPrimaryDark);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            int colorPrimary = getArguments().getInt(COLOR_PRIMARY);
            int colorPrimaryDark = getArguments().getInt(COLOR_PRIMARY_DARK);
            FragmentActivity activity = getActivity();
            if(activity instanceof ToolbarActivity){
                ((ToolbarActivity) activity).getToolbar().setBackgroundColor(getResources().getColor(colorPrimary));
                if(activity instanceof MainActivity){
                    ((MainActivity)activity).changeStatusBarColor(colorPrimaryDark);
                }
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    Window window = activity.getWindow();
//                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//                    window.setStatusBarColor(getResources().getColor(colorPrimaryDark));
//                }
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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
