package com.wkswind.codereader.utils;


import com.wkswind.codereader.utils.io.CharsetToolkit;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by Administrator on 2015/2/3.
 */
public class CharsetDetector {
    private static final int BUFFER_LENGTH = 4 * 1024;

    public static final Charset decodeCharset(File file) throws IOException {
        return CharsetToolkit.guessEncoding(file, BUFFER_LENGTH, Charset.forName("utf-8"));
    }

    public static final Charset decodeCharset(String fileName) throws IOException {
        return decodeCharset(new File(fileName));
    }
}
