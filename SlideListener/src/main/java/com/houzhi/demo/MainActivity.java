package com.houzhi.slidefinish.demo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.houzhi.slidefinish.fragment.SlideRightFinishFragment;
import com.houzhi.slidefinish.fragment.demo.DemoSlideBottomFinishFragment;
import com.houzhi.slidefinish.fragment.demo.DemoSlideHFinishFragment;
import com.houzhi.slidefinish.fragment.demo.DemoSlideLeftFinishFragment;
import com.houzhi.slidefinish.fragment.demo.DemoSlideRightFinishFragment;
import com.houzhi.slidefinish.fragment.demo.DemoSlideTopFinishFragment;
import com.scut.houzhi.qqemailtransdemo.R;

public class MainActivity extends FragmentActivity implements SlideRightFinishFragment.SlideFragmentFinishListener{
	FragmentManager fragmentManager = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		fragmentManager = getSupportFragmentManager();
		
		
		FragmentTransaction transaction =fragmentManager.beginTransaction();

		
		transaction.add(R.id.content,DemoSlideHFinishFragment.newInstance(Color.RED), "first");
		transaction.add(R.id.content,DemoSlideRightFinishFragment.newInstance(Color.BLUE), "first");
		transaction.add(R.id.content,DemoSlideLeftFinishFragment.newInstance(Color.GREEN), "green");
		
		transaction.add(R.id.content,DemoSlideTopFinishFragment.newInstance(Color.YELLOW), "YELLOW");
		transaction.add(R.id.content,DemoSlideBottomFinishFragment.newInstance(Color.MAGENTA), "MAGENTA");
		
		
		
		
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
	public void onSlideFragmentFinish(Fragment fragment) {
		FragmentTransaction transaction =fragmentManager.beginTransaction();
		
		transaction.remove(fragment);
		
		transaction.commit();
	}

}
