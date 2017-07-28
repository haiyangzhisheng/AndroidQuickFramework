package com.aaagame.proframework.activity;

import android.os.Bundle;
import android.view.View;

import com.aaagame.proframework.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

@ContentView(R.layout.t_activity_model)
public class T_Model_Activity extends BaseFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reqData();
    }

    //=============================初始化view
        @Override  public void initView() {

    }

    //=============================初始化监听
        @Override  public void initListener() {

    }

    @Event(value = {})
    private void setClick(View view) {
        switch (view.getId()) {
            default:
                break;
        }
    }

    //=============================初始化数据和变量
        @Override  public void initData() {

    }

    //=============================网络请求数据
        @Override  public void reqData() {

    }
    //=============================其他操作
}
