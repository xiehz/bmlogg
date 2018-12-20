package com.szbm.wh.x.bmlogg.pojo

import com.szbm.wh.x.bmlogg.vo.BH_BoreholeInfo

data class BoreholeDetial (
    val depth:Float?,
    val lng:Double?,
    val lat:Double?,
    val begin_time:String?,
    val rock_soil: Int?,
    val sampling: Int?,
    val spt: Int?,
    val end_time :String?
)