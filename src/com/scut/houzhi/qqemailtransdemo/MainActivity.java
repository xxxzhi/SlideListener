package com.scut.houzhi.qqemailtransdemo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.scut.houzhi.slidefragment.SlideRightFinishFragment;
import com.scut.houzhi.slidefragment.demo.DemoSlideBottomFinishFragment;
import com.scut.houzhi.slidefragment.demo.DemoSlideHFinishFragment;
import com.scut.houzhi.slidefragment.demo.DemoSlideLeftFinishFragment;
import com.scut.houzhi.slidefragment.demo.DemoSlideRightFinishFragment;
import com.scut.houzhi.slidefragment.demo.DemoSlideTopFinishFragment;

public class MainActivity extends FragmentActivity implements SlideRightFinishFragment.OnFragmentWantFinishListener{
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
	public void onFragmentWantFinnish(Fragment fragment) {
		FragmentTransaction transaction =fragmentManager.beginTransaction();
		
		transaction.remove(fragment);
		
		transaction.commit();
	}

}
