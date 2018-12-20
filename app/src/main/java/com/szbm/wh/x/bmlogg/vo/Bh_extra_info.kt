package com.szbm.wh.x.bmlogg.vo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.szbm.wh.x.bmlogg.vo.BH_Logger

@Entity(
        foreignKeys = [ForeignKey(
                entity = BH_BoreholeInfo::class,
                parentColumns = ["iid"],
                childColumns = ["borehole_iid"]
        )]
)
data class Bh_extra_info
(

        @PrimaryKey
        var iid :Long,

        var borehole_iid :Long,

        var  _long :Double?,

        var lat :Double?,

        var B :Double?,

        var L :Double?,

        var H :Double?,

        var comment:String?
)
{
        var from_server:Int? = 0 // 默认从服务器上来 <>0 则是本地新建
}

