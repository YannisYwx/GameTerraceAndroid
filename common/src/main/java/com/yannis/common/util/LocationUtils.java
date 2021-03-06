package com.yannis.common.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/26  17:53
 * @email : 923080261@qq.com
 * @description : 定位工具类
 */
public class LocationUtils {
    private static String TAG = LocationUtils.class.getSimpleName();
    public static int GPS_LOCATION_REQUEST_CODE = 9996;

    private LocationUtils() {
    }

    private static class NestClass {
        private static LocationUtils instance = new LocationUtils();
    }

    public static LocationUtils getInstance() {
        return NestClass.instance;
    }

    /**
     * 检查是否开启GPS或AGPS功能
     *
     * @return
     */
    public boolean isOpenGPS(Context context) {

        LocationManager locationManager = (LocationManager) context.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        assert locationManager != null;
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            return true;
        }
        return false;
    }

    /**
     * 开启GPS
     *
     * @param activity
     */
    public void openGPS(Activity activity) {
        //跳转到GPS系统设置界面
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        activity.startActivityForResult(intent, GPS_LOCATION_REQUEST_CODE);
    }

    /**
     * 获得定位location
     *
     * @param context
     * @return
     */
    public Location getLocation(Context context) {
        // 获取位置管理服务
        LocationManager locationManager = (LocationManager) context.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        // 查找到服务信息
        Criteria criteria = new Criteria();
        //低精度，如果设置为高精度，依然获取不了location。
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        //不要求海拔
        criteria.setAltitudeRequired(false);
        //不要求方位
        criteria.setBearingRequired(false);
        //允许有花费
        criteria.setCostAllowed(true);
        //低功耗
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        // 获取GPS信息
        String locationProvider = locationManager.getBestProvider(criteria, true);
        //从可用的位置提供器中，匹配以上标准的最佳提供器
        Location location = null;
        try {
            location = locationManager.getLastKnownLocation(locationProvider);
            // 设置监听器，自动更新的最小时间为间隔N秒(1秒为1*1000，这样写主要为了方便)或最小位移变化超过N米
            locationManager.requestLocationUpdates(locationProvider, 100 * 1000, 500, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        if (location != null) {
            String locationStr = "纬度：" + location.getLatitude() + "\n" + "经度：" + location.getLongitude();
            Log.e(TAG, locationStr);
            Log.e(TAG, getAddressName(context, location));
        } else {
            Log.e(TAG, "location = null");

        }

        return location;
    }

    /**
     * 获取详细的地址信息
     *
     * @param context
     * @return 地址信息
     */
    public String getDetailAddress(Context context) {
        String detailAddress = null;

        // 获取位置管理服务
        LocationManager locationManager = (LocationManager) context.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        // 查找到服务信息
        Criteria criteria = new Criteria();
        //低精度，如果设置为高精度，依然获取不了location。
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        //不要求海拔
        criteria.setAltitudeRequired(false);
        //不要求方位
        criteria.setBearingRequired(false);
        //允许有花费
        criteria.setCostAllowed(true);
        //低功耗
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        // 获取GPS信息
        String locationProvider = locationManager.getBestProvider(criteria, true);
        //从可用的位置提供器中，匹配以上标准的最佳提供器
        Location location = null;
        try {
            location = locationManager.getLastKnownLocation(locationProvider);
            // 设置监听器，自动更新的最小时间为间隔N秒(1秒为1*1000，这样写主要为了方便)或最小位移变化超过N米
            locationManager.requestLocationUpdates(locationProvider, 100 * 1000, 500, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        if (location != null) {
            String locationStr = "纬度：" + location.getLatitude() + "\n" + "经度：" + location.getLongitude();
            Log.e(TAG, locationStr);
            Geocoder gc = new Geocoder(context.getApplicationContext(), Locale.getDefault());
            try {
                List<Address> addresses = gc.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                if (addresses.size() > 0) {
                    Address address = addresses.get(0);
                    detailAddress = address.getAddressLine(0);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.e(TAG, "location = null");

        }
        return detailAddress;
    }


    /**
     * 根据经纬度解析location（这个是耗时的，最好放在线程中）
     *
     * @param context
     * @param location
     * @return
     */
    public String getAddressName(Context context, Location location) {
        String cityName = null;
        // 更具地理环境来确定编码
        Geocoder gc = new Geocoder(context.getApplicationContext(), Locale.getDefault());
        try {
            // 取得地址相关的一些信息\经度、纬度
            List<Address> addresses = gc.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            StringBuilder sb = new StringBuilder();
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                sb.append("市 :");
                //市
                sb.append(address.getLocality()).append("\n");
                sb.append("区 :");
                //区
                sb.append(address.getSubLocality()).append("\n");
                sb.append("街道 :");
                //街道
                sb.append(address.getAddressLine(0)).append("\n");
                sb.append("名称 :");
                //名称
                sb.append(address.getAddressLine(1)).append("\n");
                sb.append("邮编 :");
                //邮编
                sb.append(address.getPostalCode()).append("\n");
                cityName = sb.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cityName;
    }
}
