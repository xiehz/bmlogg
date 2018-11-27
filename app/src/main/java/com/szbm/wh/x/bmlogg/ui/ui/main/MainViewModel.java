package com.szbm.wh.x.bmlogg.ui.ui.main;

import com.szbm.wh.x.bmlogg.BmloggSharedPreference;
import com.szbm.wh.x.bmlogg.pojo.Resource;
import com.szbm.wh.x.bmlogg.repository.ProjectTreeRepos;
import com.szbm.wh.x.bmlogg.util.AbsentLiveData;
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

public class MainViewModel extends ViewModel {

    @Inject
    ProjectTreeRepos projectTreeRepos;

    @Inject
    BmloggSharedPreference bmloggSharedPreference;

    @Inject
    MainViewModel(){

    }
    final MediatorLiveData<ProjectInfo> projectInfoMutableLiveData =
            new MediatorLiveData<>();

    final LiveData<Resource<List<ProjectNode>>> projectTreeAdapterLiveData =
            Transformations.switchMap(projectInfoMutableLiveData,
                    project->{
                        if(project != null)
                            return projectTreeRepos.createTree(project);
                        else
                            return AbsentLiveData.Companion.create();
                    });


    void getProject(){
        int project = bmloggSharedPreference.readCurrentProject();
        ProjectInfo projectInfo = projectInfoMutableLiveData.getValue();

        if(projectInfo == null || project != projectInfo.getIid())
        {
            LiveData<ProjectInfo> projectInfoLiveData = projectTreeRepos.loadProject(project);
            projectInfoMutableLiveData.addSource(projectInfoLiveData,
                    projectInfo1 -> {
                        projectInfoMutableLiveData.removeSource(projectInfoLiveData);
                        projectInfoMutableLiveData.setValue(projectInfo1);
                    });
        }
    }


    final MediatorLiveData<Resource<String>> saved = new MediatorLiveData<>();
    private LiveData<Resource<String>> resourceLiveData;
    void saveProject(List<ProjectNode> projectNodes){
        saved.removeSource(resourceLiveData);
        resourceLiveData = projectTreeRepos.saveProject(projectNodes);
        saved.addSource(resourceLiveData,saved::setValue);
    }


}
