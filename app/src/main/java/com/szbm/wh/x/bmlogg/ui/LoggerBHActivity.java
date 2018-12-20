package com.szbm.wh.x.bmlogg.ui;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.AMapLocationQualityReport;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.maps.model.Text;
import com.amap.api.maps.model.TextOptions;
import com.autonavi.amap.mapcore.Inner_3dMap_location;
import com.szbm.wh.x.bmlogg.BmloggSharedPreference;
import com.szbm.wh.x.bmlogg.R;
import com.szbm.wh.x.bmlogg.databinding.LoggerBhActivityBinding;
import com.szbm.wh.x.bmlogg.databinding.MapFragmentBinding;
import com.szbm.wh.x.bmlogg.pojo.BoreholeAndExtra;
import com.szbm.wh.x.bmlogg.pojo.Resource;
import com.szbm.wh.x.bmlogg.pojo.Status;
import com.szbm.wh.x.bmlogg.ui.common.InjectActivity;
import com.szbm.wh.x.bmlogg.ui.location.Utils;
import com.szbm.wh.x.bmlogg.ui.ui.loggerbh.LoggerBhViewModel;
import com.szbm.wh.x.bmlogg.ui.ui.main.MainReceiver;
import com.szbm.wh.x.bmlogg.ui.ui.map.MapFragment;
import com.szbm.wh.x.bmlogg.ui.ui.map.MapViewModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

public class LoggerBHActivity extends CheckPermissionsActivity
        implements LocationSource,AMapLocationListener{

    @Inject
    BmloggSharedPreference bmloggSharedPreference;
    @Inject
    ViewModelProvider.Factory factory;
    private LoggerBhViewModel mViewModel;
    boolean first = true;
    private AMap aMap;
    private MapView mapView;
    private UiSettings mUiSettings;
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private OnLocationChangedListener onLocationChangedListener;

    private BoreholeGemetry boreholeGemetry;
    private MyRadiusGeometry myRadiusGeometry;
    private DistanceGeometry distanceGeometry;

    MyLocationStyle myLocationStyle;
    MutableLiveData<Resource<LatLng>> myLocation;
    MutableLiveData<Resource<String>> myLocationReport;
    final static String s_index = "s_boreole_index";
    LoggerBhActivityBinding bhActivityBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bhActivityBinding = DataBindingUtil.setContentView(this,R.layout.logger_bh_activity);
        mViewModel = ViewModelProviders.of(this,factory).get(LoggerBhViewModel.class);
        mapView = bhActivityBinding.map;
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        setupMap();

        long iid = getIntent().getLongExtra(s_index,-1L);
        mViewModel.boreholeExtra.observe(this,data -> {
            bhActivityBinding.setResource(data);
            bhActivityBinding.setCallback(()->mViewModel.index.setValue(iid));

            if(data.getData()!= null){
                this.boreholeGemetry.addBoreholes(data.getData());
                this.boreholeGemetry.zoom();
            }
        });
        myLocation.observe(this,latLngResource -> {
            if(latLngResource.getData()!= null){
                int radius = bmloggSharedPreference.readsearch_radius();
                this.myRadiusGeometry.addMyRadius(latLngResource.getData(),radius);
                if(this.boreholeGemetry.marker != null){
                    this.distanceGeometry.calcDistance(myRadiusGeometry.circle.getCenter(),
                            boreholeGemetry.marker.getPosition());
                }
                else{
                    this.distanceGeometry.clear();
                }
            }
        });
        mViewModel.index.setValue(iid);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logger_bh_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_mine_radius:
                int radius = bmloggSharedPreference.readsearch_radius();
                new MaterialDialog.Builder(this)
                        .title(R.string.action_logger_bh_radius)
                        .content("单位：米，默认30米")
                        .inputType(InputType.TYPE_CLASS_NUMBER)
                        .input("输入整数", String.valueOf(radius), ((dialog, input) -> {}))
                        .positiveText(R.string.ok)
                        .negativeText(R.string.cancel)
                        .onPositive((dialog,which)->{
                            int newR = radius;
                            if(dialog.getInputEditText().getText() != null && dialog.getInputEditText().getText().length() >0){
                                newR = Integer.parseInt(dialog.getInputEditText().getText().toString());
                            }
                            bmloggSharedPreference.writesearch_radius(newR);
                        })
                        .show();
                break;
            case R.id.action_location_report:
                myLocationReport.observe(this,stringResource -> {
                    if (stringResource.getStatus() == Status.SUCCESS){
                        new MaterialDialog.Builder(this)
                                .title(R.string.location_report)
                                .content(stringResource.getData())
                                .show();
                    }else if(stringResource.getStatus() == Status.ERROR){
                        new MaterialDialog.Builder(this)
                                .title(R.string.location_report)
                                .icon(getResources().getDrawable(R.drawable.ic_error_outline_black))
                                .content(stringResource.getMessage())
                                .show();
                    }
                    else{

                    }

                    myLocationReport.removeObservers(this);
                });
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    /**
     * 初始化AMap对象
     */
    private void setupMap() {
        if (aMap == null) {
            aMap = mapView.getMap();
            mUiSettings = aMap.getUiSettings();
        }
        myLocation = new MutableLiveData<>();
        myLocationReport = new MutableLiveData<>();
        distanceGeometry = new DistanceGeometry();

        boreholeGemetry = new BoreholeGemetry();
        myRadiusGeometry = new MyRadiusGeometry();

        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_BUTTOM);
        mUiSettings.setScaleControlsEnabled(true);
        mUiSettings.setCompassEnabled(true);
        myLocationStyle = new MyLocationStyle();
        myLocationStyle.radiusFillColor(Color.argb(50, 1, 1, 1));
        myLocationStyle.strokeWidth(10.0f).strokeColor(Color.argb(50, 1, 1, 1));
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.navi_map_gps_locked));
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
        aMap.setMyLocationStyle(myLocationStyle);
        mUiSettings.setMyLocationButtonEnabled(true); // 是否显示默认的定位按钮
        aMap.setMyLocationEnabled(true);// 是否可触发定位并显示定位层
        aMap.setLocationSource(this);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        destroyLocation();
        mapView.onDestroy();
    }

    protected View getMyView(String code) {
        View view=getLayoutInflater().inflate(R.layout.marker_borehole, null);
        TextView tv_val=(TextView) view.findViewById(R.id.code);
        tv_val.setText(code);
        return view;
    }

    class BoreholeGemetry{
        Marker marker;
        void addBoreholes(BoreholeAndExtra boreholeAndExtra){
            this.clear();
            Double lat = boreholeAndExtra.getLat();
            Double _long = boreholeAndExtra.get_long();
            if(lat != null && _long != null){
                LatLng latLng = new LatLng(lat,_long);

                MarkerOptions markerOption = new MarkerOptions().
                        icon(BitmapDescriptorFactory.fromView(getMyView(boreholeAndExtra.getCode())))
                        .position(latLng)
                        .draggable(false)
                        .title("钻孔:"+boreholeAndExtra.getCode());
                this.marker = aMap.addMarker(markerOption);
                this.marker.setObject(boreholeAndExtra);
            }
        }

        private void clear(){
            if(marker != null){
                marker.remove();
                marker = null;
            }
        }

        private void zoom()
        {
            if(marker != null)
                aMap.moveCamera(CameraUpdateFactory.changeLatLng(marker.getPosition()));
        }
    }

    class MyRadiusGeometry{
        Circle circle;
        void addMyRadius(LatLng center, int radius){
            this.clear();
            CircleOptions circleOptions = new CircleOptions();
            circleOptions.center(center)
                    .radius(radius).strokeColor(Color.argb(255, 255, 1, 1))
                    .fillColor(Color.argb(25, 1, 1, 1)).strokeWidth(5);
            circle = aMap.addCircle(circleOptions);
        }
        private void clear(){
            if(circle != null)
                circle.remove();
            circle = null;
        }
        void zoom(){
            if(circle != null){
                CameraUpdate cameraUpdate =
                        CameraUpdateFactory.changeLatLng(circle.getCenter());
                aMap.moveCamera(cameraUpdate);
            }
        }
    }

    class DistanceGeometry{
        Polyline distance;
        Text disttext;

        public void calcDistance(LatLng a, LatLng b){
            this.clear();
            PolylineOptions options = new PolylineOptions();
            options.add(a);
            options.add(b);
            options.width(10).useGradient(true).setDottedLine(false).color(Color.argb(255, 255, 1, 1));
            distance = aMap.addPolyline(options);

            TextOptions textOptions = new TextOptions();
            textOptions.position(new LatLng((a.latitude +b.latitude)/2.0f,(a.longitude + b.longitude)/2.0f));
            textOptions.fontSize(50);
            float d = AMapUtils.calculateLineDistance(a,b);
            textOptions.text(new DecimalFormat("#.00").format(d)+"米");
            disttext = aMap.addText(textOptions);
        }

        private void clear(){
            if(distance != null)
                distance.remove();
            if(disttext != null)
                disttext.remove();
            distance = null;
            disttext = null;
        }

    }


    /**
     * 默认的定位参数
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private AMapLocationClientOption getDefaultOption(){
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(false); //可选，设置是否使用缓存定位，默认为true
        mOption.setGeoLanguage(AMapLocationClientOption.GeoLanguage.DEFAULT);//可选，设置逆地理信息的语言，默认值为默认语言（根据所在地区选择语言）
        return mOption;
    }

    /**
     * 定位监听
     */
    @Override
    public void onLocationChanged(AMapLocation location) {
        if (null != location) {
            if(this.onLocationChangedListener != null){
                this.onLocationChangedListener.onLocationChanged(location);
            }
            Status status = Status.LOADING;
            StringBuffer sb = new StringBuffer();
            //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
            if(location.getErrorCode() == 0){
                if(first){
                    LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
                    first = false;
                }
                sb.append("定位成功" + "\n");
                sb.append("定位类型: " + location.getLocationType() + "\n");
                sb.append("经    度    : " + location.getLongitude() + "\n");
                sb.append("纬    度    : " + location.getLatitude() + "\n");
                sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
                sb.append("提供者    : " + location.getProvider() + "\n");

                sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n");
                sb.append("角    度    : " + location.getBearing() + "\n");
                // 获取当前提供定位服务的卫星个数
                sb.append("星    数    : " + location.getSatellites() + "\n");
                sb.append("国    家    : " + location.getCountry() + "\n");
                sb.append("省            : " + location.getProvince() + "\n");
                sb.append("市            : " + location.getCity() + "\n");
                sb.append("城市编码 : " + location.getCityCode() + "\n");
                sb.append("区            : " + location.getDistrict() + "\n");
                sb.append("区域 码   : " + location.getAdCode() + "\n");
                sb.append("地    址    : " + location.getAddress() + "\n");
                sb.append("兴趣点    : " + location.getPoiName() + "\n");
                //定位完成的时间
                sb.append("定位时间: " + Utils.formatUTC(location.getTime(), "yyyy-MM-dd HH:mm:ss") + "\n");
                myLocation.setValue(Resource.Companion.success(new LatLng(location.getLatitude(),location.getLongitude())));
                status = Status.SUCCESS;
            } else {
                //定位失败
                sb.append("定位失败" + "\n");
                sb.append("错误码:" + location.getErrorCode() + "\n");
                sb.append("错误信息:" + location.getErrorInfo() + "\n");
                sb.append("错误描述:" + location.getLocationDetail() + "\n");
                myLocation.setValue(Resource.Companion.error(sb.toString(),null));
                status = Status.ERROR;
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
            //解析定位结果
            if(status == Status.SUCCESS)
                myLocationReport.setValue(Resource.Companion.success(sb.toString()));
            else if(status == Status.ERROR){
                myLocationReport.setValue(Resource.Companion.error(sb.toString(),null));
            }
            else{

            }
        } else {
            myLocation.setValue(Resource.Companion.error("定位失败！",null));
        }
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

    /**
     * 销毁定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void destroyLocation(){
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }


    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        this.onLocationChangedListener = onLocationChangedListener;
        if(locationClient == null){
            //初始化client
            locationClient = new AMapLocationClient(this);
            locationOption = getDefaultOption();
            //设置定位参数
            locationClient.setLocationOption(locationOption);
            // 设置定位监听
            locationClient.setLocationListener(this);
            // 设置定位参数
            locationClient.setLocationOption(locationOption);
            // 启动定位
            locationClient.startLocation();
        }

    }

    @Override
    public void deactivate() {
        // 停止定位
        locationClient.stopLocation();
    }
}
