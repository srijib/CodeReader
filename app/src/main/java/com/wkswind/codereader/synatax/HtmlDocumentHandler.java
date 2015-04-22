package com.wkswind.codereader.synatax;

import android.text.TextUtils;

public class HtmlDocumentHandler extends DocumentHandlerImpl {

	@Override
	public String getFileExtension() {
		return "html";
	}

	@Override
	public String getFileScriptFiles() {
		return "";
	}

}
