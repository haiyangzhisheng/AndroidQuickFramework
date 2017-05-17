package com.aaagame.proframework.utils;

/**
 * Created by Administrator on 2016/12/15.
 */

public class ConInterface {
    public static String HOST = Contants.HOST;
    /**
     * 我的优惠券
     */
    public static final String Mine_Coupon = HOST + "rest/voucher/findMyVouchers.cs";
    /**
     * 我的电子券
     */
    public static final String Mine_Electronic_Coupon = HOST + "rest/ecard/findMyEcard.cs";
    /**
     * 添加电子券
     */
    public static final String Mine_Electronic_Coupon_Add = HOST + "rest/ecard/binding.cs";
    /**
     * 上传头像
     */
    public static final String Upload_Avatar = HOST + "rest/user/uploadAvatar.cs";
    /**
     * 编辑个人信息
     */
    public static final String EditUserInfo = HOST + "rest/user/editUserInfo.cs";
    /**
     * 用户认证
     */
    public static final String AuthUser = HOST + "rest/user/authUser.cs";
    /**
     * 订单列表
     */
    public static final String OrderList = HOST + "rest/order/myOrderList.cs";
    /**
     * 订单详情
     */
    public static final String OrderDetail = HOST + "rest/order/orderDetail.cs";
    /**
     * 取消订单
     */
    public static final String CancleOrder = HOST + "rest/order/cancelOrder.cs";
    /**
     * 删除订单
     */
    public static final String DeleteOrder = HOST + "rest/order/delOrder.cs";
    /**
     * 订单收货
     */
    public static final String ReceiveOrder = HOST + "rest/order/takeDelivery.cs";
    /**
     * 订单评价
     */
    public static final String OrderEvals = HOST + "rest/order/addOrderEvals.cs";

    /**
     * 定制案例
     */
    public static final String ShopCustomList = HOST + "rest/shopcustom/getShopCustomList.cs";
    /**
     * 定制分类
     */
    public static final String ShopClassList = HOST + "rest/shopcustom/getShopClassList.cs";
    /**
     * 定制二级分类商品列表
     */
    public static final String ShopGoodsTwoList = HOST + "rest/shopcustom/getShopGoodsTwoList.cs";


    /**
     * 一级分类全部商品
     */
    public static final String ShopGoodsOneList = HOST + "rest/shopcustom/getShopGoodsOneList.cs";
    /**
     * 设计师首页
     */
    public static final String DiscoverIndex = HOST + "rest/shopdiscover/getDiscoverIndex.cs";
    /**
     * 更多设计师
     */
    public static final String DesignerList = HOST + "rest/shopdiscover/getDesignerList.cs";
    /**
     * 搜索设计师
     */
    public static final String GetSearchDesignerList = HOST + "rest/shopdiscover/getSearchDesignerList.cs";
    /**
     * 设计师案例
     */
    public static final String DesignWorksList = HOST + "rest/shopdiscover/getDesignWorksList.cs";
    /**
     * 设计师详情
     */
    public static final String DesignerDetail = HOST + "rest/shopdiscover/getDesignerDetail.cs";
    /**
     * 设计师个人作品分页
     */
    public static final String MyDesignerList = HOST + "rest/shopdiscover/getMyDesignerList.cs";
    /**
     * 设计师作品详情
     */
    public static final String DesignWorksDetail = HOST + "rest/shopdiscover/getDesignWorksDetail.cs";
    /**
     * 定制新需求列表
     */
    public static final String ShopCustomOrderList = HOST + "rest/shopdiscover/getShopCustomOrderList.cs";
    /**
     * 定制需求详情
     */
    public static final String ShopCustomOrderDetail = HOST + "rest/shopdiscover/getShopCustomOrderDetail.cs";
    /**
     * 需求投标作品分页数据
     */
    public static final String ShopCustomOrderPage = HOST + "rest/shopdiscover/getShopCustomOrderPage.cs";
    /**
     * 提交需求
     */
    public static final String SaveShopCustomOrder = HOST + "rest/shopdiscover/saveShopCustomOrder.cs";

    /**
     * 选中中标操作
     */
    public static final String ConfirmShopCustomOrder = HOST + "rest/shopdiscover/confirmShopCustomOrder.cs";
    /**
     * 收益管理首页
     */
    public static final String RevenueManagementIndex = HOST + "rest/shopdiscover/revenueManagementIndex.cs";

    /**
     * 收益管理获取需求列表
     */
    public static final String ShopCustomOrderByUserId = HOST + "rest/shopdiscover/getShopCustomOrderByUserId.cs";
    /**
     * 设计师余额变化记录
     */
    public static final String UserDesignerRecordList = HOST + "rest/shopdiscover/getUserDesignerRecordList.cs";
    /**
     * 保存提现记录接口
     */
    public static final String SaveUserDesignerRecord = HOST + "rest/shopdiscover/saveUserDesignerRecord.cs";
    /**
     * 我要定制
     */
    public static final String CreateWdzOrder = HOST + "rest/order/createWdzOrder.cs";
    /**
     * 版本更新
     */
    public static final String GetVersion = HOST + "rest/app/getVersion.cs";
    /**
     * 项目列表数据
     */
    public static final String GetMyShopCustomOrderList = HOST + "rest/shopdiscover/getMyShopCustomOrderList.cs";
    /**
     * 私人定制
     */
    public static final String CreateDzOrder = HOST + "rest/order/createDzOrder.cs";
    /**
     * app启动广告
     */
    public static final String BootAd = HOST + "rest/app/bootAd.cs";
    /**
     * 首页优惠券领取
     */
    public static final String VoucherShow = HOST + "rest/voucher/voucherShow.cs";
    /**
     * 删除需求附件接口
     */
    public static final String DeleteCustomAttach = HOST + "rest/shopdiscover/deleteCustomAttach.cs";
    /**
     * 修改定制需求
     */
    public static final String UpdateShopCustomOrder = HOST + "rest/shopdiscover/updateShopCustomOrder.cs";


}
