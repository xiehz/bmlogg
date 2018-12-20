package com.szbm.wh.x.bmlogg.vo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
        foreignKeys = [ForeignKey(entity = Ts_sptinfo::class,
                parentColumns = ["iid"],childColumns = ["spt_id"])]
)
data class Ts_sptdata
(
        @PrimaryKey
        var iid :Long,

        var spt_id :Long,

        var begin_depth :Float?,

        var end_depth :Float?,

        var pole_length :Float?,

        var hit_count :Float?,

        var hit_count_once :Float?,

        var ignore :Boolean?
){
        var from_server:Int? = 0 // 默认从服务器上来 <>0 则是本地新建
}
