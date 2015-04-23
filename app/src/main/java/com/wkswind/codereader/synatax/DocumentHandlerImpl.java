package com.wkswind.codereader.synatax;

import android.text.Html;
import android.text.TextUtils;

/**
 * Created by Administrator on 2015/4/22.
 */
public class DocumentHandlerImpl implements DocumentHandler {
    @Override
    public String getFileExtension() {
        return null;
    }

    @Override
    public String getFileMimeType() {
        return "text/html";
    }

    @Override
    public String getFilePrettifyClass() {
        return "prettyprint";
    }

    @Override
    public String getFileFormattedString(String fileString) {
        return TextUtils.htmlEncode(fileString).replace(System.getProperty("line.separator"), "<br>").replace("\t","&emsp;");
//        StringBuilder sb = new StringBuilder("<ol>");
//        sb.append(new StringBuilder(TextUtils.htmlEncode(fileString).replaceAll(System.getProperty("line.separator"), "<br></li>").replaceAll("\t","<pre>&#9;</pre>")));
//        sb.append("</ol>");
//        return sb.toString();
    }

    @Override
    public String getFileScriptFiles() {
        return null;
    }
}
