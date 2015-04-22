package com.wkswind.codereader.synatax;

import android.text.TextUtils;

public class PythonDocumentHandler extends DocumentHandlerImpl {

	@Override
	public String getFileExtension() {
		return "py";
	}

	@Override
	public String getFileScriptFiles() {
		return "";
	}

}
