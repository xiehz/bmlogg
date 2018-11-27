package com.szbm.wh.x.bmlogg.vo

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
        foreignKeys = [androidx.room.ForeignKey(entity = bh_extra_info::class,
                parentColumns = ["iid"], childColumns = ["borehole_extra_iid"])],
        indices = [Index("borehole_extra_iid")]
)
data class bh_begin_info
(
        @PrimaryKey
        var iid :Long,

        var borehole_extra_iid :Long,

        var logger_iid :Long,

        var comment :String?
)

