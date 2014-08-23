package com.example.zzy_sport.view;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zzy_sports.R;
import com.example.zzy_sports.utils.DensityUtil;

public class XListViewHeader extends LinearLayout {
	private LinearLayout mContainer;
	private ImageView mArrowImageView;
	private ProgressBar mProgressBar;
	private TextView mHintTextView;
	private int mState = STATE_NORMAL;

	private Animation mRotateUpAnim;
	private Animation mRotateDownAnim;

	private final int ROTATE_ANIM_DURATION = 180;

	public final static int STATE_NORMAL = 0;
	public final static int STATE_READY = 1;
	public final static int STATE_REFRESHING = 2;
	public static final int LINEAROUT_1_ID = 1;
	public static final int RELAYOUT_ID = 2;
	public static final int HEAD_TIME_VIEW_ID = 3;

	public XListViewHeader(Context context) {
		super(context);
		initView(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public XListViewHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	private void initView(Context context) {
		// 根布局
		mContainer = new LinearLayout(context);
		mContainer.setGravity(Gravity.BOTTOM);
		// 初始情况，设置下拉刷新view高度为0
		LinearLayout.LayoutParams linearLp1 = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, 0);
		// 将根布局加入该LinearLayout中
		addView(mContainer, linearLp1);
		setGravity(Gravity.BOTTOM);

		// 里面的相对布局
		RelativeLayout relayout = new RelativeLayout(context);
		relayout.setId(RELAYOUT_ID);
		LinearLayout.LayoutParams linearLp2 = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, DensityUtil.dip2px(getContext(), 60));
		// 将里面的相对布局加入根布局中
		mContainer.addView(relayout, linearLp2);

		// 相对布局里的子线性布局1
		LinearLayout linear1 = new LinearLayout(context);
		linear1.setId(LINEAROUT_1_ID);
		RelativeLayout.LayoutParams relativeLp1 = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		relativeLp1.addRule(RelativeLayout.CENTER_IN_PARENT);
		linear1.setGravity(Gravity.CENTER);
		linear1.setOrientation(LinearLayout.VERTICAL);
		// 将子布局linear1加入relayout中
		relayout.addView(linear1, relativeLp1);

		// 下拉刷新提示TextView
		mHintTextView = new TextView(context);
		mHintTextView.setText("下拉刷新");
		LinearLayout.LayoutParams linearLp3 = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		// 将TextView加入linear1中
		linear1.addView(mHintTextView, linearLp3);

		// 子线性布局1（linear1）里的线性布局linear12
		LinearLayout linear12 = new LinearLayout(context);
		LinearLayout.LayoutParams linearLp4 = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		linearLp4.setMargins(0, 3, 0, 0);
		// 将linear12加入linear1中
		linear1.addView(linear12, linearLp4);

		// 提示时间TextView
		TextView tv = new TextView(context);
		tv.setText("上次更新时间：");
		tv.setTextSize(12);
		// 将提示时间TextView加入linear12
		linear12.addView(tv, linearLp3);

		// 时间值TextView
		TextView tv2 = new TextView(context);
		tv2.setId(HEAD_TIME_VIEW_ID);
		tv2.setTextSize(12);
		// 将时间值TextView加入linear12
		linear12.addView(tv2, linearLp3);

		// ImageView
		mArrowImageView = new ImageView(context);
		RelativeLayout.LayoutParams relativeLp2 = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		relativeLp2.rightMargin = 20;
		relativeLp2.addRule(RelativeLayout.CENTER_VERTICAL);
		relativeLp2.addRule(RelativeLayout.LEFT_OF, LINEAROUT_1_ID);
		mArrowImageView.setImageBitmap(readAssetImage(context));
		// 将ImageView加到相对布局relayout中
		relayout.addView(mArrowImageView, relativeLp2);

		// ProgressBar
		mProgressBar = new ProgressBar(context);
		RelativeLayout.LayoutParams relativeLp3 = new RelativeLayout.LayoutParams(
				DensityUtil.dip2px(getContext(), 45), DensityUtil.dip2px(getContext(), 45));
		relativeLp3.addRule(RelativeLayout.LEFT_OF, LINEAROUT_1_ID);
		relativeLp3.addRule(RelativeLayout.CENTER_VERTICAL);
		relativeLp3.rightMargin = 20;
		mProgressBar.setVisibility(View.INVISIBLE);
		// 将mProgressBar加到相对布局relayout中
		relayout.addView(mProgressBar, relativeLp3);

		mRotateUpAnim = new RotateAnimation(0.0f, -180.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateUpAnim.setFillAfter(true);
		mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateDownAnim.setFillAfter(true);
	}

	public void setState(int state) {
		if (state == mState)
			return;
		if (state == STATE_REFRESHING) { // 显示进度
			mArrowImageView.clearAnimation();
			mArrowImageView.setVisibility(View.INVISIBLE);
			mProgressBar.setVisibility(View.VISIBLE);
		} else { // 显示箭头图片
			mArrowImageView.setVisibility(View.VISIBLE);
			mProgressBar.setVisibility(View.INVISIBLE);
		}
		switch (state) {
		case STATE_NORMAL:
			if (mState == STATE_READY) {
				mArrowImageView.startAnimation(mRotateDownAnim);
			}
			if (mState == STATE_REFRESHING) {
				mArrowImageView.clearAnimation();
			}
			mHintTextView.setText("下拉刷新");
			break;
		case STATE_READY:
			if (mState != STATE_READY) {
				mArrowImageView.clearAnimation();
				mArrowImageView.startAnimation(mRotateUpAnim);
				mHintTextView.setText("松开刷新数据");
			}
			break;
		case STATE_REFRESHING:
			mHintTextView.setText("正在加载...");
			break;
		default:
		}
		mState = state;
	}

	public void setVisiableHeight(int height) {
		if (height < 0)
			height = 0;
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContainer
				.getLayoutParams();
		lp.height = height;
		mContainer.setLayoutParams(lp);
	}

	public int getVisiableHeight() {
		return mContainer.getHeight();
	}

	/**
	 * 读取assets里面文件名为xlistview_arrow.png的图片
	 * 
	 * @param context
	 * @return bitmap
	 */
	private Bitmap readAssetImage(Context context) {
		AssetManager asset = context.getAssets();
		InputStream assetFile = null;
		Bitmap bitmap = null;
		try {
			assetFile = asset.open("xlistview_arrow.png");
			if(assetFile == null){
				bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.xlistview_arrow);
			}else{				
				bitmap = BitmapFactory.decodeStream(assetFile);
			}
		} catch (IOException e) {
			bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.xlistview_arrow);
//			e.printStackTrace();
		}
		return bitmap;
	}

}
