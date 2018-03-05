package com.xing.xbannerview;

import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class Adapter_banner_base<T> extends PagerAdapter {
    private List<T> datas;
    private SparseArray<View> views;

    /**
     * XBannerView配套的Adapter
     */
    public Adapter_banner_base(final List<T> datas) {
        this.datas = datas;
        views = new SparseArray<>(datas.size());
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int newPosition = position % datas.size();
        View view = newView(newPosition);
        views.put(newPosition, view);
        if (container.equals(view.getParent())) {
            container.removeView(view);
        }
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public List<T> getDatas() {
        return datas;
    }

    public int getRelaSize() {
        return datas.size();
    }

    public T getItem(int position) {
        return datas.get(position);
    }

    public abstract View newView(int position);
}
