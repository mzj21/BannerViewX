package com.xing.xbannerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class XBannerView extends FrameLayout {
    private static int DEF_DOTS_NORMAL_WIDTH;
    private static int DEF_DOTS_NORMAL_HEIGHT;
    private static int DEF_DOTS_FOCUSED_WIDTH;
    private static int DEF_DOTS_FOCUSED_HEIGHT;
    private static int DEF_DOTS_MARGIN_LEFT;
    private static int DEF_DOTS_MARGIN_RIGHT;
    private static int DEF_DOTS_MARGIN_TOP;
    private static int DEF_DOTS_MARGIN_BOTTOM;
    private static int DEF_DOTS_BACKGROUND_FOCUSED;
    private static int DEF_DOTS_BACKGROUND_NORMAL;
    private static int DEF_DOTS_GRADIENTBACKGROUND;
    private static boolean DEF_DOTS_GRADIENTBACKGROUND_VISIBLE;
    private static float DEF_RATIO;
    private int DEF_TIME = 5 * 1000;
    private int dots_normal_width;
    private int dots_normal_height;
    private int dots_focused_width;
    private int dots_focused_height;
    private int dots_margin_left;
    private int dots_margin_right;
    private int dots_margin_top;
    private int dots_margin_bottom;
    private int dots_background_focused;
    private int dots_background_normal;
    private int dots_gradientbackground;
    private boolean dots_background_normal_visible;
    private int time;
    private float ratio;
    private TypedArray ta;
    private XViewPager banner_xvp;
    private List<View> dots;
    private LinearLayout dots_bg;
    private LinearLayout dots_ll;
    private int size;
    private MarginLayoutParams lp;
    private LinearLayout.LayoutParams layoutParams_focused, layoutParams_normal;

    public XBannerView(Context context) {
        super(context, null);
    }

    public XBannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ta = context.obtainStyledAttributes(attrs, R.styleable.XBannerView);
        DEF_DOTS_NORMAL_WIDTH = getResources().getDimensionPixelOffset(R.dimen._10dp);
        DEF_DOTS_NORMAL_HEIGHT = getResources().getDimensionPixelOffset(R.dimen._10dp);
        DEF_DOTS_FOCUSED_WIDTH = getResources().getDimensionPixelOffset(R.dimen._10dp);
        DEF_DOTS_FOCUSED_HEIGHT = getResources().getDimensionPixelOffset(R.dimen._10dp);
        DEF_DOTS_MARGIN_LEFT = getResources().getDimensionPixelOffset(R.dimen._5dp);
        DEF_DOTS_MARGIN_RIGHT = getResources().getDimensionPixelOffset(R.dimen._5dp);
        DEF_DOTS_MARGIN_TOP = getResources().getDimensionPixelOffset(R.dimen._5dp);
        DEF_DOTS_MARGIN_BOTTOM = getResources().getDimensionPixelOffset(R.dimen._5dp);
        DEF_DOTS_BACKGROUND_FOCUSED = R.drawable.xbv_dots_focused;
        DEF_DOTS_BACKGROUND_NORMAL = R.drawable.xbv_dots_normal;
        DEF_DOTS_GRADIENTBACKGROUND_VISIBLE = true;
        DEF_DOTS_GRADIENTBACKGROUND = R.drawable.xbv_dots_gradient;
        DEF_RATIO = 0.5f;

        dots_normal_width = ta.getDimensionPixelOffset(R.styleable.XBannerView_xbv_dots_normal_width, DEF_DOTS_NORMAL_WIDTH);
        dots_normal_height = ta.getDimensionPixelOffset(R.styleable.XBannerView_xbv_dots_normal_height, DEF_DOTS_NORMAL_HEIGHT);
        dots_focused_width = ta.getDimensionPixelOffset(R.styleable.XBannerView_xbv_dots_focused_width, DEF_DOTS_FOCUSED_WIDTH);
        dots_focused_height = ta.getDimensionPixelOffset(R.styleable.XBannerView_xbv_dots_focused_height, DEF_DOTS_FOCUSED_HEIGHT);
        dots_margin_left = ta.getDimensionPixelOffset(R.styleable.XBannerView_xbv_dots_margin_left, DEF_DOTS_MARGIN_LEFT);
        dots_margin_right = ta.getDimensionPixelOffset(R.styleable.XBannerView_xbv_dots_margin_right, DEF_DOTS_MARGIN_RIGHT);
        dots_margin_top = ta.getDimensionPixelOffset(R.styleable.XBannerView_xbv_dots_margin_top, DEF_DOTS_MARGIN_TOP);
        dots_margin_bottom = ta.getDimensionPixelOffset(R.styleable.XBannerView_xbv_dots_margin_bottom, DEF_DOTS_MARGIN_BOTTOM);
        dots_background_focused = ta.getResourceId(R.styleable.XBannerView_xbv_dots_background_focused, DEF_DOTS_BACKGROUND_FOCUSED);
        dots_background_normal = ta.getResourceId(R.styleable.XBannerView_xbv_dots_background_normal, DEF_DOTS_BACKGROUND_NORMAL);
        dots_gradientbackground = ta.getResourceId(R.styleable.XBannerView_xbv_dots_gradientbackground, DEF_DOTS_GRADIENTBACKGROUND);
        dots_background_normal_visible = ta.getBoolean(R.styleable.XBannerView_xbv_dots_gradientbackground_visible, DEF_DOTS_GRADIENTBACKGROUND_VISIBLE);
        time = ta.getInt(R.styleable.XBannerView_xbv_time, DEF_TIME);
        ratio = ta.getFloat(R.styleable.XBannerView_xbv_ratio, DEF_RATIO);

        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.xbannerview, this);
        dots_bg = (LinearLayout) findViewById(R.id.dots_bg);
        dots_ll = (LinearLayout) findViewById(R.id.dots_ll);
        banner_xvp = (XViewPager) findViewById(R.id.banner_xvp);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) banner_xvp.getLayoutParams();
        layoutParams.height = (int) (getResources().getDisplayMetrics().widthPixels * ratio);
        banner_xvp.setLayoutParams(layoutParams);
        if (dots_background_normal_visible) {
            dots_bg.setBackgroundResource(dots_gradientbackground);
        }
    }

    public void setDatas(final AdBannerAdapter adapt) {
        size = adapt.getRelaSize();
        dots = new ArrayList<>();
        dots_ll.removeAllViews();
        for (int i = 0; i < size; i++) {
            View view = new View(getContext());
            if (i == 0) {
                layoutParams_focused = new LinearLayout.LayoutParams(dots_focused_width, dots_focused_height);
                layoutParams_focused.setMargins(dots_margin_left, dots_margin_top, dots_margin_right, dots_margin_bottom);
                view.setLayoutParams(layoutParams_focused);
                view.setBackgroundResource(dots_background_focused);
            } else {
                layoutParams_normal = new LinearLayout.LayoutParams(dots_normal_width, dots_normal_height);
                layoutParams_normal.setMargins(dots_margin_left, dots_margin_top, dots_margin_right, dots_margin_bottom);
                view.setLayoutParams(layoutParams_normal);
                view.setBackgroundResource(dots_background_normal);
            }
            dots_ll.addView(view);
            dots.add(view);
        }

        banner_xvp.removeAllViews();
        final int currentItem = Integer.MAX_VALUE / 2 - ((Integer.MAX_VALUE / 2) % size);
        try {
            Field field = ViewPager.class.getDeclaredField("mCurItem");
            Field field2 = ViewPager.class.getDeclaredField("mRestoredCurItem");
            try {
                field.setAccessible(true);
                field.setInt(banner_xvp, currentItem);

                field2.setAccessible(true);
                field2.setInt(banner_xvp, currentItem);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        banner_xvp.setAdapter(adapt);
        PageChangeListener pageChangeListener = new PageChangeListener();
        banner_xvp.addOnPageChangeListener(pageChangeListener);
        setEnableSwap(size != 1);//1个的时候不自动轮播
        if (size != 1) {
            onStart();
        }
    }

    private class PageChangeListener implements ViewPager.OnPageChangeListener {
        private int oldPosition = 0;

        public void onPageSelected(int position) {
            if (size > 1) {
                for (int i = 0; i < size; i++) {
                    dots.get(i).setLayoutParams(layoutParams_normal);
                    dots.get(i).setBackgroundResource(dots_background_normal);
                    dots.get(position % size).setLayoutParams(layoutParams_focused);
                    dots.get(position % size).setBackgroundResource(dots_background_focused);
                }
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, time);
            }
        }

        public void onPageScrollStateChanged(int arg0) {

        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }
    }

    public void onStart() {
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, time);
    }

    public void onStop() {
        handler.removeCallbacks(runnable);
    }

    private Handler handler = new Handler();

    private Runnable runnable = new Runnable() {
        public void run() {
            assert banner_xvp != null;
            banner_xvp.setCurrentItem(banner_xvp.getCurrentItem() + 1);
        }
    };

    public void setEnableSwap(boolean enableSwap) {
        banner_xvp.setEnableSwap(enableSwap);
    }

    public ViewPager getViewPager () {
        return  banner_xvp;
    }
}