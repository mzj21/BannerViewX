package com.xing.xbannerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class XViewPager extends ViewPager {

    private boolean enableSwap = true; // 表示该ViewPager能否被滑动

    public XViewPager(Context context) {
        super(context,null);
    }

    public XViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.XViewPager);
        enableSwap = ta.getBoolean(R.styleable.XViewPager_slideable, true);
        ta.recycle();
    }

    public boolean isEnableSwap() {
        return enableSwap;
    }

    public void setEnableSwap(boolean enableSwap) {
        this.enableSwap = enableSwap;
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        return enableSwap && super.onTouchEvent(arg0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return enableSwap && super.onInterceptTouchEvent(arg0);
    }
}
