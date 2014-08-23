package com.example.zzy_sports;

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.zzy_sport.adapter.BaseLoadMoreListAdapter;
import com.example.zzy_sport.view.XListView;
import com.example.zzy_sport.view.XListView.IXListViewListener;
import com.example.zzy_sports.app.SystemApplication;
import com.example.zzy_sports.bean.PushUpBean;
import com.example.zzy_sports.utils.DateUtil;
import com.example.zzy_sports.utils.SportDao;

public class PushUpTimesShowActivity extends Activity implements  IXListViewListener{
	private XListView mPullToRefreshView;
	private ListAdapter listAdapter;
	private SportDao sportDao;
	private String name;
	private Button sport_user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_push_up_times_show);
		
		Intent intent = getIntent();  
		name = intent.getStringExtra("name");
		mPullToRefreshView = (XListView) findViewById(R.id.news_list);
		mPullToRefreshView.setRefreshTime("刚刚");
		mPullToRefreshView.setPullLoadEnable(false);
		mPullToRefreshView.setPullRefreshEnable(true);
		mPullToRefreshView.setXListViewListener(this);
	    mPullToRefreshView.setLongClickable(true);
	    listAdapter = new  ListAdapter(this);
		mPullToRefreshView.setAdapter(listAdapter);
		sportDao = SystemApplication.getSportDao();
		sport_user =  (Button)findViewById(R.id.sport_user);
		sport_user.setText(name);
		mPullToRefreshView.headerRefreshing();
	}

	class ListAdapter extends BaseLoadMoreListAdapter<PushUpBean>{
		
    	public ListAdapter(Activity context){
    		super(context);
    	}
    	
    	@Override
    	public int getCount() {
    		return mDatas.size();
    	}
    	
		@Override
		public View getView(final int position, View view, ViewGroup parent) {
			final ViewHolder holder;
			if (view != null && view.getId() == R.id.list_item) {
				holder = (ViewHolder) view.getTag();
			} else {
				view = getLayoutInflater().inflate(R.layout.listitem , null);
				holder = new ViewHolder();
				holder.tvtimes = (TextView) view.findViewById(R.id.tvtimes);
				holder.tvDate = (TextView) view.findViewById(R.id.tvDate);
				view.setTag(holder);
			}
			if(mDatas.size() > 0 && position < mDatas.size()){
				final PushUpBean pb = mDatas.get(position);	
				if (null != pb) {
					holder.tvDate.setText("时间："+pb.getDate());
					holder.tvtimes.setText("次数："+pb.getTimes());
				}
			}
			return view;
		}
		

		class ViewHolder {
			TextView tvtimes;
			TextView tvDate;
		}
	}
	@Override
	public void onRefresh() {
		List<PushUpBean> list = sportDao.getPushUpBeanOrderByTime("0",""+(listAdapter.getBeginIndex() + listAdapter.getPageSize()));
		listAdapter.clearDatas();
		listAdapter.addDatas(list);
		initExpenseRecordList("0",""+(listAdapter.getBeginIndex() + listAdapter.getPageSize()));
			
	}

	@Override
	public void onLoadMore() {
		listAdapter.beginIndexAscending();
		List<PushUpBean> list = sportDao.getPushUpBeanOrderByTime(""+listAdapter.getBeginIndex(), ""+listAdapter.getPageSize());
		listAdapter.addDatas(list);
		initExpenseRecordList(""+listAdapter.getBeginIndex(), ""+listAdapter.getPageSize());
		
		
	}
	
	public void initExpenseRecordList(String beginIndex,String endIndex){
		
		
		mPullToRefreshView.setRefreshTime(DateUtil.sdf_T.format(new Date(System.currentTimeMillis())));
		if(beginIndex.equals("0")){		
			mPullToRefreshView.stopRefresh();
		}else{					
			mPullToRefreshView.stopLoadMore();
		}
		if (listAdapter.getCount() < listAdapter.getTotalCount()) {
			mPullToRefreshView.setPullLoadEnable(true);
		}else{
			mPullToRefreshView.setPullLoadEnable(false);
		}
	}

}
