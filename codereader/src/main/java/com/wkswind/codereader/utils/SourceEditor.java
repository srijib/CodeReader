/*
 * Copyright 2012 GitHub Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wkswind.codereader.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.webkit.MimeTypeMap;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wkswind.codereader.R;
import com.wkswind.codereader.synatax.DocumentHandler;
import com.wkswind.codereader.synatax.DocumentHandlerImpl;
import com.wkswind.codereader.utils.io.CharsetDetector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;


/**
 * Utilities for displaying source code in a {@link WebView}
 */
public class SourceEditor {

    private static final String URL_PAGE = "file:///android_asset/source-editor.html";

    private final WebView view;

    private boolean wrap;

    private String name;

    private String content;

    private String charSet;

    private boolean markdown = true;

    /**
     * Create source editor using given web view
     *
     * @param view
     */
    public SourceEditor(final WebView view) {
        WebViewClient client = new WebViewClient() {
            private ProgressDialog pd;
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Context context = view.getContext();
                pd = ProgressDialog.show(context, context.getString(R.string.loading), context.getString(R.string.loading));
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if(pd != null){
                    if(pd.isShowing()){
                        pd.dismiss();
                    }
                    pd = null;
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (URL_PAGE.equals(url)) {
                    view.loadUrl(url);
                    return false;}
                return true;
//                } else {
//                    UriLauncherActivity.launchUri(view.getContext(), Uri.parse(url));
//                    return true;
//                }
            }
        };
        view.setWebViewClient(client);

        WebSettings settings = view.getSettings();
        settings.setJavaScriptEnabled(true);
        view.addJavascriptInterface(this, "SourceEditor");
//        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
            settings.setBuiltInZoomControls(true);
            settings.setUseWideViewPort(true);
//        }

        this.view = view;
    }

    /**
     * @return name
     */
    @JavascriptInterface
    public String getName() {
        return name;
    }

    @JavascriptInterface
    public void showSource(String html){

    }

    /**
     * @return content
     */
    @JavascriptInterface
    public String getRawContent() {
        return content;
    }

    /**
     * @return content
     */
    @JavascriptInterface
    public String getContent() {
            return getRawContent();
    }

    /**
     * @return wrap
     */
    @JavascriptInterface
    public boolean getWrap() {
        return wrap;
    }

    /**
     * @return markdown
     */
    public boolean isMarkdown() {
        return markdown;
    }

    /**
     * Set whether lines should wrap
     *
     * @param wrap
     * @return this editor
     */
//    public SourceEditor setWrap(final boolean wrap) {
//        this.wrap = wrap;
//        loadSource();
//        return this;
//    }

    /**
     * Sets whether the content is a markdown file
     *
     * @param markdown
     * @return this editor
     */
    public SourceEditor setMarkdown(final boolean markdown) {
        this.markdown = markdown;
        return this;
    }

//    /**
//     * Bind content to current {@link WebView}
//     *
//     * @param name
//     * @param content
//     * @return this editor
//     */
//    public SourceEditor setSource(final String name, final String content) {
//        this.name = name;
//        this.content = content;
//        loadSource();
//
//        return this;
//    }

    private void loadSource() {
        if (name != null && content != null)
            if (markdown){
                StringBuilder realContent = new StringBuilder();
                realContent.append("<html><head><title></title>");
                realContent.append("<meta charset=\"utf-8\"/>");
                realContent.append("<link rel=\"stylesheet\" href=\"file:///android_asset/lib/codemirror.css\">");
                realContent.append("<link rel=\"stylesheet\" href=\"file:///android_asset/source-editor.css\">");
                realContent.append("<script src=\"file:///android_asset/lib/codemirror.js\"></script>");
                realContent.append("<script src=\"file:///android_asset/mode/meta.js\"></script>");
                realContent.append("<script src=\"file:///android_asset/loadmode.js\"></script>");
                realContent.append("<script src=\"file:///android_asset/source-editor.js\"></script>");

                        realContent.append("</head>");
                realContent.append("<body>");
//                realContent.append(content);
                realContent.append("</body>");
                realContent.append("</html>");
                view.loadDataWithBaseURL(null, realContent.toString(), "text/html", charSet, null);
            }

            else
                view.loadUrl(URL_PAGE);
    }

//    /**
//     * Bind blob content to current {@link WebView}
//     *
//     * @param name
//     * @param blob
//     * @return this editor
//     */
//    public SourceEditor setSource(final String name, final Blob blob) {
//        String content = blob.getContent();
//        if (content == null)
//            content = "";
//        boolean encoded = !TextUtils.isEmpty(content) && ENCODING_BASE64.equals(blob.getEncoding());
//        return setSource(name, content, encoded);
//    }
    public SourceEditor setSource(final Uri uri) throws IOException {
        File file = new File(uri.getPath());
        name = file.getName();
        Charset charset = CharsetDetector.decodeCharset(file);
        charSet = charset.name();
        DocumentHandler handler = getHandlerByFileExtension(uri);
        final long length = file.length();

        if (handler == null) {
            return this;
        }

        byte[] array = new byte[(int) length];
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            is.read(array);
            content = new String(array,charset.name());
            loadSource();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return this;
    }

    private DocumentHandler getHandlerByFileExtension(Uri uri) {
        DocumentHandlerImpl impl = new DocumentHandlerImpl();
        File file = new File(uri.getPath());
        if (file.exists() && file.isFile()) {
            String extension = MimeTypeMap.getFileExtensionFromUrl(file
                    .getAbsolutePath());
            impl.setFileExtension(extension);
            if(!TextUtils.isEmpty(extension)){
                impl.setFileScriptClass(extension.substring(1));
            }
            return impl;
        }
        return null;

    }

    /**
     * Toggle line wrap
     *
     * @return this editor
     */
//    public SourceEditor toggleWrap() {
//        return setWrap(!wrap);
//    }

    /**
     * Toggle markdown file rendering
     *
     * @return this editor
     */
    public SourceEditor toggleMarkdown() {
        return setMarkdown(!markdown);
    }
}
