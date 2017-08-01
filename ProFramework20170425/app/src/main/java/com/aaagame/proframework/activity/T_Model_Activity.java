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
        //在父类的onCreate方法中已经按照initView();initListener();initData();的顺序进行了调用，reqData()需要自己需要的时候调用
        super.onCreate(savedInstanceState);
        reqData();
    }

    //=============================初始化view
    @Override
    public void initView() {

    }

    //=============================初始化监听
    @Override
    public void initListener() {

    }

    //Xutils的事件监听处理，默认为onClick方法的调用{}中填入R.id.控件名，以逗号分割
    @Event(value = {})
    private void setClick(View view) {
        switch (view.getId()) {
            default:
                break;
        }
    }

    //=============================初始化数据和变量
    @Override
    public void initData() {

    }

    //=============================网络请求数据
    @Override
    public void reqData() {

    }
    //=============================其他操作
}
