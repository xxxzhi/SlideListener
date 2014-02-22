package com.scut.houzhi.qqemailtransdemo;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;

public class MainActivity extends FragmentActivity implements BlankFragment.OnFragmentInteractionListener{
	FragmentManager fragmentManager = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		fragmentManager = getSupportFragmentManager();
		
		
		FragmentTransaction transaction =fragmentManager.beginTransaction();
		

		transaction.add(R.id.content,new ViewFlipperFragment(), "flipper");
		
		
		transaction.add(R.id.content,BlankFragment.newInstance(Color.BLUE, "First"), "first");
		transaction.add(R.id.content,BlankFragment.newInstance(Color.GREEN, "green"), "green");
		
		transaction.commit();
	}
	@Override
	public void onFragmentInteraction(Uri uri) {
		// TODO Auto-generated method stub
		
	}

}
