package com.houzhi.slidefinish;

import com.houzhi.slidefinish.fragment.SlideVerticalFinishFragment;

import android.app.Activity;
import android.graphics.Path.Direction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

public class SlideFinish {

	public static enum SlideDirection {
		LEFT, RIGHT, HORIZONAL, TOP, BOTTOM, VERTICAL
	};

	/**
	 * listen to the slide
	 * 
	 * @author houzhi
	 * 
	 */
	public static interface SlideListener {
		/**
		 * 
		 */
		public void onSlideFinish();
	}

	private SlideListener mSlideListener;

	protected FrameLayout.LayoutParams saveParams = null;

	public SlideListener getSlideListener() {
		return mSlideListener;
	}

	public void setSlideListener(SlideListener mSlideListener) {
		this.mSlideListener = mSlideListener;
	}

	/**
	 * this method will setContentView for you
	 * 
	 * @param activity
	 *            which you use this SLideFinish,and you must make sure that the
	 *            activity is Translucent(you can set theme to be
	 *            Theme.Translucent
	 * @param resource
	 *            the view id you want to be activity contentview.
	 * @param direction
	 *            you want to slide direction to finish
	 * @return the view after slidefinish method decode
	 */
	public View slideFinish(Activity activity, int resource,
			SlideDirection direction) {
		View view = activity.getLayoutInflater().inflate(resource, null);

		return slideFinish(activity, view, direction);
	}

	/**
	 * this method will setContentView for you
	 * 
	 * @param activity
	 *            which you use this SLideFinish,and you must make sure that the
	 *            activity is Translucent(you can set theme to be
	 *            Theme.Translucent
	 * @param view
	 *            the view you want to be activity contentview.
	 * @param direction
	 *            you want to slide direction to finish
	 * @return the view after slidefinish method decode
	 */
	public View slideFinish(Activity activity, View view,
			SlideDirection direction) {
		View rtnView = null;
		switch (direction) {
		case LEFT:
		case RIGHT:
		case HORIZONAL:
			rtnView = slideHorizonalFinish(activity, view, direction);
			break;
		case TOP:
		case BOTTOM:
		case VERTICAL:
			rtnView = slideVerticalFinish(activity, view, direction);
		}
		return rtnView;
	}

	/**
	 * same as slideFinish but you cann't set the direction to be vertical
	 * direction
	 * 
	 * @param activity
	 * @param view
	 * @param direction
	 * @return
	 */
	public View slideHorizonalFinish(Activity activity, View view,
			final SlideDirection direction) {

		// 用一个framelayout 包装子类传过来的
		final FrameLayout parent = new FrameLayout(activity);
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		params.gravity = Gravity.TOP | Gravity.LEFT;
		parent.setLayoutParams(params);

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
							if (moveDis <= 0)
								isMoveFragment = false;
							break;
						case RIGHT:
							if (moveDis >= 0)
								isMoveFragment = false;
							break;
						case HORIZONAL:
							if (moveDis == 0)
								isMoveFragment = false;
							break;
						default:
							break;
						}
						if (!isMoveFragment) {

							break;
						}
					} else {
						switch (direction) {
						case LEFT:
							if (moveDis <= 0)
								moveDis = 0;
							break;
						case RIGHT:
							if (moveDis >= 0)
								moveDis = 0;
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
					startEndHorizonalAnimation(v, duration, from, to, disappear);
					return true;
				}

				return false;
			}
		});

		return parent;
	}

	/**
	 * same as slideFinish but you cann't set the direction to be horizonal
	 * direction
	 * 
	 * @param activity
	 * @param view
	 * @param direction
	 * @return
	 */
	public View slideVerticalFinish(Activity activity, View view,
			final SlideDirection direction) {

		// 用一个framelayout 包装子类传过来的
		final FrameLayout parent = new FrameLayout(activity);
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		params.gravity = Gravity.TOP | Gravity.LEFT;
		parent.setLayoutParams(params);

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
						if (!isMoveFragment) {

							break;
						}
					} else {
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
					startEndVerticalAnimation(v, duration, from, to, disappear);
					return true;
				}

				return false;
			}
		});

		return parent;
	}

	// 移动动画
	private void startEndHorizonalAnimation(final View view, long duration,
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
					if (mSlideListener != null) {
						mSlideListener.onSlideFinish();
					}
				} else {
					// 恢复 一定要清除动画，否则动画效果仍然在，params 效果 跟Animation 效果同时作用着
					view.clearAnimation();
					view.setLayoutParams(saveParams);

				}
			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationStart(Animation arg0) {

			}
		});

		view.startAnimation(animationSet);

	}

	// 移动动画
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
					if (mSlideListener != null) {
						mSlideListener.onSlideFinish();
					}
				} else {
					// 恢复 一定要清除动画，否则动画效果仍然在，params 效果 跟Animation 效果同时作用着
					view.clearAnimation();
					view.setLayoutParams(saveParams);

				}
			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub

			}
		});

		view.startAnimation(animationSet);

	}

}
