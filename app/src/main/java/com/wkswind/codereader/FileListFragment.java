package com.wkswind.codereader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.wkswind.codereader.fileexplorer.FileAdapter;
import com.wkswind.codereader.fileexplorer.FileExplorerActivity;
import com.wkswind.codereader.fileexplorer.FileExplorerFragment;
import com.wkswind.codereader.fileexplorer.sort.SortType;

import java.io.File;

/**
 * Created by Administrator on 2015/4/23.
 */
public class FileListFragment extends BaseListFragment {
    protected boolean isSearching = false;
    private FileExplorerFragment.IFileSelected iFileSelected;
    protected void showProgress(boolean show){
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        if(activity instanceof IFileSelected){
            iFileSelected = (IFileSelected) activity;
        }else{
            throw new IllegalArgumentException("activity not instanceof IFileSelected ");
        }
    }

    public static interface IFileSelected{
        public void onFileSelected(File file);
    }

    @Override
    public void onDetach() {
        // TODO Auto-generated method stub
        super.onDetach();
        iFileSelected = null;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        super.onPrepareOptionsMenu(menu);
        menu.setGroupEnabled(R.id.group, !isSearching);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);
        if(iFileSelected != null){
            iFileSelected.onFileSelected((File) getListAdapter().getItem(position));
        }
    }

    protected void query(){

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        FileAdapter adapter = (FileAdapter) getListAdapter();
        switch (item.getItemId()) {
            case R.id.sort_by_name:
                adapter.sort(SortType.byDefault);
                break;
            case R.id.sort_by_date:
                adapter.sort(SortType.byDate);
                break;
            case R.id.sort_by_size:
                adapter.sort(SortType.bySize);
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
}
