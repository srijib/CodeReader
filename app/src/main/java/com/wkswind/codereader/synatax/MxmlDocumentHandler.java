package com.wkswind.codereader.synatax;

import android.text.TextUtils;

public class MxmlDocumentHandler extends DocumentHandlerImpl {

	@Override
	public String getFileExtension() {
		return "mxml";
	}

	@Override
	public String getFileScriptFiles() {
		return "";
	}

}
