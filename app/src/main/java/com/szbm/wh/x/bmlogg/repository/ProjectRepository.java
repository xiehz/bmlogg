package com.szbm.wh.x.bmlogg.repository;


import com.szbm.wh.x.bmlogg.BmloggExecutors;
import com.szbm.wh.x.bmlogg.api.ApiResponse;
import com.szbm.wh.x.bmlogg.api.ApiSuccessResponse;
import com.szbm.wh.x.bmlogg.api.BmloggApi;
import com.szbm.wh.x.bmlogg.api.BmloggService;
import com.szbm.wh.x.bmlogg.db.BmLoggDb;
import com.szbm.wh.x.bmlogg.db.dao.ProjectInfoDao;
import com.szbm.wh.x.bmlogg.pojo.Re_Project;
import com.szbm.wh.x.bmlogg.pojo.Resource;
import com.szbm.wh.x.bmlogg.util.RateLimiter;
import com.szbm.wh.x.bmlogg.vo.BH_Logger_Project;
import com.szbm.wh.x.bmlogg.vo.C_PubDic;
import com.szbm.wh.x.bmlogg.vo.ProjectBoreholes;
import com.szbm.wh.x.bmlogg.vo.ProjectInfo;
import com.szbm.wh.x.bmlogg.vo.C_stratum_ext;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import retrofit2.Call;
import retrofit2.Response;

@Singleton
public class ProjectRepository {

    @Inject
    BmLoggDb bmLoggDb;
    @Inject
    ProjectInfoDao projectInfoDao;

    @Inject
    BmloggExecutors bmloggExecutors;

    @Inject
    BmloggService bmloggService;

    @Inject
    public ProjectRepository(){

    }

    public LiveData<ProjectInfo> loadByProjectID(long project_iid){
        return projectInfoDao.select(project_iid);
    }

    long number;
    public LiveData<Resource<PagedList<ProjectInfo>>> loadFromNetWork(long number){
        this.number = number;
        return new ProjectsBoundResource(bmloggExecutors).asLiveData();
    }

    public LiveData<PagedList<ProjectInfo>> loadFromDisk(long number){
        PagedList.Config config = new PagedList.Config.Builder().setPageSize(20)
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(20)
                .build();
        DataSource.Factory<Integer, ProjectInfo> dbSource = bmLoggDb.projectInfoDao().factoryAll(number);
        return  new LivePagedListBuilder(dbSource,config).build();
    }

    class ProjectsBoundResource extends NetworkBoundResource<PagedList<ProjectInfo>,List<ProjectInfo>>{
        private final RateLimiter<Long> repoListRateLimit =new RateLimiter(10, TimeUnit.MINUTES);

        public ProjectsBoundResource(@NotNull BmloggExecutors appExecutors) {
            super(appExecutors);
        }

        @Override
        protected void onFetchFailed() {
            super.onFetchFailed();
        }

        @Override
        protected List<ProjectInfo> processResponse(@NotNull ApiSuccessResponse<List<ProjectInfo>> response) {
            return super.processResponse(response);
        }

        @Override
        protected void saveCallResult(List<ProjectInfo> item) {
            bmLoggDb.beginTransaction();
            try{
                bmLoggDb.bh_logger_ProjectDao().delete(number);
                projectInfoDao.inserts(item);
                List<BH_Logger_Project> bh_logger_projects = new ArrayList<>(item.size());
                for (ProjectInfo p: item
                     ) {
                    BH_Logger_Project logger_project = new
                            BH_Logger_Project(number,p.getIid());
                    bh_logger_projects.add(logger_project);
                }
                bmLoggDb.bh_logger_ProjectDao().inserts(bh_logger_projects);
                bmLoggDb.setTransactionSuccessful();
            }
            finally {
                bmLoggDb.endTransaction();
            }
        }

        @Override
        protected boolean shouldFetch(@Nullable PagedList<ProjectInfo> data) {
            return repoListRateLimit.shouldFetch(number);
        }

        @NotNull
        @Override
        protected LiveData<PagedList<ProjectInfo>> loadFromDb() {
            PagedList.Config config = new PagedList.Config.Builder().setPageSize(20)
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(20)
                    .build();
            return new LivePagedListBuilder(bmLoggDb.projectInfoDao().factoryAll(number),config)
                    .build();
        }

        @NotNull
        @Override
        protected LiveData<ApiResponse<List<ProjectInfo>>> createCall() {
            return bmloggService.getProjectInfos(number);
        }
    }

    public LiveData<Resource<Integer>> loadBoreholeIndexes(long project,long number){
        MediatorLiveData<Resource<Integer>> result = new MediatorLiveData<>();
        result.setValue(Resource.Companion.loading(null));

        bmloggExecutors.networkIO().execute(()->{
            try {

                List<Long> boreholes = fetchIndices(project, number);
                bmLoggDb.beginTransaction();
                ProjectBoreholes projectBoreholes = new ProjectBoreholes(project,boreholes,boreholes.size());
                bmLoggDb.projectBoreholesDao().insert(projectBoreholes);
                bmLoggDb.setTransactionSuccessful();
                bmloggExecutors.mainThread().execute(()->{
                    LiveData<ProjectBoreholes> pbsSource =bmLoggDb.projectBoreholesDao().select(project);
                    result.addSource(pbsSource, as -> {
                        result.removeSource(pbsSource);
                        result.setValue(Resource.Companion.success(as == null ?0 : as.getCount()));
                    });
                });
            }
            catch (Exception e){
                bmloggExecutors.mainThread().execute(()->{
                    LiveData<ProjectBoreholes> pbsSource =bmLoggDb.projectBoreholesDao().select(project);
                    result.addSource(pbsSource, as -> {
                        result.removeSource(pbsSource);
                        result.setValue(Resource.Companion.error(e.getMessage(),as == null ?0 : as.getCount()));
                    });
                });
            }
            finally {
                if(bmLoggDb.inTransaction())
                    bmLoggDb.endTransaction();
            }
        });

        return result;
    }

    public LiveData<Resource<ProjectBoreholes>> assiciateProjectBoreholes(long project,long number){
        MediatorLiveData<Resource<ProjectBoreholes>> results = new MediatorLiveData<>();
        results.setValue(Resource.Companion.loading(null));

        bmloggExecutors.networkIO().execute(()->{
            try {
                Re_Project re_project = fetchProject(project);
                List<C_PubDic> c_pubDics = fetchC_PubDic();
                List<C_stratum_ext> c_stratum_exts = fetchc_stratum_ext();
                List<Long> boreholes = fetchIndices(project, number);

                bmLoggDb.beginTransaction();
                insert(re_project,c_pubDics,c_stratum_exts);
                ProjectBoreholes projectBoreholes = new ProjectBoreholes(project,boreholes,boreholes.size());
                bmLoggDb.projectBoreholesDao().insert(projectBoreholes);
                bmLoggDb.setTransactionSuccessful();
                bmloggExecutors.mainThread().execute(()->{
                    LiveData<ProjectBoreholes> pbsSource =bmLoggDb.projectBoreholesDao().select(project);
                    results.addSource(pbsSource, as -> {
                        results.removeSource(pbsSource);
                        results.setValue(Resource.Companion.success(as));
                    });
                });
            }
            catch (Exception ie)
            {
                bmloggExecutors.mainThread().execute(()->{
                    LiveData<ProjectBoreholes> pbsSource =bmLoggDb.projectBoreholesDao().select(project);
                    results.addSource(pbsSource, as -> {
                        results.removeSource(pbsSource);
                        results.setValue(Resource.Companion.error(ie.getMessage(),as));
                    });
                });
            }
            finally {
                if(bmLoggDb.inTransaction())
                    bmLoggDb.endTransaction();
            }
        });
        return results;
    }

    private Re_Project fetchProject(long project)throws Exception{
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
            throw new IOException("获取钻孔记录失败！");
        }
    }

    private void insert(Re_Project re_project, List<C_PubDic> c_pubDics, List<C_stratum_ext> c_stratum_exts)
    {
        bmLoggDb.projectPhaseDao().inserts(re_project.getProjectPhase());
        bmLoggDb.projectAreaDao().inserts(re_project.getProjectArea());
        bmLoggDb.c_Project_LithologyDao().inserts(re_project.getC_Project_Lithology());
        bmLoggDb.c_Project_StratumDao().inserts(re_project.getC_Project_Stratum());
        bmLoggDb.c_project_stratum_extDao().inserts(re_project.getC_project_stratum_ext());
        bmLoggDb.sec_lineinfoDao().inserts(re_project.getSec_lineinfo());
        bmLoggDb.c_PubDicDao().inserts(c_pubDics);
        bmLoggDb.c_stratum_extDao().inserts(c_stratum_exts);
    }
}
