package com.wkswind.codereader.synatax;

import android.text.TextUtils;

public class MlDocumentHandler extends DocumentHandlerImpl {

	@Override
	public String getFileExtension() {
		return "ml";
	}

	@Override
	public String getFilePrettifyClass() {
		return "prettyprint lang-ml";
	}

	@Override
	public String getFileScriptFiles() {
		return "<script src='file:///android_asset/lang-ml.js' type='text/javascript'></script> ";
	}

}
