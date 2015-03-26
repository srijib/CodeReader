package com.wkswind.minilibrary.utils;

import android.content.Context;
import android.widget.Toast;
import android.support.annotation.StringRes;

/**
 * Created by Administrator on 2015/3/26.
 */
public final class ToastUtils {
    private static Toast toast;

    public static void showToast(Context context,@StringRes int content){
        initToast(context, content).show();
    }

    public static void showToast(Context context, CharSequence content){
        initToast(context, content).show();
    }

    private static Toast initToast(Context context, @StringRes int content){
        if(toast != null){
            toast.cancel();
        }
        toast = Toast.makeText(context.getApplicationContext(),content,Toast.LENGTH_SHORT);
        return toast;
    }

    private static Toast initToast(Context context, CharSequence content){
        if(toast != null){
            toast.cancel();
        }
        toast = Toast.makeText(context.getApplicationContext(),content,Toast.LENGTH_SHORT);
        return toast;
    }
}
