package com.wkswind.codereader.synatax;

import android.text.TextUtils;

public class LispDocumentHandler extends DocumentHandlerImpl {

	@Override
	public String getFileExtension() {
		return "lisp";
	}

	@Override
	public String getFilePrettifyClass() {
		return "prettyprint lang-lisp";
	}

	@Override
	public String getFileScriptFiles() {
		return "<script src='file:///android_asset/lang-lisp.js' type='text/javascript'></script> ";
	}

}
