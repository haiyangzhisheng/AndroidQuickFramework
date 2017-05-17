package com.lcodecore.tkrefreshlayout;

public interface PullListener {
        /**
         * 下拉中
         *
         * @param refreshLayout
         * @param fraction
         */
        void onPullingDown(TwinklingRefreshLayout refreshLayout, float fraction);

        /**
         * 上拉
         */
        void onPullingUp(TwinklingRefreshLayout refreshLayout, float fraction);

        /**
         * 下拉松开
         *
         * @param refreshLayout
         * @param fraction
         */
        void onPullDownReleasing(TwinklingRefreshLayout refreshLayout, float fraction);

        /**
         * 上拉松开
         */
        void onPullUpReleasing(TwinklingRefreshLayout refreshLayout, float fraction);

        /**
         * 刷新中。。。
         */
        void onRefresh(TwinklingRefreshLayout refreshLayout);

        /**
         * 加载更多中
         */
        void onLoadMore(TwinklingRefreshLayout refreshLayout);

        /**
         * 手动调用finishRefresh或者finishLoadmore之后的回调
         */
        void onFinishRefresh();

        void onFinishLoadMore();
    }