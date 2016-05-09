/**
 * 
 */
package com.houzhi.slidefinish.demo.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.houzhi.slidefinish.R;

/**
 * @author houzhi
 *	可监听滑动删除 fragment
 *	使用该fragment 的Activity 必须实现SlideFragmentFinishListener 接口
 *
 *	
 */
public abstract class BaseSlideFinishFragment extends Fragment {
	
	/**
	 * 
	 * @author houzhi
	 * 
	 */
	public static interface SlideFragmentFinishListener{
		public void onSlideFragmentFinish(Fragment fragment);
	}
	
	//监听 fragment 接口
	protected SlideFragmentFinishListener mOnFragmentWantFinishListener;
	


	@Override
	public void onAttach(Activity activity) {
		if(activity instanceof SlideFragmentFinishListener){
			mOnFragmentWantFinishListener = (SlideFragmentFinishListener)activity;
		}else{
			throw new RuntimeException("the Activity which use SlideFinishFragment" +
					" must implements OnFragmentWantFinishLIstener!");
		}
		super.onAttach(activity);
	}

	

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

	}
}
