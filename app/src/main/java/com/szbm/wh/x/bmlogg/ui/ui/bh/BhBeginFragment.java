package com.szbm.wh.x.bmlogg.ui.ui.bh;

import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.model.LatLng;
import com.szbm.wh.x.bmlogg.R;
import com.szbm.wh.x.bmlogg.databinding.BhBeginFragmentBinding;
import com.szbm.wh.x.bmlogg.pojo.ImageInfo;
import com.szbm.wh.x.bmlogg.ui.common.CSKeys;
import com.szbm.wh.x.bmlogg.ui.common.ImageAdapter;
import com.szbm.wh.x.bmlogg.ui.common.InjectFragment;
import com.szbm.wh.x.bmlogg.ui.location.SignIn;
import com.szbm.wh.x.bmlogg.ui.ui.map.CheckPermissionsFragment;
import com.szbm.wh.x.bmlogg.vo.BH_BoreholeInfo;
import com.szbm.wh.x.bmlogg.vo.Bh_geo_date;
import com.szbm.wh.x.bmlogg.vo.Bh_imagesinfo;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

public class BhBeginFragment extends ImagesGeoDateFragment {

    @Inject
    ViewModelProvider.Factory factory;

    private BhBeginViewModel mViewModel;
    BhBeginFragmentBinding binding;
    long borehole_iid;
    String borehole_name;

    public static BhBeginFragment newInstance() {
        return new BhBeginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.bh_begin_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this,factory).get(BhBeginViewModel.class);

        borehole_iid = getArguments().getLong(CSKeys.cs_bh_iid,-1L);
        borehole_name = getArguments().getString(CSKeys.cs_bh_name);

        binding.setLifecycleOwner(this);
        binding.setViewModel(mViewModel);

        binding.records.setAdapter(getImageAdapter());
        binding.setLocatetimeListener(v->{
            super.startLT(getActivity());
        });
        binding.setLocateListener(v->{
            Bh_geo_date bhGeoDate = mViewModel.server_geo_date.getValue();
            super.goToMap(new LatLng(bhGeoDate.getLat(),bhGeoDate.getLongi()),borehole_name);
        });
        binding.setAddListener((v)->{
            takePhoto();
        });

        onViewModel();
        mViewModel.borehole.setValue(borehole_iid);
    }

    @Override
    public void deletePhoto(Bh_imagesinfo bh_imagesinfo) {
        mViewModel.deleteImage.setValue(bh_imagesinfo);
    }

    @Override
    public void addPhoto(Uri fileUri) {
        mViewModel.addUri.setValue(fileUri);
    }

    @Override
    public void changePhoto(Bh_imagesinfo bh_imagesinfo, Uri uri) {
        bh_imagesinfo.setUrl(uri.toString());
        mViewModel.changeImage.setValue(bh_imagesinfo);
    }

    @Override
    public void savePhoto(Bh_imagesinfo bh_imagesinfo) {
        mViewModel.saveImage.setValue(bh_imagesinfo);
    }

    @Override
    public void onSignInSuccess(double lng, double lat, String time, String info) {
        super.onSignInSuccess(lng, lat, time, info);
        mViewModel.locateAndTime(lng,lat,time);
    }

    private void onViewModel(){
        mViewModel.extra_infoLiveData.observe(this,bh_extra_info -> {
            if(bh_extra_info != null){
                super.setBorehole(new LatLng(bh_extra_info.getLat(),bh_extra_info.get_long()));
            }
        });
        mViewModel.server_imageInfos.observe(this,
                bh_imagesinfos -> {
                    super.submitPhotos(bh_imagesinfos);
                });
        mViewModel.server_geo_date.observe(this,bh_geo_date -> {

        });
        mViewModel.addImageResult.observe(this,bh_imagesinfo -> {
            super.onPhotoAdded(bh_imagesinfo);
        });
        mViewModel.saveImageResult.observe(this,bh_imagesinfo ->
                super.onPhotoSaved(bh_imagesinfo));
        mViewModel.changeImagesinfoLiveData.observe(this,
                bh_imagesinfo -> super.onPhotoChanged(bh_imagesinfo));
        mViewModel.deleteImageResult.observe(this,bh_imagesinfo -> {
            if(bh_imagesinfo == null)
                super.onPhotoDeleted();
        });
    }
}
