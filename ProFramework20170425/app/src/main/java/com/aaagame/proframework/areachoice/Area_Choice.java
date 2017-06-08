package com.aaagame.proframework.areachoice;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.aaagame.proframework.R;
import com.aaagame.proframework.areachoice.entity.CityModel;
import com.aaagame.proframework.areachoice.entity.DistrictModel;
import com.aaagame.proframework.areachoice.entity.ProvinceModel;
import com.aaagame.proframework.areachoice.wheel.OnWheelChangedListener;
import com.aaagame.proframework.areachoice.wheel.WheelView;
import com.aaagame.proframework.areachoice.wheel.adapters.ArrayWheelAdapter;
import com.aaagame.proframework.utils.AACom;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Area_Choice implements OnClickListener, OnWheelChangedListener {
    private Activity myActivity;

    public Area_Choice(Activity myActivity) {
        this.myActivity = myActivity;
    }

    public PopupWindow mPopWindow;

    // =========================================================================================================

    /**
     * 显示到底部
     */
    public TextView showPopupWindow_Bottom(int layoutId) {
        mPopWindow = init_PopupWindow();
        // 显示PopupWindow
        View rootview = LayoutInflater.from(myActivity).inflate(layoutId, null);
        mPopWindow.setAnimationStyle(R.style.dialog_frag_anim);
        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
        initWhell(contentView);

        try {
            mCurrentProviceName = mProvinceDatas[0];
            mCurrentProviceName_Id = mProvinceDatas_Id[0];
            mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[0];
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tv_ok;
    }

    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;
    private TextView tv_cancle, tv_ok;

    private void initWhell(View contentView) {

        setUpViews();
        setUpListener();
        setUpData();
    }

    private void setUpViews() {
        mViewProvince = (WheelView) contentView.findViewById(R.id.id_province);
        mViewCity = (WheelView) contentView.findViewById(R.id.id_city);
        mViewDistrict = (WheelView) contentView.findViewById(R.id.id_district);
        tv_ok = (TextView) contentView.findViewById(R.id.tv_ok);
        tv_cancle = (TextView) contentView.findViewById(R.id.tv_cancle);
    }

    private void setUpListener() {
        // 添加change事件
        mViewProvince.addChangingListener(this);
        // 添加change事件
        mViewCity.addChangingListener(this);
        // 添加change事件
        mViewDistrict.addChangingListener(this);
        // 添加onclick事件
        tv_ok.setOnClickListener(this);
        tv_cancle.setOnClickListener(this);
    }

    private void setUpData() {
        initProvinceDatas();
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(myActivity, mProvinceDatas));
        // 设置可见条目数量
        mViewProvince.setVisibleItems(7);
        mViewCity.setVisibleItems(7);
        mViewDistrict.setVisibleItems(7);
        updateCities();
        updateAreas();
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        // TODO Auto-generated method stub
        if (wheel == mViewProvince) {
            updateCities();
            try {
                mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[0];
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (wheel == mViewCity) {
            updateAreas();
            try {
                mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[0];
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (wheel == mViewDistrict) {
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
        }
    }

    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas() {
        int pCurrent = mViewCity.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        String[] areas = mDistrictDatasMap.get(mCurrentCityName);

        if (areas == null) {
            areas = new String[]{""};
        }
        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(myActivity, areas));
        mViewDistrict.setCurrentItem(0);
    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities() {
        int pCurrent = mViewProvince.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[pCurrent];
        mCurrentProviceName_Id = mProvinceDatas_Id[pCurrent];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[]{""};
        }
        mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(myActivity, cities));
        mViewCity.setCurrentItem(0);
        updateAreas();
    }

    @Override
    public void onClick(View v) {
        if (tv_cancle == v) {
            mPopWindow.dismiss();
        }
    }

    public String getSelectedResult() {
        mCurrentCityName_Id = mNameToCode_city.get(mCurrentCityName);
        mCurrentDistrictName_Id = mNameToCode_district.get(mCurrentDistrictName);
        /*if (mCurrentProviceName.equals("香港") || mCurrentProviceName.equals("台湾")) {
            return mCurrentProviceName + mCurrentCityName;
		} else if (mCurrentProviceName.equals("澳门")) {
			return mCurrentCityName;
		} else if (mCurrentCityName.startsWith(mCurrentProviceName)) {
			return mCurrentCityName + mCurrentDistrictName;
		} else {
			return mCurrentProviceName + mCurrentCityName + mCurrentDistrictName;
		}*/

        if (mCurrentProviceName.equals("台湾")) {
            return mCurrentProviceName + mCurrentCityName;
        } else if (mCurrentProviceName.equals("香港") || mCurrentProviceName.equals("澳门")) {
            return mCurrentCityName + mCurrentDistrictName;
        } else if (mCurrentCityName.startsWith(mCurrentProviceName)) {
            return mCurrentCityName + mCurrentDistrictName;
        } else {
            return mCurrentProviceName + mCurrentCityName + mCurrentDistrictName;
        }
    }

    // =================================================================================PopupWindow
    View contentView;

    private PopupWindow init_PopupWindow() {
        // 设置contentView
        contentView = LayoutInflater.from(myActivity).inflate(R.layout.area_choice, null);
        PopupWindow mPopWindow = new PopupWindow(contentView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,
                true);
        mPopWindow.setContentView(contentView);
        // 在PopupWindow里面就加上下面代码，让键盘弹出时，不会挡住pop窗口。
        mPopWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // 点击空白处时，隐藏掉pop窗口
        mPopWindow.setFocusable(true);
        mPopWindow.setTouchable(true);
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        backgroundAlpha(1);
        mPopWindow.setOnDismissListener(new poponDismissListener());
        // 设置动画
        // mPopWindow.setAnimationStyle(R.style.popup_down_animations);
        // 保证点击空白处消失
        return mPopWindow;
    }

    /**
     * 添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     *
     * @author cg
     */
    class poponDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            // Log.v("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(1f);
        }

    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = myActivity.getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        myActivity.getWindow().setAttributes(lp);
    }

    // =============================初始化

    /**
     * 所有省
     */
    protected String[] mProvinceDatas;
    /**
     * 所有省
     */
    protected String[] mProvinceDatas_Id;
    /**
     * key - 省 value - 市
     */
    protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    /**
     * key - 市 values - 区
     */
    protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();

    /**
     * key - 区 values - 邮编
     */
    protected Map<String, String> mNameToCode_district = new HashMap<String, String>();
    /**
     * key - 市 values - 邮编
     */
    protected Map<String, String> mNameToCode_city = new HashMap<String, String>();
    /**
     * key - 省 values - 邮编
     */
    protected Map<String, String> mNameToCode_province = new HashMap<String, String>();

    /**
     * 当前省的名称
     */
    public String mCurrentProviceName;

    /**
     * 当前省的名称
     */
    public String mCurrentProviceName_Id;
    /**
     * 当前市的名称
     */
    public String mCurrentCityName;
    /**
     * 当前区的名称
     */
    public String mCurrentDistrictName = "";
    /**
     * 当前市的名称
     */
    public String mCurrentCityName_Id;
    /**
     * 当前区的名称
     */
    public String mCurrentDistrictName_Id = "";

    private final static String fileName = "address.json";

    /**
     * 解析省市区的XML数据
     */

    private void initProvinceDatas() {
        if (mProvinceDatas != null && mCitisDatasMap.size() > 0 && mCurrentCityName != null
                && mNameToCode_city.size() > 0) {
            return;
        }
        List<ProvinceModel> provinceList = null;
        try {
            String jsonStr = AppJsonFileReader.getJson(myActivity, fileName);
            // 获取解析出来的数据
            java.lang.reflect.Type type = new TypeToken<List<ProvinceModel>>() {
            }.getType();
            provinceList = AACom.getGson().fromJson(jsonStr, type);
            // */ 初始化默认选中的省、市、区
            if (provinceList != null && !provinceList.isEmpty()) {
                mCurrentProviceName = provinceList.get(0).getArea_name();
                mCurrentProviceName_Id = provinceList.get(0).getArea_id() + "";
                List<CityModel> cityList = provinceList.get(0).getCity();
                if (cityList != null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getArea_name();
                    mCurrentCityName_Id = cityList.get(0).getArea_id() + "";
                    List<DistrictModel> districtList = cityList.get(0).getDistrict();
                    mCurrentDistrictName = districtList.get(0).getArea_name();
                    mCurrentDistrictName_Id = districtList.get(0).getArea_id() + "";
                }
            }
            // */
            mProvinceDatas = new String[provinceList.size()];
            mProvinceDatas_Id = new String[provinceList.size()];
            for (int i = 0; i < provinceList.size(); i++) {
                // 遍历所有省的数据
                mProvinceDatas[i] = provinceList.get(i).getArea_name();
                mProvinceDatas_Id[i] = provinceList.get(i).getArea_id() + "";
                mNameToCode_province.put(provinceList.get(i).getArea_name(), provinceList.get(i).getArea_id() + "");
                List<CityModel> cityList = provinceList.get(i).getCity();
                String[] cityNames = new String[cityList.size()];
                for (int j = 0; j < cityList.size(); j++) {
                    // 遍历省下面的所有市的数据
                    cityNames[j] = cityList.get(j).getArea_name();
                    mNameToCode_city.put(cityList.get(j).getArea_name(), cityList.get(j).getArea_id() + "");
                    List<DistrictModel> districtList = cityList.get(j).getDistrict();
                    String[] distrinctNameArray = new String[districtList.size()];
                    DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
                    for (int k = 0; k < districtList.size(); k++) {
                        // 遍历市下面所有区/县的数据
                        DistrictModel districtModel = new DistrictModel(districtList.get(k).getArea_id(),
                                districtList.get(k).getArea_name());
                        // 区/县对于的邮编，保存到mZipcodeDatasMap
                        mNameToCode_district.put(districtList.get(k).getArea_name(),
                                districtList.get(k).getArea_id() + "");
                        distrinctArray[k] = districtModel;
                        distrinctNameArray[k] = districtModel.getArea_name();
                    }
                    // 市-区/县的数据，保存到mDistrictDatasMap
                    mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
                }
                // 省-市的数据，保存到mCitisDatasMap
                mCitisDatasMap.put(provinceList.get(i).getArea_name(), cityNames);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }
    }
    // ====异步

    public void initProvinceDatasOtherAsy() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                initProvinceDatas();
            }
        }).start();
    }
}
