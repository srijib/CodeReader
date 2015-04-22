package com.wkswind.codereader.synatax;

import android.text.TextUtils;

public class RubyDocumentHandler extends DocumentHandlerImpl {

	@Override
	public String getFileExtension() {
		return "rb";
	}

	@Override
	public String getFileScriptFiles() {
		return "";
	}

}
