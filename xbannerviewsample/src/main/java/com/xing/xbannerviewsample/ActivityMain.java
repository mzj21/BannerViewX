package com.xing.xbannerviewsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
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
import java.util.Random;

import static com.xing.xbannerviewsample.Data.URLS;

public class ActivityMain extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    XBannerView xbv;
    AdBannerAdapter adBannerAdapter;
    ArrayList<AdEntity> adEntityList;
    int num = 1;
    Switch switch_mode, switch_dots_mode, switch_dots_bg;
    TextView tv_num;
    EditText et_scrollduration, et_autotime;
    ViewPagerScroller vps;
    Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initImageLoader();
        setContentView(R.layout.activity_main);
        xbv = (XBannerView) findViewById(R.id.xbv);
        switch_mode = (Switch) findViewById(R.id.switch_mode);
        switch_dots_mode = (Switch) findViewById(R.id.switch_dots_mode);
        switch_dots_bg = (Switch) findViewById(R.id.switch_dots_bg);
        tv_num = (TextView) findViewById(R.id.tv_num);
        et_scrollduration = (EditText) findViewById(R.id.et_scrollduration);
        et_autotime = (EditText) findViewById(R.id.et_autotime);
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
        tv_num.setText(String.valueOf(num));
        switch_mode.setOnCheckedChangeListener(this);
        switch_dots_mode.setOnCheckedChangeListener(this);
        switch_dots_bg.setOnCheckedChangeListener(this);
        findViewById(R.id.add).setOnClickListener(this);
        findViewById(R.id.less).setOnClickListener(this);
        findViewById(R.id.btn_scrollduration).setOnClickListener(this);
        findViewById(R.id.btn_autotime).setOnClickListener(this);
        adEntityList = new ArrayList<>();
        random = new Random();
        for (int i = 0; i < num; i++) {
            adEntityList.add(new AdEntity("第" + (i + 1) + "张", "http://www.baidu.com",
                    URLS[random.nextInt(URLS.length)], "net"));
        }
        adBannerAdapter = new AdBannerAdapter(this, adEntityList);
        xbv.setDatas(adBannerAdapter);
        adBannerAdapter.setOnBannerViewItemClickListener(new AdBannerAdapter.OnBannerViewItemClickListener() {
            @Override
            public void onClick(View v, int position, AdEntity data) {
                toast(data.getTitle());
            }
        });
        vps = new ViewPagerScroller(this);
    }

    void toast(CharSequence text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.switch_mode:
                if (switch_dots_mode.isChecked()) {
                    switch_mode.setChecked(false);
                    toast("指示器椭圆时不能切换为滑动模式");
                    return;
                }
                xbv.setMode(isChecked ? XBannerView.XBANNERVIEW_MODE_SLIDE : XBannerView.XBANNERVIEW_MODE_NORMAL);
                refresh();
                break;
            case R.id.switch_dots_mode:
                if (switch_mode.isChecked()) {
                    switch_dots_mode.setChecked(false);
                    toast("指示器椭圆时不能切换为滑动模式");
                    return;
                }
                xbv.setDotsMode(isChecked ? XBannerView.XBANNERVIEW_DOTS_MODE_OVAL : XBannerView.XBANNERVIEW_DOTS_MODE_CIRCLE);
                refresh();
                break;
            case R.id.switch_dots_bg:
                xbv.setDotsBackground(isChecked ? R.drawable.xbv_dots_gradient : 0);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                num++;
                refresh();
                break;
            case R.id.less:
                num--;
                refresh();
                break;
            case R.id.btn_scrollduration:
                vps.setScrollDuration(Integer.parseInt(et_scrollduration.getText().toString()));
                vps.initViewPagerScroll(xbv.getViewPager());
                break;
            case R.id.btn_autotime:
                xbv.setAutotime(Integer.parseInt(et_autotime.getText().toString()));
                break;
        }
    }

    void refresh() {
        if (num < 1) {
            num = 1;
        }
        tv_num.setText(String.valueOf(num));
        adEntityList = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            adEntityList.add(new AdEntity("第" + (i + 1) + "张", "http://www.baidu.com",
                    URLS[random.nextInt(URLS.length)], "net"));
        }
        adBannerAdapter = new AdBannerAdapter(this, adEntityList);
        xbv.setDatas(adBannerAdapter);
        adBannerAdapter.setOnBannerViewItemClickListener(new AdBannerAdapter.OnBannerViewItemClickListener() {
            @Override
            public void onClick(View v, int position, AdEntity data) {
                toast(data.getTitle());
            }
        });
    }
}
