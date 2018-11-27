package com.szbm.wh.x.bmlogg.vo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.szbm.wh.x.bmlogg.vo.BH_Logger

@Entity(
        foreignKeys = [ForeignKey(entity = bh_extra_info::class,
                parentColumns = ["iid"],childColumns = ["borehole_extra_iid"] ),
        ForeignKey(entity = ts_sptdata::class,parentColumns = ["iid"],
                childColumns = ["bhspt_iid"]),
        ForeignKey(entity = BH_Logger::class,
                parentColumns = ["number"],
                childColumns = ["logger_iid"])]
)
data class tsext_spt_info
(
        @PrimaryKey
        var iid :Int,

        var borehole_extra_iid :Int,

        var bhspt_iid :Int,

        var logger_iid :Int,

        var  comment :String?
)