package com.szbm.wh.x.bmlogg.Strategy;

import com.szbm.wh.x.bmlogg.db.BmLoggDb;

/*
简单粗暴的安全索引策略
 */
public class Strategy {
    private static Strategy sInstance;
    private Strategy strategy;
    private static final Object sLock = new Object();
    private long start = 1000000;
    public static Strategy getInstance() {
        synchronized (sLock) {
            if (sInstance == null) {
                sInstance = new Strategy();
            }
        }
        return sInstance;
    }

    private Strategy() {

    }

    public long genBoreholeIid(BmLoggDb bmLoggDb){
       long max = bmLoggDb.bh_BoreholeInfoDao().selectMaxWithoutLife();
       if(max < start)
           return start;
       else
           return max +1;
    }
    public long genExtraIid(BmLoggDb bmLoggDb){
        long max = bmLoggDb.bh_extra_infoDao().selectMaxWithoutLife();
        if(max < start)
            return start;
        else
            return max +1;
    }
    public long genBeginIid(BmLoggDb bmLoggDb){
        long max = bmLoggDb.bh_begin_infoDao().selectMaxWithoutLife();
        if(max < start)
            return start;
        else
            return max +1;
    }
    public long genEndIid(BmLoggDb bmLoggDb){
        long max = bmLoggDb.bh_end_infoDao().selectMaxWithoutLife();
        if(max < start)
            return start;
        else
            return max +1;
    }
    public long genCoreCatalogIid(BmLoggDb bmLoggDb){
        long max = bmLoggDb.bh_CoreCatalogDao().selectMaxWithoutLife();
        if(max < start)
            return start;
        else
            return max +1;
    }
    public long genRockSoilIid(BmLoggDb bmLoggDb){
        long max = bmLoggDb.bhext_rocksoil_infoDao().selectMaxWithoutLife();
        if(max < start)
            return start;
        else
            return max +1;
    }
    public long genSampleIid(BmLoggDb bmLoggDb){
        long max = bmLoggDb.sampleRecordDao().selectMaxWithoutLife();
        if(max < start)
            return start;
        else
            return max +1;
    }
    public long genExtSampleIid(BmLoggDb bmLoggDb){
        long max = bmLoggDb.bhext_sampling_infoDao().selectMaxWithoutLife();
        if(max < start)
            return start;
        else
            return max +1;
    }
    public long genSptIid(BmLoggDb bmLoggDb){
        long max = bmLoggDb.ts_sptinfoDao().selectMaxWithoutLife();
        if(max < start)
            return start;
        else
            return max +1;
    }
    public long genSptDataIid(BmLoggDb bmLoggDb){
        long max = bmLoggDb.ts_sptdataDao().selectMaxWithoutLife();
        if(max < start)
            return start;
        else
            return max +1;
    }
    public long genExtSptIid(BmLoggDb bmLoggDb){
        long max = bmLoggDb.tsext_spt_infoDao().selectMaxWithoutLife();
        if(max < start)
            return start;
        else
            return max +1;
    }
}
