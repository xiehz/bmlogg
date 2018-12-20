package com.szbm.wh.x.bmlogg.worker;

import android.content.Context;

import com.szbm.wh.x.bmlogg.api.BmloggApi;
import com.szbm.wh.x.bmlogg.db.BmLoggDb;
import com.szbm.wh.x.bmlogg.vo.ProjectBoreholes;

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
        long project = args.getLong(Constants.DATA_PROJECT_KEY,-1l);
        long number = args.getLong(Constants.DATA_NUMBER_KEY,-1l);

        try {
            List<Long> integerList = fetchIndices(project,number);
            updateProjectBoreholes(project,integerList);
            BoreholeDownLoader loader = new BoreholeDownLoader();
            for (Long index:integerList
                 ) {
                BmLoggDb bmLoggDb = BmLoggDb.Companion.getInstance(getApplicationContext());
                loader.fetchBorehole(bmLoggDb,index);
            }
        }catch (Exception ie)
        {
            setOutputData(new Data.Builder().putString(Constants.PROJECT_DATA_OUTPUT,ie.getMessage()).build());
            return Result.FAILURE;
        }
        return Result.SUCCESS;
    }

    private void updateProjectBoreholes(long project,List<Long> integerList)throws Exception
    {
        //更新项目记录表
        BmLoggDb bmLoggDb = BmLoggDb.Companion.getInstance(getApplicationContext());
        ProjectBoreholes projectBoreholes = bmLoggDb.projectBoreholesDao().selectWithoutLive(project);
        if(projectBoreholes == null){
            throw new Exception("更新项目记录表失败！");
        }
        else{
            try{
                projectBoreholes.setBoreholeIds(integerList);
                projectBoreholes.setCount(integerList.size());
                bmLoggDb.beginTransaction();
                bmLoggDb.projectBoreholesDao().insert(projectBoreholes);
                bmLoggDb.setTransactionSuccessful();
            }finally {
                bmLoggDb.endTransaction();
            }
        }
    }
        private List<Long> fetchIndices(long projectid,long number) throws IOException
        {
            Call<List<Long>> infoCall = BmloggApi
                    .getInstance()
                    .getBmloggService()
                    .getBoreholeIndices(projectid,number);

            Response<List<Long>> response = infoCall.execute();

            if (response.isSuccessful()) {
                List<Long> body = response.body();
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



}
