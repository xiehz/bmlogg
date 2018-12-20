package com.szbm.wh.x.bmlogg.ui.ui.boreholes;

import com.szbm.wh.x.bmlogg.BmloggExecutors;
import com.szbm.wh.x.bmlogg.BmloggSharedPreference;
import com.szbm.wh.x.bmlogg.api.BmloggService;
import com.szbm.wh.x.bmlogg.db.BmLoggDb;
import com.szbm.wh.x.bmlogg.page.Listing;
import com.szbm.wh.x.bmlogg.page.NetworkState;
import com.szbm.wh.x.bmlogg.repository.BoreholePage.DbBoreholeRepository;
import com.szbm.wh.x.bmlogg.repository.ProjectRepository;
import com.szbm.wh.x.bmlogg.vo.BH_BoreholeInfo;
import com.szbm.wh.x.bmlogg.vo.BH_Logger;
import com.szbm.wh.x.bmlogg.vo.ProjectInfo;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;
import dagger.Lazy;


public class BoreholesViewModel extends ViewModel {

    private final static int s_page_size = 20;
    @Inject
    BmLoggDb bmLoggDb;
    @Inject
    BmloggService bmloggService;
    @Inject
    BmloggExecutors bmloggExecutors;
    @Inject
    BmloggSharedPreference bmloggSharedPreference;
    @Inject
    ProjectRepository projectRepository;

    @Inject
    BH_Logger bh_logger;
    @Inject
    BoreholesViewModel(){
        dbRedditPostRepository = ()->new DbBoreholeRepository(
                bmLoggDb,
                bmloggService,
                bmloggExecutors.diskIO(),
                s_page_size);
    }

    final MediatorLiveData<ProjectInfo> current = new MediatorLiveData<>();
    Lazy<DbBoreholeRepository> dbRedditPostRepository;
    private final MutableLiveData<String> code = new MutableLiveData<>();
    private final LiveData<Listing<BH_BoreholeInfo>> repoResult =
            Transformations.map(code,input -> dbRedditPostRepository.get().
                    postsOfSubreddit(current.getValue().getIid(),input,s_page_size,bh_logger.getNumber()));

    final LiveData<PagedList<BH_BoreholeInfo>> posts = Transformations.switchMap(repoResult, listing->listing.getPagedList());
    final LiveData<NetworkState> networkState = Transformations.switchMap(repoResult, it->it.getNetworkState());
    final LiveData<NetworkState> refreshState = Transformations.switchMap(repoResult,it->it.getRefreshState());

    void makeCurrent(){
        LiveData<ProjectInfo> data = projectRepository.loadByProjectID(bmloggSharedPreference.readCurrentProject());
        current.addSource(data,projectInfo -> {
            current.removeSource(data);
            current.setValue(projectInfo);
        } );
    }
    void refresh() {
        if(repoResult.getValue() != null)
        {
            if(this.repoResult.getValue()!=null)
                this.repoResult.getValue().getRefresh().invoke();
        }
    }

    Boolean showBorehole(String c) {
        if(this.code.getValue() !=null && this.code.getValue().equals(c)){
            return false;
        }

        this.code.setValue(c);
        return true;
    }

     int retry() {
        if(this.repoResult.getValue() != null)
        {
            Listing<BH_BoreholeInfo> listing = repoResult.getValue();
            if(listing != null && listing.getRetry() != null)
                listing.getRetry().invoke();
        }
        return 0;
    }


}
