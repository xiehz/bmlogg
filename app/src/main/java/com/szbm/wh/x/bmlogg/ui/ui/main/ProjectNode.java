package com.szbm.wh.x.bmlogg.ui.ui.main;

import java.util.List;


public class ProjectNode {
    public long iid;
    public String name;
    public boolean checked;
    public ProjectEnum level;
    public List<Integer> childs;
    public Integer parent;

    public ProjectNode(ProjectEnum level)
    {
        this.level = level;
    }

}
