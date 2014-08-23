package com.example.zzy_sports.utils;

import android.database.Cursor;

public abstract interface CursorTransferable<T>
{
  public abstract T toObject(Cursor paramCursor);
}