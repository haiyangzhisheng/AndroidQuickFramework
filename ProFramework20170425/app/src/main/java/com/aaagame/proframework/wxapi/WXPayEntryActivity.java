package com.aaagame.proframework.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.aaagame.proframework.utils.AAToast;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new TextView(this));
        api = WXAPIFactory.createWXAPI(this, "wx_appid");
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			builder.setTitle(R.string.app_tip);
//			builder.setMessage(getString(R.string.pay_result_callback_msg, String.valueOf(resp.errCode)));
//			builder.show();
            //发送通知
            Intent intent = new Intent();
            intent.setAction("pay_result_weixin");
            intent.putExtra("pay_result_weixin", resp.errCode);
            sendBroadcast(intent);
            if (resp.errCode == -2) {//用户取消
                AAToast.toastShow(this, "取消支付");
                finish();
            } else if (resp.errCode == -1) {
                AAToast.toastShow(this, "支付失败");
//				if (CommitOrderActivity.payInstance != null && !CommitOrderActivity.payInstance.isFinishing()) {
//					CommitOrderActivity.payInstance.finish();
//				}
                finish();

            } else {

                AAToast.toastShow(this, resp.errStr);
//
//				if (CommitOrderActivity.payInstance != null && !CommitOrderActivity.payInstance.isFinishing()) {
//					CommitOrderActivity.payInstance.finish();
//				}
                finish();
            }
        }
    }
}