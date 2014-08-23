package com.example.zzy_sports.utils;

import android.database.Cursor;

import com.example.zzy_sports.bean.PushUpBean;

public class PushUpTransfer implements CursorTransferable<PushUpBean> {

	@Override
	public PushUpBean toObject(Cursor mCursor) {
		PushUpBean pb = new PushUpBean();
		pb.setId(mCursor.getInt(mCursor.getColumnIndexOrThrow(SportDao.COLUMN_ID)));
		pb.setDate(mCursor.getString(mCursor.getColumnIndexOrThrow(SportDao.COLUMN_DATE)));
		pb.setName(mCursor.getString(mCursor.getColumnIndexOrThrow(SportDao.COLUMN_NAME)));
		pb.setTimes(mCursor.getString(mCursor.getColumnIndexOrThrow(SportDao.COLUMN_TIMES)));
		return pb;
	}

}
