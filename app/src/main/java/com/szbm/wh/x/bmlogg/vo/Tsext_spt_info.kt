package com.szbm.wh.x.bmlogg.vo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
        foreignKeys = [ForeignKey(entity = Bh_extra_info::class,
                parentColumns = ["iid"],childColumns = ["borehole_extra_iid"] ),
        ForeignKey(entity = Ts_sptdata::class,parentColumns = ["iid"],
                childColumns = ["bhspt_iid"]),
        ForeignKey(entity = BH_Logger::class,
                parentColumns = ["number"],
                childColumns = ["logger_iid"])]
)
data class Tsext_spt_info
(
        @PrimaryKey
        var iid :Long,

        var borehole_extra_iid :Long,

        var bhspt_iid :Long,

        var logger_iid :Long,

        var  comment :String?
){
        var from_server:Int? = 0 // 默认从服务器上来 <>0 则是本地新建
}