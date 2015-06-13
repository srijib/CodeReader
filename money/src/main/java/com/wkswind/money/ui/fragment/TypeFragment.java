package com.wkswind.money.ui.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.wkswind.money.R;
import com.wkswind.money.base.BaseFragment;

/**
 * Created by Administrator on 2015/5/19.
 */
public class TypeFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener {
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView content;
    private FloatingActionMenu fabMenu;

    public static TypeFragment newInstance(String label, int colorPrimary, int colorPrimaryDark){
        TypeFragment fragment = new TypeFragment();
        Bundle args = new Bundle();
        args.putString(LABEL, label);
        args.putInt(COLOR_PRIMARY, colorPrimary);
        args.putInt(COLOR_PRIMARY_DARK, colorPrimaryDark);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_type, container, false);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.container);
        content = (RecyclerView) view.findViewById(R.id.content);
        content.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        content.setItemAnimator(new DefaultItemAnimator());
        fabMenu = (FloatingActionMenu) view.findViewById(R.id.fab_menu);
        fabMenu.setClosedOnTouchOutside(true);
//        for(int i=0, j=fabMenu.getChildCount() ;i<j ;i++){
//            View child = fabMenu.getChildAt(i);
//            if (child instanceof FloatingActionButton && i != 0){
//                child.setOnClickListener(this);
//            }
//        }
//        content.setRecycledViewPool();
        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onClick(View v) {
        Toast.makeText(getActivity(), ((FloatingActionButton)v).getLabelText(), Toast.LENGTH_SHORT).show();
    }
}
