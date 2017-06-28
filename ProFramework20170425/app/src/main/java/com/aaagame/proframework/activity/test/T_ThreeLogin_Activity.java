package com.aaagame.proframework.activity.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.aaagame.proframework.R;
import com.aaagame.proframework.activity.BaseFragmentActivity;
import com.aaagame.proframework.utils.Ahttp;
import com.aaagame.proframework.utils.ArequestCallBack;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import java.util.Map;

import cn.jpush.android.api.JPushInterface;

@ContentView(R.layout.t_activity_model)
public class T_ThreeLogin_Activity extends BaseFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
        initData();
        reqData();
    }

    //=============================初始化view
    private void initView() {

    }

    //=============================初始化监听
    private void initListener() {

//        threeLogin(SHARE_MEDIA.QQ);
//        threeLogin(SHARE_MEDIA.WEIXIN);
    }

    @Event(value = {})
    private void setClick(View view) {
        switch (view.getId()) {
            default:
                break;
        }
    }

    //=============================初始化数据和变量
    private void initData() {

    }

    //=============================网络请求数据
    private void reqData() {

    }
    //=============================其他操作

    /**
     * 第三方登录
     *
     * @param platform
     */
    public void threeLogin(SHARE_MEDIA platform) {
        //三方登录时是否每次都要进行授权
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        UMShareAPI.get(this).setShareConfig(config);
        UMShareAPI.get(getApplicationContext()).getPlatformInfo(this, platform, umAuthListener);
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(getApplicationContext(), "授权成功", Toast.LENGTH_SHORT).show();
            String type = "1";
            if (platform == SHARE_MEDIA.QQ) {
                type = "2";
            }
            String token = data.get("accessToken");
            if (token == null) {
                token = data.get("access_token");
            }
            reqThreeLogin(type, data.get("uid"), token);
        }

        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(getApplicationContext(), "授权失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(getApplicationContext(), "授权取消", Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * 第三方登录
     */
    public void reqThreeLogin(String type, String openid, String accessToken) {
        JSONObject ob = new JSONObject();
        try {
            ob.put("type", type); //类型 1-微信 2-qq
            ob.put("openid", openid);
            ob.put("accessToken", accessToken);
            ob.put("clientId", JPushInterface.getRegistrationID(getApplicationContext()));
        } catch (JSONException e) {

        }
        final Ahttp ahttp = new Ahttp(myActivity, "", ob.toString());
        ahttp.send(new ArequestCallBack<String>(myActivity, ahttp) {
            @Override
            public void onSuccess(String responseInfo) {
                super.onSuccess(responseInfo);
                if (isError) {
                    return;
                }
                animFinish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }

}
