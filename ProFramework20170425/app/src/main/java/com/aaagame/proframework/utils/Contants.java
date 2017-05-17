package com.aaagame.proframework.utils;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @author: myName
 * @date: 2017-01-13 17:50
 */
public class Contants {
    //是否登录
    public static final String IS_LOGIN = "is_login";
    public static final String IS_FIRSE_IN = "is_first_in";
    public static final String LOGIN_SUCCESS_TIME = "login_success_time";
    // 用户信息
    public static final String USER_ID = "userId"; // 用户id
    public static final String GOOD_ID = "good_id";
    public static final String USER_TOKEN = "userToken"; //
    public static final String USER_INFO = "userInfo";
    public static final String From = "from";
    public static final String BEAN = "bean";
    public static final String LIST = "list";
    public static final String STRING = "str";
    public static final String SearchHistory = "SearchHistory";
    public static final String SearchKey = "SearchKey";//
    public static final String FINISH_LOGIN = "finish_login";
    public static final String REFRESH_SHOPPING_MALL = "refresh_shoping_mall";
    /**
     * 数据库版本
     */
    public static final int DATA_BASE_VERSION = 1;
    public static final String sKey = "637c3da60e43a39e9d8bd3d929e77786";  // "hzygift"  AES.getMd5

    public static final String PAY_TYPE = "pay_type";
    public static final String PAY_PRICE = "pay_price";
    public static final String ORDERSN = "order_sn";

    /**
     * 微信支付
     */
    public static final String APP_ID = "wxd2287aa9b3d7b19c";
    public static final String PARTNER_ID = "1441577602";
    public static final String PAY_RESULT_WEIXIN = "pay_result_weixin";

    //        public static String HOST = "http://192.168.50.223:8080/hzy/";
//    public static String IMAGE_HOST = "http://192.168.50.223:8080/hzy/img/";
//      public static String HOST = "http://192.168.50.190:8080/hzy/";
//      public static String IMAGE_HOST = "http://192.168.50.190:8080/hzy/pic/showPic.cs?fileName=";
//    public static String HOST = "http://116.255.244.116:8080/hzy/";
//    public static String IMAGE_HOST = "http://116.255.244.116:8080/hzy/img/";
    public static String HOST = "http://www.dixinyi.com/";
    public static String IMAGE_HOST = "http://img.dixinyi.com/";

    /**
     * 物流信息
     */
    public static String QUERY_LOGISTIC = "http://www.kuaidi100.com/query?";
    /**
     * 登录
     */
    public static String LOGIN = HOST + "rest/user/login.cs";
    /**
     * 注册
     */
    public static String REGISTER = HOST + "rest/user/register.cs";
    /**
     * 获取验证码
     */
    public static String GET_CODE = HOST + "rest/user/getCode.cs";
    /**
     * 忘记密码
     */
    public static String FORGET_PSW = HOST + "rest/user/forgetPwd.cs";
    /**
     * 设置用户角色
     */
    public static String SET_ROLE = HOST + "rest/user/setRole.cs";
    /**
     * 新增收货地址
     */
    public static String ADD_ADDRESS = HOST + "rest/user/addDeliveryAddr.cs";
    /**
     * 删除收货地址
     */
    public static String DEL_ADDRESS = HOST + "rest/user/delDeliveryAddr.cs";
    /**
     * 修改地址
     */
    public static String UPDATE_ADDRESS = HOST + "rest/user/updateDeliveryAddr.cs";
    /**
     * 我的地址列表
     */
    public static String MY_ADDRESS = HOST + "rest/user/myDeliveryAddr.cs";
    /**
     * 设置默认收货地址
     */
    public static String SET_DEFAULT_ADDRESS = HOST + "rest/user/setDefaultAddr.cs";
    /**
     * 获取默认收货地址
     */
    public static String GET_DEFAULT_ADDRESS = HOST + "rest/user/getDefaultShopAddress.cs";
    /**
     * 首页接口
     */
    public static String HOME_SHOPPING_MALL = HOST + "rest/shopgoods/getGoodsIndex.cs";
    /**
     * 分类一级列表
     */
    public static String CLASSIFY_ONE = HOST + "rest/shopgoods/getShopGoodsClassList.cs";
    /**
     * 分类二级列表带商品列表
     */
    public static String CLASSIFY_TWO_SEARCH_GOODS_LIST = HOST + "rest/shopgoods/getShopGoodsClassListTwo.cs";
    /**
     * 最新礼物  、明星礼物列表
     */
    public static String START_NEW_GIFT_LIST = HOST + "rest/shopgoods/getMoreGoodsList.cs";
    /**
     * 关键词搜索商品列表
     */
    public static String SEARCH_GOODS_LIST = HOST + "rest/shopgoods/getSearchGoodsList.cs";
    /**
     * 分类id搜索商品列表
     */
    public static String SEARCH_GOODS_LIST1 = HOST + "rest/shopgoods/getGoodsListByClassId.cs";
    /**
     * 搜索关键词
     */
    public static String HOT_WORD = HOST + "rest/shopgoods/getGoodsHotWords.cs";
    /**
     * 换一批
     */
    public static String NEXT = HOST + "rest/shopgoods/getMyLikeGoodsList.cs";
    /**
     * 商品详情
     */
    public static String GOOD_DETAIL = HOST + "rest/shopgoods/getGoodsDetail.cs";
    /**
     * 商品详情_猜你喜欢
     */
    public static String GUESS_YOUR_FAVOURITE = HOST + "rest/shopgoods/getMyLikeGoodsListByClassId.cs";
    /**
     * 获取sku
     */
    public static String GET_SKU = HOST + "rest/shopgoods/getShopGoodsSku.cs";
    /**
     * 加入购物车
     */
    public static String SHOPPING_CAR_ADD = HOST + "rest/cart/addToCart.cs";
    /**
     * 删除购物车
     */
    public static String SHOPPING_CAR_DEL = HOST + "rest/cart/delCarts.cs";
    /**
     * 购物车列表
     */
    public static String SHOPPING_CAR_LIST = HOST + "rest/cart/myCartList.cs";
    /**
     * 更新购物车数量
     */
    public static String SHOPPING_CAR_UPDATE = HOST + "rest/cart/updateCartCnt.cs";
    /**
     * 获取购物车数量
     */
    public static String SHOPPING_CAR_COUNT = HOST + "rest/cart/cartCnt.cs";
    /**
     * 结算
     */
    public static String SUBMIT_BUY = HOST + "rest/order/toSettle.cs";
    /**
     * 支付
     */
    public static String PAY = HOST + "rest/pay/prePay.cs";
    /**
     * 生成订单
     */
    public static String CREATE_ORDER = HOST + "rest/order/createShopOrder.cs";
    /**
     * 再次购买
     */
    public static final String BUY_AGAIN = HOST + "rest/order/buyAgain.cs";
    /**
     * 收藏列表
     */
    public static String MY_COLLECT_LIST = HOST + "rest/shopgoods/favGoodsList.cs";
    /**
     * 收藏
     */
    public static String COLLECT = HOST + "rest/shopgoods/addFavGoods.cs";
    /**
     * 取消收藏
     */
    public static String COLLECT_CANCLE = HOST + "rest/shopgoods/cancelFavGoods.cs";
    /**
     * 明细、积分兑换记录
     */
    public static String RECORD = HOST + "rest/point/pointLogList.cs";
    /**
     * 获取认证信息
     */
    public static String AUTH_INFO = HOST + "rest/user/authInfo.cs";
    /**
     * 获取商品全部评论
     */
    public static String All_COMMENT = HOST + "rest/shopgoods/getGoodsEvaluateList.cs";
    /**
     * 消息列表
     */
    public static String MESSAGE_LIST = HOST + "rest/app/msgList.cs";

    /**
     * 删除消息
     */
    public static String DEL_MESSAGE = HOST + "rest/app/delMsg.cs";

    /**
     * 删除消息
     */
    public static String PassportAuth = HOST + "rest/user/passportAuth.cs";
    /**
     * 获取我的积分
     */
    public static String MY_POINTS = HOST + "rest/user/getPoint.cs";
    /**
     * 礼包劵
     */
    public static String RECHANGE_ECARD = HOST + "goods/exGoods/";
    /**
     * 礼包劵兑换记录
     */
    public static String RECHANGE_ECARD_RECODE = HOST + "rest/ecard/bill.cs";
    /**
     * 礼包劵兑换商品详情
     */
    public static String RECHANGE_ECARD_DETAIL = HOST + "exchange/goods/";
    /**
     * 礼包劵兑换确认兑换
     */
    public static String RECHANGE_GIFT = HOST + "rest/ecard/exchangeGifts.cs";
    /**
     * 礼包劵兑换-确认订单页
     */
    public static String RECHANGE_GIFT_SURE = HOST + "rest/ecard/confirmExchange.cs";

    /**
     * 设计师分类
     */
    public static String DESIGN_CLASS = HOST + "rest/shopdiscover/designClass.cs";

}
