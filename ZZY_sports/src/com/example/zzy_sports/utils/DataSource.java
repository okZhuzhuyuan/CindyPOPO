package com.example.zzy_sports.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DataSource {

	private SQLiteDatabase db;
	private DBHelper dbHelp;
	
	public DataSource(Context context){
		dbHelp = new DBHelper(context);
	}
	
	public SQLiteDatabase openWrite(){
		if(db == null || !db.isOpen()){
			db = dbHelp.getWritableDatabase();
			/*if(!db.isReadOnly()) {
				// Enable foreign key constraints
				db.execSQL("PRAGMA foreign_keys = ON;");
			}*/
		}
		return db;
	}
	
	public SQLiteDatabase openReadOnly(){
		if(db == null || !db.isOpen()){
			db = dbHelp.getReadableDatabase();
		}
		return db;
	}
	
	public void shutdown(){
		if(db != null && db.isOpen()){
			db.close();
		}
	}
}
