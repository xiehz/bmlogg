package com.szbm.wh.x.bmlogg.repository;

import com.szbm.wh.x.bmlogg.BmloggExecutors;
import com.szbm.wh.x.bmlogg.BmloggSharedPreference;
import com.szbm.wh.x.bmlogg.api.BmloggService;
import com.szbm.wh.x.bmlogg.db.BmLoggDb;
import com.szbm.wh.x.bmlogg.pojo.BoreholeAndExtra;
import com.szbm.wh.x.bmlogg.pojo.Resource;
import com.szbm.wh.x.bmlogg.vo.BH_BoreholeInfo;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

public class BoreholesRepos {

    @Inject
    BmLoggDb bmLoggDb;

    @Inject
    BmloggService bmloggService;

    @Inject
    BmloggSharedPreference bmloggSharedPreference;

    @Inject
    BmloggExecutors bmloggExecutors;

    @Inject
    public BoreholesRepos(){

    }
    public LiveData<Resource<List<BoreholeAndExtra>>> get(){
        MediatorLiveData<Resource<List<BoreholeAndExtra>>> results =
                new MediatorLiveData<>();
        results.setValue(Resource.Companion.loading(null));

        bmloggExecutors.diskIO().execute(()->{
            try{
                long project_id = bmloggSharedPreference.readCurrentProject();
                List<BoreholeAndExtra> boreholeAndExtras = bmLoggDb.bh_BoreholeInfoDao().listBoreholesByProject(project_id);
                bmloggExecutors.mainThread().execute(()->
                        results.setValue(Resource.Companion.success(boreholeAndExtras)));
            }
            catch (Exception e){
                bmloggExecutors.mainThread().execute(()->results.setValue(Resource.Companion.error(e.getMessage(),null)));
            }

        });

        return results;
    }
    public LiveData<BH_BoreholeInfo> getByIid(long iid){
        return bmLoggDb.bh_BoreholeInfoDao().select(iid);
    }

    public LiveData<Resource<BoreholeAndExtra>> getBorehole(long iid){

        MediatorLiveData<Resource<BoreholeAndExtra>> results =
                new MediatorLiveData<>();
        results.setValue(Resource.Companion.loading(null));

        LiveData<BoreholeAndExtra> dbSource = bmLoggDb
                .bh_BoreholeInfoDao()
                .boreholesByIID(iid);
        results.addSource(dbSource,boreholeAndExtra -> {
            results.removeSource(dbSource);
            if(boreholeAndExtra != null)
            {
                results.setValue(Resource.Companion.success(boreholeAndExtra));
            }else{
                results.setValue(Resource.Companion.error("没有查询到该钻孔信息！",null));
            }
        });

        return results;
    }


}
