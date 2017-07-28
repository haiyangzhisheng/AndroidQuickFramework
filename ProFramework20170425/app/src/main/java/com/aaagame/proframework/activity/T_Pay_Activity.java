package com.aaagame.proframework.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.aaagame.proframework.R;
import com.aaagame.proframework.dialog.PayDialog;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.t_activity_pay)
public class T_Pay_Activity extends BaseFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reqData();
    }

    //=============================初始化view
    @ViewInject(R.id.btn_create_order)
    Button btn_create_order;

        @Override  public void initView() {

    }

    //=============================初始化监听
        @Override  public void initListener() {

    }

    @Event(R.id.btn_create_order)
    private void setClick(View view) {
        switch (view.getId()) {
            case R.id.btn_create_order:
                PayDialog payDialog = new PayDialog();
                payDialog.setData(Double.parseDouble("20"), "ordersn");
                payDialog.show(myActivity.getFragmentManager(), "PayDialog");
                payDialog.addMyDismiss(new PayDialog.OnMyDismiss() {
                    @Override
                    public void toDismiss() {
//                        AppManager.getInstance().killActivity("商品界面");
                        animFinish();
                    }
                });
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
