package com.xing.xbannerview;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class AdBannerAdapter extends PagerAdapter {
    private ArrayList<ImageView> views;
    private ArrayList<AdEntity> datas;
    private int relasize;

    public interface OnBannerViewItemClickListener {
        void onClick(View v, int position, AdEntity data);
    }

    private OnBannerViewItemClickListener onBannerViewItemCilcklistener;

    public void setOnBannerViewItemClickListener(final OnBannerViewItemClickListener onBannerViewItemCilcklistener) {
        this.onBannerViewItemCilcklistener = onBannerViewItemCilcklistener;
        for (int i = 0; i < views.size(); i++) {
            final int num;
            if (relasize == 1) { // 返回视觉位置
                num = 0;
            } else if (relasize == 2) {
                if (i > 1) {
                    num = i - 2;
                } else {
                    num = i;
                }
            } else {
                num = i;
            }
            views.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBannerViewItemCilcklistener.onClick(v, num, datas.get(num));
                }
            });
        }
    }

    /**
     * XBannerView配套的Adapter
     */
    public AdBannerAdapter(final Context context, final ArrayList<AdEntity> datas) {
        super();
        this.datas = datas;
        relasize = datas.size();
        if (datas.size() == 1) { // 防止切换白屏
            datas.add(datas.get(0));
        } else if (datas.size() == 2) {
            datas.add(datas.get(0));
            datas.add(datas.get(1));
        }
        if (datas.size() > 0) {
            views = new ArrayList<>();
            for (int i = 0; i < datas.size(); i++) {
                final ImageView imageview = new ImageView(context);
                imageview.setScaleType(ImageView.ScaleType.FIT_XY);
                ImageLoader.getInstance().displayImage(datas.get(i).getImg(), imageview);
                views.add(imageview);
            }
        }
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    int getRelaSize() {
        return relasize;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int newPosition = position % views.size();
        View view = views.get(newPosition);
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
}
