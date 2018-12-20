package com.szbm.wh.x.bmlogg.vo

import androidx.room.Entity

@Entity
(
    primaryKeys = ["number"]
)
data class BH_Logger (
        var number :Long,
        var tel :String,
        var pass: String,
        var url:String?
)