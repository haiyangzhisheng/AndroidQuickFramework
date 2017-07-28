package com.aaagame.proframework.imagebrowser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.TextView;

import com.aaagame.proframework.R;
import com.aaagame.proframework.activity.BaseFragmentActivity;
import com.aaagame.proframework.dialog.AAMyAlertDialog;

public class ImagePagerActivity extends BaseFragmentActivity {
    private static final String STATE_POSITION = "STATE_POSITION";
    public static final String EXTRA_IMAGE_INDEX = "image_index";
    public static final String EXTRA_IMAGE_URLS = "image_urls";

    private ImageViewPager mPager;
    private int pagerPosition;
    private TextView app_delete;
    private TextView app_title_back;
    ImagePagerAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_detail_pager);
        app_delete = (TextView) findViewById(R.id.app_delete);
        app_title_back = (TextView) findViewById(R.id.app_title_back);

        app_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (getIntent().getBooleanExtra("showDelete", false)) {
            app_delete.setVisibility(View.VISIBLE);
        }
        app_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AAMyAlertDialog myAlertDialog = new AAMyAlertDialog(ImagePagerActivity.this);
                myAlertDialog.setMessage("您确定要删除此图片吗？");
                myAlertDialog.setPositiveButton("删除", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = getIntent();
                        try {
                            intent.putExtra("path", mAdapter.fileList[mPager.getCurrentItem()]);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        setResult(RESULT_OK, intent);
                        myAlertDialog.dismiss();
                        finish();
                    }
                });
                myAlertDialog.setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myAlertDialog.dismiss();
                    }
                });
                myAlertDialog.show();
            }
        });
        pagerPosition = getIntent().getIntExtra(EXTRA_IMAGE_INDEX, 0);
        String[] urls = getIntent().getStringArrayExtra(EXTRA_IMAGE_URLS);

        mPager = (ImageViewPager) findViewById(R.id.pager);
        mAdapter = new ImagePagerAdapter(getSupportFragmentManager(), urls);
        mPager.setAdapter(mAdapter);
        // 更新下标
        mPager.addOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(int arg0) {
            }

        });
        if (savedInstanceState != null) {
            pagerPosition = savedInstanceState.getInt(STATE_POSITION);
        }

        mPager.setCurrentItem(pagerPosition);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void reqData() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_POSITION, mPager.getCurrentItem());
    }

    private class ImagePagerAdapter extends FragmentStatePagerAdapter {

        public String[] fileList;

        public ImagePagerAdapter(FragmentManager fm, String[] fileList) {
            super(fm);
            this.fileList = fileList;
        }

        @Override
        public int getCount() {
            return fileList == null ? 0 : fileList.length;
        }

        @Override
        public Fragment getItem(int position) {
            String url = fileList[position];
            ImageDetailFragment frag = ImageDetailFragment.newInstance(url, position, fileList.length);
            return frag;
        }

    }
}