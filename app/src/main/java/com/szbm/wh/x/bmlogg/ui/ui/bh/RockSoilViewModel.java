package com.szbm.wh.x.bmlogg.ui.ui.bh;

import com.szbm.wh.x.bmlogg.binding.Converter;
import com.szbm.wh.x.bmlogg.pojo.LoggingListItem;
import com.szbm.wh.x.bmlogg.repository.BoreholeSetRepository;
import com.szbm.wh.x.bmlogg.repository.RockSoilRepository;
import com.szbm.wh.x.bmlogg.util.AbsentLiveData;
import com.szbm.wh.x.bmlogg.vo.BH_CoreCatalog;
import com.szbm.wh.x.bmlogg.vo.BoreholeSet;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class RockSoilViewModel extends ViewModel {

    @Inject
    BoreholeSetRepository boreholeSetRepository;

    @Inject
    RockSoilRepository rockSoilRepository;
    @Inject
    public RockSoilViewModel(){

    }

    final MutableLiveData<Long> borehole = new MutableLiveData<>();
    public final LiveData<BoreholeSet> boreholeSetLiveData = Transformations.switchMap(borehole, bh->
            boreholeSetRepository.loadBoreholeSetFromDisk(bh)
    );

    public final LiveData<List<BH_CoreCatalog>> coreCatalogLiveDatas = Transformations.switchMap(boreholeSetLiveData,bs->
        rockSoilRepository.loadCoreCatalogsFromDisk(bs.getIid())
    );

    public final MediatorLiveData<BH_CoreCatalog> deleteCore = new MediatorLiveData<>();

    public void delete(LoggingListItem loggingListItem){
        rockSoilRepository.deleteCoreFromDisk(loggingListItem.getIid());
        LiveData<BH_CoreCatalog> dbSource = rockSoilRepository.loadCoreFromDisk(loggingListItem.getIid());
        deleteCore.addSource(dbSource,bh_coreCatalog -> {
            deleteCore.removeSource(dbSource);
            deleteCore.setValue(bh_coreCatalog);
        });
    }

    public final MediatorLiveData<BH_CoreCatalog> addCore = new MediatorLiveData<>();
    public void add(LoggingListItem last){
        rockSoilRepository.deleteCoreFromDisk(last.getIid());
        LiveData<BH_CoreCatalog> dbSource = rockSoilRepository.
                loadCoreFromDisk(last.getIid());
        deleteCore.addSource(dbSource,bh_coreCatalog -> {
            deleteCore.removeSource(dbSource);
            deleteCore.setValue(bh_coreCatalog);
        });
    }

}
