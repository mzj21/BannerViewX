package com.xing.xbannerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class XBannerView extends FrameLayout {
    public final static int XBANNERVIEW_MODE_NORMAL = 0;
    public final static int XBANNERVIEW_MODE_SLIDE = 1;
    public final static int XBANNERVIEW_DOTS_MODE_CIRCLE = 0;
    public final static int XBANNERVIEW_DOTS_MODE_OVAL = 1;
    private static int DEFAULT_DURATION = 250;
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
    private static int DEF_AUTOTIME = 5 * 1000;
    private int duration;
    private int mode;
    private int dots_mode;
    private int dots_normal_width;
    private int dots_normal_height;
    private int dots_focused_width;
    private int dots_focused_width_new;
    private int dots_focused_height;
    private int dots_margin_left;
    private int dots_margin_right;
    private int dots_margin_top;
    private int dots_margin_bottom;
    private int dots_background_focused;
    private int dots_background_normal;
    private int dots_gradientbackground;
    private boolean dots_background_normal_visible;
    private int autotime;
    private float ratio;
    private XViewPager banner_xvp;
    private List<View> dots;
    private View dot_slide;
    private FrameLayout dots_bg;
    private LinearLayout dots_ll, dot_slide_ll;
    private int size;
    private LinearLayout.LayoutParams layoutParams_focused, layoutParams_normal;
    private ViewPagerScroller vps;
    private int slide_max_width;
    private MarginLayoutParams dot_slide_mlp;
    private int scrollwidth;

    public XBannerView(Context context) {
        super(context, null);
    }

    public XBannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        vps = new ViewPagerScroller(context);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.XBannerView);
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

        duration = ta.getInt(R.styleable.XBannerView_xbv_viewpager_scrollduration, DEFAULT_DURATION);
        mode = ta.getInt(R.styleable.XBannerView_xbv_mode, XBANNERVIEW_MODE_NORMAL);
        dots_mode = ta.getInt(R.styleable.XBannerView_xbv_dots_mode, XBANNERVIEW_DOTS_MODE_CIRCLE);
        if (mode == XBANNERVIEW_MODE_SLIDE && dots_mode == XBANNERVIEW_DOTS_MODE_OVAL) {
            dots_mode = XBANNERVIEW_DOTS_MODE_CIRCLE;
        }
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
        autotime = ta.getInt(R.styleable.XBannerView_xbv_autotime, DEF_AUTOTIME);
        ratio = ta.getFloat(R.styleable.XBannerView_xbv_ratio, DEF_RATIO);
        init();
        ta.recycle();
    }

    private void init() {
        setDotsMode(mode);
        slide_max_width = dots_normal_width + dots_margin_left + dots_margin_right;
        LayoutInflater.from(getContext()).inflate(R.layout.xbannerview, this);
        dots_bg = (FrameLayout) findViewById(R.id.dots_bg);
        dots_ll = (LinearLayout) findViewById(R.id.dots_ll);
        dot_slide_ll = (LinearLayout) findViewById(R.id.dots_slide_ll);
        banner_xvp = (XViewPager) findViewById(R.id.banner_xvp);

        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) banner_xvp.getLayoutParams();
        layoutParams.height = (int) (getResources().getDisplayMetrics().widthPixels * ratio);
        banner_xvp.setLayoutParams(layoutParams);
        vps.setScrollDuration(duration);
        vps.initViewPagerScroll(banner_xvp);
        if (dots_background_normal_visible) {
            dots_bg.setBackgroundResource(dots_gradientbackground);
        }
    }

    public void setDatas(Adapter_banner_base adapt) {
        size = adapt.getRelaSize();
        dots = new ArrayList<>();
        dots_ll.removeAllViews();
        dot_slide_ll.removeAllViews();

        layoutParams_normal = new LinearLayout.LayoutParams(dots_normal_width, dots_normal_height);
        layoutParams_normal.setMargins(dots_margin_left, dots_margin_top, dots_margin_right, dots_margin_bottom);

        if (mode == XBANNERVIEW_MODE_NORMAL) {
            for (int i = 0; i < size; i++) {
                View view = new View(getContext());
                if (i == 0) {
                    layoutParams_focused = new LinearLayout.LayoutParams(dots_focused_width_new, dots_focused_height);
                    layoutParams_focused.setMargins(dots_margin_left, dots_margin_top, dots_margin_right, dots_margin_bottom);
                    view.setLayoutParams(layoutParams_focused);
                    view.setBackgroundResource(dots_background_focused);
                } else {
                    view.setLayoutParams(layoutParams_normal);
                    view.setBackgroundResource(dots_background_normal);
                }
                dots_ll.addView(view);
                dots.add(view);
            }
        } else {
            for (int i = 0; i < size; i++) {
                View view = new View(getContext());
                view.setLayoutParams(layoutParams_normal);
                view.setBackgroundResource(dots_background_normal);
                dots_ll.addView(view);
                dots.add(view);
            }
            FrameLayout.LayoutParams dot_slide_ll_lp = (LayoutParams) dot_slide_ll.getLayoutParams();
            dot_slide_ll_lp.width = slide_max_width * size;
            dot_slide_ll_lp.height = dots_ll.getHeight();
            dot_slide_ll.setLayoutParams(dot_slide_ll_lp);
            dot_slide = new View(getContext());
            layoutParams_focused = new LinearLayout.LayoutParams(dots_focused_width, dots_focused_height);
            layoutParams_focused.setMargins(dots_margin_left, dots_margin_top, dots_margin_right, dots_margin_bottom);
            dot_slide.setLayoutParams(layoutParams_focused);
            dot_slide.setBackgroundResource(dots_background_focused);
            dot_slide_ll.addView(dot_slide);
            dot_slide_mlp = (MarginLayoutParams) dot_slide.getLayoutParams();
        }

        banner_xvp.removeAllViews();
        int currentItem = Integer.MAX_VALUE / 2 - ((Integer.MAX_VALUE / 2) % size);
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
        if (size == 1) {
            onStop();
        } else {
            onStart();
        }
    }

    private class PageChangeListener implements ViewPager.OnPageChangeListener {
        public void onPageSelected(int position) {
            if (mode == XBANNERVIEW_MODE_NORMAL && size > 1) {
                for (int i = 0; i < size; i++) {
                    dots.get(i).setLayoutParams(layoutParams_normal);
                    dots.get(i).setBackgroundResource(dots_background_normal);
                    dots.get(position % size).setLayoutParams(layoutParams_focused);
                    dots.get(position % size).setBackgroundResource(dots_background_focused);
                }
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, autotime);
            }
        }

        public void onPageScrollStateChanged(int state) {

        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (mode == XBANNERVIEW_MODE_SLIDE && size > 1) {
                scrollwidth = (int) ((position % size + positionOffset) * slide_max_width);
                if (scrollwidth <= (size - 1) * slide_max_width) {
                    dot_slide_mlp.setMarginStart(dots_margin_left + scrollwidth);
                    dot_slide_mlp.leftMargin = dots_margin_left + scrollwidth;
                    dot_slide.setLayoutParams(dot_slide_mlp);
                }
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, autotime);
            }
        }
    }

    public void onStart() {
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, autotime);
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

    public ViewPager getViewPager() {
        return banner_xvp;
    }

    public int getViewPagervpScrollDuration() {
        return duration;
    }

    public void setDotsBackground(@DrawableRes int resid) {
        dots_bg.setBackgroundResource(resid);
    }

    public void setAutotime(int time) {
        autotime = time;
        onStart();
    }

    public void setMode(int mode) {
        if (dots_mode == XBANNERVIEW_DOTS_MODE_OVAL && mode == XBANNERVIEW_MODE_SLIDE) {
            return;
        }
        this.mode = mode;
    }

    public void setDotsMode(int mode) {
        if (this.mode == XBANNERVIEW_MODE_SLIDE && mode == XBANNERVIEW_DOTS_MODE_OVAL) {
            return;
        }
        if (mode == XBANNERVIEW_DOTS_MODE_OVAL) {
            dots_focused_width_new = dots_focused_width * 2;
        } else {
            dots_focused_width_new = dots_focused_width;
        }
    }
}