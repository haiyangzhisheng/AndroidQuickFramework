package com.aaagame.proframework.activity.test;

import android.os.Bundle;
import android.widget.ListView;

import com.aaagame.proframework.R;
import com.aaagame.proframework.activity.BaseFragmentActivity;
import com.aaagame.proframework.utils.AACom;
import com.aaagame.proframework.utils.AAComAdapter;
import com.aaagame.proframework.utils.AAViewHolder;
import com.aaagame.proframework.utils.Ahttp;
import com.aaagame.proframework.utils.ArequestCallBack;
import com.aaagame.proframework.utils.ConInterface;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;


@ContentView(R.layout.t_activity_refreshtwinkling)
public class T_RefreshTwinkling_Activity extends BaseFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initList();
//        reqData();
    }

    @ViewInject(R.id.refresh)
    TwinklingRefreshLayout refresh;
    @ViewInject(R.id.lv_list)
    ListView lv_list;
    AAComAdapter<ListItem> adapter;

    /**
     * 初始化列表
     */
    private void initList() {
        //是否开启纯净的越界回弹模式
//        refresh.setPureScrollModeOn(true);
        List<ListItem> listItems = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ListItem item = new ListItem();
            item.myitem = i + "项目";
            listItems.add(item);
        }
        adapter = new AAComAdapter<ListItem>(myActivity, R.layout.t_activity_refreshtwinkling_item, listItems) {
            @Override
            public void convert(AAViewHolder holder, ListItem li) {
                holder.setText(R.id.tv_item, li.myitem);
            }
        };
        lv_list.setAdapter(adapter);
        // 初始化时，设置不加载更多
        refresh.setEnableLoadmore(false);
        //设置刷新和加载更多监听
        refresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                adapter.nextpage++;
                reqShowAlert = false;
//                reqData();
            }

            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                adapter.nextpage = 1;
                reqShowAlert = false;
//                reqData();
            }
        });
    }

    /**
     * 请求数据
     */
    public void reqData() {
        JSONObject jsonObject = new JSONObject();
        try {//请求页数
            jsonObject.put("page", adapter.nextpage + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Ahttp ahttp = new Ahttp(myActivity, ConInterface.UserDesignerRecordList, jsonObject.toString(), SpUtils.getUserToken(myActivity));
        Ahttp ahttp = new Ahttp(myActivity, ConInterface.Sample, jsonObject.toString());
        ahttp.send(new ArequestCallBack<String>(myActivity, ahttp) {
            @Override
            public void onSuccess(String res) {
                super.onSuccess(res);
                if (isError) {
                    return;
                }
                //解析数据
                ListBean record = AACom.getGson().fromJson(data, ListBean.class);
                List<ListItem> list = record.results;
                //判断是第一页时刷新========================================
                if (adapter.nextpage == 1) {
                    adapter.resetData(list);
                } else {
                    adapter.addData(list);
                }
                //判断是否加载完============================================
                if (adapter.nextpage == record.pageTotal) {
                    refresh.setEnableLoadmore(false);
                } else {
                    refresh.setEnableLoadmore(true);
                }

            }

            @Override
            public void onFinished() {
                super.onFinished();
                //结束刷新和加载更多效果
                refresh.finishRefreshing();
                refresh.finishLoadmore();
            }
        });
    }

    /**
     * 列表所在实体类
     */
    public class ListBean {
        public int total;//": 1,
        public List<ListItem> results;
        public int page;//": 1,
        public int pageTotal;//": 1,
        public int start;//": 0,
        public int pageSize;//": 10,
        public int end;//": 1
    }

    /**
     * 列表项
     */
    public class ListItem {
        public String myitem;
    }
}
