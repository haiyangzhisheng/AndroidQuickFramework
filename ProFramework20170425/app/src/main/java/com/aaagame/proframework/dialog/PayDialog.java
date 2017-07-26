package com.aaagame.proframework.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.aaagame.proframework.R;
import com.aaagame.proframework.bean.PayResult;
import com.aaagame.proframework.utils.AACom;
import com.aaagame.proframework.utils.AAToast;
import com.aaagame.proframework.utils.AAViewCom;
import com.aaagame.proframework.utils.Ahttp;
import com.aaagame.proframework.utils.ArequestCallBack;
import com.aaagame.proframework.utils.ConInterface;
import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.unionpay.UPPayAssistEx;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @author: myName
 * @date: 2017-02-08 15:53
 */
public class PayDialog extends DialogFragment implements View.OnClickListener {
    private TextView sum_price_tv, pay_tv;
    private ImageView cancel_iv, alipay_iv, weixin_iv, union_iv;
    private double price;
    private IWXAPI api;
    /**
     * 当前选择的支付方式，0支付宝，1微信，2银联
     */
    private int mpayType;
    private static final int SDK_PAY_FLAG = 1;
    private MyBroadcastReciver payreceiver;
    //银联
    public static final String LOG_TAG = "PayDemo";
    private final String mMode = "00";// 0 - 启动银联正式环境   1 - 连接银联测试环境
    //银联标志
    public static final int PLUGIN_VALID = 0;
    public static final int PLUGIN_NOT_INSTALLED = -1;
    public static final int PLUGIN_NEED_UPGRADE = 2;
    //订单信息
    private String mpayInfo;
    //订单号
    private String orderSn;
    //订单类型
    private String orderType = "1";

    /**
     * 设置订单类型
     *
     * @param orderType
     */
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.dialog_pay);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消

        // 设置宽度为屏宽, 靠近屏幕底部。
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        window.setAttributes(lp);

        initView(dialog.getWindow().getDecorView());
        api = WXAPIFactory.createWXAPI(getActivity(), "");
        registerBroadcast("pay_result_weixin");
        return dialog;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onMyDismiss != null) {
            onMyDismiss.toDismiss();
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void setData(double price, String orderSn) {
        this.price = price;
        this.orderSn = orderSn;
    }

    private void initView(View view) {
        alipay_iv = AAViewCom.getIv(view, R.id.alipay_iv);
        weixin_iv = AAViewCom.getIv(view, R.id.weixin_iv);
        union_iv = AAViewCom.getIv(view, R.id.union_iv);
        sum_price_tv = AAViewCom.getTv(view, R.id.sum_price_tv);
        cancel_iv = AAViewCom.getIv(view, R.id.cancel_iv);
        pay_tv = AAViewCom.getTv(view, R.id.pay_tv);
        pay_tv.setOnClickListener(this);
        cancel_iv.setOnClickListener(this);
        alipay_iv.setOnClickListener(this);
        weixin_iv.setOnClickListener(this);
        union_iv.setOnClickListener(this);
        sum_price_tv.setText(AACom.get2price(price + ""));
        mpayType = 0;
        //默认选中支付宝
        alipay_iv.setSelected(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pay_tv:
                pay();
                break;
            case R.id.cancel_iv:
                dismiss();
                break;
            case R.id.union_iv:
                AAToast.toastShow(getActivity(), "暂未开通");
                break;
        }
        if (view.getId() == R.id.alipay_iv || view.getId() == R.id.weixin_iv) {
            alipay_iv.setSelected(false);
            weixin_iv.setSelected(false);
            union_iv.setSelected(false);
            if (view.getId() == R.id.alipay_iv) {
                mpayType = 0;
                alipay_iv.setSelected(true);
            }
            if (view.getId() == R.id.weixin_iv) {
                mpayType = 1;
                weixin_iv.setSelected(true);
            }
        }
    }

    /**
     * 注册微信支付广播
     */
    public void registerBroadcast(String action) {
        payreceiver = new MyBroadcastReciver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(action);
        getActivity().registerReceiver(payreceiver, intentFilter);
    }

    /**
     * 支付
     */
    public void pay() {
        JSONObject ob = new JSONObject();
        try {
            if (mpayType == 0) {
                ob.put("paymentCode", "alipay");
            } else if (mpayType == 1) {
                ob.put("paymentCode", "wxpay");
                if (!api.isWXAppInstalled()) {
                    AAToast.toastShow(getActivity(), "请先安装微信APP");
                    return;
                }
            } else if (mpayType == 2) {
                ob.put("paymentCode", "union");
            }
            ob.put("type", orderType);
            ob.put("orderSn", orderSn);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final Ahttp ahttp = new Ahttp(getActivity(), ConInterface.Sample, ob.toString());
        ahttp.send(new ArequestCallBack<String>(getActivity(), ahttp) {
            @Override
            public void onMySuccess(String responseInfo) {
                JSONObject obj = null;
                try {
                    obj = new JSONObject(responseInfo);
                    mpayInfo = obj.getString("data");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (mpayType == 0) {
                    goAliPay(mpayInfo);
                } else if (mpayType == 1) {
                    goWxPay(mpayInfo);
                } else if (mpayType == 2) {
                    goUnionPay(mpayInfo);
                }
            }
        });
    }

    //支付宝支付结果处理
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        toPayResultActivity(0);
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
//                            Toast.makeText(mActivity, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            // Toast.makeText(mActivity, "支付失败", Toast.LENGTH_SHORT).show();
                        }
                        toPayResultActivity(1);
                    }
                }
                break;
            }
        }

        ;
    };

    /**
     * 支付宝
     *
     * @param payInfo
     */
    private void goAliPay(final String payInfo) {
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(getActivity());
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();

    }

    /**
     * 微信支付
     *
     * @param data
     */
    public void goWxPay(String data) {
        JSONObject json = null;
        try {
            json = new JSONObject(data);
            if (null != json) {
                PayReq req = new PayReq();
                req.appId = "";
                req.partnerId = "";
                req.prepayId = json.getString("prepayid");
                req.nonceStr = json.getString("noncestr");
                req.timeStamp = json.getString("timestamp");
                req.packageValue = json.getString("package");
                req.sign = json.getString("sign");
                req.extData = "app data";
//           Toast.makeText(GoodsDetailActivity.this, "正常调起支付", Toast.LENGTH_SHORT).show();
                // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                api.sendReq(req);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 银联支付
     */
    public void goUnionPay(final String tn) {

        int ret = UPPayAssistEx.startPay(getActivity(), null, null, tn, mMode);
        if (ret == PLUGIN_NEED_UPGRADE || ret == PLUGIN_NOT_INSTALLED) {
            // 需要重新安装控件
            Log.e(LOG_TAG, " plugin not found or need upgrade!!!");

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("提示");
            builder.setMessage("完成购买需要安装银联支付控件，是否安装？");

            builder.setNegativeButton("确定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            UPPayAssistEx.installUPPayPlugin(getActivity());
                            dialog.dismiss();
                        }
                    });

            builder.setPositiveButton("取消",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.create().show();

        }
        Log.e(LOG_TAG, "" + ret);
    }

    //在Activity中的onActivityResult调用
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        String str = data.getExtras().getString("pay_result");
        String msg = "";
        if (str.equalsIgnoreCase("success")) {
            // 支付成功后，extra中如果存在result_data，取出校验
            // result_data结构见c）result_data参数说明
            if (data.hasExtra("result_data")) {
                String result = data.getExtras().getString("result_data");
                try {
                    JSONObject resultJson = new JSONObject(result);
                    String sign = resultJson.getString("sign");
                    String dataOrg = resultJson.getString("data");
                    // 验签证书同后台验签证书
                    // 此处的verify，商户需送去商户后台做验签
                    boolean ret = verify(dataOrg, sign, mMode);
                    if (ret) {
                        // 验证通过后，显示支付结果
                        msg = "支付成功！";
                        toPayResultActivity(1);
                    } else {
                        // 验证不通过后的处理
                        // 建议通过商户后台查询支付结果
                        msg = "支付失败！";
                        toPayResultActivity(0);
                    }
                } catch (JSONException e) {
                }
            } else {
                // 未收到签名信息
                // 建议通过商户后台查询支付结果
                msg = "支付成功！";
                toPayResultActivity(0);
            }
        } else if (str.equalsIgnoreCase("fail")) {
            msg = "支付失败！";
            toPayResultActivity(1);
        } else if (str.equalsIgnoreCase("cancel")) {
            msg = "用户取消了支付";
            AAToast.toastShow(getActivity(), msg);
        }
    }

    private boolean verify(String msg, String sign64, String mode) {
        // 此处的verify，商户需送去商户后台做验签
        return true;

    }

    //微信支付结果处理
    public class MyBroadcastReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("pay_result_weixin")) {//微信支付结果
                if (intent.getIntExtra("pay_result_weixin", 0) == -2) {//取消订单

                } else if (intent.getIntExtra("pay_result_weixin", 0) == -1) {//支付失败
                    toPayResultActivity(1);
                } else if (intent.getIntExtra("pay_result_weixin", 0) == 0) {//支付成功
                    toPayResultActivity(0);
                }

            }
        }
    }

    /**
     * 支付后跳转支付结果页
     *
     * @param from
     */
    public void toPayResultActivity(int from) {
        try {
            Intent intent = new Intent();
//            intent.setClass(activity, PayResultActivity.class);
            intent.putExtra("from", from);
            intent.putExtra("paytype", mpayType);
            intent.putExtra("orderType", orderType);
//        intent.putExtra(Contants.PAY_PRICE,price);
            intent.putExtra("ordersn", orderSn);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.fragment_enter, R.anim.fragment_exit);
            getActivity().finish();
            dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {
        hideSoftKeyboard(getActivity());
        super.onStop();
    }


    /**
     * 隐藏虚拟键盘
     */
    protected void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (activity.getWindow()
                    .getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
                if (activity.getCurrentFocus() != null)
                    if (inputMethodManager != null) {
                        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private OnMyDismiss onMyDismiss;

    public void addMyDismiss(OnMyDismiss onMyDismiss) {
        this.onMyDismiss = onMyDismiss;
    }

    public interface OnMyDismiss {
        public void toDismiss();
    }

}
