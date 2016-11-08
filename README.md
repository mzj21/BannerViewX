# xbannerview
![Demo](https://github.com/mzj21/xbannerview/blob/master/screenshots/xbannerview.gif?raw=true)

### 简介
轮播图控件，使用ImageLoader加载图片。

### 目前
1. 支持单张及多张轮播图无限循环
2. 支持添加图片点击事件
3. 支持自定义切换时间
4. 支持修改宽高比适应多种需求
5. 支持修改ViewPager切换速率

### 特别注意
单图时以设置不允许滚动

### 使用
Step 1. Add it in your root build.gradle at the end of repositories:
```
allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }
	}
}
```

Step 2. Add the dependency
```
dependencies {
	    compile 'com.github.mzj21:XBannerView:1.2.1'
}
```

Step 3. 配置Imageloader权限，Android Manifest
```
<manifest>
    <!-- Include following permission if you load images from Internet -->
    <uses-permission android:name="android.permission.INTERNET" />
    <!-- Include following permission if you want to cache images on SD card -->
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    ...
</manifest>
```
Step 4. 初始化Imageloader，详情见xbannerviewsample配置
```
public class MyActivity extends Activity {
    @Override
    public void onCreate() {
        super.onCreate();

        // Create global configuration and initialize ImageLoader with this config
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
            ...
            .build();
        ImageLoader.getInstance().init(config);
        ...
    }
}
```

### 例子
```
<com.xing.xbannerview.XBannerView
	xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/xbannerview"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
	app:xbv_dots_focused_height="@dimen/_10dp"
    app:xbv_dots_focused_width="@dimen/_20dp"
    app:xbv_dots_margin_left="@dimen/_5dp"
    app:xbv_dots_margin_right="@dimen/_5dp"
    app:xbv_dots_normal_height="@dimen/_10dp"
    app:xbv_dots_normal_width="@dimen/_10dp"
    app:xbv_time="4000"/>
```
添加数据
```
XBannerView xbv = (XBannerView) findViewById(R.id.xbannerview);
ViewPagerScroller vps = new ViewPagerScroller(this); //控制滑动速率
vps.setScrollDuration(1500); //设置滑动速率，单位毫秒
vps.initViewPagerScroll(xbv.getViewPager()); //绑定viewpager
ArrayList<AdEntity> adEntityList = new ArrayList<>();
adEntityList.add(new AdEntity("多图第一张", "http://www.youku.com/","http://img.netbian.com/file/2016/1009/41d7174cd21d70fa382df1e6ea76987e.jpg", "net"));
adEntityList.add(new AdEntity("多图第二张", "http://www.qidian.com/","http://img.netbian.com/file/20150111/421fc98f8f7fc490cd5f0a64f165c734.jpg", "net"));
adEntityList.add(new AdEntity("多图第三张", "http://www.sina.com.cn/","http://img.netbian.com/file/2016/1016/79907729a7d8d684245082f7b309c3b9.jpg", "net"));
AdBannerAdapter adBannerAdapter = new AdBannerAdapter(this, adEntityList);
xbv.setDatas(adBannerAdapter);
```
添加监听
```
adBannerAdapter.setOnBannerViewItemClickListener(new AdBannerAdapter.OnBannerViewItemClickListener() {
    @Override
    public void onClick(View v, int position, AdEntity data) {
        toast(data.getTitle());
    }
});
```
关闭自动播放，开启自动播放，默认大于1图时自动播放
```
@Override
protected void onPause() {
    super.onPause();
    xbannerview.onStop();
}
@Override
protected void onResume() {
    super.onResume();
    xbannerview.onStart();
}
```

### xml 属性
- xbv_dots_normal_width: 				指示器单个点正常时的宽，默认10dp
- xbv_dots_normal_height: 				指示器单个点正常时的高，默认10dp
- xbv_dots_focused_width: 				指示器单个点选中时的宽，默认10dp
- xbv_dots_focused_height: 				指示器单个点选中时的高，默认10dp
- xbv_dots_margin_left:  				指示器单个点的marginleft，默认5dp
- xbv_dots_margin_right: 				指示器单个点的marginright，默认5dp
- xbv_dots_margin_top: 					指示器单个点的margintop，默认5dp
- xbv_dots_margin_bottom: 				指示器单个点的marginbottom，默认5dp
- xbv_dots_background_focused:  		指示器单个点的选中背景，默认@drawable/xbv_dots_focused
- xbv_dots_background_normal:   		指示器单个点的正常背景，默认@drawable/xbv_dots_normal
- xbv_dots_gradientbackground_visible:  指示器的渐变背景是否可见，默认true
- xbv_dots_gradientbackground:   		指示器的渐变背景，默认@drawable/xbv_dots_gradient
- xbv_time: 							轮播间隔，单位毫秒，默认5000
- xbv_ratio: 							高度与宽度的比例，默认0.5
