package com.szbm.wh.x.bmlogg.worker;

import com.szbm.wh.x.bmlogg.api.BmloggApi;
import com.szbm.wh.x.bmlogg.db.BmLoggDb;
import com.szbm.wh.x.bmlogg.pojo.Re_tsext_spt_info;
import com.szbm.wh.x.bmlogg.vo.BH_BoreholeInfo;
import com.szbm.wh.x.bmlogg.vo.BH_CoreCatalog;
import com.szbm.wh.x.bmlogg.vo.BH_Logger;
import com.szbm.wh.x.bmlogg.vo.BoreholeSet;
import com.szbm.wh.x.bmlogg.vo.Re_BH_BoreholeInfo;
import com.szbm.wh.x.bmlogg.vo.Re_BH_CoreCatalog;
import com.szbm.wh.x.bmlogg.vo.Re_SampleRecord;
import com.szbm.wh.x.bmlogg.vo.Re_bh_begin_info;
import com.szbm.wh.x.bmlogg.vo.Re_bh_end_info;
import com.szbm.wh.x.bmlogg.vo.Re_bh_extra_info;
import com.szbm.wh.x.bmlogg.vo.Re_ts_sptdata;
import com.szbm.wh.x.bmlogg.vo.Re_ts_sptinfo;
import com.szbm.wh.x.bmlogg.vo.SampleRecord;
import com.szbm.wh.x.bmlogg.vo.Bh_begin_info;
import com.szbm.wh.x.bmlogg.vo.Bh_end_info;
import com.szbm.wh.x.bmlogg.vo.Bh_extra_info;
import com.szbm.wh.x.bmlogg.vo.Bh_geo_date;
import com.szbm.wh.x.bmlogg.vo.Bh_imagesinfo;
import com.szbm.wh.x.bmlogg.vo.Bhext_rocksoil_info;
import com.szbm.wh.x.bmlogg.vo.Bhext_sampling_info;
import com.szbm.wh.x.bmlogg.vo.Ts_sptdata;
import com.szbm.wh.x.bmlogg.vo.Ts_sptinfo;
import com.szbm.wh.x.bmlogg.vo.Tsext_spt_info;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class BoreholeDownLoader {

    public void fetchBorehole(BmLoggDb bmLoggDb,long iid)throws Exception
    {
        Call<Re_BH_BoreholeInfo> infoCall = BmloggApi
                .getInstance()
                .getBmloggService()
                .getBoreholeWorker(iid);

        Response<Re_BH_BoreholeInfo> response = infoCall.execute();

        if (response.isSuccessful()) {
            Re_BH_BoreholeInfo body = response.body();
            if (body == null || response.code() == 204)
            {
                return ;
            }
            else{
                try{
                    bmLoggDb.beginTransaction();
                    this.insert(bmLoggDb,body);
                    bmLoggDb.setTransactionSuccessful();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                finally {
                    bmLoggDb.endTransaction();
                }
            }
        } else {
            throw new Exception("下载钻孔失败："+iid);
        }
    }

    //    val iid :Int,
//    val extra_iid:Int,
//    var begin_iid:Int,
//    var end_iid:Int,
//    var rock_soils:List<Int>,//-BH_CoreCatalog表单
//    var rsCount:Int,
//    var samplings:List<Int>,//-Sample表单
//    var sCount:Int,
//    var spts:List<Int>,//sptinfo 表单
//    var sptCount :Int
    private void insert(BmLoggDb bmLoggDb,Re_BH_BoreholeInfo body)
    {
        BH_BoreholeInfo bh_boreholeInfo = Re_BH_BoreholeInfo.Companion.convertTo(body);
        bmLoggDb.bh_BoreholeInfoDao().insert(bh_boreholeInfo);
        long iid = bh_boreholeInfo.getIid();

        if(body.getSec_linebh() != null)
            bmLoggDb.sec_linebhDao().inserts(body.getSec_linebh());

        List<Long> catalogs =insertBH_Catalog(bmLoggDb,body.getBH_CoreCatalog());

        List<Long> samples = insertSampleRecord(bmLoggDb,body.getBH_SampleRecord());
        ExtraExt extras = insertExtra(bmLoggDb,body.getBh_extra_info());
        List<Long> spts =insertSpt(bmLoggDb,body.getTs_sptinfo());

        BoreholeSet boreholeSet = new BoreholeSet(
                iid,
                extras.extra_iid,
                extras.begin_iid,
                extras.end_iid,
                catalogs,
                catalogs.size(),
                samples,
                samples.size(),
                spts,
                spts.size()
        );

        //记录钻孔及其子关系
        bmLoggDb.boreholesSetDao().insert(boreholeSet);
    }

    private List<Long> insertBH_Catalog(BmLoggDb bmLoggDb,
                                        List<Re_BH_CoreCatalog> re_bh_coreCatalogs){
        if(re_bh_coreCatalogs == null) return new ArrayList<>();
        List<Long> ids = new ArrayList<>(re_bh_coreCatalogs.size());

        List<BH_CoreCatalog> bh_coreCatalogs = new ArrayList<>(re_bh_coreCatalogs.size());
        List<Bhext_rocksoil_info> bhext_rocksoil_infos = new ArrayList<>();
        for (Re_BH_CoreCatalog catalog:re_bh_coreCatalogs
                ) {
            BH_CoreCatalog bh_coreCatalog = Re_BH_CoreCatalog.Companion.convertTo(catalog);
            bh_coreCatalogs.add(bh_coreCatalog);
            ids.add(bh_coreCatalog.getIid());
            if(catalog.getBhext_rocksoil_info() != null)
                bhext_rocksoil_infos.addAll(catalog.getBhext_rocksoil_info());
        }
        bmLoggDb.bh_CoreCatalogDao().inserts(bh_coreCatalogs);
        bmLoggDb.bhext_rocksoil_infoDao().inserts(bhext_rocksoil_infos);
        return ids;
    }

    private List<Long> insertSampleRecord(BmLoggDb bmLoggDb,
                                          List<Re_SampleRecord> re_sampleRecords){
        if(re_sampleRecords == null) return new ArrayList<>();
        List<SampleRecord> sampleRecords = new ArrayList<>(re_sampleRecords.size());
        List<Long> ids = new ArrayList<>(re_sampleRecords.size());

        List<Bhext_sampling_info> bhext_sampling_infos = new ArrayList<>();
        for (Re_SampleRecord sampleRecord:re_sampleRecords
                ) {
            SampleRecord sa = Re_SampleRecord.Companion.convertTo(sampleRecord);
            sampleRecords.add(sa);
            ids.add(sa.getIid());
            if(sampleRecord.getBhext_sampling_info() != null)
                bhext_sampling_infos.addAll(sampleRecord.getBhext_sampling_info());
        }
        bmLoggDb.sampleRecordDao().inserts(sampleRecords);
        bmLoggDb.bhext_sampling_infoDao().inserts(bhext_sampling_infos);

        return ids;
    }

    private ExtraExt insertExtra(BmLoggDb bmLoggDb, List<Re_bh_extra_info> re_bh_extra_infos){
        if(re_bh_extra_infos == null) return new ExtraExt();

        List<Bh_extra_info> bh_extra_infos = new ArrayList<>(re_bh_extra_infos.size());
        List<Bh_begin_info> bh_begin_infos = new ArrayList<>();
        List<Bh_end_info> bh_end_infos = new ArrayList<>();
        List<BH_Logger> bh_loggers = new ArrayList<>();
        List<Bh_geo_date> bh_geo_dates = new ArrayList<>();
        List<Bh_imagesinfo> bh_imagesinfos = new ArrayList<>();

        ExtraExt extraExt = new ExtraExt();
        extraExt.extra_iid = -1;
        extraExt.begin_iid = -1;
        extraExt.end_iid = -1;
        for (Re_bh_extra_info re_bh_extra_info : re_bh_extra_infos){
            Bh_extra_info extra = Re_bh_extra_info.Companion.converTo(re_bh_extra_info);
            bh_extra_infos.add(extra);
            extraExt.extra_iid = extra.getIid();

            if(re_bh_extra_info.getBh_begin_info() != null){
                for (Re_bh_begin_info re_begin: re_bh_extra_info.getBh_begin_info()
                        ) {
                    Bh_begin_info bh_begin_info = Re_bh_begin_info.Companion.converTo(re_begin);
                    bh_begin_infos.add(bh_begin_info);
                    extraExt.begin_iid=(bh_begin_info.getIid());
                    bh_loggers.add(re_begin.getBh_logger());
                }
            }

            if(re_bh_extra_info.getBh_end_info() != null){
                for (Re_bh_end_info re_end: re_bh_extra_info.getBh_end_info()
                        ) {
                    Bh_end_info bh_end_info = Re_bh_end_info.Companion.converTo(re_end);
                    bh_end_infos.add(bh_end_info);
                    extraExt.end_iid = (bh_end_info.getIid());
                    bh_loggers.add(re_end.getBh_logger());
                }
            }
            if(re_bh_extra_info.getBh_geo_date() != null){
                bh_geo_dates.addAll(re_bh_extra_info.getBh_geo_date());
            }
            if(re_bh_extra_info.getBh_imagesinfo() != null){
                bh_imagesinfos.addAll(re_bh_extra_info.getBh_imagesinfo());
            }
        }

        bmLoggDb.loggerDao().inserts(bh_loggers);
        bmLoggDb.bh_extra_infoDao().inserts(bh_extra_infos);
        bmLoggDb.bh_begin_infoDao().inserts(bh_begin_infos);
        bmLoggDb.bh_end_infoDao().inserts(bh_end_infos);
        bmLoggDb.bh_geo_dateDao().inserts(bh_geo_dates);
        bmLoggDb.bh_imagesinfoDao().inserts(bh_imagesinfos);

        return extraExt;
    }

    private List<Long> insertSpt(BmLoggDb bmLoggDb, List<Re_ts_sptinfo> re_ts_sptinfos){
        if(re_ts_sptinfos == null) return new ArrayList<>();
        List<Ts_sptinfo> ts_sptinfos = new ArrayList<>(re_ts_sptinfos.size());
        List<Long> ids = new ArrayList<>(re_ts_sptinfos.size());

        List<Ts_sptdata> ts_sptdataList = new ArrayList<>();
        List<Tsext_spt_info> tsext_spt_infos = new ArrayList<>();
        List<BH_Logger> bh_loggers = new ArrayList<>();

        for (Re_ts_sptinfo re_ts_sptinfo :re_ts_sptinfos){
            Ts_sptinfo ts_sptinfo = Re_ts_sptinfo.Companion.converTo(re_ts_sptinfo);
            ts_sptinfos.add(ts_sptinfo);
            ids.add(ts_sptinfo.getIid());

            if(re_ts_sptinfo.getTs_sptdata() != null)
            {
                for(Re_ts_sptdata re_ts_sptdata : re_ts_sptinfo.getTs_sptdata()){
                    if(re_ts_sptdata.getTsext_spt_info() != null){
                        for(Re_tsext_spt_info re_tsext_spt_info : re_ts_sptdata.getTsext_spt_info()){
                            bh_loggers.add(re_tsext_spt_info.getBh_logger());
                            tsext_spt_infos.add(Re_tsext_spt_info.Companion.converTo(re_tsext_spt_info));
                        }
                    }
                    ts_sptdataList.add(Re_ts_sptdata.Companion.converTo(re_ts_sptdata));
                }
            }
        }

        bmLoggDb.ts_sptinfoDao().inserts(ts_sptinfos);
        bmLoggDb.ts_sptdataDao().inserts(ts_sptdataList);
        bmLoggDb.loggerDao().inserts(bh_loggers);
        bmLoggDb.tsext_spt_infoDao().inserts(tsext_spt_infos);
        return ids;
    }

    class ExtraExt{
        public long extra_iid;
        public long begin_iid;
        public long end_iid;
    }
}
