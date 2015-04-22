package com.wkswind.codereader.synatax;

import android.text.TextUtils;

public class XmlDocumentHandler extends DocumentHandlerImpl {
	@Override
	public String getFileExtension() {
		return "xml";
	}

	@Override
	public String getFileScriptFiles() {
		return "";
	}

}
