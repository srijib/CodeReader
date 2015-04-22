package com.wkswind.codereader.fileexplorer;

import android.app.Activity;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.wkswind.codereader.BaseListFragment;
import com.wkswind.codereader.R;
import com.wkswind.codereader.ReaderActivity;
import com.wkswind.codereader.SettingsActivity;
import com.wkswind.codereader.database.CodeProvider;
import com.wkswind.codereader.database.HistorysColumn;
import com.wkswind.codereader.database.StarsColumn;
import com.wkswind.codereader.fileexplorer.sort.SortType;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/4/7.
 */
public class RelatedFragment extends BaseListFragment implements LoaderManager.LoaderCallbacks<Cursor>{

    public static final int HISTORY_LOADER = 2;
    public static final int STAR_LOADER = 3;
    private boolean isSearching = false;
    private int loaderId;
    private ContentObserver observer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public static final RelatedFragment newInstance(Bundle args){
        RelatedFragment fragment = new RelatedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private FileExplorerFragment.IFileSelected iFileSelected;

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        if(activity instanceof FileExplorerFragment.IFileSelected){
            iFileSelected = (FileExplorerFragment.IFileSelected) activity;
        }else{
            throw new IllegalArgumentException("activity not instanceof IFileSelected ");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);
        if(iFileSelected != null){
            iFileSelected.onFileSelected((File) getListAdapter().getItem(position));
        }
    }

    @Override
    public void onDetach() {
        // TODO Auto-generated method stub
        super.onDetach();
        iFileSelected = null;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loaderId = HISTORY_LOADER;
        if(getArguments() != null){
            loaderId = getArguments().getInt(RelatedFragment.class.getSimpleName());
        }
        observer = new ContentObserver(new Handler()) {
            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
                getLoaderManager().restartLoader(loaderId, null, RelatedFragment.this);
            }
        };
        getActivity().getContentResolver().registerContentObserver(CodeProvider.Stars.CONTENT_URI, true, observer);
        getActivity().getContentResolver().registerContentObserver(CodeProvider.Historys.CONTENT_URI, true, observer);
        getLoaderManager().initLoader(loaderId,null,this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().getContentResolver().unregisterContentObserver(observer);
    }

    void query(){
        showProgress(true);
        if(getLoaderManager().getLoader(loaderId) == null){
            getLoaderManager().initLoader(loaderId, getArguments(), this);
        }else{
            getLoaderManager().restartLoader(loaderId, getArguments(), this);
        }
    }

    void showProgress(boolean show){
        if(show){
            setListShown(false);
        }else{
            if (isResumed()) {
                setListShown(true);
            } else {
                setListShownNoAnimation(true);
            }
        }
        setListShown(!show);
        isSearching = show;
        getActivity().supportInvalidateOptionsMenu();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case R.id.sort_by_name:
                ((FileAdapter)getListAdapter()).sort(SortType.byDefault);
                break;
            case R.id.sort_by_date:
                ((FileAdapter)getListAdapter()).sort(SortType.byDate);
                break;
            case R.id.sort_by_size:
                ((FileAdapter)getListAdapter()).sort(SortType.bySize);
                break;
            case R.id.action_refresh:
                query();
                break;
            case android.R.id.home:
                getActivity().finish();
                break;
            case R.id.action_open:
                startActivityForResult(
                        new Intent(getActivity(), FileExplorerActivity.class),
                        ReaderActivity.REQUEST_FILE_CHOOSE);
                break;
            case R.id.action_setting:
                startActivity(new Intent(getActivity(),SettingsActivity.class));
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id){
            case HISTORY_LOADER:
                return new CursorLoader(getActivity(), CodeProvider.Historys.CONTENT_URI, new String[]{"*"},null,null, HistorysColumn.lastReadTime +" DESC ");
            case STAR_LOADER:
                return new CursorLoader(getActivity(),CodeProvider.Stars.CONTENT_URI, new String[]{"*"}, StarsColumn.star+"=?", new String[]{"1"}, StarsColumn.lastReadTime + " DESC ");
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        ArrayList<File> files = new ArrayList<>();
        if(data != null){
            while(data.moveToNext()){
                files.add(new File(data.getString(data.getColumnIndex(HistorysColumn.fileName))));
            }
        }
        data.close();
        setListAdapter(new FileAdapter(getActivity(),files));
        showProgress(false);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        ListAdapter adapter = getListAdapter();
        if(adapter instanceof FileAdapter){
            ((FileAdapter) adapter).reset();
        }
    }
}
