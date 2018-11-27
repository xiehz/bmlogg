package com.szbm.wh.x.bmlogg.repository;

import com.szbm.wh.x.bmlogg.BmloggExecutors;
import com.szbm.wh.x.bmlogg.api.BmloggService;
import com.szbm.wh.x.bmlogg.db.BmLoggDb;
import com.szbm.wh.x.bmlogg.db.dao.BH_loggerDao;
import com.szbm.wh.x.bmlogg.db.dao.ProjectPhaseDao;
import com.szbm.wh.x.bmlogg.pojo.Resource;
import com.szbm.wh.x.bmlogg.pojo.Status;
import com.szbm.wh.x.bmlogg.ui.ui.main.ProjectNode;
import com.szbm.wh.x.bmlogg.ui.ui.main.ProjectTreeAdapter;
import com.szbm.wh.x.bmlogg.ui.ui.project.ProjectAdapter;
import com.szbm.wh.x.bmlogg.vo.C_PubDic;
import com.szbm.wh.x.bmlogg.vo.ProjectArea;
import com.szbm.wh.x.bmlogg.vo.ProjectInfo;
import com.szbm.wh.x.bmlogg.vo.ProjectPhase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import static com.szbm.wh.x.bmlogg.ui.ui.main.ProjectEnum.AREA;
import static com.szbm.wh.x.bmlogg.ui.ui.main.ProjectEnum.PHASE;
import static com.szbm.wh.x.bmlogg.ui.ui.main.ProjectEnum.PROJECT;

@Singleton
public class ProjectTreeRepos {

    @Inject
    BmloggExecutors bmloggExecutors;

    @Inject
    BmLoggDb bmLoggDb;

    @Inject
    ProjectTreeRepos(){

    }

    public LiveData<Resource<List<ProjectNode>>> createTree(ProjectInfo projectInfo){
        MediatorLiveData<Resource<List<ProjectNode>>> result = new MediatorLiveData<>();
        result.setValue(Resource.Companion.loading(null));
        bmloggExecutors.diskIO().execute(()->{
                    List<ProjectNode> projectNodes = createAdapter(projectInfo);
                    bmloggExecutors.mainThread().execute(()->{
                        try{
                            result.setValue(Resource.Companion.success(projectNodes));
                        }
                        catch (Exception e)
                        {
                            result.setValue(Resource.Companion.error(e.getMessage(),null));
                        }

                    });
                }
            );
        return result;
    }

    public LiveData<ProjectInfo> loadProject(int projectid){
       return bmLoggDb.projectInfoDao().select(projectid);
    }

    public List<ProjectNode> createAdapter(ProjectInfo projectInfo){
        ProjectNode root= new ProjectNode(PROJECT);
        root.iid = projectInfo.getIid();
        root.name = projectInfo.getName();
        root.checked = projectInfo.getChecked()==1;
        root.childs = new ArrayList<>();
        root.parent = -1;

        List<ProjectNode> projectNodes = new ArrayList<>();
        projectNodes.add(root);


        Iterator<ProjectPhase> projectPhaseIterator = bmLoggDb.projectPhaseDao()
                .selectByProject(projectInfo.getIid()).iterator();

        //工程阶段
        int i ;
        while (projectPhaseIterator.hasNext())
        {
            ProjectPhase projectPhase = projectPhaseIterator.next();
            ProjectNode child = new ProjectNode(PHASE);
            child.iid = projectPhase.getIid();
            child.name = bmLoggDb.c_PubDicDao().selectText(projectPhase.getPhase());
            child.checked = projectPhase.getChecked() == 1;
            child.childs = new ArrayList<>();
            child.parent = 0;

            projectNodes.add(child);
            i = projectNodes.size() - 1;
            root.childs.add(projectNodes.size() -1);

            //工程部位
            List<ProjectArea> projectAreas = bmLoggDb.projectAreaDao()
                    .selectByProject(projectInfo.getIid(),projectPhase.getIid());

            for (ProjectArea projectArea:projectAreas
                    ) {
                ProjectNode grand = new ProjectNode(AREA);
                grand.iid = projectArea.getIid();
                grand.name = projectArea.getName();
                grand.checked = projectArea.getChecked() == 1;
                grand.childs = null;
                grand.parent = i;

                projectNodes.add(grand);
                child.childs.add(projectNodes.size() - 1);
            }

        }
        return projectNodes;
    }

    public LiveData<Resource<String>> saveProject(List<ProjectNode> projectNodes){
        MediatorLiveData<Resource<String>> result = new MediatorLiveData<>();
        result.setValue(Resource.Companion.loading(null));

        bmloggExecutors.diskIO().execute(()->{

            try{
                bmLoggDb.beginTransaction();
                for (ProjectNode node:projectNodes
                        ) {
                    save(node);
                }
                bmLoggDb.setTransactionSuccessful();
                bmloggExecutors.mainThread().execute(()->{
                    result.setValue(Resource.Companion.success(null));
                });
            }catch (Exception e){
                bmloggExecutors.mainThread().execute(()->{
                    result.setValue(Resource.Companion.error(e.getMessage(),null));
                });
            }
            finally {
                bmLoggDb.endTransaction();
            }
        });
        return result;
    }

    public void save(ProjectNode projectNode){
        int v = projectNode.checked ? 1: 0;
        //回存数据库
        switch (projectNode.level)
        {
            case PROJECT:
                ProjectInfo projectInfo = bmLoggDb.projectInfoDao().selectEntity((int)projectNode.iid);
                projectInfo.setChecked(v);
                bmLoggDb.projectInfoDao().insert(projectInfo);
                break;
            case PHASE:
                ProjectPhase projectPhase = bmLoggDb.projectPhaseDao().selectEntity((int)projectNode.iid);
                projectPhase.setChecked(v);
                bmLoggDb.projectPhaseDao().insert(projectPhase);
                break;
            case AREA:
                ProjectArea projectArea = bmLoggDb.projectAreaDao().selectEntity((int)projectNode.iid);
                projectArea.setChecked(v);
                bmLoggDb.projectAreaDao().insert(projectArea);
                break;
        }
    }

}
