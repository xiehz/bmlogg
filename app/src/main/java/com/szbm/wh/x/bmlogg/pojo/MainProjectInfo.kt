package com.szbm.wh.x.bmlogg.pojo

import com.szbm.wh.x.bmlogg.vo.ProjectInfo

data class MainProjectInfo(
        var projectInfo:ProjectInfo,
        var projectLocation:String?,
        var projectDes:String?,
        var boreholeCount:Int
)