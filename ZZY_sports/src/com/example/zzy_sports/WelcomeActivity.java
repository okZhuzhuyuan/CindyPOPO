package com.example.zzy_sports;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.example.zzy_sport.adapter.AccountAdapter;
import com.example.zzy_sports.app.SystemApplication;
import com.example.zzy_sports.bean.SportName;
import com.example.zzy_sports.utils.SportNameDao;

public class WelcomeActivity extends Activity {

	private Button nextPage;
	private ImageButton ibtnAccountMore;
	private Intent intent = new Intent();
	private Context context = this;
	private PopupWindow popWinAccount; 
	private EditText etAccount;
	private ListView lvAccount;
	private AccountAdapter accountAdapter;
	private List<SportName> users;
	private SportNameDao sportNameDao;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_sports);
		
		nextPage = (Button)findViewById(R.id.nextpage);
		ibtnAccountMore = (ImageButton)findViewById(R.id.ibtnAccountMore);
		etAccount = (EditText)findViewById(R.id.etAccount);
		
		sportNameDao = SystemApplication.getSportNameDao();
		users =sportNameDao.getSportNameList();
		if(users!=null && users.size() > 0){
			etAccount.setText(users.get(0).getName());
		}
		nextPage.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				String name = etAccount.getText().toString();
				name = name.trim();
				etAccount.setText(name);
				if(name.equals("")){
					name = "路人甲";
				}
				
				SportName sn = new SportName();
				sn.setName(name);
				sportNameDao.exeUpdate(sn);
				intent.setClass(context, MainActivity.class);
				intent.putExtra("name", name);
				startActivity(intent);
				finish();
			}
		});
		ibtnAccountMore.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				if (popWinAccount == null) {
					if (accountAdapter == null) {
						accountAdapter = new AccountAdapter(context);
						//初始化已登录用户
						initUserAccount();
						lvAccount = (ListView) LayoutInflater.from(context).inflate(R.layout.little_list, null);
						lvAccount.setAdapter(accountAdapter);
						accountAdapter.setItemOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View arg0) {
								int position = Integer.parseInt(arg0.getTag(R.id.tag_childindex).toString());
								SportName tempName = (SportName) accountAdapter.getItem(position);
								etAccount.setText(tempName.getName());
								//重新初始化已登录用户
								popWinAccount.dismiss();
								popWinAccount = null;
							}
						});
						accountAdapter.setDelAccountOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View arg0) {
								int position = Integer.parseInt(arg0.getTag().toString());
								SportName tempName = (SportName) accountAdapter.getItem(position);
								
								try {
//									userDao.open();
									if(sportNameDao.delete(""+tempName.getId())){
										//重新初始化已登录用户
										accountAdapter.removeData(tempName);
									}
								} catch (SQLiteException e) {
									e.printStackTrace();
								}finally{
//									userDao.safeReleaseDatabase();
									popWinAccount.update();
									//popWinAccount.dismiss();
								}
							}
						});
						popWinAccount = new PopupWindow(lvAccount, etAccount.getWidth(), android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
						popWinAccount.showAsDropDown(etAccount);
					} else {
						//初始化已登录用户
						///initLoginUserAccount(etAccount.getText().toString().trim());
						popWinAccount = new PopupWindow(lvAccount, etAccount.getWidth(), android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
						popWinAccount.showAsDropDown(etAccount);
					}
				} else {
					popWinAccount.dismiss();
					popWinAccount = null;
				}
			}
		});
		
	}
	
	public void initUserAccount(){
		try {
//			userDao.open();
			
			accountAdapter.clearAndAppendData(users);
		} catch (SQLiteException e) {
			e.printStackTrace();
		}finally{
//			userDao.safeReleaseDatabase();
		}
	}

}
