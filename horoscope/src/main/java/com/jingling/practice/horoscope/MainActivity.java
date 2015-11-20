package com.jingling.practice.horoscope;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TabHost;

import com.duducat.activeconfig.ActiveConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;


public class MainActivity extends FragmentActivity implements ViewPager.OnPageChangeListener, TabHost.OnTabChangeListener {
    private TabHost tabHost;
    private HorizontalScrollView hScrollView;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private HashMap<String, TabInfo> mapTabInfo = new HashMap<String, TabInfo>();


    /**
     * tab顶部标签的基本信息
     */
    private class TabInfo {
        private String tag;
        public View On;
        public View Off;
        public View Tab;

        public TabInfo(String tag, Class<?> clazz, Bundle args) {
            this.tag = tag;
        }
    }

    public class TabFactory implements TabHost.TabContentFactory {
        private final Context mContext;

        public TabFactory(Context context) {
            mContext = context;
        }

        public View createTabContent(String tag) {
            View v = new View(mContext);
            v.setMinimumWidth(0);
            v.setMinimumHeight(0);
            return v;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        //注册嘟嘟猫后台管理
        ActiveConfig.register(MainActivity.this, "19K3Me8Z", "hVAjCHK55TaB66YR");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hScrollView = (HorizontalScrollView) findViewById(R.id.hScrollView);
        //初始化viewpager
        initViewPager();
        //初始化tabhost
        initTabHost(savedInstanceState);
    }

    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("tab", tabHost.getCurrentTabTag());
        super.onSaveInstanceState(outState);
    }

    private void initViewPager() {
        //封装Fragment对象
        List<Fragment> fragments = new Vector<Fragment>();

        for (int i = 0; i < 12; i++) {
            Bundle budle = new Bundle();
            budle.putInt("i", i);
            Fragment f = Fragment.instantiate(MainActivity.this, HoroscopeFragment.class.getName(), budle);
            fragments.add(f);
        }
        pagerAdapter = new PagerAdapter(super.getSupportFragmentManager(), fragments);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int i, float v, int i2) {

    }

    @Override
    public void onPageSelected(int i) {
        this.tabHost.setCurrentTab(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    private void initTabHost(Bundle args) {
        tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.setup();//初始化tabhost
        TabInfo tabInfo = null;
        for (int i = 0; i < 12; i++) {
            TabHost.TabSpec tabSpec = this.tabHost.newTabSpec("Tab" + i);
            tabSpec.setContent(new TabFactory(this));
            View tabView = LayoutInflater.from(tabHost.getContext()).inflate(R.layout.tab_layout, null);
            //获得本地图片路径
            int resID = getResources().getIdentifier("a" + (i + 1) + "_0", "drawable", getPackageName());
            ((ImageView) tabView.findViewById(R.id.on)).setImageDrawable(getResources().getDrawable(resID));

            resID = getResources().getIdentifier("a" + (i + 1) + "_1", "drawable", getPackageName());
            ((ImageView) tabView.findViewById(R.id.off)).setImageDrawable(getResources().getDrawable(resID));


            //设置每一页spec显示的view
            tabSpec.setIndicator(tabView);
            tabHost.addTab(tabSpec);
            tabInfo = new TabInfo("Tab" + i, HoroscopeFragment.class, args);
            tabInfo.On = (ImageView) tabView.findViewById(R.id.on);
            tabInfo.Off = (ImageView) tabView.findViewById(R.id.off);
            tabInfo.Tab = tabView;
            this.mapTabInfo.put(tabInfo.tag, tabInfo);

            //默认第一张显示的标签页
            this.onTabChanged("Tab0");
            tabHost.setOnTabChangedListener(this);
        }

    }

    @Override
    public void onTabChanged(String tabId) {
        TabInfo newTab = this.mapTabInfo.get(tabId);
        for (Map.Entry<String, TabInfo> t : mapTabInfo.entrySet()) {
            if (t.getKey().equals(tabId)) {
                t.getValue().On.setVisibility(View.VISIBLE);
                t.getValue().Off.setVisibility(View.INVISIBLE);
            } else {
                t.getValue().On.setVisibility(View.INVISIBLE);
                t.getValue().Off.setVisibility(View.VISIBLE);
            }
        }

        hScrollView.requestChildRectangleOnScreen(newTab.Tab, new Rect(0, 0, newTab.Tab.getWidth(), 1), false);

        int pos = this.tabHost.getCurrentTab();
        this.viewPager.setCurrentItem(pos, true);
    }


    public class PagerAdapter extends FragmentPagerAdapter {

        private List<android.support.v4.app.Fragment> fragments;

        public PagerAdapter(android.support.v4.app.FragmentManager fm, List<android.support.v4.app.Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return this.fragments.get(position);
        }

        @Override
        public int getCount() {
            return this.fragments.size();
        }
    }


}
