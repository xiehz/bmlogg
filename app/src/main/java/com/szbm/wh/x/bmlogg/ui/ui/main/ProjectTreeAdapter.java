package com.szbm.wh.x.bmlogg.ui.ui.main;

import com.szbm.wh.x.bmlogg.vo.C_PubDic;
import com.szbm.wh.x.bmlogg.vo.ProjectInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import androidx.annotation.WorkerThread;

import static com.szbm.wh.x.bmlogg.ui.ui.main.ProjectEnum.PROJECT;

/**
 * Created by hzxie on 2017/9/30.
 */

public class ProjectTreeAdapter {
    List<ProjectNode> projectNodes;
    public ProjectTreeAdapter(List<ProjectNode> projectNodes)
    {
        this.projectNodes = projectNodes;
    }
}
