package com.szbm.wh.x.bmlogg.repository;

import com.szbm.wh.x.bmlogg.BmloggExecutors;
import com.szbm.wh.x.bmlogg.BmloggSharedPreference;
import com.szbm.wh.x.bmlogg.Strategy.Strategy;
import com.szbm.wh.x.bmlogg.api.BmloggService;
import com.szbm.wh.x.bmlogg.db.BmLoggDb;
import com.szbm.wh.x.bmlogg.pojo.BoreholeAndExtra;
import com.szbm.wh.x.bmlogg.vo.Bh_begin_info;
import com.szbm.wh.x.bmlogg.vo.Bh_end_info;
import com.szbm.wh.x.bmlogg.vo.Bh_geo_date;
import com.szbm.wh.x.bmlogg.vo.Bh_imagesinfo;
import com.szbm.wh.x.bmlogg.vo.BoreholeSet;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

public class ExtraRepository {
    @Inject
    BmLoggDb bmLoggDb;

    @Inject
    BmloggService bmloggService;

    @Inject
    BmloggSharedPreference bmloggSharedPreference;

    @Inject
    BmloggExecutors bmloggExecutors;

    @Inject
    public ExtraRepository(){

    }

    public LiveData<BoreholeAndExtra> loadBoreholeExtraFromDisk(long iid){
        return bmLoggDb
                .bh_BoreholeInfoDao()
                .boreholesByIID(iid);

    }

    public LiveData<String> loadBeginTimeFromDisk(long begin_iid){
        return bmLoggDb.bh_begin_infoDao().selectTime(begin_iid);
    }

    public LiveData<String> loadEndTimeFromDisk(long end_iid){
        return bmLoggDb.bh_end_infoDao().selectTime(end_iid);
    }

    public LiveData<Bh_begin_info> loadbh_begin_infoFromDisk(BoreholeSet boreholeSet,long number){
        if(boreholeSet.getBegin_iid() != -1){
            return bmLoggDb.bh_begin_infoDao().select(boreholeSet.getBegin_iid());
        }
        else {
            MediatorLiveData<Bh_begin_info> result = new MediatorLiveData<>();

            bmloggExecutors.diskIO().execute(()->{
                try{
                    long iid = Strategy.getInstance().genBeginIid(bmLoggDb);
                    Bh_begin_info bh_begin_info = new Bh_begin_info(iid,boreholeSet.getExtra_iid(),number,null);
                    bmLoggDb.beginTransaction();
                    bmLoggDb.bh_begin_infoDao().insert(bh_begin_info);
                    boreholeSet.setBegin_iid(bh_begin_info.getIid());
                    bmLoggDb.boreholesSetDao().insert(boreholeSet);
                    bmLoggDb.setTransactionSuccessful();
                }catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    bmLoggDb.endTransaction();
                    bmloggExecutors.mainThread().execute(()->{
                        LiveData<Bh_begin_info> dbsource = bmLoggDb.bh_begin_infoDao().select(boreholeSet.getBegin_iid());
                        result.addSource(dbsource,begin->{
                            result.removeSource(dbsource);
                            result.setValue(begin);
                        });
                    });
                }
            });

            return result;
        }
    }

    public LiveData<Bh_end_info> loadbh_end_infoFromDisk(BoreholeSet boreholeSet, long number){
        if(boreholeSet.getEnd_iid() != -1){
            return bmLoggDb.bh_end_infoDao().select(boreholeSet.getEnd_iid());
        }
        else {
            MediatorLiveData<Bh_end_info> result = new MediatorLiveData<>();

            bmloggExecutors.diskIO().execute(()->{
                try{
                    long iid = Strategy.getInstance().genEndIid(bmLoggDb);
                    Bh_end_info bh_end_info = new Bh_end_info(iid,boreholeSet.getExtra_iid(),number,null);
                    bmLoggDb.beginTransaction();
                    bmLoggDb.bh_end_infoDao().insert(bh_end_info);
                    boreholeSet.setEnd_iid(bh_end_info.getIid());
                    bmLoggDb.boreholesSetDao().insert(boreholeSet);
                    bmLoggDb.setTransactionSuccessful();
                }catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    bmLoggDb.endTransaction();
                    bmloggExecutors.mainThread().execute(()->{
                        LiveData<Bh_end_info> dbsource = bmLoggDb.bh_end_infoDao().select(boreholeSet.getEnd_iid());
                        result.addSource(dbsource,end->{
                            result.removeSource(dbsource);
                            result.setValue(end);
                        });
                    });
                }
            });

            return result;
        }
    }

    public LiveData<List<Bh_imagesinfo>> getImageinfos(long oft, String type){
        MediatorLiveData<List<Bh_imagesinfo>> result = new MediatorLiveData<>();
        LiveData<List<Bh_imagesinfo>> dbSource = bmLoggDb
                .bh_imagesinfoDao()
                .selectImages(oft,type);
        result.addSource(dbSource, list->{
            result.removeSource(dbSource);
            result.setValue(list);
        });
        return result;
    }


        public LiveData<Bh_geo_date> getbh_geo_date(long oft, String type){
        return bmLoggDb.bh_geo_dateDao().selectByBegin(oft,type);
    }

    public LiveData<Bh_imagesinfo> addBh_ImagesInfo(Bh_imagesinfo bh_imagesinfo){
        MediatorLiveData<Bh_imagesinfo> result = new MediatorLiveData<>();
        bmloggExecutors.diskIO().execute(()->{
                long iid = bmLoggDb.bh_imagesinfoDao().insert(bh_imagesinfo);
                bmloggExecutors.mainThread().execute(()->{
                    LiveData<Bh_imagesinfo> dbSource = bmLoggDb.bh_imagesinfoDao().select(iid);
                    result.addSource(dbSource,image->{
                        result.removeSource(dbSource);
                        result.setValue(image);
                    });
                });
        });
        return result;
    }

    public LiveData<Bh_imagesinfo> deleteImage(Bh_imagesinfo bh_imagesinfo){
        MediatorLiveData<Bh_imagesinfo> result = new MediatorLiveData<>();
        bmloggExecutors.diskIO().execute(()->{
            bmLoggDb.bh_imagesinfoDao().delete(bh_imagesinfo.getIid());
            bmloggExecutors.mainThread().execute(()->{
                LiveData<Bh_imagesinfo> dbSource = bmLoggDb.bh_imagesinfoDao().select(bh_imagesinfo.getIid());
                result.addSource(dbSource,image->{
                    result.removeSource(dbSource);
                    result.setValue(image);
                });
            });
        });
        return result;
    }

    public void insertGeoDate(Bh_geo_date bh_geo_date){
        bmloggExecutors.diskIO().execute(()->{
           bmLoggDb.bh_geo_dateDao().insert(bh_geo_date);
        });
    }
}
