package com.szbm.wh.x.bmlogg.repository;

import com.szbm.wh.x.bmlogg.db.BmLoggDb;
import com.szbm.wh.x.bmlogg.db.dao.BH_BoreholeInfoDao;
import com.szbm.wh.x.bmlogg.vo.BH_BoreholeInfo;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;

public class BoreholesRepos {

    @Inject
    BmLoggDb bmLoggDb;
    @Inject
    public BoreholesRepos(){

    }

    public LiveData<List<BH_BoreholeInfo>> load(){
        return bmLoggDb.bh_BoreholeInfoDao().selectAll();
    }

    public DataSource.Factory<Integer, BH_BoreholeInfo> getFactory(){
        return bmLoggDb.bh_BoreholeInfoDao().factoryAll();
    }


}
