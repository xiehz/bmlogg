package com.szbm.wh.x.bmlogg.vo

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
        foreignKeys = [androidx.room.ForeignKey(entity = Bh_extra_info::class,
                parentColumns = ["iid"], childColumns = ["borehole_extra_iid"])],
        indices = [Index("borehole_extra_iid")
        ],
        primaryKeys = ["iid","from_server"]
)
data class Bh_begin_info
(
        var iid :Long,

        var borehole_extra_iid :Long,

        var logger_iid :Long,

        var comment :String?
)
{
        var from_server:Int = 0 // 默认从服务器上来 <>0 则是本地新建
}
