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

public abstract class SlideVerticalFinishFragment extends BaseSlideFinishFragment {
	
	
	protected static enum SlideDirection{ TOP,BOTTOM,VERTICAL};
	
	//滑动方向
	private SlideDirection direction = SlideDirection.VERTICAL;
	
	public SlideVerticalFinishFragment(boolean isTop){
		if(isTop){
			direction = SlideDirection.TOP;
		}else{
			direction = SlideDirection.BOTTOM;
		}
	}
	
	public SlideVerticalFinishFragment(){
		
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
			float beginY = 0;

			boolean isMoveFragment = false;
			boolean isFirstMoveEvent = false;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				float moveDis = 0;

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					beginY = event.getRawY();
					isFirstMoveEvent = true;
					isMoveFragment = true;
					Log.i("", "touch ACTION_DOWN beginX---" + beginY);

					saveParams = new FrameLayout.LayoutParams(
							(ViewGroup.MarginLayoutParams) v.getLayoutParams());
					return true;
				case MotionEvent.ACTION_MOVE:
					if (!isMoveFragment) {
						// 非滑动fragment
						break;
					}
					moveDis = event.getRawY() - beginY;
					if (isFirstMoveEvent) {
						isFirstMoveEvent = false;
						switch (direction) {
						case BOTTOM:
							if (moveDis <= 0) isMoveFragment = false;
							break;
						case TOP:
							if (moveDis >= 0) isMoveFragment = false;
							break;
						case VERTICAL:
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
						case BOTTOM:
							if (moveDis <= 0) moveDis = 0 ;
							break;
						case TOP:
							if (moveDis >= 0)  moveDis = 0;
							break;
						case VERTICAL:
							break;
						default:
							break;
						}
					}

					FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) v
							.getLayoutParams();

					params.topMargin = (int) moveDis;
					params.bottomMargin = (int) -moveDis;
					params.gravity = Gravity.LEFT | Gravity.TOP;
					Log.i("",
							"touch ACTION_MOVE---" + moveDis + "--"
									+ event.getRawY());
					v.setLayoutParams(params);
					return true;
				case MotionEvent.ACTION_UP:
					if (!isMoveFragment)
						break;

					moveDis = event.getRawY() - beginY;
					float dis = -moveDis;
					boolean disappear = false;
					float from = 0,
					to = -moveDis;
					if (Math.abs(moveDis) > v.getHeight() / 2) {
						// 移动超出 消失此Fragment
						to = v.getHeight() * (Math.abs(moveDis) / moveDis);
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
		TranslateAnimation translateAnimation = new TranslateAnimation(0,
				0, from, to);
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
							.onFragmentWantFinnish(SlideVerticalFinishFragment.this);
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
