package com.aaagame.proframework.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.aaagame.proframework.R;
import com.aaagame.proframework.adapter.ViewPagerAdapter;
import com.aaagame.proframework.fragment.T_TestFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.t_activity_smarttab)
public class T_SmartTabActivity extends BaseFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTab();
    }

    //选项卡=============================
    @ViewInject(R.id.tab_head)
    public SmartTabLayout tab_head;
    @ViewInject(R.id.viewpager)
    public ViewPager viewpager;
    public int currentTag = 0;
    T_TestFragment pm1, pm2, pm3;

    /**
     * 初始化选项卡
     */
    private void initTab() {
        //设置Tab内容view
        tab_head.setCustomTabView(R.layout.tab_item_conent, R.id.tab_item_text);
        pm1 = new T_TestFragment();
        pm2 = new T_TestFragment();
        pm3 = new T_TestFragment();
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(pm1);
        fragments.add(pm2);
        fragments.add(pm3);
        List<String> titles = new ArrayList<>();
        titles.add("选项1");
        titles.add("选项2");
        titles.add("选项3");
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments, titles);
        //给ViewPager设置适配器
        viewpager.setAdapter(viewPagerAdapter);
        tab_head.setViewPager(viewpager);
        tab_head.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentTag = position;
//                if (position == 0) {
//                    pm1.reqData("2");
//                }
//                if (position == 1) {
//                    pm2.reqData("3");
//                }
//                if (position == 2) {
//                    pm3.reqData("1");
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
