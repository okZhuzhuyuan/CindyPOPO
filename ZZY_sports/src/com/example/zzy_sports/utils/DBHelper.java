package com.example.zzy_sports.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 
 * @author zhuzhuyuan
 *
 */
public class DBHelper extends SQLiteOpenHelper {
	private final static String DATABASE_NAME = "zzy_sprots.db";
	private final static int DATABASE_VERSION = 16;
	
    public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
    public void onCreate(SQLiteDatabase db){
		StringBuffer zzy_sport = new StringBuffer("CREATE TABLE ");
		zzy_sport.append("zzy_sport").append("(");
		zzy_sport.append("id").append(" INTEGER primary key,");
		zzy_sport.append("date").append(" varchar(50),");
		zzy_sport.append("times").append(" varchar(50),");
		zzy_sport.append("name").append(" varchar(50) )");
		db.execSQL(zzy_sport.toString());
		
		StringBuffer zzy_sport_name = new StringBuffer("CREATE TABLE ");
		zzy_sport_name.append("zzy_sport_name").append("(");
		zzy_sport_name.append("id").append(" INTEGER primary key,");
		zzy_sport_name.append("name").append(" varchar(50) )");
		db.execSQL(zzy_sport_name.toString());
	}
//
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if(oldVersion != newVersion){
			//放开代码会造成数据丢失
		//	db.execSQL("DROP TABLE IF EXISTS " + SportDao.TABLE);
		//	db.execSQL("DROP TABLE IF EXISTS " + SportNameDao.TABLE);
	      //  onCreate(db);
		}
	}
}
