package com.wkswind.codereader.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.wkswind.codereader.ReaderActivity;
import com.wkswind.codereader.database.Result;

import java.io.File;

/**
 * Created by Administrator on 2015-11-30.
 */
public class JumpUtils {
    /**
     * 打开文件
     * @param activity
     * @param result
     */
    public static void jumpToReader(Activity activity, Result result){
        File file = new File(result.getAbsolutePath());
        activity.startActivity(new Intent(activity, ReaderActivity.class).setAction(Intent.ACTION_VIEW).setData(Uri.fromFile(file)));
    }
}
