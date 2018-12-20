package com.szbm.wh.x.bmlogg.vo

import androidx.room.Entity
import androidx.room.TypeConverters
import com.szbm.wh.x.bmlogg.db.LoggTypeConverters

@Entity(primaryKeys = ["iid"])
@TypeConverters(LoggTypeConverters::class)
data class ProjectBoreholes(
        var iid :Long,
        var boreholeIds :List<Long>,
        var count:Int
)