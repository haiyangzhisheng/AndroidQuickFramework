package com.aaagame.proframework.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.aaagame.proframework.R;
import com.aaagame.proframework.utils.MyItemClickListener;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.t_activity_recyclerview)
public class T_RecyclerView_Activity extends BaseFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reqData();
    }

    //=============================初始化view
    @ViewInject(R.id.rec_list)
    RecyclerView rec_list;
    @ViewInject(R.id.btn_add)
    Button btn_add;
    MyAdapter myAdapter;
    List<String> mList;
    @ViewInject(R.id.refresh)
    TwinklingRefreshLayout refresh;

    @Override
    public void initView() {
        //是否开启纯净的越界回弹模式
        refresh.setPureScrollModeOn(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rec_list.setLayoutManager(layoutManager);
        mList = new ArrayList<String>();
        mList.add("111");
        mList.add("222");
        mList.add("333");
        myAdapter = new MyAdapter(mList);
        rec_list.setAdapter(myAdapter);
        myAdapter.setOnItemClickListener(new MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                toastShow(position + "当前位置");
            }
        });
    }

    //=============================初始化监听
    @Override
    public void initListener() {

    }

    //=============================初始化数据和变量
    @Override
    public void initData() {

    }

    //=============================网络请求数据
    @Override
    public void reqData() {

    }

    @Event(value = {R.id.btn_add, R.id.btn_delete})
    private void setClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                mList.add(2, "test");
                myAdapter.notifyItemInserted(2);
                break;
            case R.id.btn_delete:
                mList.remove(2);
                myAdapter.notifyItemRemoved(2);
                break;
            default:
                break;
        }
    }

    //=============================其他操作
    public static class MyAdapter extends RecyclerView.Adapter {
        private List<String> mDataset;

        public MyAdapter(List<String> dataset) {
            super();
            mDataset = dataset;
        }

        @Override
        public MviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = View.inflate(parent.getContext(), android.R.layout.simple_list_item_1, null);
            MviewHolder holder = new MviewHolder(view, myItemClickListener);
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((MviewHolder) holder).mTextView.setText(mDataset.get(position));
        }

        MyItemClickListener myItemClickListener;

        public void setOnItemClickListener(MyItemClickListener itemClickListener) {
            myItemClickListener = itemClickListener;
        }

        @Override
        public int getItemCount() {
            return mDataset.size();
        }

        public static class MviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public TextView mTextView;
            MyItemClickListener mClickListener;

            public MviewHolder(View itemView, MyItemClickListener itemClickListener) {
                super(itemView);
                mTextView = (TextView) itemView;
                this.mClickListener = itemClickListener;
                itemView.setOnClickListener(this);

            }

            @Override
            public void onClick(View view) {
                if (mClickListener != null) {
                    mClickListener.onItemClick(view, getLayoutPosition());
                }
            }
        }
    }
}
