 
package com.example.zzy_sport.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zzy_sports.R;


 
public class BaseLoadMoreListAdapter<T> extends BaseAdapter {
	protected List<T> mDatas;
	protected Activity mContext;
	protected int pageSize = 15;//每页加载数据条数
	protected int beginIndex = 0;
	protected int totalCount = 0; // 总数据
	protected int lastItem = 0;
	protected boolean inited;	//标示是否第一次加载数据
	protected boolean error;	//标示Tab切换第一次是否加载数据异常，如果加载异常，再次进入Tab页重新加载数据
	protected boolean doing;	//标示正在加载数据
	private OnFooterClickListener mOnFooterClickListener;

	public BaseLoadMoreListAdapter(Activity context) {
		this.mDatas = new ArrayList<T>();
		this.mContext = context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		if(beginIndex + pageSize < totalCount){
			return mDatas.size()+1;
		}else{
			return mDatas.size();
		}
	}
	
	public int getDataCount(){
		return mDatas.size();
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
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
		// TODO Auto-generated method stub
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
		if (beginIndex + pageSize < totalCount && position == getCount() - 1) {
			View viewListMore = mContext.getLayoutInflater().inflate(R.layout.common_listfooter_more, null);
			if(doing){				
				TextView bottomTextView = (TextView)viewListMore.findViewById(R.id.tvShowMore);
				bottomTextView.setText(mContext.getResources().getString(R.string.loading));
			}
			viewListMore.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(!doing){
						if(mOnFooterClickListener != null){
							beginIndexAscending();
							mOnFooterClickListener.onFooterClick();
						}
					}
				}
			});
			return viewListMore;
		}
		return convertView;
	}
	
	public void addData(T mData){
		if(mData != null){			
			mDatas.add(mData);
			notifyDataSetChanged();
		}
	}
	
	public void addDatas(List<T> datas){
		if(datas.size() > 0){			
			mDatas.addAll(datas);
			notifyDataSetChanged();
		}
	}

	public void clearDatas(){
		if(mDatas.size() > 0){			
			mDatas.clear();
			notifyDataSetChanged();
		}
	}
	
	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getBeginIndex() {
		return beginIndex;
	}
	
	public void initBeginIndex(){
		beginIndex = 0;
		totalCount = 0;
	}

	public void beginIndexAscending() {
		beginIndex += pageSize;
	}
	
	public boolean isInited() {
		return inited;
	}

	public void inited() {
		this.inited = true;
	}
	
	public void notInited() {
		this.inited = false;
	}

	public boolean isError() {
		return error;
	}

	/**
	 * setError
	 * 描述：标示Tab切换第一次是否加载数据异常，如果加载异常，再次进入Tab页重新加载数据
	 * 创建人：李满义
	 * 创建日期：2013-2-28 下午1:48:35
	 * @param error
	 * 修改人：
	 * 修改日期：
	 * 修改备注：
	 */
	public void setError(boolean error) {
		this.error = error;
	}

	public boolean isDoing() {
		return doing;
	}

	public void setDoing(boolean doing) {
		this.doing = doing;
	}
	
	public int getLastItem() {
		return lastItem;
	}

	public void setLastItem(int lastItem) {
		this.lastItem = lastItem;
	}

	public interface OnFooterClickListener {
		public void onFooterClick();
	}

	public OnFooterClickListener getOnFooterClickListener() {
		return mOnFooterClickListener;
	}

	public void setOnFooterClickListener(
			OnFooterClickListener mOnFooterClickListener) {
		this.mOnFooterClickListener = mOnFooterClickListener;
	}
	
}
