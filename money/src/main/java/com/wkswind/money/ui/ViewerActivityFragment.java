package com.wkswind.money.ui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wkswind.money.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class ViewerActivityFragment extends Fragment {

    public ViewerActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_viewer, container, false);
    }
}
