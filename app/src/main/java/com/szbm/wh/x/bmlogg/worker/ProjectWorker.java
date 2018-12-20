package com.szbm.wh.x.bmlogg.worker;

import android.content.Context;

import com.szbm.wh.x.bmlogg.api.BmloggApi;
import com.szbm.wh.x.bmlogg.db.BmLoggDb;
import com.szbm.wh.x.bmlogg.pojo.Re_Project;
import com.szbm.wh.x.bmlogg.vo.C_PubDic;
import com.szbm.wh.x.bmlogg.vo.C_stratum_ext;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import retrofit2.Call;
import retrofit2.Response;

public class ProjectWorker extends Worker {

    public ProjectWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Data args = getInputData();
        int project = args.getInt(Constants.DATA_PROJECT_KEY,-1);

        try {
            Re_Project re_project = fetchProject(project);
            List<C_PubDic> c_pubDics = fetchC_PubDic();
            List<C_stratum_ext> c_stratum_exts = fetchc_stratum_ext();

            insert(re_project,c_pubDics,c_stratum_exts);
        }catch (Exception ie)
        {
            setOutputData(new Data.Builder().putString(Constants.PROJECT_DATA_OUTPUT,ie.getMessage()).build());
            return Result.FAILURE;
        }
        return Result.SUCCESS;
    }

    private Re_Project fetchProject(int project)throws Exception{
        Call<Re_Project> infoCall = BmloggApi
                .getInstance()
                .getBmloggService()
                .getProjectInfoWorker(project);

        Response<Re_Project> response = infoCall.execute();

        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new Exception(response.errorBody()== null? response.message():response.errorBody().string());
        }
    }

    private List<C_PubDic> fetchC_PubDic()throws Exception{
        Call<List<C_PubDic>> infoCall = BmloggApi
                .getInstance()
                .getBmloggService()
                .getC_PubDicWorker();

        Response<List<C_PubDic>> response = infoCall.execute();

        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new Exception(response.errorBody()== null? response.message():response.errorBody().string());
        }
    }

    private List<C_stratum_ext> fetchc_stratum_ext()throws Exception{
        Call<List<C_stratum_ext>> infoCall = BmloggApi
                .getInstance()
                .getBmloggService()
                .getc_stratum_extWorker();

        Response<List<C_stratum_ext>> response = infoCall.execute();

        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new Exception(response.errorBody()== null? response.message():response.errorBody().string());
        }
    }

    private void insert(Re_Project re_project, List<C_PubDic> c_pubDics, List<C_stratum_ext> c_stratum_exts)
    {
        BmLoggDb bmLoggDb = BmLoggDb.Companion.getInstance(getApplicationContext());
        bmLoggDb.beginTransaction();
        bmLoggDb.projectPhaseDao().inserts(re_project.getProjectPhase());
        bmLoggDb.projectAreaDao().inserts(re_project.getProjectArea());
        bmLoggDb.c_Project_LithologyDao().inserts(re_project.getC_Project_Lithology());
        bmLoggDb.c_Project_StratumDao().inserts(re_project.getC_Project_Stratum());
        bmLoggDb.c_project_stratum_extDao().inserts(re_project.getC_project_stratum_ext());
        bmLoggDb.sec_lineinfoDao().inserts(re_project.getSec_lineinfo());
        bmLoggDb.c_PubDicDao().inserts(c_pubDics);
        bmLoggDb.c_stratum_extDao().inserts(c_stratum_exts);
        bmLoggDb.setTransactionSuccessful();
        bmLoggDb.endTransaction();
    }
}
