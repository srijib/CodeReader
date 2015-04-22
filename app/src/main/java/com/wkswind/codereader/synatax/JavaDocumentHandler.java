package com.wkswind.codereader.synatax;

import android.text.TextUtils;

public class JavaDocumentHandler extends DocumentHandlerImpl {

	@Override
	public String getFileExtension() {
		return ".java";
	}

	@Override
	public String getFileScriptFiles() {
		return "";
	}

}
