package com.szbm.wh.x.bmlogg.repository;

import com.szbm.wh.x.bmlogg.BmloggExecutors;
import com.szbm.wh.x.bmlogg.api.ApiResponse;
import com.szbm.wh.x.bmlogg.api.ApiSuccessResponse;
import com.szbm.wh.x.bmlogg.api.BmloggService;
import com.szbm.wh.x.bmlogg.db.dao.BH_loggerDao;
import com.szbm.wh.x.bmlogg.db.BmLoggDb;
import com.szbm.wh.x.bmlogg.pojo.Resource;
import com.szbm.wh.x.bmlogg.util.AbsentLiveData;
import com.szbm.wh.x.bmlogg.vo.BH_Logger;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

@Singleton
public class LoginRepository {

    @Inject
    BmloggExecutors bmloggExecutors;

    @Inject
    BmLoggDb bmLoggDb;

    @Inject
    BH_loggerDao bh_loggerDao;

    @Inject
    BmloggService bmloggService;

    @Inject
    public LoginRepository(){

    }

    BH_Logger logger;

    public LiveData<Resource<BH_Logger>> login(BH_Logger bh_logger){
        this.logger = bh_logger;
        LoginService loginService = new LoginService(bmloggExecutors);
        return loginService.asLiveData();
    }

    public final class LoginService extends NetworkBoundResource<BH_Logger,BH_Logger>
    {
        public LoginService(@NotNull BmloggExecutors appExecutors) {
            super(appExecutors);
        }

        @Override
        protected void onFetchFailed() {
            super.onFetchFailed();
        }

        @Override
        protected BH_Logger processResponse(@NotNull ApiSuccessResponse<BH_Logger> response) {
            return super.processResponse(response);
        }

        @Override
        protected void saveCallResult(BH_Logger item) {
            bmLoggDb.beginTransaction();
            try{
                bh_loggerDao.insert(item);
                bmLoggDb.setTransactionSuccessful();
            }
            finally {
                bmLoggDb.endTransaction();
            }
        }

        @Override
        protected boolean shouldFetch(@Nullable BH_Logger data) {
            return data == null ;
        }

        @NotNull
        @Override
        protected LiveData<BH_Logger> loadFromDb() {
            return Transformations.switchMap(
                    bh_loggerDao.findByTelPass(logger.getTel(),logger.getPass()),
                    dao->dao==null?AbsentLiveData.Companion.create():
                            bh_loggerDao.findByTelPass(logger.getTel(),logger.getPass()));
        }

        @NotNull
        @Override
        protected LiveData<ApiResponse<BH_Logger>> createCall() {
            return bmloggService.getLogger(logger.getTel(),logger.getPass());
        }
    }
}
