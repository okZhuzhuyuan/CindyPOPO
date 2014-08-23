package com.example.zzy_sports.utils;

import android.database.Cursor;

import com.example.zzy_sports.bean.PushUpBean;
import com.example.zzy_sports.bean.SportName;

public class SportNameTransfer implements CursorTransferable<SportName> {

	@Override
	public SportName toObject(Cursor mCursor) {
		SportName pb = new SportName();
		pb.setId(mCursor.getInt(mCursor.getColumnIndexOrThrow(SportNameDao.COLUMN_ID)));
		pb.setName(mCursor.getString(mCursor.getColumnIndexOrThrow(SportNameDao.COLUMN_NAME)));
		return pb;
	}

}