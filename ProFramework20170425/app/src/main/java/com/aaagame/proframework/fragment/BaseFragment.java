package com.aaagame.proframework.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aaagame.proframework.R;
import com.aaagame.proframework.activity.BaseFragmentActivity;
import com.aaagame.proframework.dialog.AAShowDialog;
import com.aaagame.proframework.utils.AACom;
import com.google.gson.Gson;

import org.xutils.x;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @author: ds
 * @date: 2016-12-30 15:00
 */
public abstract class BaseFragment extends Fragment {
    public abstract void initView();

    public abstract void initListener();

    public abstract void initData();

    public abstract void reqData();

    public BaseFragmentActivity myActivity;
    private boolean injected = false;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myActivity = (BaseFragmentActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        myActivity = (BaseFragmentActivity) this.getActivity();
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        injected = true;
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!injected) {
            x.view().inject(this, this.getView());
        }
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 添加Activity动画效果
     */
    public void animStartActivity(Class c) {
        startActivity(new Intent(myActivity, c));
        myActivity.overridePendingTransition(R.anim.fragment_enter, R.anim.fragment_exit);
    }

    /**
     * 添加Activity动画效果
     */
    public void animStartActivityForResult(Class c, int requestCode) {
        startActivityForResult(new Intent(myActivity, c), requestCode);
        myActivity.overridePendingTransition(R.anim.fragment_enter, R.anim.fragment_exit);
    }

    /**
     * 添加Activity动画效果
     */
    public void animStartActivityForResult(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
        myActivity.overridePendingTransition(R.anim.fragment_enter, R.anim.fragment_exit);
    }

    /**
     * 添加Activity动画效果
     */
    public void animStartActivity(Intent intent) {
        startActivity(intent);
        myActivity.overridePendingTransition(R.anim.fragment_enter, R.anim.fragment_exit);
    } // 处理后退键的情况


    public void animFinish() {
        myActivity.finish(); // finish当前activity
        myActivity.overridePendingTransition(R.anim.fragment_back_enter, R.anim.fragment_back_exit);
    }

    public void showAlert(String title, String msg) {
        AAShowDialog.showAlert(myActivity, title, msg);
    }

    public void showAlert(String msg) {
        AAShowDialog.showAlert(myActivity, msg);
    }

    public void showAlert(final boolean noFinish, String btn_text, String title, String msg) {
        AAShowDialog.showAlert(noFinish, myActivity, btn_text, title, msg);

    }

    public Gson getGson() {
        return AACom.getGson();
    }

}
