package com.wkswind.codereader.synatax;

import android.text.TextUtils;

public class PerlDocumentHandler extends DocumentHandlerImpl {

	@Override
	public String getFileExtension() {
		return "pl";
	}

	@Override
	public String getFileScriptFiles() {
		return "";
	}

}
