package com.wkswind.codereader.synatax;

import android.text.TextUtils;

public class LuaDocumentHandler extends DocumentHandlerImpl {

	@Override
	public String getFileExtension() {
		return "lua";
	}

	@Override
	public String getFilePrettifyClass() {
		return "prettyprint lang-lua";
	}

	@Override
	public String getFileScriptFiles() {
		return "<script src='file:///android_asset/lang-lua.js' type='text/javascript'></script> ";
	}

}
