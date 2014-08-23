package com.example.zzy_sports.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.zzy_sports.bean.PushUpBean;

public class SportDao extends AbstractBaseDao {
	public static final String TABLE = "zzy_sport";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_DATE = "date";
	public static final String COLUMN_TIMES = "times";
	public static final String COLUMN_NAME = "name";
	
	public static final PushUpTransfer pt = new PushUpTransfer();
	public SportDao(SQLiteDatabase dataSource){
		this.setDatabase(dataSource);
	}
	
	public long exeUpdate(PushUpBean pb){
		ContentValues cv = new ContentValues();
		PushUpBean pbtemp = getPushUpBeanByTime(pb.getDate(),pb.getName());
		if(pbtemp==null){
			cv.put(COLUMN_ID, getNextId());
			cv.put(COLUMN_DATE, pb.getDate());
			cv.put(COLUMN_TIMES, pb.getTimes());
			cv.put(COLUMN_NAME, pb.getName());
			return db.insert(TABLE, null, cv);
		}else{
			cv.put(COLUMN_TIMES,pb.getTimes());
			return db.update(TABLE, cv, COLUMN_DATE + "=?", new String[]{pb.getDate()});
		}
	}
	
	
	private static final String QUERY_GROUP_PUSHUP = 
			String.format("SELECT * FROM %s WHERE %s=? and %s=?"
			, new Object[] {TABLE, COLUMN_DATE,COLUMN_NAME});
	private static final String QUERY_ALL_PUSHUP = 
			String.format("SELECT * FROM %s order by %s limit ?,? "
			, new Object[] {TABLE, COLUMN_DATE});
	public PushUpBean getPushUpBeanByTime(String date,String name){
		ArrayList<PushUpBean> resultList = new ArrayList<PushUpBean>();
		query(QUERY_GROUP_PUSHUP, resultList, pt, new String[]{date,name});
		if(resultList.size() > 0){
			return resultList.get(0);
		}else{
			return null;
		}
	}
	
	
	public List<PushUpBean> getPushUpBeanOrderByTime(String begin,String end){
		ArrayList<PushUpBean> resultList = new ArrayList<PushUpBean>();
		query(QUERY_ALL_PUSHUP, resultList, pt, new String[]{begin,end});
		return resultList;
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
}
