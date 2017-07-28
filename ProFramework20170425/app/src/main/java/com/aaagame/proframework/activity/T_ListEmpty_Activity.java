package com.aaagame.proframework.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;

import com.aaagame.proframework.R;
import com.aaagame.proframework.utils.AAComAdapter;
import com.aaagame.proframework.utils.AAViewHolder;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

@ContentView(R.layout.t_activity_listempty)
public class T_ListEmpty_Activity extends BaseFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reqData();
    }

    //=============================初始化view
    @ViewInject(R.id.lv_list)
    ListView lv_list;
    @ViewInject(R.id.gv_list)
    GridView gv_list;

        @Override  public void initView() {
        AAComAdapter<String> adapter = new AAComAdapter<String>(myActivity, R.layout.t_activity_main_item) {
            @Override
            public void convert(AAViewHolder holder, String mt) {

            }
        };
        adapter.setEmptyIcon(R.drawable.empty_default);
        adapter.setEmptyAlert("为空提示");
        adapter.getAction("为空操作").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastShow("为空操作");
            }
        });
        //gridView操作和listView相同
//        gv_list.setAdapter(adapter);
        lv_list.setAdapter(adapter);
        //设置Adapter数据为空时显示为空界面
        adapter.resetData(new ArrayList<String>());
    }

    //=============================初始化监听
        @Override  public void initListener() {

    }

    //=============================初始化数据和变量
        @Override  public void initData() {

    }

    //=============================网络请求数据
        @Override  public void reqData() {

    }
    //=============================其他操作
}
