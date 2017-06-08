package com.aaagame.proframework.areachoice;

import android.content.Context;
import android.content.res.AssetManager;

import com.aaagame.proframework.areachoice.entity.CityModel;
import com.aaagame.proframework.areachoice.entity.ProvinceModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AppJsonFileReader {
    public static String getJson(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static List<ProvinceModel> setProvinceData(String str) {
        List<ProvinceModel> data = new ArrayList<ProvinceModel>();
        try {
            JSONArray jsonArray = new JSONArray(str);

            ProvinceModel provinceModel;
            JSONArray cArray;
            CityModel cModel;
            List<CityModel> cList;
            JSONObject iObject, jObject;
            for (int i = 0, ilen = jsonArray.length(); i < ilen; i++) {
                iObject = jsonArray.getJSONObject(i);

                cList = new ArrayList<>();
                cArray = iObject.getJSONArray("city");
                for (int j = 0, jlen = cArray.length(); j < jlen; j++) {
                    jObject = cArray.getJSONObject(j);
                    cModel = new CityModel(jObject.getInt("area_id"),
                            jObject.getString("area_name"));
                    cList.add(cModel);
                }

                provinceModel = new ProvinceModel(iObject.getInt("area_id"),
                        iObject.getString("area_name"), cList);
                data.add(provinceModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;

    }
}
