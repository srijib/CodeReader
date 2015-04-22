package com.wkswind.codereader.synatax;

import android.text.TextUtils;

public class CppDocumentHandler extends DocumentHandlerImpl {

	@Override
	public String getFileExtension() {
		return "cpp";
	}

	@Override
	public String getFileScriptFiles() {
		return "";
	}

}
