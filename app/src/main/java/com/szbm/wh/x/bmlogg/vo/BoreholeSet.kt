package com.szbm.wh.x.bmlogg.vo

import androidx.room.Entity
import androidx.room.TypeConverters
import com.szbm.wh.x.bmlogg.db.LoggTypeConverters

@Entity(primaryKeys = ["iid"])
@TypeConverters(LoggTypeConverters::class)
data class BoreholeSet(
    val iid :Long,
    val extra_iid:Long,
    var begin_iid:Long,
    var end_iid:Long,
    var rock_soils:List<Long>,//-BH_CoreCatalog表单
    var rsCount:Int,
    var samplings:List<Long>,//-Sample表单
    var sCount:Int,
    var spts:List<Long>,//sptinfo 表单
    var sptCount :Int
)