package com.aaagame.proframework.utils;

/**
 * Created by Administrator on 2017/8/27 0027.
 */

public interface MultiItemTypeSupport<T> {
    int getLayoutId(int itemType);
    int getItemViewType(int position,T t);
}
