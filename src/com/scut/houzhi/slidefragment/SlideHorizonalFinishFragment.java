package com.scut.houzhi.slidefragment;

import android.os.Bundle;
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

public abstract class SlideHorizonalFinishFragment extends BaseSlideFinishFragment {
	
	
	protected static enum SlideDirection{ LEFT,RIGHT,HORIZONAL};
	
	//滑动方向
	private SlideDirection direction = SlideDirection.HORIZONAL;
	
	public SlideHorizonalFinishFragment(boolean isLeft){
		if(isLeft){
			direction = SlideDirection.LEFT;
		}else{
			direction = SlideDirection.RIGHT;
		}
	}
	
	public SlideHorizonalFinishFragment(){
		
	}
	
	
	/**
	 * 
	 */
	@Override
	final public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {

		// 用一个framelayout 包装子类传过来的
		final FrameLayout parent = new FrameLayout(container.getContext());
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		params.gravity = Gravity.TOP | Gravity.LEFT;
		parent.setLayoutParams(params);

		View view = onCreateContentView(inflater, container, savedInstanceState);
		// 添加子类 产生 的view
		parent.addView(view);

		parent.setOnTouchListener(new View.OnTouchListener() {
			float beginX = 0;

			boolean isMoveFragment = false;
			boolean isFirstMoveEvent = false;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				float moveDis = 0;

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					beginX = event.getRawX();
					isFirstMoveEvent = true;
					isMoveFragment = true;
					Log.i("", "touch ACTION_DOWN beginX---" + beginX);

					saveParams = new FrameLayout.LayoutParams(
							(ViewGroup.MarginLayoutParams) v.getLayoutParams());
					return true;
				case MotionEvent.ACTION_MOVE:
					if (!isMoveFragment) {
						// 非滑动fragment
						break;
					}
					moveDis = event.getRawX() - beginX;
					if (isFirstMoveEvent) {
						isFirstMoveEvent = false;
						switch (direction) {
						case LEFT:
							if (moveDis <= 0) isMoveFragment = false;
							break;
						case RIGHT:
							if (moveDis >= 0) isMoveFragment = false;
							break;
						case HORIZONAL:
							if (moveDis == 0) isMoveFragment = false;
							break;
						default:
							break;
						}
						if(!isMoveFragment){
							
							break;
						}
					}else{
						switch (direction) {
						case LEFT:
							if (moveDis <= 0) moveDis = 0 ;
							break;
						case RIGHT:
							if (moveDis >= 0)  moveDis = 0;
							break;
						case HORIZONAL:
							break;
						default:
							break;
						}
					}
					
					
					
					FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) v
							.getLayoutParams();
					
					params.leftMargin = (int) moveDis;
					params.rightMargin = (int) -moveDis;
					params.gravity = Gravity.LEFT | Gravity.TOP;
					Log.i("",
							"touch ACTION_MOVE---" + moveDis + "--"
									+ event.getRawX());
					v.setLayoutParams(params);
					return true;
				case MotionEvent.ACTION_UP:
					if (!isMoveFragment)
						break;

					moveDis = event.getRawX() - beginX;
					float dis = -moveDis;
					boolean disappear = false;
					float from = 0,
					to = -moveDis;
					if (Math.abs(moveDis) > v.getWidth() / 2) {
						// 移动超出 消失此Fragment
						to = v.getWidth() * (Math.abs(moveDis) / moveDis);
						disappear = true;
					}
					long duration = 500;
					Log.i("", "touch dis---" + disappear + "=---=" + (moveDis)
							+ "---" + v.getWidth());
					startEndAnimation(v, duration, from, to, disappear);
					return true;
				}

				return false;
			}
		});

		return parent;
	}

	// 移动动画
	private void startEndAnimation(final View view, long duration, float from,
			float to, final boolean disappear) {
		AnimationSet animationSet = new AnimationSet(true);

		if (disappear) {
			AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
			animationSet.addAnimation(alphaAnimation);
		}
		TranslateAnimation translateAnimation = new TranslateAnimation(from,
				to, 0, 0);
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
				if (disappear) {
					mOnFragmentWantFinishListener
							.onFragmentWantFinnish(SlideHorizonalFinishFragment.this);
				} else {
					// 恢复 一定要清除动画，否则动画效果仍然在，params 效果 跟Animation 效果同时作用着
					view.clearAnimation();
					view.setLayoutParams(saveParams);

				}
			}
		});

		view.startAnimation(animationSet);

	}

}
