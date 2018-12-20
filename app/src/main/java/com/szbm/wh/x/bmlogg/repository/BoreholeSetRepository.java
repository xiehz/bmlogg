package com.szbm.wh.x.bmlogg.repository;

import com.szbm.wh.x.bmlogg.BmloggExecutors;
import com.szbm.wh.x.bmlogg.BmloggSharedPreference;
import com.szbm.wh.x.bmlogg.api.BmloggService;
import com.szbm.wh.x.bmlogg.db.BmLoggDb;
import com.szbm.wh.x.bmlogg.pojo.Resource;
import com.szbm.wh.x.bmlogg.vo.Bh_end_info;
import com.szbm.wh.x.bmlogg.vo.Bh_extra_info;
import com.szbm.wh.x.bmlogg.vo.BoreholeSet;
import com.szbm.wh.x.bmlogg.worker.BoreholeDownLoader;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

public class BoreholeSetRepository {

    @Inject
    BmLoggDb bmLoggDb;

    @Inject
    BmloggService bmloggService;

    @Inject
    BmloggSharedPreference bmloggSharedPreference;

    @Inject
    BmloggExecutors bmloggExecutors;
    @Inject
    public BoreholeSetRepository(){

    }

    public LiveData<BoreholeSet> loadBoreholeSetFromDisk(long iid){
        return bmLoggDb.boreholesSetDao().select(iid);
    }
    public LiveData<Bh_extra_info> loadExtraInfoFromDisk(long iid){
        return bmLoggDb.bh_extra_infoDao().select(iid);
    }

    public MediatorLiveData<Resource<BoreholeSet>> loadFromNetWork(long iid) {
        MediatorLiveData<Resource<BoreholeSet>> result = new MediatorLiveData<>();
        result.setValue(Resource.Companion.loading(null));
        bmloggExecutors.diskIO().execute(()->{

            try {
                BoreholeDownLoader loader = new BoreholeDownLoader();
                loader.fetchBorehole(bmLoggDb,iid);
                bmloggExecutors.mainThread().execute(()->{
                    LiveData<BoreholeSet> boreholeSetLiveData =
                            bmLoggDb.boreholesSetDao().select(iid);
                    result.addSource(boreholeSetLiveData,boreholeSet -> {
                        result.removeSource(boreholeSetLiveData);
                        result.setValue(Resource.Companion.success(boreholeSet));
                    });
                });
            }
            catch (Exception e){
                bmloggExecutors.mainThread().execute(()->{
                    LiveData<BoreholeSet> boreholeSetLiveData =
                            bmLoggDb.boreholesSetDao().select(iid);
                    result.addSource(boreholeSetLiveData,boreholeSet -> {
                        result.removeSource(boreholeSetLiveData);
                        result.setValue(Resource.Companion.error(e.getMessage(),boreholeSet));
                    });
                });
            }
        });

        return result;
    }

    class BoreholeBoundResource {
        int iid ;
        public BoreholeBoundResource(int iid) {
            this.iid = iid;
        }


    }
}
