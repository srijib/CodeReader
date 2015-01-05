package com.wkswind.codereader.fileexplorer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.wkswind.codereader.R;
import com.wkswind.codereader.adapter.DirectoryAdapter;
import com.wkswind.codereader.utils.BaseDialogFragment;
import com.wkswind.minilibrary.utils.PrefsUtils;

import java.io.File;

/**
 * Created by Administrator on 2015/1/5.
 */
public class DirectoryExplorerDialog extends BaseDialogFragment {
    private ListView lst;
    private File currentDir;
    public static final DirectoryExplorerDialog newInstance(Bundle args){
        DirectoryExplorerDialog dialog = new DirectoryExplorerDialog();
        dialog.setArguments(args);
        return dialog;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        currentDir = new File(PrefsUtils.get(activity,"doc_directory_ex",Environment.getExternalStorageDirectory().getAbsolutePath()));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        currentDir = null;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_main, null);
        lst = (ListView) view.findViewById(R.id.history);
        final DirectoryAdapter adapter = new DirectoryAdapter(getActivity(), currentDir);
        lst.setAdapter(adapter);
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                currentDir = ((DirectoryAdapter)parent.getAdapter()).getItem(position);
                getDialog().setTitle(currentDir.getAbsolutePath());
                DirectoryAdapter curAdapter = new DirectoryAdapter(getActivity(), ((DirectoryAdapter)parent.getAdapter()).getItem(position));
                lst.setAdapter(curAdapter);
            }
        });
        FileExplorerFragment.setEmptyView(lst);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setTitle(currentDir.getAbsolutePath()).setView(view).setNegativeButton(android.R.string.cancel,null).setNeutralButton(R.string.action_back,null).setPositiveButton(android.R.string.ok,null);
        return builder.create();
    }

    @Override
    public void onStart()
    {
        super.onStart();    //super.onStart() is where dialog.show() is actually called on the underlying dialog, so we have to do it after this point
        final AlertDialog d = (AlertDialog)getDialog();
        if(d != null){
            Button positiveButton = d.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    PrefsUtils.put(getActivity(), "doc_directory_ex", currentDir.getAbsolutePath());
                    d.dismiss();
                }
            });
            final Button neutralButton = d.getButton(Dialog.BUTTON_NEUTRAL);
            neutralButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentDir = currentDir.getParentFile();
                    d.setTitle(currentDir.getAbsolutePath());
                    DirectoryAdapter curAdapter = new DirectoryAdapter(getActivity(),currentDir);
                    lst.setAdapter(curAdapter);
                    neutralButton.setEnabled(!currentDir.getAbsolutePath().equals(Environment.getExternalStorageDirectory()));
                }
            });
        }
    }

}
