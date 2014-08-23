package com.example.zzy_sports.utils;

import java.util.List;

import android.database.Cursor;

public abstract interface CursorToCollection
{
  public abstract <T> boolean toList(Cursor paramCursor, CursorTransferable<T> paramCursorTransferable, List<T> paramList);
}
