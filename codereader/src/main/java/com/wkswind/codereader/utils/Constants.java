package com.wkswind.codereader.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by Administrator on 2015-12-3.
 */
public final class Constants {
    /**
     * delay for double click
     */
    public static final long DOUBLE_CLICK_DURATION = 200l;
    /**
     * default scan root for {@link com.wkswind.codereader.database.DocType}
     */
    public static final File DEFAULT_SCAN_ROOT = Environment.getExternalStorageDirectory();
}
