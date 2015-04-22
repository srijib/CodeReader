package com.wkswind.codereader.synatax;

import android.text.TextUtils;

public class VbDocumentHandler extends DocumentHandlerImpl {

	@Override
	public String getFileExtension() {
		return "ml";
	}

	@Override
	public String getFilePrettifyClass() {
		return "prettyprint lang-vb";
	}

	@Override
	public String getFileScriptFiles() {
		return "<script src='file:///android_asset/lang-vb.js' type='text/javascript'></script> ";
	}

}
