package com.szbm.wh.x.bmlogg.ui.ui.main;

public enum  ProjectEnum  {
    PROJECT(0),
    PHASE(1),
    AREA(2),
    BOREHOLE(3);

    private int v;
    private ProjectEnum(int v)
    {
        this.v = v;
    }

    public int toInt()
    {
        return this.v;
    }


}
