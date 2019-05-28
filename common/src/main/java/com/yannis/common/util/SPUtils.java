package com.yannis.common.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/16  10:39
 * @email : 923080261@qq.com
 * @description : SharedPreferences 工具类
 */
public class SPUtils {

    public static String FILE_NAME = "YAppSp";

    private static SharedPreferences sp;
    private static SharedPreferences.Editor editor;
    private static Context sContext;

    /**
     * 初始化数据
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        WeakReference<Context> weakReference = new WeakReference<>(context);
        sContext = weakReference.get().getApplicationContext();
        sp = sContext.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        editor = sp.edit();
    }


    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param key    键
     * @param object 值
     */
    public static void put(String key, Object object) {
        checkIsInit();
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }

        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key           键
     * @param defaultObject 默认值
     * @return 保存值
     */
    public static Object get(String key, Object defaultObject) {
        checkIsInit();
        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key 键
     */
    public static void remove(String key) {
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     */
    public static void clear() {
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param key 键
     * @return true 存在  false 不存在
     */
    public static boolean contains(String key) {
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @return 所有的键值对
     */
    public static Map<String, ?> getAll() {
        return sp.getAll();
    }

    /**
     * 检查是否初始化
     */
    private static void checkIsInit() {
        if (null == sContext) {
            throw new NullPointerException("The context is null you must call init method first");
        }
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @@author zhy
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return 方法
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor SharedPreferences.Editor
         */
        static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            editor.commit();
        }
    }

}
