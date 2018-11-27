package com.szbm.wh.x.bmlogg.worker;

import android.content.Context;

import com.szbm.wh.x.bmlogg.api.BmloggApi;
import com.szbm.wh.x.bmlogg.db.BmLoggDb;
import com.szbm.wh.x.bmlogg.pojo.Re_tsext_spt_info;
import com.szbm.wh.x.bmlogg.vo.BH_BoreholeInfo;
import com.szbm.wh.x.bmlogg.vo.BH_CoreCatalog;
import com.szbm.wh.x.bmlogg.vo.BH_Logger;
import com.szbm.wh.x.bmlogg.vo.Re_BH_BoreholeInfo;
import com.szbm.wh.x.bmlogg.vo.Re_BH_CoreCatalog;
import com.szbm.wh.x.bmlogg.vo.Re_SampleRecord;
import com.szbm.wh.x.bmlogg.vo.Re_bh_begin_info;
import com.szbm.wh.x.bmlogg.vo.Re_bh_end_info;
import com.szbm.wh.x.bmlogg.vo.Re_bh_extra_info;
import com.szbm.wh.x.bmlogg.vo.Re_ts_sptdata;
import com.szbm.wh.x.bmlogg.vo.Re_ts_sptinfo;
import com.szbm.wh.x.bmlogg.vo.SampleRecord;
import com.szbm.wh.x.bmlogg.vo.bh_begin_info;
import com.szbm.wh.x.bmlogg.vo.bh_end_info;
import com.szbm.wh.x.bmlogg.vo.bh_extra_info;
import com.szbm.wh.x.bmlogg.vo.bhext_rocksoil_info;
import com.szbm.wh.x.bmlogg.vo.bhext_sampling_info;
import com.szbm.wh.x.bmlogg.vo.ts_sptdata;
import com.szbm.wh.x.bmlogg.vo.ts_sptinfo;
import com.szbm.wh.x.bmlogg.vo.tsext_spt_info;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import retrofit2.Call;
import retrofit2.Response;
/*
暂时不考虑效率问题
 */
public class BoreholeWorker extends Worker {
    public BoreholeWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Data args = getInputData();
        int project = args.getInt(Constants.DATA_KEY,-1);

        try {
            List<Integer> integerList = fetchIndices(project);
            for (Integer index:integerList
                 ) {
                fetchBorehole(index);
            }
        }catch (Exception ie)
        {
            setOutputData(new Data.Builder().putString(Constants.PROJECT_DATA_OUTPUT,ie.getMessage()).build());
            return Result.FAILURE;
        }
        return Result.SUCCESS;
    }

    private List<Integer> fetchIndices(int projectid) throws IOException
    {
        Call<List<Integer>> infoCall = BmloggApi
                .getInstance()
                .getBmloggService()
                .getBoreholeIndices(projectid);

        Response<List<Integer>> response = infoCall.execute();

        if (response.isSuccessful()) {
            List<Integer> body = response.body();
            if (body == null || response.code() == 204)
            {
                return new ArrayList<>();
            }
            else{
                return body;
            }
        } else {
            throw new IOException("获取钻孔失败！");
        }
    }

    private void fetchBorehole(int iid)throws Exception
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
                    BmLoggDb bmLoggDb = BmLoggDb.Companion.getInstance(getApplicationContext());
                    bmLoggDb.beginTransaction();
                    this.insert(bmLoggDb,body);
                    bmLoggDb.setTransactionSuccessful();
                    bmLoggDb.endTransaction();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        } else {
            throw new Exception("下载钻孔失败："+iid);
        }
    }

    private void insert(BmLoggDb bmLoggDb,Re_BH_BoreholeInfo body)
    {
        BH_BoreholeInfo bh_boreholeInfo = Re_BH_BoreholeInfo.Companion.convertTo(body);
        bmLoggDb.bh_BoreholeInfoDao().insert(bh_boreholeInfo);
        if(body.getSec_linebh() != null)
        bmLoggDb.sec_linebhDao().inserts(body.getSec_linebh());

        insertBH_Catalog(bmLoggDb,body.getBH_CoreCatalog());
        insertSampleRecord(bmLoggDb,body.getBH_SampleRecord());
        insertExtra(bmLoggDb,body.getBh_extra_info());
        insertSpt(bmLoggDb,body.getTs_sptinfo());
    }

    private void insertBH_Catalog(BmLoggDb bmLoggDb,
                        List<Re_BH_CoreCatalog> re_bh_coreCatalogs){
        if(re_bh_coreCatalogs == null) return;
        List<BH_CoreCatalog> bh_coreCatalogs = new ArrayList<>(re_bh_coreCatalogs.size());
        List<bhext_rocksoil_info> bhext_rocksoil_infos = new ArrayList<>();
        for (Re_BH_CoreCatalog catalog:re_bh_coreCatalogs
                ) {
            bh_coreCatalogs.add(Re_BH_CoreCatalog.Companion.convertTo(catalog));
            if(catalog.getBhext_rocksoil_info() != null)
                bhext_rocksoil_infos.addAll(catalog.getBhext_rocksoil_info());
        }
        bmLoggDb.bh_CoreCatalogDao().inserts(bh_coreCatalogs);
        bmLoggDb.bhext_rocksoil_infoDao().inserts(bhext_rocksoil_infos);
    }

    private void insertSampleRecord(BmLoggDb bmLoggDb,
                                  List<Re_SampleRecord> re_sampleRecords){
        if(re_sampleRecords == null) return;
        List<SampleRecord> sampleRecords = new ArrayList<>(re_sampleRecords.size());
        List<bhext_sampling_info> bhext_sampling_infos = new ArrayList<>();
        for (Re_SampleRecord sampleRecord:re_sampleRecords
                ) {
            sampleRecords.add(Re_SampleRecord.Companion.convertTo(sampleRecord));
            if(sampleRecord.getBhext_sampling_info() != null)
                bhext_sampling_infos.addAll(sampleRecord.getBhext_sampling_info());
        }
        bmLoggDb.sampleRecordDao().inserts(sampleRecords);
        bmLoggDb.bhext_sampling_infoDao().inserts(bhext_sampling_infos);
    }

    private void insertExtra(BmLoggDb bmLoggDb, List<Re_bh_extra_info> re_bh_extra_infos){
        if(re_bh_extra_infos == null) return;
        List<bh_extra_info> bh_extra_infos = new ArrayList<>(re_bh_extra_infos.size());
        List<bh_begin_info> bh_begin_infos = new ArrayList<>();
        List<bh_end_info> bh_end_infos = new ArrayList<>();
        List<BH_Logger> bh_loggers = new ArrayList<>();

        for (Re_bh_extra_info re_bh_extra_info : re_bh_extra_infos){
            bh_extra_infos.add(Re_bh_extra_info.Companion.converTo(re_bh_extra_info));
            if(re_bh_extra_info.getBh_begin_info() != null){
                for (Re_bh_begin_info re_begin: re_bh_extra_info.getBh_begin_info()
                        ) {
                    bh_begin_infos.add(Re_bh_begin_info.Companion.converTo(re_begin));
                    bh_loggers.add(re_begin.getBh_logger());
                }
            }

            if(re_bh_extra_info.getBh_end_info() != null){
                for (Re_bh_end_info re_end: re_bh_extra_info.getBh_end_info()
                        ) {
                    bh_end_infos.add(Re_bh_end_info.Companion.converTo(re_end));
                    bh_loggers.add(re_end.getBh_logger());
                }
            }
        }

        bmLoggDb.loggerDao().inserts(bh_loggers);
        bmLoggDb.bh_extra_infoDao().inserts(bh_extra_infos);
        bmLoggDb.bh_begin_infoDao().inserts(bh_begin_infos);
        bmLoggDb.bh_end_infoDao().inserts(bh_end_infos);
    }

    private void insertSpt(BmLoggDb bmLoggDb, List<Re_ts_sptinfo> re_ts_sptinfos){
        if(re_ts_sptinfos == null) return;
        List<ts_sptinfo> ts_sptinfos = new ArrayList<>(re_ts_sptinfos.size());
        List<ts_sptdata> ts_sptdataList = new ArrayList<>();
        List<tsext_spt_info> tsext_spt_infos = new ArrayList<>();
        List<BH_Logger> bh_loggers = new ArrayList<>();

        for (Re_ts_sptinfo re_ts_sptinfo :re_ts_sptinfos){
            ts_sptinfos.add(Re_ts_sptinfo.Companion.converTo(re_ts_sptinfo));
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
    }


}
