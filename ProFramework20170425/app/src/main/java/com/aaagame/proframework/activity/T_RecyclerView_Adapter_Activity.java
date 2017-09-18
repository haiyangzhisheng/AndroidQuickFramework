package com.aaagame.proframework.activity;

import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.aaagame.proframework.R;
import com.aaagame.proframework.recycleradapter.AARecAdapter;
import com.aaagame.proframework.recycleradapter.base.AARecViewHolder;
import com.aaagame.proframework.recycleradapter.wrapper.AARecEmptyWrapper;
import com.aaagame.proframework.utils.MyItemClickListener;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.t_activity_recyclerview)
public class T_RecyclerView_Adapter_Activity extends BaseFragmentActivity {

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
    AARecAdapter myAdapter;
    List<String> mList;
    @ViewInject(R.id.refresh)
    TwinklingRefreshLayout refresh;
    AARecEmptyWrapper wrapper;

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
        myAdapter = new AARecAdapter<String>(myActivity, android.R.layout.simple_list_item_1) {
            @Override
            protected void convert(AARecViewHolder holder, String str, int position) {
                ((TextView) holder.getConvertView()).setText(str);
            }
        };
        wrapper = new AARecEmptyWrapper(myAdapter);
        wrapper.setEmptyView(R.layout.aa_list_nodata);
        rec_list.setAdapter(wrapper);
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
                myAdapter.setDatas(mList);
                List<String> oldList = new ArrayList<>();
                oldList.addAll(mList);
                for (int i = 0; i < 5; i++) {
                    mList.add("test" + i);
                }
                DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallBack(oldList, mList));
                diffResult.dispatchUpdatesTo(wrapper);
                break;
            case R.id.btn_delete:
                mList.remove(0);
                wrapper.notifyItemRemoved(0);
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

    /**
     * 介绍：核心类 用来判断 新旧Item是否相等
     * 作者：iaiai
     * 邮箱：176291935@qq.com
     * 时间： 2016/9/12.
     */

    public class DiffCallBack extends DiffUtil.Callback {
        private List<String> mOldDatas, mNewDatas;//看名字

        public DiffCallBack(List<String> mOldDatas, List<String> mNewDatas) {
            this.mOldDatas = mOldDatas;
            this.mNewDatas = mNewDatas;
        }

        //老数据集size
        @Override
        public int getOldListSize() {
            return mOldDatas != null ? mOldDatas.size() : 0;
        }

        //新数据集size
        @Override
        public int getNewListSize() {
            return mNewDatas != null ? mNewDatas.size() : 0;
        }

        /**
         * Called by the DiffUtil to decide whether two object represent the same Item.
         * 被DiffUtil调用，用来判断 两个对象是否是相同的Item。
         * For example, if your items have unique ids, this method should check their id equality.
         * 例如，如果你的Item有唯一的id字段，这个方法就 判断id是否相等。
         * 本例判断name字段是否一致
         *
         * @param oldItemPosition The position of the item in the old list
         * @param newItemPosition The position of the item in the new list
         * @return True if the two items represent the same object or false if they are different.
         */
        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return mOldDatas.get(oldItemPosition).equals(mNewDatas.get(newItemPosition));
        }

        /**
         * Called by the DiffUtil when it wants to check whether two items have the same data.
         * 被DiffUtil调用，用来检查 两个item是否含有相同的数据
         * DiffUtil uses this information to detect if the contents of an item has changed.
         * DiffUtil用返回的信息（true false）来检测当前item的内容是否发生了变化
         * DiffUtil uses this method to check equality instead of {@link Object#equals(Object)}
         * DiffUtil 用这个方法替代equals方法去检查是否相等。
         * so that you can change its behavior depending on your UI.
         * 所以你可以根据你的UI去改变它的返回值
         * For example, if you are using DiffUtil with a
         * {@link RecyclerView.Adapter RecyclerView.Adapter}, you should
         * return whether the items' visual representations are the same.
         * 例如，如果你用RecyclerView.Adapter 配合DiffUtil使用，你需要返回Item的视觉表现是否相同。
         * This method is called only if {@link #areItemsTheSame(int, int)} returns
         * {@code true} for these items.
         * 这个方法仅仅在areItemsTheSame()返回true时，才调用。
         *
         * @param oldItemPosition The position of the item in the old list
         * @param newItemPosition The position of the item in the new list which replaces the
         *                        oldItem
         * @return True if the contents of the items are the same or false if they are different.
         */
        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            String beanOld = mOldDatas.get(oldItemPosition);
            String beanNew = mNewDatas.get(newItemPosition);
            if (!beanOld.equals(beanNew)) {
                return false;//如果有内容不同，就返回false
            }
            return true; //默认两个data内容是相同的
        }
    }
}
