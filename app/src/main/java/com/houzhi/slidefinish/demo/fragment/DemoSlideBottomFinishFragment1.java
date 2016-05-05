package com.houzhi.slidefinish.demo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.TextView;

import com.houzhi.slidefinish.R;
import com.houzhi.slidefinish.fragment.BaseSlideFinishFragment;
import com.houzhi.slidefinish.widget.SlideFrameLayout;

public class DemoSlideBottomFinishFragment1 extends BaseSlideFinishFragment {

	private static final String ARG_BG_COLOR = "backgroundcolor";

	private int bgColor;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_blank, container, false);
		TextView tv = (TextView)view.findViewById(R.id.tv_hint);
		tv.setText(R.string.slide_bottom);
		SlideFrameLayout slideFrameLayout = new SlideFrameLayout(getActivity());
		slideFrameLayout.addView(view);
		slideFrameLayout.setDirection(SlideFrameLayout.SlideDirection.BOTTOM);

		slideFrameLayout.setOnSlideListener(new SlideFrameLayout.OnSlideListener() {

			@Override
			public void onSlideFinish() {
				mOnFragmentWantFinishListener
						.onSlideFragmentFinish(DemoSlideBottomFinishFragment1.this);
			}
		});
		slideFrameLayout.setOnTouchListener(new View.OnTouchListener() {
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
		slideFrameLayout.setBackgroundColor(bgColor);

		return slideFrameLayout;


//		view.setOnTouchListener(new View.OnTouchListener() {
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				Log.i("", "onTouch"+""+event.getAction());
//				return false;
//			}
//		});
//		return view;
	}

	//滑动方向
	private SlideFrameLayout.SlideDirection direction = SlideFrameLayout.SlideDirection.BOTTOM;


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
							.onSlideFragmentFinish(DemoSlideBottomFinishFragment1.this);
				} else {
					// 恢复 一定要清除动画，否则动画效果仍然在，params 效果 跟Animation 效果同时作用着
					view.clearAnimation();
					view.setLayoutParams(saveParams);

				}
			}
		});

		view.startAnimation(animationSet);

	}





	public static DemoSlideBottomFinishFragment1 newInstance(int bgColor) {
		DemoSlideBottomFinishFragment1 fragment = new DemoSlideBottomFinishFragment1();
		Bundle args = new Bundle();
		args.putInt(ARG_BG_COLOR, bgColor);
		fragment.setArguments(args);
		return fragment;
	}

	public DemoSlideBottomFinishFragment1() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			bgColor = getArguments().getInt(ARG_BG_COLOR);
		}
	}

	@Override
	protected View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return null;
	}
}
