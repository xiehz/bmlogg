package com.szbm.wh.x.bmlogg.ui.ui.main;

import com.szbm.wh.x.bmlogg.BmloggSharedPreference;
import com.szbm.wh.x.bmlogg.pojo.MainProjectInfo;
import com.szbm.wh.x.bmlogg.pojo.Resource;
import com.szbm.wh.x.bmlogg.repository.ProjectRepository;
import com.szbm.wh.x.bmlogg.repository.ProjectTreeRepos;
import com.szbm.wh.x.bmlogg.util.AbsentLiveData;
import com.szbm.wh.x.bmlogg.vo.BH_Logger;
import com.szbm.wh.x.bmlogg.vo.ProjectInfo;
import com.szbm.wh.x.databinding.ObservableViewModel;

import java.util.List;

import javax.inject.Inject;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import dagger.Lazy;

public class MainViewModel extends ViewModel {

    @Inject
    BH_Logger bh_logger;


    @Inject
    ProjectRepository projectRepository;

    @Inject
    BmloggSharedPreference bmloggSharedPreference;

    @Inject
    MainViewModel(){

    }

    public long loadCurrent(){
        return bmloggSharedPreference.readCurrentProject();
    }

    final MutableLiveData<Long> projectLiveData = new MutableLiveData<>();

    final LiveData<ProjectInfo> projectInfoResource =
            Transformations.switchMap(projectLiveData,project->
                projectRepository.loadByProjectID(project));


    final LiveData<Resource<Integer>> boreholeCountResource =
            Transformations.switchMap(projectLiveData,
                    project-> projectRepository.loadBoreholeIndexes(project,bh_logger.getNumber()));


}
