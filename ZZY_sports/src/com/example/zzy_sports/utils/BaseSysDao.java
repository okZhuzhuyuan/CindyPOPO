package com.example.zzy_sports.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 
 * @author zhuzhuyuan
 *
 */
public class BaseSysDao {
//	private Context mContext;
	protected SQLiteDatabase db;
//	private DBHelper dbHelp;
	
	public BaseSysDao() {
		super();
	}
	
//	public BaseSysDao(Context context) {
//		super();
//		this.mContext = context;
//	}
//	public BaseSysDao open() throws SQLiteException{
//		dbHelp = new DBHelper(mContext); 
//		db = dbHelp.getWritableDatabase();
//		return this;
//	}
	
	public SQLiteDatabase getDb() {
		return db;
	}
	
	public void setDatabase(SQLiteDatabase db){
		this.db = db;
	}
	
//	private void close(){
//		if(db != null){
//			db.close();
//		}
//		db = null;
//	}
	
//	public void safeReleaseDatabase(){
//		try{
//			this.close();
//		}catch(Exception e){
//		}
//	}
	
	public void closeCursor(Cursor mCursor){
		if(mCursor != null && !mCursor.isClosed()){				
			try{
				mCursor.close();
			}catch(Exception e){
			}
		}
	}
}
