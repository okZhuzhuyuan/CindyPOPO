package com.example.zzy_sports.app;


import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.zzy_sports.utils.DataSource;
import com.example.zzy_sports.utils.PreferencesUtil;
import com.example.zzy_sports.utils.SportDao;
import com.example.zzy_sports.utils.SportNameDao;

public class SystemApplication extends Application {
	public static PreferencesUtil mPreferUtil;
	private static SystemApplication mInstance = null;
	private static SportDao sportDao = null;
	private static SportNameDao sportNameDao = null;
	@Override
	public void onCreate(){
		super.onCreate();
		mPreferUtil = PreferencesUtil.getPreferencesUtilInstance();
		mPreferUtil.setContext(getApplicationContext());
		mInstance = (SystemApplication)getApplicationContext();
		initDAO();
	}
	
	
	private void initDAO(){
		Context context = getApplicationContext();
		DataSource dataSource = new DataSource(context);
		SQLiteDatabase database = dataSource.openWrite();
		sportDao = new SportDao(database);
		sportNameDao = new SportNameDao(database);
	}
	
	public static SportDao getSportDao(){
		return sportDao;
	}
	public static SportNameDao getSportNameDao(){
		return sportNameDao;
	}
	public PreferencesUtil getPreferencesUtil(){
		return mPreferUtil;
	}
	
	public static SystemApplication getInstance() {
		
		return mInstance;
	}
}
