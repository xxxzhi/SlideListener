package com.houzhi.slidefinish.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

public class SlideFrameLayout extends FrameLayout {
	/**
	 * move thershold ，if the distance of moving more than thershold ,this framelayout will be slide.
	 */
	private static final int MOVE_THRESHOLD = 5;
	
	
	public SlideFrameLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public SlideFrameLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	} 

	public SlideFrameLayout(Context context) {
		super(context);
		init();
	}

	public static enum SlideDirection {
		LEFT, RIGHT, HORIZONAL, TOP, BOTTOM, VERTICAL,NO
	};

	/**
	 * listen to the slide
	 * 
	 * @author houzhi
	 * 
	 */
	public static interface OnSlideListener {
		/**
		 * 
		 */
		public void onSlideFinish();
	}

	private OnSlideListener mOnSlideListener;

	protected FrameLayout.LayoutParams saveParams = null;

	public OnSlideListener getOnSlideListener() {
		return mOnSlideListener;
	}

	public void setOnSlideListener(OnSlideListener mSlideListener) {
		this.mOnSlideListener = mSlideListener;
	}

	private void init() {
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		params.gravity = Gravity.TOP | Gravity.LEFT;
		setLayoutParams(params);
	}

	private boolean slideHorizonalFinish(View v, MotionEvent event) {

		float moveDis = 0;

		switch (event.getAction()) {
		case MotionEvent.ACTION_MOVE:
			if (!isMoveFragment) {
				// doesn't move
				break;
			}
			moveDis = event.getRawX() - beginX;
			
			switch (direction) {
			case RIGHT:
				if (moveDis <= 0)
					moveDis = 0;
				break;
			case LEFT:
				if (moveDis >= 0)
					moveDis = 0;
				break;
			case HORIZONAL:
				break;
			default:
				break;
			}
		

			FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) v
					.getLayoutParams();

			params.leftMargin = (int) moveDis;
			params.rightMargin = (int) -moveDis;
			params.gravity = Gravity.LEFT | Gravity.TOP;
			Log.i("", "touch ACTION_MOVE---" + moveDis + "--" + event.getRawX());
			v.setLayoutParams(params);
			return true;
		case MotionEvent.ACTION_UP:
			if (!isMoveFragment)
				break;

			moveDis = event.getRawX() - beginX;
			switch (direction) {
			case RIGHT:
				if (moveDis <= 0)
					moveDis = 0;
				break;
			case LEFT:
				if (moveDis >= 0)
					moveDis = 0;
				break;
			case HORIZONAL:
				break;
			default:
				break;
			}
			
			boolean disappear = false;
			float from = 0,
			to = -moveDis;
			if (Math.abs(moveDis) > v.getWidth() / 2) {
				// 移动超出 消失此Fragment
				to = v.getWidth() * (Math.abs(moveDis) / moveDis);
				disappear = true;
			}
			long duration = 500;
			Log.i("", "touch dis---" + disappear + "=---=" + (moveDis) + "---"
					+ v.getWidth());
			startEndHorizontalAnimation(v, duration, from, to, disappear);
			return true;
		}
		return false;

	}

	private SlideDirection direction = SlideDirection.RIGHT;

	public SlideDirection getDirection() {
		return direction;
	}

	public void setDirection(SlideDirection direction) {
		this.direction = direction;
	}

	private boolean slideVerticalFinish(View v, MotionEvent event) {

		float moveDis = 0;

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			break;
		case MotionEvent.ACTION_MOVE:
			if (!isMoveFragment) {
				// not move
				break;
			}
			moveDis = event.getRawY() - beginY;
			
			//judge whether  distance of moving over or not
			switch (direction) {
			case BOTTOM:
				if (moveDis <= 0)
					moveDis = 0;
				break;
			case TOP:
				if (moveDis >= 0)
					moveDis = 0;
				break;
			case VERTICAL:
				break;
			default:
				break;
			}

			FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) v
					.getLayoutParams();

			params.topMargin = (int) moveDis;
			params.bottomMargin = (int) -moveDis;
			params.gravity = Gravity.LEFT | Gravity.TOP;
			Log.i("", "touch ACTION_MOVE---" + moveDis + "--" + event.getRawY());
			v.setLayoutParams(params);
			return true;
		case MotionEvent.ACTION_UP:
			if (!isMoveFragment)
				break;

			moveDis = event.getRawY() - beginY;
			//judge whether  distance of moving over or not
			switch (direction) {
			case BOTTOM:
				if (moveDis <= 0)
					moveDis = 0;
				break;
			case TOP:
				if (moveDis >= 0)
					moveDis = 0;
				break;
			case VERTICAL:
				break;
			default:
				break;
			}
			
			boolean disappear = false;
			float from = 0,
			to = -moveDis;
			if (Math.abs(moveDis) > v.getHeight() / 2) {
				// 移动超出 消失此Fragment
				to = v.getHeight() * (Math.abs(moveDis) / moveDis);
				disappear = true;
			}
			long duration = 500;
			Log.i("", "touch dis---" + disappear + "=---=" + (moveDis) + "---"
					+ v.getWidth());
			startEndVerticalAnimation(v, duration, from, to, disappear);
			return true;
		}

		return false;

	}

	// move horizontal animation
	private void startEndHorizontalAnimation(final View view, long duration,
											 float from, float to, final boolean disappear) {
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
			public void onAnimationEnd(Animation arg0) {
				if (disappear) {
					if (mOnSlideListener != null) {
						mOnSlideListener.onSlideFinish();
					}
				} else {
					recoverFromSlide();
				}
			}

			@Override
			public void onAnimationRepeat(Animation arg0) {

			}

			@Override
			public void onAnimationStart(Animation arg0) {

			}
		});

		view.startAnimation(animationSet);

	}

	/**
	 * recover from slide finish 
	 */
	public void recoverFromSlide(){
		clearAnimation();
		setLayoutParams(saveParams);
	}
	
	// move vertical animation
	private void startEndVerticalAnimation(final View view, long duration,
			float from, float to, final boolean disappear) {
		AnimationSet animationSet = new AnimationSet(true);

		if (disappear) {
			AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
			animationSet.addAnimation(alphaAnimation);
		}
		TranslateAnimation translateAnimation = new TranslateAnimation(0, 0,
				from, to);
		animationSet.addAnimation(translateAnimation);
		animationSet.setDuration(duration);
		animationSet.setFillAfter(true);
		animationSet.setFillEnabled(true);
		animationSet.setAnimationListener(new Animation.AnimationListener() {

			@Override
			public void onAnimationEnd(Animation arg0) {
				if (disappear) {
					if (mOnSlideListener != null) {
						mOnSlideListener.onSlideFinish();
					}
				} else {
					
					recoverFromSlide();

				}
			}

			@Override
			public void onAnimationRepeat(Animation arg0) {}

			@Override
			public void onAnimationStart(Animation arg0) {

			}
		});

		view.startAnimation(animationSet);

	}


	float beginX = 0, beginY = 0;

	boolean isMoveFragment = false;
	boolean isFirstMoveEvent = false;

	private boolean judgeVertical(View v, MotionEvent event) {

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			beginY = event.getRawY();
			beginX = event.getRawX();
			isFirstMoveEvent = true;
			isMoveFragment = false;
			Log.i("", "touch ACTION_DOWN beginX---" + beginY);

			saveParams = new FrameLayout.LayoutParams(
					(ViewGroup.MarginLayoutParams) v.getLayoutParams());
			return false;
		case MotionEvent.ACTION_MOVE:
			
			
			if (isFirstMoveEvent) {
				isFirstMoveEvent = false;
				isMoveFragment = true ;
				float moveDis = event.getRawY() - beginY;
				float moveDisH = Math.abs(event.getRawX() - beginX) ;
				if(moveDisH > 0){
					//  move horizonal 
					isMoveFragment = false;
					break;
				}
				
				switch (direction) {
				case BOTTOM:
					if (moveDis <= 0)
						isMoveFragment = false;
					break;
				case TOP:
					if (moveDis >= 0)
						isMoveFragment = false;
					break;
				case VERTICAL:
					if (moveDis == 0)
						isMoveFragment = false;
					break;
				default:
					break;
				}
			}
			
			if (!isMoveFragment) {
				//
				break;
			}
			
			return true;
		case MotionEvent.ACTION_UP:
			if (!isMoveFragment)
				break;

			return true;
		}

		return false;
	}

	private boolean judgeHorizonal(View v, MotionEvent event) {

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			beginX = event.getRawX();
			beginY = event.getRawY();
			isFirstMoveEvent = true;
			isMoveFragment = false;
			Log.i("", "touch ACTION_DOWN beginX---" + beginX);

			saveParams = new FrameLayout.LayoutParams(
					(ViewGroup.MarginLayoutParams) v.getLayoutParams());
			return false;
		case MotionEvent.ACTION_MOVE:
			
			if (isFirstMoveEvent) {
				float moveDis = event.getRawX() - beginX;
				float  moveDisV =Math.abs( event.getRawY() - beginY );
				isFirstMoveEvent = false;
				isMoveFragment = true ;
				if(moveDisV > 0){
					// if has move vertical 
					isMoveFragment = false;
					break;
				}
				switch (direction) {
				case RIGHT:
					if (moveDis <= 0 )
						isMoveFragment = false;
					break;
				case LEFT:
					if (moveDis >= 0)
						isMoveFragment = false;
					break;
				case HORIZONAL:
					if (moveDis == 0)
						isMoveFragment = false;
					break;
				}
			}
			
			if (!isMoveFragment) {
				break;
			}
			
			return true;
		case MotionEvent.ACTION_UP:
			if (!isMoveFragment)
				break;
			return true;
		}

		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean res = false;
		switch (direction) {
		case LEFT:
		case RIGHT:
		case HORIZONAL:
			res = slideHorizonalFinish(this, event);
			break;
		case TOP:
		case BOTTOM:
		case VERTICAL:
			res = slideVerticalFinish(this, event);
			break;
			default:
		}
		return res;
	}

	private static final String TAG = "SLIDEFRAMELAYOUT";

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		boolean judgeRes = false;
		switch (direction) {
		case LEFT:
		case RIGHT:
		case HORIZONAL:
			judgeRes = judgeHorizonal(this, ev);
			break;
		case TOP:
		case BOTTOM:
		case VERTICAL:
			judgeRes = judgeVertical(this, ev);
			break;
		case NO:
			judgeRes = false;
			break;
		}
		Log.i(TAG, "onInterceptTouchEvent--"+judgeRes);
		return judgeRes;
	}

}
