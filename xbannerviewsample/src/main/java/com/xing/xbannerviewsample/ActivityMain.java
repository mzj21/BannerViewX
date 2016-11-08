package com.xing.xbannerviewsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.xing.xbannerview.AdBannerAdapter;
import com.xing.xbannerview.AdEntity;
import com.xing.xbannerview.ViewPagerScroller;
import com.xing.xbannerview.XBannerView;

import java.util.ArrayList;

public class ActivityMain extends AppCompatActivity {
    XBannerView xbv1, xbv2, xbv3, xbv4;
    AdBannerAdapter adBannerAdapter1, adBannerAdapter2, adBannerAdapter3, adBannerAdapter4;
    ArrayList<AdEntity> adEntityList1, adEntityList2, adEntityList3, adEntityList4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initImageLoader();
        setContentView(R.layout.activity_main);
        xbv1 = (XBannerView) findViewById(R.id.xbv1);
        xbv2 = (XBannerView) findViewById(R.id.xbv2);
        xbv3 = (XBannerView) findViewById(R.id.xbv3);
        xbv4 = (XBannerView) findViewById(R.id.xbv4);
        init();
    }

    private void initImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(ImageLoaderOptions.getDefault()).threadPriority(Thread.NORM_PRIORITY - 2)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();
        ImageLoader.getInstance().init(config);
    }

    private void init() {
        adEntityList1 = new ArrayList<>();
        adEntityList1.add(new AdEntity("单独第一张", "http://www.baidu.com",
                "http://imgsrc.baidu.com/forum/pic/item/4adcad014c086e06887e9f9a02087bf408d1cbd7.jpg", "net"));
        adBannerAdapter1 = new AdBannerAdapter(this, adEntityList1);
        xbv1.setDatas(adBannerAdapter1);
        adBannerAdapter1.setOnBannerViewItemClickListener(new AdBannerAdapter.OnBannerViewItemClickListener() {
            @Override
            public void onClick(View v, int position, AdEntity data) {
                toast(data.getTitle());
            }
        });

        ViewPagerScroller viewPagerScroller1 = new ViewPagerScroller(this);
        viewPagerScroller1.setScrollDuration(1500);
        viewPagerScroller1.initViewPagerScroll(xbv2.getViewPager());
        adEntityList2 = new ArrayList<>();
        adEntityList2.add(new AdEntity("双图第一张", "http://www.bilibili.com/",
                "http://img.netbian.com/file/2016/1017/4eb617e58b535bda58cdc92496afeb8f.jpg", "net"));
        adEntityList2.add(new AdEntity("双图第二张", "https://github.com",
                "http://img.netbian.com/file/2016/1017/4daabd33fddc771f38ce47647ca9b71b.jpg", "net"));
        adBannerAdapter2 = new AdBannerAdapter(this, adEntityList2);
        xbv2.setDatas(adBannerAdapter2);
        adBannerAdapter2.setOnBannerViewItemClickListener(new AdBannerAdapter.OnBannerViewItemClickListener() {
            @Override
            public void onClick(View v, int position, AdEntity data) {
                toast(data.getTitle());
            }
        });

        ViewPagerScroller viewPagerScroller2 = new ViewPagerScroller(this);
        viewPagerScroller2.setScrollDuration(2500);
        viewPagerScroller2.initViewPagerScroll(xbv3.getViewPager());
        adEntityList3 = new ArrayList<>();
        adEntityList3.add(new AdEntity("多图第一张", "http://www.youku.com/",
                "http://img.netbian.com/file/2016/1009/41d7174cd21d70fa382df1e6ea76987e.jpg", "net"));
        adEntityList3.add(new AdEntity("多图第二张", "http://www.qidian.com/",
                "http://img.netbian.com/file/20150111/421fc98f8f7fc490cd5f0a64f165c734.jpg", "net"));
        adEntityList3.add(new AdEntity("多图第三张", "http://www.sina.com.cn/",
                "http://img.netbian.com/file/2016/1016/79907729a7d8d684245082f7b309c3b9.jpg", "net"));
        adBannerAdapter3 = new AdBannerAdapter(this, adEntityList3);
        xbv3.setDatas(adBannerAdapter3);
        adBannerAdapter3.setOnBannerViewItemClickListener(new AdBannerAdapter.OnBannerViewItemClickListener() {
            @Override
            public void onClick(View v, int position, AdEntity data) {
                toast(data.getTitle());
            }
        });

        ViewPagerScroller viewPagerScroller3 = new ViewPagerScroller(this);
        viewPagerScroller3.setScrollDuration(1500);
        viewPagerScroller3.initViewPagerScroll(xbv4.getViewPager());
        adEntityList4 = new ArrayList<>();
        adEntityList4.add(new AdEntity("多图第一张", "http://www.youku.com/",
                "http://img.netbian.com/file/20150524/c7dfccf48e572d790a3840c21ae769fa.jpg", "net"));
        adEntityList4.add(new AdEntity("多图第二张", "http://www.qidian.com/",
                "http://img.netbian.com/file/2016/0815/f5644d74a963a883bcb81530361b9dda.jpg", "net"));
        adEntityList4.add(new AdEntity("多图第三张", "http://www.sina.com.cn/",
                "http://img.netbian.com/file/2016/0921/13875aedceabd7ae3f7d0b7682464b3f.jpg", "net"));
        adEntityList4.add(new AdEntity("多图第四张", "http://www.sina.com.cn/",
                "http://img.netbian.com/file/2016/1013/343dde3d9fe13b6135f671c686120a7c.jpg", "net"));
        adBannerAdapter4 = new AdBannerAdapter(this, adEntityList4);
        xbv4.setDatas(adBannerAdapter4);
        adBannerAdapter4.setOnBannerViewItemClickListener(new AdBannerAdapter.OnBannerViewItemClickListener() {
            @Override
            public void onClick(View v, int position, AdEntity data) {
                toast(data.getTitle());
            }
        });
    }

    void toast(CharSequence text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
