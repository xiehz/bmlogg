package com.szbm.wh.x.bmlogg.ui.ui.loggingdetail;


import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

/*
创建备份数据
 */
public class LoggingDetailViewModel extends ViewModel {

    @Inject
    public LoggingDetailViewModel(){

    }

    final MutableLiveData<Long> borehole = new MutableLiveData<>();



}
