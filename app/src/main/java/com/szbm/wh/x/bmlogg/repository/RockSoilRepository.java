package com.szbm.wh.x.bmlogg.repository;

import com.szbm.wh.x.bmlogg.BmloggExecutors;
import com.szbm.wh.x.bmlogg.Strategy.Strategy;
import com.szbm.wh.x.bmlogg.db.BmLoggDb;
import com.szbm.wh.x.bmlogg.page.PagingRequestHelper;
import com.szbm.wh.x.bmlogg.ui.ui.bh.RockSoilViewModel;
import com.szbm.wh.x.bmlogg.vo.BH_CoreCatalog;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import retrofit2.http.PUT;

public class RockSoilRepository {
    @Inject
    BmloggExecutors bmloggExecutors;

    @Inject
    BmLoggDb bmLoggDb;

    @Inject
    public RockSoilRepository(){

    }

    public LiveData<List<BH_CoreCatalog>> loadCoreCatalogsFromDisk(long borehole){
        return bmLoggDb.bh_CoreCatalogDao().selectByBorehole(borehole);
    }
    public LiveData<BH_CoreCatalog> loadCoreFromDisk(long iid){
        return bmLoggDb.bh_CoreCatalogDao().select(iid);
    }
    public void deleteCoreFromDisk(long iid){
        bmLoggDb.bh_CoreCatalogDao().delete(iid);
    }
    public LiveData<BH_CoreCatalog> addCore(long last,long borehole,float step){
        MediatorLiveData<BH_CoreCatalog> result = new MediatorLiveData<>();

        bmloggExecutors.diskIO().execute(()->{
            long iid = Strategy.getInstance().genCoreCatalogIid(bmLoggDb);
            BH_CoreCatalog lastCore =bmLoggDb.bh_CoreCatalogDao().selectWithoutLive(last);
            if(lastCore != null){

            }
//            BH_CoreCatalog newer = new BH_CoreCatalog(
//                    iid,
//
//                    )
            if(last == -1){
                BH_CoreCatalog coreCatalog = new BH_CoreCatalog(
                        iid,
                        borehole,
                        step,
                        step,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                );

                bmLoggDb.bh_CoreCatalogDao().insert(coreCatalog);
            }else{

            }
        });

        return result;
    }
}
