package com.szbm.wh.x.bmlogg.ui.ui.loggerbh;

import com.szbm.wh.x.bmlogg.pojo.BoreholeAndExtra;
import com.szbm.wh.x.bmlogg.pojo.Resource;
import com.szbm.wh.x.bmlogg.repository.BoreholesRepos;

import javax.inject.Inject;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class LoggerBhViewModel extends ViewModel {
    @Inject
    BoreholesRepos boreholesRepos;
    @Inject
    LoggerBhViewModel(){

    }

    public final MutableLiveData<Long> index = new MutableLiveData<>();

    public final LiveData<Resource<BoreholeAndExtra>> boreholeExtra =
            Transformations.switchMap(index,iid-> boreholesRepos.getBorehole(iid));

}
