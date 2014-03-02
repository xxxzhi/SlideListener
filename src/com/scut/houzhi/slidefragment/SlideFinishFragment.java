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
public abstract class SlideFinishFragment extends Fragment {
	public interface OnFragmentWantFinishListener {
			public void onFragmentWantFinnish(Fragment fragment);
		}
	 
	//监听 fragment 接口
	private OnFragmentWantFinishListener mOnFragmentWantFinishListener;
	
	private Activity activity ;
	
	/**
	 * 平面信息
	 */
	private DisplayMetrics outMetrics = new DisplayMetrics();
	
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
	
	
	
	
	private FrameLayout.LayoutParams saveParams = null;
	
	/**
	 * 
	 */
	@Override
	final public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		//用一个framelayout 包装子类传过来的
		final FrameLayout parent = new FrameLayout(container.getContext());
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		params.gravity = Gravity.TOP | Gravity.LEFT;
		parent.setLayoutParams(params);
		
		View view = onCreateContentView(inflater, container, savedInstanceState);
		//添加子类 产生 的view
		parent.addView(view);
		
		parent.setOnTouchListener(new  View.OnTouchListener() {
			float beginX = 0 ;
			
			boolean isMoveFragment = false;
			boolean isFirstMoveEvent = false;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				float moveDis = 0 ;
				 
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					beginX = event.getRawX();
					isFirstMoveEvent = true ;
					isMoveFragment = true;
					Log.i("", "touch ACTION_DOWN beginX---"+ beginX );
					
					saveParams = new FrameLayout.LayoutParams((ViewGroup.MarginLayoutParams) v.getLayoutParams());
					return true;
				case MotionEvent.ACTION_MOVE:
					if(!isMoveFragment){
						//非滑动fragment
						break;
					}
					moveDis = event.getRawX() - beginX;
					if(isFirstMoveEvent){
						if( moveDis ==0 ){
							isMoveFragment = false;
							break;
						}else{
							isMoveFragment = true;
						}
					}
					isFirstMoveEvent = false;
					
					FrameLayout.LayoutParams params =(FrameLayout.LayoutParams) v.getLayoutParams();
					
					params.leftMargin = (int) moveDis;
					params.rightMargin = (int) - moveDis;
					params.gravity = Gravity.LEFT | Gravity.TOP;
					Log.i("", "touch ACTION_MOVE---"+ moveDis  +"--"+event.getRawX() );
					v.setLayoutParams(params);
					return true;
				case MotionEvent.ACTION_UP:
					if( !isMoveFragment )
						break;
					
					moveDis = event.getRawX() - beginX;
					float dis = - moveDis ;
					boolean disappear = false;
					float from = 0,to = -moveDis;
					if(Math.abs(moveDis) > v.getWidth()/2){
						//移动超出  消失此Fragment
						to = v.getWidth()*(Math.abs(moveDis)/moveDis);
						disappear = true;
					}
					long duration = 500;
					Log.i("", "touch dis---"+disappear+"=---="+(moveDis)+"---"+ v.getWidth());
					startEndAnimation(v, duration, from,to, disappear);
					return true;
				}
				
				
				return false;
				
//				float moveDis = 0 ;
//				switch(event.getAction()){
//				case MotionEvent.ACTION_DOWN:
//					beginX = event.getX();
//					break;
//				case MotionEvent.ACTION_MOVE:
//					moveDis = event.getX() - beginX;
//					FrameLayout.LayoutParams params =(FrameLayout.LayoutParams) v.getLayoutParams();
//					
//					params.leftMargin += moveDis;
//					params.rightMargin -= moveDis;
//					params.gravity = Gravity.LEFT | Gravity.TOP;
//					v.setLayoutParams(params);
//					return true;
//				case MotionEvent.ACTION_UP:
//					moveDis = event.getX() - beginX;
//					break;
//				}
//				
//				
//				
//				return true;
				
			}
		});
		
		return parent;
	}
	
	
	
	//移动动画
	private void startEndAnimation(final View view,long duration,float from,float to,final boolean disappear){
		AnimationSet animationSet = new AnimationSet(true);
		
		if(disappear){
			AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
			animationSet.addAnimation(alphaAnimation);
		}
		TranslateAnimation translateAnimation =
				new TranslateAnimation(from,to,  0, 0);
		animationSet.addAnimation(translateAnimation);
		animationSet.setDuration(duration);
		animationSet.setFillAfter(true);
		animationSet.setFillEnabled(true);
		animationSet.setAnimationListener(new Animation.AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation arg0) {
				if(disappear){
					mOnFragmentWantFinishListener.onFragmentWantFinnish(SlideFinishFragment.this);
				}else{
					//恢复  一定要清除动画，否则动画效果仍然在，params 效果 跟Animation 效果同时作用着
					view.clearAnimation();
					view.setLayoutParams(saveParams);
					
				}
			}
		});
		
		view.startAnimation(animationSet);
		
		
		
	}
	
	
	
	
	
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
