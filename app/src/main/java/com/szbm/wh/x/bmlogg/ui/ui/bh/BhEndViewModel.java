package com.szbm.wh.x.bmlogg.ui.ui.bh;

import android.net.Uri;

import com.szbm.wh.x.bmlogg.repository.BoreholeSetRepository;
import com.szbm.wh.x.bmlogg.repository.ExtraRepository;
import com.szbm.wh.x.bmlogg.util.AbsentLiveData;
import com.szbm.wh.x.bmlogg.vo.BH_Logger;
import com.szbm.wh.x.bmlogg.vo.Bh_begin_info;
import com.szbm.wh.x.bmlogg.vo.Bh_end_info;
import com.szbm.wh.x.bmlogg.vo.Bh_extra_info;
import com.szbm.wh.x.bmlogg.vo.Bh_geo_date;
import com.szbm.wh.x.bmlogg.vo.Bh_imagesinfo;
import com.szbm.wh.x.bmlogg.vo.BoreholeSet;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class BhEndViewModel extends ViewModel {
    @Inject
    public BhEndViewModel(){

    }
    @Inject
    BH_Logger bh_logger;
    @Inject
    ExtraRepository extraRepository;
    @Inject
    BoreholeSetRepository boreholeSetRepository;


    final MutableLiveData<Long> borehole = new MutableLiveData<>();
    public final LiveData<BoreholeSet> boreholeSetLiveData = Transformations.switchMap(borehole, bh->
            boreholeSetRepository.loadBoreholeSetFromDisk(bh)
    );
    public final LiveData<Bh_extra_info> extra_infoLiveData = Transformations.switchMap(boreholeSetLiveData, bh->
            boreholeSetRepository.loadExtraInfoFromDisk(bh.getExtra_iid())
    );

    public final LiveData<Bh_end_info> server_result = Transformations.switchMap(boreholeSetLiveData, bhset->{
        if(bhset != null){
            return  extraRepository.loadbh_end_infoFromDisk(bhset,bh_logger.getNumber());
        }
        else{
            return AbsentLiveData.Companion.create();
        }
    });

    public final LiveData<List<Bh_imagesinfo>> server_imageInfos = Transformations.switchMap(server_result, bh_end_info->{
        if(bh_end_info != null){
            return  extraRepository.getImageinfos(bh_end_info.getIid(),"Bh_end_info");
        }
        else{
            return AbsentLiveData.Companion.create();
        }
    });

    public final LiveData<Bh_geo_date> server_geo_date = Transformations.switchMap(server_result, bh_end_info->{
        if(bh_end_info != null){
            return  extraRepository.getbh_geo_date(bh_end_info.getIid(),"Bh_end_info");
        }
        else{
            return AbsentLiveData.Companion.create();
        }
    });

    public final MutableLiveData<Uri> addUri = new MutableLiveData<>();

    public LiveData<Bh_imagesinfo> addImageResult = Transformations.switchMap(addUri,uri->{
        if(uri == null){
            return AbsentLiveData.Companion.create();
        }
        else{
            Bh_end_info bh_end_info = server_result.getValue();
            Bh_imagesinfo bh_imagesinfo = new Bh_imagesinfo(null,
                    "Bh_end_info",
                    bh_end_info.getIid(),
                    null,
                    uri.toString(),
                    bh_end_info.getBorehole_extra_iid());
            return extraRepository.addBh_ImagesInfo(bh_imagesinfo);
        }
    });

    public final MutableLiveData<Bh_imagesinfo> changeImage = new MutableLiveData<>();

    public LiveData<Bh_imagesinfo> changeImagesinfoLiveData = Transformations.switchMap(changeImage,uri->
            extraRepository.addBh_ImagesInfo(uri));

    public final MutableLiveData<Bh_imagesinfo> deleteImage = new MutableLiveData<>();
    public final LiveData<Bh_imagesinfo> deleteImageResult = Transformations.switchMap(deleteImage,input -> {
        return extraRepository.deleteImage(input);
    });

    public final MutableLiveData<Bh_imagesinfo> saveImage = new MutableLiveData<>();
    public final LiveData<Bh_imagesinfo> saveImageResult = Transformations.switchMap(saveImage,input -> {
        return extraRepository.addBh_ImagesInfo(input);
    });

    public MediatorLiveData<Bh_geo_date> geodate = new MediatorLiveData<>();
    public void locateAndTime(double lng ,double lat, String time){
        Bh_geo_date geo_date = new Bh_geo_date(lng,
                lat,
                time,
                "Bh_end_info",
                server_result.getValue().getIid(),
                server_result.getValue().getBorehole_extra_iid());
        extraRepository.insertGeoDate(geo_date);
//        geodate.addSource(extraRepository.insertBhGeoDate(geo_date),gd->{
//            geodate.setValue(gd);
//        });
    }
}
