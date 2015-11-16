package com.wkswind.codereader;

import android.app.Application;
import android.content.Context;

import com.wkswind.codereader.database.DaoMaster;
import com.wkswind.codereader.database.DaoSession;
import com.wkswind.codereader.database.SqlHelper;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

@ReportsCrashes(formKey = "", mailTo = "wkswind@gmail.com",
mode = ReportingInteractionMode.TOAST,
forceCloseDialogAfterToast = false, // optional, default false
resToastText = R.string.crash_toast_text)
public class CodeReaderApplication extends Application {
	private static DaoSession session;
	private static DaoMaster master;
	@Override
	public void onCreate() {
		super.onCreate();
		ACRA.init(this);
		initGreenDaoConfig(this);
	}

	public static DaoSession getSession(){
		return session;
	}

	private void initGreenDaoConfig(Context context) {
		if(master == null){
			synchronized (CodeReaderApplication.class){
				if(master == null){
					master = new DaoMaster(new SqlHelper(context).getWritableDatabase());
				}
			}
		}
		if(session == null){
			synchronized (CodeReaderApplication.class){
				if(session == null){
					session = master.newSession();
				}
			}
		}
	}

}
