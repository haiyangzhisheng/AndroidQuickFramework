package com.aaagame.proframework.utils;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import com.aaagame.proframework.R;

import org.xutils.common.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class AAComAdapter<T> extends BaseAdapter {

    protected Context mContext;
    public List<T> mDatas = new ArrayList<T>();
    protected LayoutInflater mInflater;
    private int mlayoutId;
    private AAViewHolder holder;
    private ListView listView;
    private int noDataLayoutId = -1;
    public Photo_Take_Util currentUtil;
    /**
     * 对每一行设置不同颜色时存储颜色列表
     */
    private SparseArray<String> arraycolor;
    private boolean showFresh = false;
    /**
     * 是否显示无数据界面
     */
    private boolean showNoData = true;
    public int nextpage = 1;

    public void setNotShowNoData() {
        showNoData = false;
    }

    /**
     * 设置没有数据显示时，显示的界面
     *
     * @param noDataLayoutId
     */
    public void setNotShowLayoutId(int noDataLayoutId) {
        this.noDataLayoutId = noDataLayoutId;
    }


    public AAComAdapter(Context context, int layoutId, List<T> datas, boolean showFresh) {
        this.mContext = context;
        this.mlayoutId = layoutId;
        mInflater = LayoutInflater.from(context);
        this.mDatas.clear();
        this.mDatas.addAll(datas);
        this.showFresh = showFresh;
        this.emptyView = LayoutInflater.from(mContext).inflate(R.layout.aa_list_nodata, null);
    }

    public AAComAdapter(Context context, int layoutId, List<T> datas) {
        this.mContext = context;
        this.mlayoutId = layoutId;
        mInflater = LayoutInflater.from(context);
        this.mDatas.clear();
        this.mDatas.addAll(datas);
        if (this.mDatas.size() == 0) {
            this.mDatas.add(null);
        }
        this.emptyView = LayoutInflater.from(mContext).inflate(R.layout.aa_list_nodata, null);
    }

    public AAComAdapter(Context context, int layoutId) {
        this.mContext = context;
        this.mlayoutId = layoutId;
        mInflater = LayoutInflater.from(context);
        this.mDatas.clear();
        this.emptyView = LayoutInflater.from(mContext).inflate(R.layout.aa_list_nodata, null);
    }

    /**
     * 设置空界面图标
     *
     * @param resid
     */
    public void setEmptyIcon(int resid) {
        AAViewCom.getIv(emptyView, R.id.iv_empty).setBackgroundResource(resid);
    }

    /**
     * 设置显示购物车背景
     */
    public void setShowShoppingCartEmpty() {
        AAViewCom.getIv(emptyView, R.id.iv_shopping_cart).setVisibility(View.VISIBLE);
    }

    /**
     * 设置空界面提示
     *
     * @param alert
     */
    public void setEmptyAlert(String alert) {
        AAViewCom.getTv(emptyView, R.id.tv_alert).setText(alert);
    }

    /**
     * 获取空界面操作按钮
     *
     * @return
     */
    public Button getAction(String text) {
        Button action = AAViewCom.getBtn(emptyView, R.id.btn_action);
        action.setVisibility(View.VISIBLE);
        action.setText(text);
        return action;
    }

    /**
     * 设置空界面在Tab下时，调整顶部高度
     */
    public void setEmptyTab() {
        View view_top = AAViewCom.getView(emptyView, R.id.view_top);
        ViewGroup.LayoutParams layoutParams = view_top.getLayoutParams();
        layoutParams.height = DensityUtil.dip2px(50);
        layoutParams.width = 10;
        view_top.setLayoutParams(layoutParams);
    }

    private int myNumColumns = 2;
    private boolean isInitNumColumns = false;
    private int dividerHeight = 0;

    private View emptyView;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (parent instanceof GridView) {
            if (!isInitNumColumns) {
                GridView gridView = (GridView) parent;
                myNumColumns = gridView.getNumColumns();
                isInitNumColumns = true;
            }
        }
        try {//获取listView分割线
            if (listView.getDividerHeight() > 0) {
                dividerHeight = listView.getDividerHeight();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mDatas.get(position) == null && mDatas.size() == 1) {
            //获得listview的实例
            try {
                if (parent instanceof ListView) {
                    ListView listView = (ListView) parent;
                    listView.setEnabled(false);
                    listView.setDividerHeight(0);
                }
                if (parent instanceof GridView) {
                    GridView gridView = (GridView) parent;
                    gridView.setEnabled(false);
                    gridView.setNumColumns(1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (noDataLayoutId == -1) {
                return emptyView;
            } else {
                return LayoutInflater.from(mContext).inflate(noDataLayoutId, parent, false);
            }
        } else {
            try {
                if (listView.getDividerHeight() == 0 && dividerHeight > 0) {
                    listView.setDividerHeight(dividerHeight);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (parent instanceof ListView) {
                    ListView listView = (ListView) parent;
                    listView.setEnabled(true);
                }
                if (parent instanceof GridView) {
                    GridView gridView = (GridView) parent;
                    gridView.setEnabled(true);
                    gridView.setNumColumns(myNumColumns);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            holder = AAViewHolder.get(mContext, convertView, parent, mlayoutId, position, showFresh);
            convert(holder, (T) getItem(position));
            return holder.getConvertView();
        }
    }

    public void addTextColor(int position, String color) {
        if (arraycolor == null) {
            arraycolor = new SparseArray<String>();
        }
        arraycolor.put(position, color);
    }

    public String getTextColor(int position) {
        return arraycolor.get(position);
    }

    public abstract void convert(AAViewHolder holder, T mt);

    public AAViewHolder getHolder() {
        return holder;
    }

    public void resetData(List<T> mDatas) {
        this.mDatas.clear();
        this.mDatas.addAll(mDatas);
        if (showNoData) {
            if (this.mDatas.size() == 0) {
                this.mDatas.add(null);
            }
        }
        notifyDataSetChanged();
    }

    public void addData(List<T> mDatas) {
        this.mDatas.addAll(mDatas);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
