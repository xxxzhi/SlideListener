/**
 * 
 */
package com.houzhi.slidefinish.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
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

/**
 * @author houzhi
 *	可监听滑动删除 fragment
 *	使用该fragment 的Activity 必须实现OnFragmentWantFinishListener 接口
 *
 *	
 */
public abstract class SlideTopFinishFragment extends SlideVerticalFinishFragment {

	public SlideTopFinishFragment() {
		super(true);
	}
	
}
