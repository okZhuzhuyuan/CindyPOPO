package com.example.zzy_sport.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class BaseListAdapter<T> extends BaseAdapter {
	protected List<T> mDatas;
	protected Context mContext;

	public BaseListAdapter(Context context) {
		this.mDatas = new ArrayList<T>();
		this.mContext = context;
	}

	public List<T> getDatas() {
		return mDatas;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return mDatas.size();
	}
	
	public int getDataCount(){
		return mDatas.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		if (mDatas.size() <= position)return null;
		return mDatas.get(position);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return convertView;
	}
	
	
	public void clearAndAppendData(List<T> datas){
		this.mDatas.clear();
		appendDatas(datas);
	}
	
	public void removeData(T mData){
		this.mDatas.remove(mData);
		notifyDataSetChanged();
	}
	
	public void removeData(int position){
		T mData = mDatas.get(position);
		this.mDatas.remove(mData);
		notifyDataSetChanged();
	}
	
	public void appendData(T mData){
		this.mDatas.add(mData);
		notifyDataSetChanged();
	}
	
	public void appendDatas(List<T> datas){
		this.mDatas.addAll(datas);
		notifyDataSetChanged();
	}

	public void clearDatas(){
		this.mDatas.clear();
		notifyDataSetChanged();
	}
}
