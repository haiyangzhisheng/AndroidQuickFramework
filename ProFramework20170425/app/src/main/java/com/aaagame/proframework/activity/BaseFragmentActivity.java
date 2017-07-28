package com.aaagame.proframework.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.aaagame.proframework.R;
import com.aaagame.proframework.dialog.AAShowDialog;
import com.aaagame.proframework.utils.AACom;
import com.aaagame.proframework.utils.AAToast;
import com.aaagame.proframework.utils.AAViewCom;
import com.aaagame.proframework.utils.AppManager;
import com.aaagame.proframework.widget.AppTitleBar;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.xutils.x;

import java.io.StringReader;
import java.lang.reflect.Type;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @author: myName
 * @date: 2016-12-30 14:50
 */
public abstract class BaseFragmentActivity extends FragmentActivity implements View.OnClickListener {

    public abstract void initView();

    public abstract void initListener();

    public abstract void initData();

    public abstract void reqData();
    /**
     * Activity之间传递参数标识
     */
    public final String bundleData = "bundleData";
    public Activity myActivity;
    protected AppTitleBar mTitleBar;
    /**
     * 本次请求时显示提示,false时不显示请求提示
     */
    public boolean reqShowAlert = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getInstance().addActivity(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED);
        // 注入view和事件
        x.view().inject(this);
        reqShowAlert = true;
        this.myActivity = this;
        mTitleBar = (AppTitleBar) findViewById(R.id.app_title_bar);
        setListener();
        initView();
        initListener();
        initData();
    }

    DialogInterface.OnKeyListener keylistener = new DialogInterface.OnKeyListener() {
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                return true;
            } else {
                return false;
            }
        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        this.myActivity = this;
    }


    @Override
    public void onClick(View view) {

    }

    /**
     * 返回
     *
     * @param view
     */
    public void goback(View view) {
        myActivity.finish();
    }

    protected void setListener() {
        if (mTitleBar != null) {
            mTitleBar.getBack().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myActivity.finish();
                    hideSoftKeyboard();
                    myActivity.overridePendingTransition(R.anim.fragment_back_enter, R.anim.fragment_back_exit);
                }
            });
        }
    }


    /**
     * 隐藏虚拟键盘
     */
    protected void hideSoftKeyboard() {
        // if (getWindow().getAttributes().softInputMode !=
        // WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (getCurrentFocus() != null)
                if (inputMethodManager != null) {
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // }
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            animFinish();
            myActivity.overridePendingTransition(R.anim.fragment_back_enter, R.anim.fragment_back_exit);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        AppManager.getInstance().killActivity(this);
        super.onDestroy();
    }

    public void animFinish() {
        this.finish(); // finish当前activity
        myActivity.overridePendingTransition(R.anim.fragment_back_enter, R.anim.fragment_back_exit);
    }

    // ================================公共方法

    public String getStrFrEdit(int etid) {
        return AACom.getStrFrEdit(myActivity, etid);
    }

    public void setStrToEdit(int etid, String value) {
        AAViewCom.getEt(myActivity, etid).setText(value);
    }

    public void setStrToTv(int etid, String value) {
        AAViewCom.getTv(myActivity, etid).setText(value);
    }

    public String getStrFrTv(int tvid) {
        return AACom.getStrFrTv(myActivity, tvid);
    }

    public boolean isEditEmpty(int etid) {
        return AACom.isEditEmpty(myActivity, etid);
    }

    public boolean isTvEmpty(int etid) {
        return AACom.isTvEmpty(myActivity, etid);
    }

    public void toastShow(String text) {
        AAToast.toastShow(myActivity, text);
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

    /**
     * 检查是否有EditText为空
     *
     * @param ids
     * @return
     */
    public boolean checkHaveEditEmpty(Integer[] ids) {
        for (int id : ids) {
            if (isEditEmpty(id)) {
                if (AAViewCom.getEt(myActivity, id).getHint() == null) {
                    return true;
                }
                String show = AAViewCom.getEt(myActivity, id).getHint().toString();
                if (!show.startsWith("请输入")) {
                    show = "请输入" + show;
                }
                toastShow(show);
                return true;
            }
        }
        return false;
    }

    /**
     * 设置EditText为不可用
     *
     * @param ids
     * @return
     */
    public void setEditUnEnabled(Integer[] ids) {
        for (int id : ids) {
            AAViewCom.getEt(myActivity, id).setEnabled(false);
        }
    }

    /**
     * 返回格式化对象
     *
     * @param json
     * @param classOfT
     * @param <T>
     * @return
     */
    public <T> T fromJson(String json, Class<T> classOfT) {
        return getGson().fromJson(json, classOfT);
    }

    /**
     * 返回格式化对象
     *
     * @param json
     * @param typeOfT
     * @param <T>
     * @return
     * @throws JsonSyntaxException
     */
    public <T> T fromJson(String json, Type typeOfT) throws JsonSyntaxException {
        if (json == null) {
            return null;
        }
        StringReader reader = new StringReader(json);
        T target = (T) getGson().fromJson(reader, typeOfT);
        return target;
    }

    /**
     * 转为Json字符串
     *
     * @param src
     * @return
     */
    public String toJson(Object src) {
        return getGson().toJson(src);
    }

}
