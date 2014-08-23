package com.example.zzy_sports.utils;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.text.TextUtils;

public abstract class AbstractBaseDao extends BaseSysDao {

	private static final CursorToCollection ctc = new SimpleCursorSet();
	
	protected PrimaryKeyTransfer singleLongTransfer = new PrimaryKeyTransfer(null);
	
	public AbstractBaseDao(){}
	
//	public AbstractBaseDao(Context context){
//		super(context);
//	}
	
	protected <T> int query(String sql, CursorToCollection cursorToCollection
			, CursorTransferable<T> transfer, List<T> resultList, String[] params){
		assert (!TextUtils.isEmpty(sql));
		int i = 0;
		Cursor cursor = null;
		try {
			cursor = db.rawQuery(sql, params);
			while(cursor.moveToNext()){
				i++;
				cursorToCollection.toList(cursor, transfer, resultList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			closeCursor(cursor);
		}
		return i;
	}
	
	/**
	 * 查询数据库，并把结果放到给定集合中
	 * @param sql 
	 * @param paramList 集合结果
	 * @param transfer 对象转换器
	 */
	public final <T> void query(String sql, List<T> resultList, CursorTransferable<T> transfer, String[] params){
		query(sql, ctc, transfer, resultList, params);
	}
	
	public final String queryPrimaryKey(String sql){
	    Object result = getSingle(sql, new PrimaryKeyTransfer(null), null);
	    if (result != null){
//	    	return ((Integer)result).intValue();
	    	return result.toString();
	    }
	    return null;
	}
	
	/**
	 * 返回统计结果
	 * @param sql
	 * @return
	 */
	public int queryForInt(String sql, String[] params){
	    Object result = getSingle(sql, new PrimaryKeyTransfer(null), params);
	    if (result != null)
	      return ((Integer)result).intValue();
	    return 0;
	}
	
	public final void execSQL(String sql, Object[] args){
	    this.db.execSQL(sql, args);
	}
	
	public final <T> T getSingle(String sql, CursorTransferable<T> transfer, String[] params){
	    List<T> localArrayList = new ArrayList<T>();
	    query(sql, ctc, transfer, localArrayList, params);
	    if (localArrayList.size() > 0)
	      return localArrayList.get(0);
	    return null;
	}
	
}