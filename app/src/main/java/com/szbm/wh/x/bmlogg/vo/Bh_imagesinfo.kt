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
data class Bh_imagesinfo
(

        var name :String?,

        var typeoft :String,

        var instanceoft :Long,

        var des :String?,

        var url :String,
        var bh_extra_iid:Long
)
{
    @PrimaryKey(autoGenerate = true)
    var iid :Long = 0
    var from_server:Int? = 0 // 默认从服务器上来 <>0 则是本地新建
}

