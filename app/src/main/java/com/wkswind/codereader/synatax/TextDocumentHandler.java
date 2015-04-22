package com.wkswind.codereader.synatax;

import android.text.TextUtils;

public class TextDocumentHandler extends DocumentHandlerImpl {

	@Override
	public String getFileExtension() {
		return "txt";
	}

	@Override
	public String getFileScriptFiles() {
		return "";
	}

}
