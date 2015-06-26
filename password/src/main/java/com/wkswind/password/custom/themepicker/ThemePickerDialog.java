package com.wkswind.password.custom.themepicker;

/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * You can find source here:
 * https://android.googlesource.com/platform/frameworks/opt/colorpicker/+/master/src/com/android/colorpicker
 */


import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.wkswind.password.R;


/**
 * A dialog which takes in as input an array of colors and creates a palette allowing the user to
 * select a specific color swatch, which invokes a listener.
 */
public class ThemePickerDialog extends DialogFragment implements ThemePickerSwatch.OnThemeSelectedListener {

    public static final int SIZE_LARGE = 1;
    public static final int SIZE_SMALL = 2;

    protected AlertDialog mAlertDialog;

    protected static final String KEY_TITLE_ID = "title_id";
    protected static final String KEY_THEMES = "colors";
    protected static final String KEY_SELECTED_THEME = "selected_color";
    protected static final String KEY_COLUMNS = "columns";
    protected static final String KEY_SIZE = "size";

    protected int mTitleResId = R.string.action_color_selector;
    protected int[] mThemes = null;
    protected int mSelectedTheme;
    protected int mColumns;
    protected int mSize;

    private ThemePickerPalette mPalette;
    private ProgressBar mProgress;

    protected ThemePickerSwatch.OnThemeSelectedListener mListener;

    public ThemePickerDialog() {
        // Empty constructor required for dialog fragments.
    }

    public static ThemePickerDialog newInstance(int titleResId, int[] themes, int selectedTheme,
            int columns, int size) {
        ThemePickerDialog ret = new ThemePickerDialog();
        ret.initialize(titleResId, themes, selectedTheme, columns, size);
        return ret;
    }

    public void initialize(int titleResId, int[] themes, int selectedTheme, int columns, int size) {
        setArguments(titleResId, columns, size);
        setColors(themes, selectedTheme);
    }

    public void setArguments(int titleResId, int columns, int size) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_TITLE_ID, titleResId);
        bundle.putInt(KEY_COLUMNS, columns);
        bundle.putInt(KEY_SIZE, size);
        setArguments(bundle);
    }

    public void setOnColorSelectedListener(ThemePickerSwatch.OnThemeSelectedListener listener) {
        mListener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mTitleResId = getArguments().getInt(KEY_TITLE_ID);
            mColumns = getArguments().getInt(KEY_COLUMNS);
            mSize = getArguments().getInt(KEY_SIZE);
        }

        if (savedInstanceState != null) {
            mThemes = savedInstanceState.getIntArray(KEY_THEMES);
            mSelectedTheme = (Integer) savedInstanceState.getSerializable(KEY_SELECTED_THEME);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Activity activity = getActivity();

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.calendar_color_picker_dialog, null);
        mProgress = (ProgressBar) view.findViewById(android.R.id.progress);
        mPalette = (ThemePickerPalette) view.findViewById(R.id.color_picker);
        mPalette.init(mSize, mColumns, this);

        if (mThemes != null) {
            showPaletteView();
        }

        mAlertDialog = new AlertDialog.Builder(activity)
            .setTitle(mTitleResId)
            .setView(view)
            .create();

        return mAlertDialog;
    }

    @Override
    public void onThemeSelected(int theme) {
        if (mListener != null) {
            mListener.onThemeSelected(theme);
        }

        if (getTargetFragment() instanceof ThemePickerSwatch.OnThemeSelectedListener) {
            final ThemePickerSwatch.OnThemeSelectedListener listener =
                    (ThemePickerSwatch.OnThemeSelectedListener) getTargetFragment();
            listener.onThemeSelected(theme);
        }

        if (theme != mSelectedTheme) {
            mSelectedTheme = theme;
            // Redraw palette to show checkmark on newly selected color before dismissing.
            mPalette.drawPalette(mThemes, mSelectedTheme);
        }

        dismiss();
    }

    public void showPaletteView() {
        if (mProgress != null && mPalette != null) {
            mProgress.setVisibility(View.GONE);
            refreshPalette();
            mPalette.setVisibility(View.VISIBLE);
        }
    }

    public void showProgressBarView() {
        if (mProgress != null && mPalette != null) {
            mProgress.setVisibility(View.VISIBLE);
            mPalette.setVisibility(View.GONE);
        }
    }

    public void setColors(int[] colors, int selectedColor) {
        if (mThemes != colors || mSelectedTheme != selectedColor) {
            mThemes = colors;
            mSelectedTheme = selectedColor;
            refreshPalette();
        }
    }

    public void setColors(int[] colors) {
        if (mThemes != colors) {
            mThemes = colors;
            refreshPalette();
        }
    }

    public void setSelectedColor(int color) {
        if (mSelectedTheme != color) {
            mSelectedTheme = color;
            refreshPalette();
        }
    }

    private void refreshPalette() {
        if (mPalette != null && mThemes != null) {
//            int[] colors = new int[mThemes.length];
//            for(int i=0,j=mThemes.length;i<j;i++){
//                colors[i] = Utils.AttributeParser.parseAttribute(getActivity(),mThemes[i],R.attr.colorPrimary);
//            }
//            int mSelectedColor = Utils.AttributeParser.parseAttribute(getActivity(),mSelectedTheme, R.attr.colorPrimary);
//            mPalette.drawPalette(colors, mSelectedTheme);
            mPalette.drawPalette(mThemes, mSelectedTheme);
        }
    }

    public int[] getColors() {
        return mThemes;
    }

    public int getSelectedColor() {
        return mSelectedTheme;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntArray(KEY_THEMES, mThemes);
        outState.putSerializable(KEY_SELECTED_THEME, mSelectedTheme);
    }
}