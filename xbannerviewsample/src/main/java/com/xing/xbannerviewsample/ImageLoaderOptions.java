package com.xing.xbannerviewsample;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

class ImageLoaderOptions {

    static DisplayImageOptions getDefault() {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.NONE)
                .displayer(new SimpleBitmapDisplayer()).displayer(new FadeInBitmapDisplayer(150))
                .bitmapConfig(Bitmap.Config.ARGB_8888).build();
        return defaultOptions;
    }

    public static DisplayImageOptions getLocalOption() {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .displayer(new SimpleBitmapDisplayer()).displayer(new FadeInBitmapDisplayer(150))
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        return defaultOptions;
    }

    public static DisplayImageOptions getThumalOption() {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(false).cacheOnDisk(false)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).displayer(new SimpleBitmapDisplayer()).bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        return defaultOptions;
    }
}
