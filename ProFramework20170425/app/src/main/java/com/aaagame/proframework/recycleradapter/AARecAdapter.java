package com.aaagame.proframework.recycleradapter;

import android.content.Context;
import android.view.LayoutInflater;

import com.aaagame.proframework.recycleradapter.base.AARecViewHolder;
import com.aaagame.proframework.recycleradapter.base.ItemViewDelegate;

import java.util.List;

/**
 * Created by zhy on 16/4/9.
 */
public abstract class AARecAdapter<T> extends AARecAdapter_MultiItemType<T> {
    protected Context mContext;
    protected int mLayoutId;
    protected LayoutInflater mInflater;

    public AARecAdapter(Context context, final int layoutId) {
        super(context);
        initAdapter(context, layoutId);
    }

    public AARecAdapter(final Context context, final int layoutId, List<T> datas) {
        super(context, datas);
        initAdapter(context, layoutId);
    }

    private void initAdapter(Context context, final int layoutId) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;

        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(AARecViewHolder holder, T t, int position) {
                AARecAdapter.this.convert(holder, t, position);
            }
        });
    }

    protected abstract void convert(AARecViewHolder holder, T t, int position);


}
