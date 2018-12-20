package com.szbm.wh.x.bmlogg.ui.ui.map;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.text.TextPaint;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.szbm.wh.x.bmlogg.R;
import com.szbm.wh.x.bmlogg.page.Listing;
import com.szbm.wh.x.bmlogg.pojo.BoreholeAndExtra;
import com.szbm.wh.x.bmlogg.pojo.Resource;
import com.szbm.wh.x.bmlogg.pojo.Status;
import com.szbm.wh.x.bmlogg.repository.BoreholesRepos;
import com.szbm.wh.x.bmlogg.ui.ui.main.MainViewModel;
import com.szbm.wh.x.bmlogg.vo.BH_BoreholeInfo;
import com.szbm.wh.x.bmlogg.vo.BH_Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import dagger.Lazy;

public class MapViewModel extends ViewModel {
    @Inject
    BoreholesRepos boreholesRepos;
    @Inject
    BH_Logger bh_logger;

    @Inject
    public MapViewModel(){

    }

    final MediatorLiveData<Resource<List<BoreholeAndExtra>>> boreholes =
            new MediatorLiveData<>();

    final MediatorLiveData<Resource<List<BoreholeAndExtra>>> subBoreholes = new MediatorLiveData<>();
    /*
    ProjectInfo
    BH_Logger null is ok
     */
    LiveData<Resource<List<BoreholeAndExtra>>> dbSource;
    public void getBoreholes(){
        boreholes.removeSource(dbSource);
        dbSource = boreholesRepos.get();
        boreholes.addSource(dbSource,listResource -> {
            boreholes.setValue(listResource);
        });
    }

    /*
    ProjectInfo
    BH_Logger null is ok
     */
    public void getSubBoreholes(LatLng center, int radius){
        subBoreholes.removeSource(dbSource);
        dbSource = boreholesRepos.get();
        subBoreholes.addSource(dbSource,listResource -> {
            if(listResource.getStatus() == Status.SUCCESS && listResource.getData() != null){
                List<BoreholeAndExtra> radiusList = new ArrayList<>(listResource.getData().size());
                for (BoreholeAndExtra bo:listResource.getData()
                     ) {
                    if(bo.getLat() != null && bo.get_long() != null){
                        if(AMapUtils.calculateLineDistance(center,new LatLng(bo.getLat(),bo.get_long())) <= radius){
                            radiusList.add(bo);
                        }
                    }
                }
                subBoreholes.setValue(Resource.Companion.success(radiusList));
            }
            else{
                subBoreholes.setValue(listResource);
            }
        });
    }
}
