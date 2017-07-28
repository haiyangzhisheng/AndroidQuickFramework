package com.aaagame.proframework.activity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aaagame.proframework.R;
import com.aaagame.proframework.utils.AAMethod;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;


@ContentView(R.layout.t_activity_help)
public class T_HelpActivity extends BaseFragmentActivity {
    public static final String Demand_Xy_Help = "协议";


    @ViewInject(R.id.tv_content)
    TextView tv_content;
    @ViewInject(R.id.line_logo)
    LinearLayout line_logo;
    String mytitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mytitle = getIntent().getStringExtra(bundleData);
        mTitleBar.setTitle(mytitle);
        setStrToTv(R.id.tv_current_version, "版本号：V" +
                AAMethod.getVersionName(myActivity));
        initData();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void reqData() {

    }

    @Override
    public void initData() {
        switch (mytitle) {
            case Demand_Xy_Help:
                tv_content.setText(Demand_Xy_Content);
                break;
        }
    }

    /**
     * 协议
     */
    private final String Demand_Xy_Content = "";
}
