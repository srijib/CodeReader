package com.wkswind.password.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;

import com.wkswind.password.R;

/**
 * Created by Administrator on 2015/6/12.
 */
public class Utils {

    public static class AttributeParser{
        public static int parseAttribute(Context context, int themeId, int attributeId){
            TypedArray a = context.getTheme().obtainStyledAttributes(themeId, new int[] {attributeId});
            int attributeResourceId = a.getResourceId(0, 0);
            return attributeResourceId;
        }
    }

    public static class ColorUtils{

        /**
         * Create an array of int with colors
         *
         * @param context
         * @return
         */
        public static int[] colorChoice(Context context){

            int[] mColorChoices=null;
            String[] color_array = context.getResources().getStringArray(R.array.default_color_choice_values);

            if (color_array!=null && color_array.length>0) {
                mColorChoices = new int[color_array.length];
                for (int i = 0; i < color_array.length; i++) {
                    mColorChoices[i] = Color.parseColor(color_array[i]);
                }
            }
            return mColorChoices;
        }

        /**
         * Parse whiteColor
         *
         * @return
         */
        public static int parseWhiteColor(){
            return Color.parseColor("#FFFFFF");
        }

    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}

