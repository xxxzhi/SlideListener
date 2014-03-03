/**
 * 
 */
package com.scut.houzhi.slidefragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

/**
 * @author houzhi
 *	可监听滑动删除 fragment
 *	使用该fragment 的Activity 必须实现OnFragmentWantFinishListener 接口
 *
 *	
 */
public abstract class BaseSlideFinishFragment extends Fragment {
	public static interface OnFragmentWantFinishListener {
			public void onFragmentWantFinnish(Fragment fragment);
		}
	 
	//监听 fragment 接口
	protected OnFragmentWantFinishListener mOnFragmentWantFinishListener;
	
	protected Activity activity ;
	
	/**
	 * 屏幕信息
	 */
	protected DisplayMetrics outMetrics = new DisplayMetrics();
	
	@Override
	public void onAttach(Activity activity) {
		if(activity instanceof OnFragmentWantFinishListener){
			mOnFragmentWantFinishListener = (OnFragmentWantFinishListener)activity;
		}else{
			throw new RuntimeException("the Activity which use SlideFinishFragment" +
					" must implements OnFragmentWantFinishLIstener!");
		}
		this.activity = activity ;
		
		activity.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		
		super.onAttach(activity);
	}
	
	
	
	
	protected FrameLayout.LayoutParams saveParams = null;
	/**
	 * the onCreateView will callback this method 
	 * @param inflater
	 * @param container
	 * @param savedInstanceState
	 * @return
	 */
	abstract protected View onCreateContentView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState);
}
