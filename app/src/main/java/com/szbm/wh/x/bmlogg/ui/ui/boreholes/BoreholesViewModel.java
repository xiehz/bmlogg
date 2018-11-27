package com.szbm.wh.x.bmlogg.ui.ui.boreholes;

import com.szbm.wh.x.bmlogg.BmloggExecutors;
import com.szbm.wh.x.bmlogg.api.BmloggService;
import com.szbm.wh.x.bmlogg.db.BmLoggDb;
import com.szbm.wh.x.bmlogg.page.Listing;
import com.szbm.wh.x.bmlogg.page.NetworkState;
import com.szbm.wh.x.bmlogg.repository.BoreholePage.DbBoreholeRepository;
import com.szbm.wh.x.bmlogg.vo.BH_BoreholeInfo;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;
import dagger.Lazy;


public class BoreholesViewModel extends ViewModel {

    @Inject
    BmLoggDb bmLoggDb;
    @Inject
    BmloggService bmloggService;
    @Inject
    BmloggExecutors bmloggExecutors;
    @Inject
    BoreholesViewModel(){
        dbRedditPostRepository = ()->new DbBoreholeRepository(
                bmLoggDb,
                bmloggService,
                bmloggExecutors.diskIO(),
                29,
                20);
    }

    Lazy<DbBoreholeRepository> dbRedditPostRepository;
    private final MutableLiveData<String> code = new MutableLiveData<>();
    private final LiveData<Listing<BH_BoreholeInfo>> repoResult =
            Transformations.map(code, input -> dbRedditPostRepository.get().
                    postsOfSubreddit(29,input,20));

    final LiveData<PagedList<BH_BoreholeInfo>> posts = Transformations.switchMap(repoResult, listing->listing.getPagedList());
    final LiveData<NetworkState> networkState = Transformations.switchMap(repoResult, it->it.getNetworkState());
    final LiveData<NetworkState> refreshState = Transformations.switchMap(repoResult,it->it.getRefreshState());

    void refresh() {
        if(repoResult.getValue() != null)
        {
            if(this.repoResult.getValue()!=null)
                this.repoResult.getValue().getRefresh().invoke();
        }
    }

    Boolean showSubreddit(String c) {
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



//    @Inject
//    BoreholesRepos boreholesRepos;
//    @Inject
//    BoreholesViewModel(){
//
//    }
//
//   Lazy<LiveData<PagedList<BH_BoreholeInfo>>> pagedListLiveDatal = new Lazy<LiveData<PagedList<BH_BoreholeInfo>>>() {
//       @Override
//       public LiveData<PagedList<BH_BoreholeInfo>> get() {
//           PagedList.Config config = new PagedList.Config.Builder().setPageSize(20)
//                   .setEnablePlaceholders(false)
//                   .setInitialLoadSizeHint(20)
//                   .build();
//           return new LivePagedListBuilder(boreholesRepos.getFactory(),config).build();
//       }
//   };


}
