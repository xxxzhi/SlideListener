package com.scut.houzhi.qqemailtransdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewFlipper;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class ViewFlipperFragment extends Fragment {

	public ViewFlipperFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View v= inflater.inflate(R.layout.fragment_view_plipper, container,
				false);
		ViewFlipper flipper = (ViewFlipper)v.findViewById(R.id.viewFlipper1);
		
		flipper.setAutoStart(true);
		flipper.setFlipInterval(1000);
		flipper.startFlipping();
		return v;
	}

}
