package com.szbm.wh.x.bmlogg.ui.ui.project;

import android.view.animation.Transformation;

import com.szbm.wh.x.bmlogg.BmloggSharedPreference;
import com.szbm.wh.x.bmlogg.pojo.Resource;
import com.szbm.wh.x.bmlogg.repository.ProjectRepository;
import com.szbm.wh.x.bmlogg.util.AbsentLiveData;
import com.szbm.wh.x.bmlogg.vo.BH_Logger;
import com.szbm.wh.x.bmlogg.vo.ProjectInfo;
import com.szbm.wh.x.bmlogg.worker.Constants;
import com.szbm.wh.x.bmlogg.worker.DBDownloadOperation;

import java.util.List;

import javax.inject.Inject;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.work.WorkManager;
import androidx.work.WorkStatus;
import retrofit2.http.PUT;

public class ProjectViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    @Inject
    BH_Logger bh_logger;

    @Inject
    ProjectRepository projectRepository;

    @Inject
    BmloggSharedPreference bmloggSharedPreference;

    final MediatorLiveData<ProjectInfo> current = new MediatorLiveData<>();

    public void setCurrent(int project_id){
        bmloggSharedPreference.writeCurrentProject(project_id);
        loadCurrent();
    }

    public void loadCurrent()
    {
        LiveData<ProjectInfo> data = projectRepository.loadByProjectID(bmloggSharedPreference.readCurrentProject());
        current.addSource(data,projectInfo -> {
            current.removeSource(data);
            current.setValue(projectInfo);
        } );
    }

    public void loadLocalDb(){
        LiveData<List<ProjectInfo>> locals = projectRepository.loadProjects();
        resourceMediatorLiveData.addSource(locals, projectInfos ->{
            resourceMediatorLiveData.removeSource(locals);
            resourceMediatorLiveData.setValue(Resource.Companion.success(projectInfos));
        });
    }



    final MediatorLiveData<Resource<List<ProjectInfo>>> resourceMediatorLiveData =
            new MediatorLiveData<>();


    LiveData dbSource;
    public void loadFromNetWork(){
        resourceMediatorLiveData.removeSource(dbSource);
        dbSource = projectRepository.loadFromNetWork();
        resourceMediatorLiveData.addSource(dbSource,
                observer->
                    resourceMediatorLiveData.setValue((Resource<List<ProjectInfo>>)(observer)));
    }


    @Inject
    public ProjectViewModel(){

    }

    void apply(DBDownloadOperation dbDownloadOperation) {
        dbDownloadOperation.getContinuation().enqueue();
    }

    LiveData<List<WorkStatus>> getProjectStatus() {
        return WorkManager
                .getInstance()
                .getStatusesByTagLiveData(Constants.TAG_PROJECT);
    }

    LiveData<List<WorkStatus>> getBHStatus(){
        return WorkManager.getInstance().getStatusesByTagLiveData(Constants.TAG_BH);
    }

}
