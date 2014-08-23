package com.example.zzy_sports.utils;

import android.database.Cursor;

public class PrimaryKeyTransfer implements CursorTransferable {
	public PrimaryKeyTransfer(AbstractBaseDao baseDao) {
	}

	public Object toObject(Cursor cursor) {
		return Integer.valueOf(cursor.getInt(0));
	}
}
