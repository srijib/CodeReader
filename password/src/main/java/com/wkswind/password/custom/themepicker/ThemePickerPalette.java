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



import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.wkswind.password.R;


/**
 * A color picker custom view which creates an grid of color squares.  The number of squares per
 * row (and the padding between the squares) is determined by the user.
 */
public class ThemePickerPalette extends TableLayout {

    public ThemePickerSwatch.OnThemeSelectedListener mOnThemeSelectedListener;

//    private String mDescription;
//    private String mDescriptionSelected;

    private int mSwatchLength;
    private int mMarginSize;
    private int mNumColumns;

    public ThemePickerPalette(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ThemePickerPalette(Context context) {
        super(context);
    }

    /**
     * Initialize the size, columns, and listener.  Size should be a pre-defined size (SIZE_LARGE
     * or SIZE_SMALL) from ColorPickerDialogFragment.
     */
    public void init(int size, int columns, ThemePickerSwatch.OnThemeSelectedListener listener) {
        mNumColumns = columns;
        Resources res = getResources();
        if (size == ThemePickerDialog.SIZE_LARGE) {
            mSwatchLength = res.getDimensionPixelSize(R.dimen.color_swatch_large);
            mMarginSize = res.getDimensionPixelSize(R.dimen.color_swatch_margins_large);
        } else {
            mSwatchLength = res.getDimensionPixelSize(R.dimen.color_swatch_small);
            mMarginSize = res.getDimensionPixelSize(R.dimen.color_swatch_margins_small);
        }
        mOnThemeSelectedListener = listener;

//        mDescription = null;
//        mDescriptionSelected = null;
    }

    private TableRow createTableRow() {
        TableRow row = new TableRow(getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(params);
        return row;
    }

    /**
     * Adds swatches to table in a serpentine format.
     */
    public void drawPalette(int[] themes, int selectedTheme) {
        if (themes == null) {
            return;
        }

        this.removeAllViews();
        int tableElements = 0;
        int rowElements = 0;
        int rowNumber = 0;

        // Fills the table with swatches based on the array of colors.
        TableRow row = createTableRow();
        for (int theme : themes) {
            tableElements++;

            View colorSwatch = createColorSwatch(theme, selectedTheme);
            setSwatchDescription(rowNumber, tableElements, rowElements, theme == selectedTheme,
                    colorSwatch);
            addSwatchToRow(row, colorSwatch, rowNumber);

            rowElements++;
            if (rowElements == mNumColumns) {
                addView(row);
                row = createTableRow();
                rowElements = 0;
                rowNumber++;
            }
        }

        // Create blank views to fill the row if the last row has not been filled.
        if (rowElements > 0) {
            while (rowElements != mNumColumns) {
                addSwatchToRow(row, createBlankSpace(), rowNumber);
                rowElements++;
            }
            addView(row);
        }
    }

    /**
     * Appends a swatch to the end of the row for even-numbered rows (starting with row 0),
     * to the beginning of a row for odd-numbered rows.
     */
    private void addSwatchToRow(TableRow row, View swatch, int rowNumber) {
        if (rowNumber % 2 == 0) {
            row.addView(swatch);
        } else {
            row.addView(swatch, 0);
        }
    }

    /**
     * Add a content description to the specified swatch view. Because the colors get added in a
     * snaking form, every other row will need to compensate for the fact that the colors are added
     * in an opposite direction from their left->right/top->bottom order, which is how the system
     * will arrange them for accessibility purposes.
     */
    private void setSwatchDescription(int rowNumber, int index, int rowElements, boolean selected,
            View swatch) {
        int accessibilityIndex;
        if (rowNumber % 2 == 0) {
            // We're in a regular-ordered row
            accessibilityIndex = index;
        } else {
            // We're in a backwards-ordered row.
            int rowMax = ((rowNumber + 1) * mNumColumns);
            accessibilityIndex = rowMax - rowElements;
        }

//        String description;
//        if (selected) {
//            description = String.format(mDescriptionSelected, accessibilityIndex);
//        } else {
//            description = String.format(mDescription, accessibilityIndex);
//        }
//        swatch.setContentDescription(description);
    }

    /**
     * Creates a blank space to fill the row.
     */
    private ImageView createBlankSpace() {
        ImageView view = new ImageView(getContext());
        TableRow.LayoutParams params = new TableRow.LayoutParams(mSwatchLength, mSwatchLength);
        params.setMargins(mMarginSize, mMarginSize, mMarginSize, mMarginSize);
        view.setLayoutParams(params);
        return view;
    }

    /**
     * Creates a color swatch.
     */
    private ThemePickerSwatch createColorSwatch(int theme, int selectedTheme) {
        ThemePickerSwatch view = new ThemePickerSwatch(getContext(), theme,
                theme == selectedTheme, mOnThemeSelectedListener);
        TableRow.LayoutParams params = new TableRow.LayoutParams(mSwatchLength, mSwatchLength);
        params.setMargins(mMarginSize, mMarginSize, mMarginSize, mMarginSize);
        view.setLayoutParams(params);
        return view;
    }
}