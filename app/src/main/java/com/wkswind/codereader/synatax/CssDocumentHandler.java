package com.wkswind.codereader.synatax;

import android.text.TextUtils;

public class CssDocumentHandler extends DocumentHandlerImpl {

	@Override
	public String getFileExtension() {
		return "css";
	}

	@Override
	public String getFilePrettifyClass() {
		return "prettyprint lang-css";
	}

	@Override
	public String getFileScriptFiles() {
		return "<script src='file:///android_asset/lang-css.js' type='text/javascript'></script> ";
	}

}
