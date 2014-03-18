package com.houzhi.qqemailtransdemo;

import com.houzhi.slidefinish.SlideFinish;
import com.houzhi.slidefinish.SlideFinish.SlideDirection;
import com.houzhi.slidefinish.fragment.SlideHorizonalFinishFragment;
import com.scut.houzhi.qqemailtransdemo.R;

import android.app.Activity;
import android.os.Bundle;
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

public class TestSlideActivity extends BaseActivity {
	protected FrameLayout.LayoutParams saveParams = null;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		SlideFinish slideFinish = new SlideFinish();
		slideFinish.setSlideListener(new SlideFinish.SlideListener() {
			
			@Override
			public void onSlideFinish() {
				finish();
			}
		});
		slideFinish.slideFinish(this, R.layout.activity_test,SlideDirection.LEFT);
	}

}
