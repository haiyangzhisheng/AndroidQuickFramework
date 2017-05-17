package com.aaagame.proframework.utils;

import android.content.Context;
import android.util.Log;

import com.aaagame.proframework.bean.AddressBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @author: myName
 * @date: 2017-01-13 18:38
 */
public class AddressUtil {
    /**
     * 读取txt文件
     * @param
     * @return
     */
    public static String getTxtString(Context context, String pathname) {
        InputStream is = null;
        try {
            is = context.getAssets().open(pathname);
        } catch (IOException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(is, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer sb = new StringBuffer("");
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
//                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     *解析省数据
     */
    public static List<AddressBean> parseProvinceJSONObject(String data) {
        List<AddressBean> provinceList = new ArrayList<AddressBean>();
        try {
            JSONArray array = new JSONArray(data);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                if (object.has("area_name")) {
                    AddressBean bean = new AddressBean();
                    bean.setArea_id(object.getString("area_id"));
                    bean.setArea_name(object.getString("area_name"));
                    provinceList.add(bean);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return provinceList;
    }
    /**
     * 解析市数据
     */
    public static List<AddressBean> parseCityJSONObject(String data, String province) {
        List<AddressBean> cityList = new ArrayList<AddressBean>();
        try {
            JSONArray array1 = new JSONArray(data);
            for (int j = 0; j<array1.length(); j++) {
                JSONObject jsonObject = array1.getJSONObject(j);
                if (jsonObject.getString("area_name").equals(province)) {
                    JSONArray array = new JSONArray(jsonObject.getString("city"));
                    for (int k = 0; k <array.length(); k++) {
                        JSONObject jo = array.getJSONObject(k);
                        if (jo.has("area_name")) {
                            AddressBean bean = new AddressBean();
                            bean.setArea_id(jo.getString("area_id"));
                            bean.setArea_name(jo.getString("area_name"));
                            cityList.add(bean);
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cityList;
    }
    /**
     * 解析区县数据
     */
    public static List<AddressBean> parseCountyJSONObject(String data, String province, String city) {
        List<AddressBean> countyList = new ArrayList<AddressBean>();
        try {
            JSONArray array1 = new JSONArray(data);
            for (int j = 0; j<array1.length(); j++) {
                JSONObject jsonObject = array1.getJSONObject(j);
                if (jsonObject.getString("area_name").equals(province)) {
                    JSONArray array = new JSONArray(jsonObject.getString("city"));
                    for (int k = 0; k <array.length(); k++) {
                        JSONObject jo = array.getJSONObject(k);
                        if (jo.getString("area_name").equals(city)) {
                            if (jo.has("district") && !(jo.getString("district").equals("[]"))) {
                                JSONArray array2 = new JSONArray(jo.getString("district"));
                                for (int i = 0; i < array2.length(); i++) {
                                    JSONObject object1 = array2.getJSONObject(i);
                                    if (object1.has("area_name")) {
                                        AddressBean bean = new AddressBean();
                                        bean.setArea_id(object1.getString("area_id"));
                                        bean.setArea_name(object1.getString("area_name"));
                                        countyList.add(bean);
                                    }
                                }
                            }
//                            else {
//                                isCity = true;
//                            }
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
           Log.e("AddressTool","区县数据解析异常");
        }
        return countyList;
    }

}
