package com.example.zzy_sports.utils;

import java.util.List;

import android.database.Cursor;

public class SimpleCursorSet
implements CursorToCollection
{
public <T> boolean toList(Cursor paramCursor, CursorTransferable<T> transfer, List<T> list)
{
  assert (paramCursor != null);
  assert (list != null);
  T result = transfer.toObject(paramCursor);
  if (result != null) {
    list.add(result);
  }
  return false;
}
}
