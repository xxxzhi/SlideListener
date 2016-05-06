package com.houzhi.slidefinish.demo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.houzhi.slidefinish.demo.fragment.DemoSlideBottomFinishFragment;
import com.houzhi.slidefinish.demo.fragment.DemoSlideBottomFinishFragment1;
import com.houzhi.slidefinish.R;
import com.houzhi.slidefinish.demo.fragment.DemoSlideHFinishFragment;
import com.houzhi.slidefinish.demo.fragment.DemoSlideHFinishFragment1;
import com.houzhi.slidefinish.demo.fragment.DemoSlideLeftFinishFragment1;
import com.houzhi.slidefinish.demo.fragment.DemoSlideRightFinishFragment1;
import com.houzhi.slidefinish.demo.fragment.DemoSlideTopFinishFragment1;
import com.houzhi.slidefinish.fragment.BaseSlideFinishFragment;

public class MainActivity extends FragmentActivity implements BaseSlideFinishFragment.SlideFragmentFinishListener{
	FragmentManager fragmentManager = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		fragmentManager = getSupportFragmentManager();
		
		
		FragmentTransaction transaction =fragmentManager.beginTransaction();

		
		transaction.add(R.id.content, DemoSlideHFinishFragment.newInstance(Color.RED), "first");
		transaction.add(R.id.content, DemoSlideRightFinishFragment1.newInstance(Color.BLUE), "first");
		transaction.add(R.id.content, DemoSlideLeftFinishFragment1.newInstance(Color.GREEN), "green");

//		transaction.add(R.id.content, DemoSlideTopFinishFragment1.newInstance(Color.YELLOW), "YELLOW");
		transaction.add(R.id.content, DemoSlideBottomFinishFragment1.newInstance(Color.MAGENTA), "MAGENTA");
		
		
		
		
		transaction.commit();
		
		
		
		findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MainActivity.this, TestSlideActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.i("Activity","onTouchEvent--"+event.getAction());
		return super.onTouchEvent(event);
	}

	@Override
	public void onSlideFragmentFinish(Fragment fragment) {
		FragmentTransaction transaction =fragmentManager.beginTransaction();
		
		transaction.remove(fragment);
		
		transaction.commit();
	}

}
