package com.szbm.wh.x.bmlogg.ui.ui.bh;

import com.szbm.wh.x.bmlogg.pojo.BoreholeAndExtra;
import com.szbm.wh.x.bmlogg.pojo.Resource;
import com.szbm.wh.x.bmlogg.repository.BoreholeSetRepository;
import com.szbm.wh.x.bmlogg.repository.ExtraRepository;
import com.szbm.wh.x.bmlogg.util.AbsentLiveData;
import com.szbm.wh.x.bmlogg.vo.BoreholeSet;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class BhViewModel extends ViewModel {

    @Inject
    ExtraRepository extraRepository;

    @Inject
    BoreholeSetRepository boreholeSetRepository;

    @Inject
    public BhViewModel(){

    }

    public final MutableLiveData<Long> index = new MutableLiveData<>();

    public final LiveData<BoreholeSet> boreholeSetLiveData =
            Transformations.switchMap(index,input ->
                boreholeSetRepository.loadBoreholeSetFromDisk(input));

    public final LiveData<BoreholeAndExtra> extraLiveData =
            Transformations.switchMap(boreholeSetLiveData,
                    boreholeSet->{
                        if(boreholeSet != null)
                            return extraRepository.loadBoreholeExtraFromDisk(boreholeSet.getIid());
                        else
                            return AbsentLiveData.Companion.create();
                    }
                );

    public final LiveData<String> beginTimeLiveData = Transformations.switchMap(boreholeSetLiveData,
            input -> {
                if(input != null){
                    return extraRepository.loadBeginTimeFromDisk(input.getBegin_iid());
                }
                else{
                    return AbsentLiveData.Companion.create();
                }
            });

    public final LiveData<String> endTimeLiveData = Transformations.switchMap(boreholeSetLiveData,
            input -> {
                if(input != null){
                    return extraRepository.loadEndTimeFromDisk(input.getEnd_iid());
                }
                else{
                    return AbsentLiveData.Companion.create();
                }
            });

    public final MutableLiveData<Long> invoker = new MutableLiveData<>();

    public final LiveData<Resource<BoreholeSet>> resourceLiveData = Transformations.switchMap(invoker,
            input->boreholeSetRepository.loadFromNetWork(input)
    );
}
