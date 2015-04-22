package com.wkswind.codereader.synatax;

import android.text.TextUtils;

public class JavascriptDocumentHandler extends DocumentHandlerImpl {

	@Override
	public String getFileExtension() {
		return "js";
	}

	@Override
	public String getFileScriptFiles() {
		return "";
	}

}
