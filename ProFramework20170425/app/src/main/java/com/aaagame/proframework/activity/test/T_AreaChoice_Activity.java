package com.aaagame.proframework.activity.test;

import android.os.Bundle;
import android.view.View;

import com.aaagame.proframework.R;
import com.aaagame.proframework.activity.BaseFragmentActivity;
import com.aaagame.proframework.areachoice.Area_Choice;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

@ContentView(R.layout.t_activity_areachoice)
public class T_AreaChoice_Activity extends BaseFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
        initData();
        reqData();
    }

    Area_Choice area_Choice;

    //=============================初始化view
    private void initView() {

    }

    //=============================初始化监听
    private void initListener() {
    }

    @Event(value = {R.id.btn_show})
    private void setClick(View view) {
        switch (view.getId()) {
            case R.id.btn_show:
                area_Choice.showPopupWindow_Bottom(R.layout.t_activity_areachoice).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        area_Choice.mPopWindow.dismiss();
                    }
                });
                break;
            default:
                break;
        }
    }

    //=============================初始化数据和变量
    private void initData() {
        area_Choice = new Area_Choice(this);
        area_Choice.initProvinceDatasOtherAsy();
    }

    //=============================网络请求数据
    private void reqData() {

    }
    //=============================其他操作
}
