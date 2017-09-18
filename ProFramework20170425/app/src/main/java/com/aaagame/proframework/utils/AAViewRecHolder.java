package com.aaagame.proframework.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class AAViewRecHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    MyItemClickListener mClickListener;
    private SparseArray<View> mViews;
    private View mConvertView;
    private Context mContext;

    public AAViewRecHolder(Context context, View itemView, MyItemClickListener itemClickListener) {
        super(itemView);
        mContext = context;
        mConvertView = itemView;
        mViews = new SparseArray<View>();
        this.mClickListener = itemClickListener;
        itemView.setOnClickListener(this);
    }

    public static AAViewRecHolder get(Context context, ViewGroup parent, int layoutId, MyItemClickListener itemClickListener) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        AAViewRecHolder holder = new AAViewRecHolder(context, itemView, itemClickListener);
        return holder;
    }

    @Override
    public void onClick(View view) {
        if (mClickListener != null) {
            mClickListener.onItemClick(view, getLayoutPosition());
        }
    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public AAViewRecHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public AAViewRecHolder setImageResource(int viewId, int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

}
