package com.yannis.common.json;

import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/18  20:12
 * @email : 923080261@qq.com
 * @description :
 */
public class JsonConvert<T> {
    String objName;

    public T parseData(String json) {
        T t = null;
        Gson gson = new Gson();
        Type type = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        if (!TextUtils.isEmpty(objName)) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                t = gson.fromJson(jsonObject.getString(objName).toString(), type);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            t = gson.fromJson(json, type);
        }

        return t;
    }

    /**
     * 需要解析的部分
     * @param objName
     */
    public void setParseObjName(String objName) {
        this.objName = objName;
    }
}
