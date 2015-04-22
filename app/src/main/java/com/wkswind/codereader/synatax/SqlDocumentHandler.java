package com.wkswind.codereader.synatax;

import android.text.TextUtils;

public class SqlDocumentHandler extends DocumentHandlerImpl {

	@Override
	public String getFileExtension() {
		return "sql";
	}

	@Override
	public String getFilePrettifyClass() {
		return "prettyprint lang-sql";
	}

	@Override
	public String getFileScriptFiles() {
		return "<script src='file:///android_asset/lang-sql.js' type='text/javascript'></script> ";
	}

}
