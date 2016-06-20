package com.example.youdaonote.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 定制的禁止滑动的ViewPager
 * 
 * @author Administrator
 *
 */
public class NoSlidingViewPager extends ViewPager {

	/** 是否允许滑动 */
	private boolean isCanScroll = false;

	public NoSlidingViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NoSlidingViewPager(Context context) {
		super(context);
	}

//	public void setScanScroll(boolean isCanScroll) {
//		this.isCanScroll = isCanScroll;
//	}
//
//	@Override
//	public void scrollTo(int x, int y) {
//		if (isCanScroll) {
//			super.scrollTo(x, y);
//		}
//	}
	

    public void setCanScroll(boolean isCanScroll)
    {
        this.isCanScroll = isCanScroll;
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        /* return false;//super.onTouchEvent(arg0); */
        if (isCanScroll)
            return super.onTouchEvent(arg0);
        else
            return false;
    }
 
    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (isCanScroll)
            return super.onInterceptTouchEvent(arg0);
        else
            return false;
    }
    
    
}
