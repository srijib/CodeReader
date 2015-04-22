package com.wkswind.codereader.synatax;

import android.text.TextUtils;

public class CDocumentHandler extends DocumentHandlerImpl {

	@Override
	public String getFileExtension() {
		return "c";
	}

	@Override
	public String getFileScriptFiles() {
		return "";
	}

}
