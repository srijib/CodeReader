package com.wkswind.minilibrary.library.widget;/*
 * Copyright (C) 2014 Chris Banes
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
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.wkswind.minilibrary.R;


/**
 * Layout which an {@link android.widget.EditText} to show a floating label when the hint is hidden
 * due to the user inputting text.
 *
 * @see <a href="https://dribbble.com/shots/1254439--GIF-Mobile-Form-Interaction">Matt D. Smith on Dribble</a>
 * @see <a href="http://bradfrostweb.com/blog/post/float-label-pattern/">Brad Frost's blog post</a>
 */
public final class FloatLabelLayout extends FrameLayout {

    private static final long ANIMATION_DURATION = 150;

    private static final float DEFAULT_PADDING_LEFT_RIGHT_DP = 4f;

    private EditText mEditText;
    private TextView mLabel;

    public FloatLabelLayout(Context context) {
        this(context, null);
    }

    public FloatLabelLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatLabelLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        final TypedArray a = context
                .obtainStyledAttributes(attrs, R.styleable.FloatLabelLayout);

        final int sidePadding = a.getDimensionPixelSize(
                R.styleable.FloatLabelLayout_floatLabelSidePadding,
                dipsToPix(DEFAULT_PADDING_LEFT_RIGHT_DP));
        mLabel = new TextView(context);
        mLabel.setPadding(sidePadding, 0, sidePadding, 0);
        mLabel.setVisibility(INVISIBLE);

        mLabel.setTextAppearance(context,
                a.getResourceId(R.styleable.FloatLabelLayout_floatLabelTextAppearance,
                        android.R.style.TextAppearance_Small));

        addView(mLabel, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        a.recycle();
    }

    @Override
    public final void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (child instanceof EditText) {
            // If we already have an EditText, throw an exception
            if (mEditText != null) {
                throw new IllegalArgumentException("We already have an EditText, can only have one");
            }

            // Update the layout params so that the EditText is at the bottom, with enough top
            // margin to show the label
            final LayoutParams lp = new LayoutParams(params);
            lp.gravity = Gravity.BOTTOM;
            lp.topMargin = (int) mLabel.getTextSize();
            params = lp;

            setEditText((EditText) child);
        }

        // Carry on adding the View...
        super.addView(child, index, params);
    }

    private void setEditText(EditText editText) {
        mEditText = editText;

        // Add a TextWatcher so that we know when the text input has changed
        mEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    // The text is empty, so hide the label if it is visible
                    if (mLabel.getVisibility() == View.VISIBLE) {
                        hideLabel();
                    }
                } else {
                    // The text is not empty, so show the label if it is not visible
                    if (mLabel.getVisibility() != View.VISIBLE) {
                        showLabel();
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

        });

        // Add focus listener to the EditText so that we can notify the label that it is activated.
        // Allows the use of a ColorStateList for the text color on the label
        mEditText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onFocusChange(View view, boolean focused) {
                mLabel.setActivated(focused);
            }
        });

        mLabel.setText(mEditText.getHint());
    }

    /**
     * @return the {@link android.widget.EditText} text input
     */
    public EditText getEditText() {
        return mEditText;
    }

    /**
     * @return the {@link android.widget.TextView} label
     */
    public TextView getLabel() {
        return mLabel;
    }

    /**
     * Show the label using an animation
     */
    private void showLabel() {
        mLabel.setVisibility(View.VISIBLE);

        ViewHelper.setAlpha(mLabel, 0f);
        ViewHelper.setTranslationX(mLabel, mLabel.getHeight());
        ViewPropertyAnimator.animate(mLabel).alpha(1f).translationY(0f).setDuration(ANIMATION_DURATION).setListener(null).start();
    }

    /**
     * Hide the label using an animation
     */
    private void hideLabel() {
        ViewHelper.setAlpha(mLabel, 1f);
        ViewHelper.setTranslationY(mLabel, 0f);
        ViewPropertyAnimator.animate(mLabel).alpha(0f).translationY(mLabel.getHeight()).setDuration(ANIMATION_DURATION).setListener(new com.nineoldandroids.animation.Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(com.nineoldandroids.animation.Animator animation) {

            }

            @Override
            public void onAnimationEnd(com.nineoldandroids.animation.Animator animation) {
                mLabel.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(com.nineoldandroids.animation.Animator animation) {

            }

            @Override
            public void onAnimationRepeat(com.nineoldandroids.animation.Animator animation) {

            }
        }).start();
    }

    /**
     * Helper method to convert dips to pixels.
     */
    private int dipsToPix(float dps) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dps,
                getResources().getDisplayMetrics());
    }
}