package com.example.zzy_sport.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.zzy_sports.R;
import com.example.zzy_sports.R.id;
import com.example.zzy_sports.R.layout;
import com.example.zzy_sports.bean.SportName;

public class AccountAdapter extends BaseListAdapter<SportName> {
	private Context mContext;

	public AccountAdapter(Context context) {
		super(context);
		mContext = context;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView != null && convertView.getId() == R.id.list_item) {
			holder = (ViewHolder) convertView.getTag();
		} else {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.listitem_loginaccount, null);
			holder = new ViewHolder();
			holder.tvAccount = (TextView) convertView.findViewById(R.id.tvAccount);
			holder.ibtnDelAccount = (ImageButton)convertView.findViewById(R.id.ibtnDelAccount);
			convertView.setTag(holder);
			
		}
		SportName pb = (SportName) mDatas.get(position);
		holder.tvAccount.setText(pb.getName());
		if(delAccountOnClickListener != null){	
			holder.ibtnDelAccount.setTag(String.valueOf(position));
			holder.ibtnDelAccount.setOnClickListener(delAccountOnClickListener);
		}
		if(itemOnClickListener != null){
			convertView.setTag(R.id.tag_childindex, position);
			convertView.setOnClickListener(itemOnClickListener);
		}
		return convertView;	
	}
	
	private OnClickListener delAccountOnClickListener;
	public void setDelAccountOnClickListener(
			OnClickListener delAccountOnClickListener) {
		this.delAccountOnClickListener = delAccountOnClickListener;
	}
	private OnClickListener itemOnClickListener;
	public void setItemOnClickListener(OnClickListener itemOnClickListener) {
		this.itemOnClickListener = itemOnClickListener;
	}


	class ViewHolder{
		TextView tvAccount;
		ImageButton ibtnDelAccount;
	}
}
