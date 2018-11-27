package com.szbm.wh.x.bmlogg.repository;


import com.szbm.wh.x.bmlogg.BmloggExecutors;
import com.szbm.wh.x.bmlogg.api.ApiResponse;
import com.szbm.wh.x.bmlogg.api.ApiSuccessResponse;
import com.szbm.wh.x.bmlogg.api.BmloggService;
import com.szbm.wh.x.bmlogg.db.BmLoggDb;
import com.szbm.wh.x.bmlogg.db.dao.ProjectInfoDao;
import com.szbm.wh.x.bmlogg.util.AbsentLiveData;
import com.szbm.wh.x.bmlogg.vo.ProjectInfo;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

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

    public LiveData<ProjectInfo> loadByProjectID(int project_iid){
        return projectInfoDao.select(project_iid);
    }

    public LiveData<List<ProjectInfo>> loadProjects(){
        return projectInfoDao.selectAll();
    }

    public LiveData loadFromNetWork(){
        return new ProjectNetWorkLoader(bmloggExecutors).asLiveData();
    }

    class ProjectNetWorkLoader extends NetworkLoaderResource<List<ProjectInfo>,List<ProjectInfo>>{

        public ProjectNetWorkLoader(@NotNull BmloggExecutors appExecutors) {
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
                projectInfoDao.inserts(item);
                bmLoggDb.setTransactionSuccessful();
            }
            finally {
                bmLoggDb.endTransaction();
            }
        }

        @Override
        protected boolean shouldFetch(@Nullable List<ProjectInfo> data) {
            return true;
        }

        @NotNull
        @Override
        protected LiveData<List<ProjectInfo>> loadFromDb() {
            return Transformations.switchMap(
                    projectInfoDao.selectAll(),
                    dao->dao==null?AbsentLiveData.Companion.create():
                            projectInfoDao.selectAll());
        }

        @NotNull
        @Override
        protected LiveData<ApiResponse<List<ProjectInfo>>> createCall() {
            return bmloggService.getProjectInfos();
        }
    }



}
