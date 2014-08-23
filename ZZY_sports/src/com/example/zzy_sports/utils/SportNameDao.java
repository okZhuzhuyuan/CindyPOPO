package com.example.zzy_sports.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.zzy_sports.bean.PushUpBean;
import com.example.zzy_sports.bean.SportName;

public class SportNameDao extends AbstractBaseDao {
	public static final String TABLE = "zzy_sport_name";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_NAME = "name";
	
	public static final SportNameTransfer pt = new SportNameTransfer();
	public SportNameDao(SQLiteDatabase dataSource){
		this.setDatabase(dataSource);
	}
	
	public long exeUpdate(SportName sn){
		ContentValues cv = new ContentValues();
		SportName pbtemp = getSportName(sn.getName());
		if(pbtemp==null){
			cv.put(COLUMN_ID, getNextId());
			cv.put(COLUMN_NAME, sn.getName());
			return db.insert(TABLE, null, cv);
		}else{
			return 0;
		}
	}
	private static final String QUERY_GROUP_SPORTNAME = 
			String.format("SELECT * FROM %s WHERE %s=? "
			, new Object[] {TABLE, COLUMN_NAME});
	private static final String QUERY_ALL_PUSHUP = 
			String.format("SELECT * FROM %s  "
			, new Object[] {TABLE});
	public SportName getSportName(String name){
		ArrayList<SportName> resultList = new ArrayList<SportName>();
		query(QUERY_GROUP_SPORTNAME, resultList, pt, new String[]{name});
		if(resultList.size() > 0){
			return resultList.get(0);
		}else{
			return null;
		}
	}
	public int getNextId(){
		Cursor mCursor = null;
		try {
			mCursor = db.query(TABLE, new String[]{COLUMN_ID}, null, null, null, null, null);
			if(mCursor != null && !mCursor.isFirst()){
				mCursor.moveToFirst();
				return mCursor.getCount();				
			}
		} catch (Exception e) {
		}finally{
			closeCursor(mCursor);
		}
		return 0;
	}
	public boolean delete(String account){
		return db.delete(TABLE, COLUMN_ID + "=" + account, null)>0;
	}
	public List<SportName> getSportNameList(){
		ArrayList<SportName> resultList = new ArrayList<SportName>();
		query(QUERY_ALL_PUSHUP, resultList, pt, null);
		return resultList;
	}
	
}
