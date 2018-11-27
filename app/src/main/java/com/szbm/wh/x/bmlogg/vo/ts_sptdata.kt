package com.szbm.wh.x.bmlogg.vo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
        foreignKeys = [ForeignKey(entity = ts_sptinfo::class,
                parentColumns = ["iid"],childColumns = ["spt_id"])]
)
data class ts_sptdata
(
        @PrimaryKey
        var iid :Int,

        var spt_id :Int,

        var begin_depth :Float?,

        var end_depth :Float?,

        var pole_length :Float?,

        var hit_count :Float?,

        var hit_count_once :Float?,

        var ignore :Boolean?
)
