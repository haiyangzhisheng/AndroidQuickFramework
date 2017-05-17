package com.aaagame.proframework.activity;

import android.os.Bundle;

import com.aaagame.proframework.R;

import org.xutils.view.annotation.ContentView;

@ContentView(R.layout.t_activity_model)
public class T_Model_Activity extends BaseFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
        initData();
        reqData();
    }

    //=============================初始化view
    private void initView() {

    }

    //=============================初始化监听
    private void initListener() {

    }

    //=============================初始化数据和变量
    private void initData() {

    }

    //=============================网络请求数据
    private void reqData() {

    }
    //=============================其他操作
}
