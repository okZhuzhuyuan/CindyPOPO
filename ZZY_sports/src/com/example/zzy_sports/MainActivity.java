package com.example.zzy_sports;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.zzy_sports.app.SystemApplication;
import com.example.zzy_sports.bean.PushUpBean;
import com.example.zzy_sports.utils.DateUtil;
import com.example.zzy_sports.utils.SportDao;

/**
 * 
 * @author zhuzhuyuan
 * 
 */
public class MainActivity extends Activity {

	private Button pushUpTimes;
	private Button saveTimes;
	private Button showTimesInfo;
	private Button restnums;
	private int times;
	private SportDao sportDao;
	private String name;
	private Context context = this;
	private Button sport_user;
	private Intent intent = new Intent();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initProperties();
		initBehavior();
	}

	private void initProperties() {
		pushUpTimes = (Button) findViewById(R.id.pushUpTimes);
		saveTimes = (Button) findViewById(R.id.save_times);
		showTimesInfo = (Button) findViewById(R.id.show_times_info);
		restnums = (Button) findViewById(R.id.restnums);
		sport_user =  (Button)findViewById(R.id.sport_user);
		sportDao = SystemApplication.getSportDao();
		Intent intent = getIntent();  
		name = intent.getStringExtra("name");
		sport_user.setText("锻炼者："+name);
		PushUpBean pb = sportDao.getPushUpBeanByTime(DateUtil.getCurrDate(),name);
		if(pb != null && pb.getTimes() != null && !pb.getTimes().equals("")){
			times = Integer.valueOf(pb.getTimes());
		}
		else
		{
			times = 0;
		}
		pushUpTimes.setText("" + times + "次");
	}

	private void initBehavior() {
		pushUpTimes.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				times++;
				pushUpTimes.setText("" + times + "次");
			}
		});
		saveTimes.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				PushUpBean pb = new PushUpBean();
				pb.setDate(DateUtil.getCurrDate());
				pb.setName(name);
				pb.setTimes("" + times);
				sportDao.exeUpdate(pb);
			}
		});
		showTimesInfo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// go to other page;
				intent.setClass(context, PushUpTimesShowActivity.class);
				intent.putExtra("name", name);
				startActivity(intent);
			}
		});
		
		restnums.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// go to other page;
				times = 0;
				PushUpBean pb = new PushUpBean();
				pb.setDate(DateUtil.getCurrDate());
				pb.setName(name);
				pb.setTimes("" + times);
				sportDao.exeUpdate(pb);
				pushUpTimes.setText("" + times + "次");
			}
		});
	}
	@Override
	protected void onPause(){
		super.onPause();
		PushUpBean pb = new PushUpBean();
		pb.setDate(DateUtil.getCurrDate());
		pb.setName(name);
		pb.setTimes("" + times);
		sportDao.exeUpdate(pb);
	}
	@Override
	protected void onResume(){
		super.onResume();
		PushUpBean pb = sportDao.getPushUpBeanByTime(DateUtil.getCurrDate(),name);
		if(pb != null && pb.getTimes() != null && !pb.getTimes().equals("")){
			times = Integer.valueOf(pb.getTimes());
			
		}
		else
		{
			times = 0;
		}
		pushUpTimes.setText("" + times + "次");
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
	}
}
