package com.szbm.wh.x.bmlogg.ui.location;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.AMapLocationQualityReport;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.LatLng;
import com.szbm.wh.x.bmlogg.R;
import com.szbm.wh.x.bmlogg.pojo.Resource;
import com.szbm.wh.x.bmlogg.pojo.Status;
import com.szbm.wh.x.bmlogg.vo.Bh_geo_date;

public class SignIn implements AMapLocationListener{
    private static SignIn sInstance;
    private SignIn strategy;
    private static final Object sLock = new Object();

    public static SignIn getInstance() {
        synchronized (sLock) {
            if (sInstance == null) {
                sInstance = new SignIn();
            }
        }
        return sInstance;
    }

    AMapLocationClient locationClient = null;
    SignInListener signInListener;
    Context context;
    MaterialDialog materialDialog;
    LatLng borehole ;
    public void create(Context context,LatLng borehole, SignInListener listener) {
        this.context = context;
        this.signInListener = listener;
        this.borehole = borehole;
        locationClient = new AMapLocationClient(context);

        AMapLocationClientOption option = new AMapLocationClientOption();
        /**
         * 设置签到场景，相当于设置为：
         * option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
         * option.setOnceLocation(true);
         * option.setOnceLocationLatest(true);
         * option.setMockEnable(false);
         * option.setWifiScan(true);
         * option.setGpsFirst(false);
         * 其他属性均为模式属性。
         * 如果要改变其中的属性，请在在设置定位场景之后进行
         */
        option.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        locationClient.setLocationOption(option);
        //设置定位监听
        locationClient.setLocationListener(this);

        materialDialog = new MaterialDialog.Builder(context)
                .title("定位")
                .content("只进行一次定位,返回最接近真实位置的定位结果(定位速度可能会延迟1-3s)")
                .progress(true,0,false)
                .canceledOnTouchOutside(false)
                .keyListener((dialog, keyCode, event) -> {
                    if(keyCode == KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0)
                        return true;
                    else
                        return false;
                })
                .build();
    }

    public void startSignIn(){
        if(null != locationClient) {
            //签到只需调用startLocation即可
            locationClient.startLocation();
            materialDialog.show();
        }

    }

    public void destory(){
        if(null != locationClient){
            locationClient.onDestroy();
        }
        locationClient = null;
    }

    /**
     * 定位监听
     */
    @Override
    public void onLocationChanged(AMapLocation location) {
        this.materialDialog.dismiss();
        if (null != location) {
            boolean success = false;
            StringBuffer sb = new StringBuffer();
            //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
            if(location.getErrorCode() == 0){
                sb.append("定位成功" + "\n");
                float d = AMapUtils.calculateLineDistance(borehole,new LatLng(location.getLatitude(),location.getLongitude()));
                sb.append("我和钻孔的距离:" + String.valueOf(d)+"米"+"\n");
                sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
                sb.append("提供者    : " + location.getProvider() + "\n");
                //定位完成的时间
                sb.append("定位时间: " + Utils.formatUTC(location.getTime(), "yyyy-MM-dd HH:mm:ss") + "\n");
                success = true;
            } else {
                //定位失败
                sb.append("定位失败" + "\n");
                sb.append("错误码:" + location.getErrorCode() + "\n");
                sb.append("错误信息:" + location.getErrorInfo() + "\n");
                sb.append("错误描述:" + location.getLocationDetail() + "\n");
                success = false;
            }
            sb.append("***定位质量报告***").append("\n");
            sb.append("* WIFI开关：").append(location.getLocationQualityReport().isWifiAble() ? "开启":"关闭").append("\n");
            sb.append("* GPS状态：").append(getGPSStatusString(location.getLocationQualityReport().getGPSStatus())).append("\n");
            sb.append("* GPS星数：").append(location.getLocationQualityReport().getGPSSatellites()).append("\n");
            sb.append("* 网络类型：" + location.getLocationQualityReport().getNetworkType()).append("\n");
            sb.append("* 网络耗时：" + location.getLocationQualityReport().getNetUseTime()).append("\n");
            sb.append("****************").append("\n");
            //定位之后的回调时间
            sb.append("回调时间: " + Utils.formatUTC(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss") + "\n");

            //解析定位结果，
            String result = sb.toString();

            if(success){
                MaterialDialog materialDialog = new MaterialDialog.Builder(context)
                        .title("定位报告")
                        .content(result)
                        .positiveText("使用本次定位")
                        .negativeText("取消")
                        .onNegative(((dialog, which) -> {}))
                        .onPositive(((dialog, which) -> {
                            signInListener.onSignInSuccess(
                                    location.getLongitude(),
                                    location.getLatitude(),
                                    Utils.formatUTC(location.getTime(), "yyyy-MM-dd HH:mm:ss"),
                                    sb.toString()
                            );
                        }))
                        .canceledOnTouchOutside(false)
                        .keyListener((dialog, keyCode, event) -> {
                            if(keyCode == KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0)
                                return true;
                            else
                                return false;
                        })
                        .build();
                materialDialog.show();
            }else {
                MaterialDialog materialDialog = new MaterialDialog.Builder(context)
                        .title("定位报告")
                        .content(result)
                        .positiveText("关闭")
                        .onPositive(((dialog, which) -> {
                        }))
                        .build();
                materialDialog.show();
            }
        } else {
            MaterialDialog materialDialog = new MaterialDialog.Builder(context)
                    .title("定位报告")
                    .content("定位失败")
                    .positiveText("关闭")
                    .onPositive(((dialog, which) -> {
                    }))
                    .build();
            materialDialog.show();
        }
        destory();
    }

    /**
     * 获取GPS状态的字符串
     * @param statusCode GPS状态码
     * @return
     */
    private String getGPSStatusString(int statusCode){
        String str = "";
        switch (statusCode){
            case AMapLocationQualityReport.GPS_STATUS_OK:
                str = "GPS状态正常";
                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPROVIDER:
                str = "手机中没有GPS Provider，无法进行GPS定位";
                break;
            case AMapLocationQualityReport.GPS_STATUS_OFF:
                str = "GPS关闭，建议开启GPS，提高定位质量";
                break;
            case AMapLocationQualityReport.GPS_STATUS_MODE_SAVING:
                str = "选择的定位模式中不包含GPS定位，建议选择包含GPS定位的模式，提高定位质量";
                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPERMISSION:
                str = "没有GPS定位权限，建议开启gps定位权限";
                break;
        }
        return str;
    }

    public interface SignInListener{
        void startLT(Context context);
        void onSignInSuccess(double lng, double lat, String time,String info);
        void goToMap(LatLng latLng,String code);
    }
}
