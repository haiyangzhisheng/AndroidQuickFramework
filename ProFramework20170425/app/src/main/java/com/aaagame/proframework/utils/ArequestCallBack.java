package com.aaagame.proframework.utils;

import android.app.Activity;

import com.aaagame.proframework.dialog.AAShowDialog;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.ex.HttpException;

public class ArequestCallBack<ResultType> implements Callback.CommonCallback<ResultType> {
    Ahttp ahttp;
    Activity myActivity;

    public void aFail(HttpException error, String msg) {
    }

    public ArequestCallBack(Activity myActivity, Ahttp ahttp) {
        this.ahttp = ahttp;
        this.myActivity = myActivity;
    }

    public ArequestCallBack(Ahttp ahttp) {
        this.ahttp = ahttp;
    }

    public Aresult aresult;
    public boolean isError = false;

    @Override
    public void onCancelled(CancelledException arg0) {
    }

    @Override
    public void onError(Throwable arg0, boolean arg1) {
        try {
            if (ahttp.loadingDialog != null) {
                ahttp.loadingDialog.dismiss();
            }
            if (myActivity != null) {
                AAShowDialog.showAlert(true, myActivity, "请检查网络稍后重试");
            }
            arg0.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFinished() {
    }

    public String data;
    public JSONObject objectData;

    @Override
    public void onSuccess(ResultType res) {
        System.out.println("返回数据" + (String) res);
        try {
            if (ahttp.loadingDialog != null) {
                ahttp.loadingDialog.dismiss();
            }
            aresult = AACom.getGson().fromJson((String) res, Aresult.class);
            JSONObject jsonObject = new JSONObject((String) res);
            try {
                data = jsonObject.get("data").toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                objectData = jsonObject.getJSONObject("data");
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (aresult.ret.equals("0")) {
                isError = true;
//                if (aresult.msg.equals("用户tk过期")) {//别处登录，下线通知
//                    EventBus.getDefault().post("MyFragment");
//                    SettingActivity.loginOut(myActivity);
//                    return;
//                }
                if (myActivity != null) {
                    AAShowDialog.showAlert(true, myActivity, aresult.msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
