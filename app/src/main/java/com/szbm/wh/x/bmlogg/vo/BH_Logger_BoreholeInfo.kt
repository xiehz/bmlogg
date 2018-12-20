package com.szbm.wh.x.bmlogg.vo

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = BH_Logger::class,parentColumns = ["number"],
        childColumns = ["number"]),
ForeignKey(entity = BH_BoreholeInfo::class,parentColumns = ["iid"],
        childColumns = ["borehole_iid"])])
data class BH_Logger_BoreholeInfo(
        @PrimaryKey
        var iid:Long,
        var number:Long,
        var borehole_iid:Long
)