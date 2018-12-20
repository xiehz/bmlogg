package com.szbm.wh.x.bmlogg.ui.ui.project;

import android.view.animation.Transformation;

import com.szbm.wh.x.bmlogg.BmloggSharedPreference;
import com.szbm.wh.x.bmlogg.pojo.Resource;
import com.szbm.wh.x.bmlogg.repository.ProjectRepository;
import com.szbm.wh.x.bmlogg.util.AbsentLiveData;
import com.szbm.wh.x.bmlogg.vo.BH_BoreholeInfo;
import com.szbm.wh.x.bmlogg.vo.BH_Logger;
import com.szbm.wh.x.bmlogg.vo.ProjectBoreholes;
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
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.work.WorkManager;
import androidx.work.WorkStatus;
import dagger.Lazy;
import retrofit2.http.PUT;

public class ProjectViewModel extends ViewModel {
    @Inject
    BH_Logger bh_logger;

    @Inject
    ProjectRepository projectRepository;

    @Inject
    BmloggSharedPreference bmloggSharedPreference;

    final MediatorLiveData<Resource<PagedList<ProjectInfo>>>
        result = new MediatorLiveData<>();
    final Lazy<LiveData<PagedList<ProjectInfo>>> diskResult =
            ()->projectRepository.loadFromDisk(bh_logger.getNumber());

    final MutableLiveData<Long> releation = new MutableLiveData<>();
    final LiveData<Resource<ProjectBoreholes>> projectBoreholesLiveData =
            Transformations.switchMap(releation,p->
                projectRepository.assiciateProjectBoreholes(p,bh_logger.getNumber()));

    MutableLiveData<Long> current_invoker = new MutableLiveData<>();
    final LiveData<ProjectInfo> current = Transformations.switchMap(current_invoker,
            input ->
                projectRepository.loadByProjectID(input));

    public void loadCurrent(){
        current_invoker.setValue(bmloggSharedPreference.readCurrentProject());
    }
    public void setCurrent(long project_id){
        bmloggSharedPreference.writeCurrentProject(project_id);
    }

    LiveData<Resource<PagedList<ProjectInfo>>> dbSource;
    public void loadFromNetWork(){
        result.removeSource(dbSource);
        dbSource = projectRepository.loadFromNetWork(bh_logger.getNumber());
        result.addSource(dbSource,
                pagedList-> result.setValue(pagedList));
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
