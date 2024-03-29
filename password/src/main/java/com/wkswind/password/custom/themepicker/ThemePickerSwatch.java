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

package com.wkswind.password.custom.themepicker;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.wkswind.password.R;
import com.wkswind.password.utils.Utils;


/**
 * Creates a circular swatch of a specified color.  Adds a checkmark if marked as checked.
 */
public class ThemePickerSwatch extends FrameLayout implements View.OnClickListener {
    private int mTheme;
    private ImageView mSwatchImage;
    private ImageView mCheckmarkImage;
    private OnThemeSelectedListener mOnThemeSelectedListener;

    /**
     * Interface for a callback when a color square is selected.
     */
    public static interface OnThemeSelectedListener {

        /**
         * Called when a specific color square has been selected.
         */
        public void onThemeSelected(int theme);
    }

    public ThemePickerSwatch(Context context, int theme, boolean checked,
                             OnThemeSelectedListener listener) {
        super(context);
        mTheme = theme;
        mOnThemeSelectedListener = listener;

        LayoutInflater.from(context).inflate(R.layout.calendar_color_picker_swatch, this);
        mSwatchImage = (ImageView) findViewById(R.id.color_picker_swatch);
        mCheckmarkImage = (ImageView) findViewById(R.id.color_picker_checkmark);
        setTheme(context.getResources().getColor(Utils.AttributeParser.parseAttribute(context, theme, R.attr.colorPrimary)));
        setChecked(checked);
        setOnClickListener(this);
    }

    protected void setTheme(int color) {
        Drawable[] colorDrawable = new Drawable[]
                {getContext().getResources().getDrawable(R.drawable.calendar_color_picker_swatch)};
        mSwatchImage.setImageDrawable(new ColorStateDrawable(colorDrawable, color));
    }

    private void setChecked(boolean checked) {
        if (checked) {
            mCheckmarkImage.setVisibility(View.VISIBLE);
        } else {
            mCheckmarkImage.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        if (mOnThemeSelectedListener != null) {
            mOnThemeSelectedListener.onThemeSelected(mTheme);
        }
    }
}