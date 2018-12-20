package com.szbm.wh.x.bmlogg.ui.ui.bh;

import com.szbm.wh.x.bmlogg.pojo.BoreholeAndExtra;
import com.szbm.wh.x.bmlogg.pojo.Resource;
import com.szbm.wh.x.bmlogg.repository.BoreholesRepos;
import com.szbm.wh.x.bmlogg.vo.BH_BoreholeInfo;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class BhBasicViewModel extends ViewModel {

    @Inject
    BoreholesRepos boreholesRepos;
    @Inject
    public BhBasicViewModel(){

    }

    public final MutableLiveData<Integer> index = new MutableLiveData<>();

    public LiveData<Resource<BoreholeAndExtra>> bh_boreholeInfo =
            Transformations.switchMap(index, input ->
                    boreholesRepos.getBorehole(input));



}
