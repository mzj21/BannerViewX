package com.xing.xbannerviewsample;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xing.xbannerview.Adapter_banner_base;

import java.util.List;

/**
 * Created by mzj on 2018/3/5.
 */

public class Adapter_banner extends Adapter_banner_base<AdEntity> {
    private Context context;

    /**
     * XBannerView配套的Adapter
     *
     * @param datas
     */
    public Adapter_banner(Context context, List<AdEntity> datas) {
        super(datas);
        this.context = context;
    }

    @Override
    public View newView(final int position) {
        final ImageView view = new ImageView(context);
        view.setScaleType(ImageView.ScaleType.FIT_XY);
        ImageLoader.getInstance().displayImage(getItem(position).getImg(), view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, getDatas().get(position).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
