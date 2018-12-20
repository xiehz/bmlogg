package com.szbm.wh.x.bmlogg.vo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
(
        foreignKeys = [ForeignKey(entity = Bh_extra_info::class,
                parentColumns = ["iid"],
                childColumns = ["bh_extra_iid"])]
)
data class Bh_geo_date
(

        var longi :Double,

        var lat :Double,

        var time :String,

        var typeoft :String,

        var instanceoft :Long,
        var bh_extra_iid:Long
)
{
    @PrimaryKey(autoGenerate = true)
    var iid :Long = 0
    var from_server:Int? = 0 // 默认从服务器上来 <>0 则是本地新建
}
