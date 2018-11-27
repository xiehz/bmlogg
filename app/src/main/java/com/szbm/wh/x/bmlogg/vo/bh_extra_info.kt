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
data class bh_extra_info
(

        @PrimaryKey
        var iid :Int,

        var borehole_iid :Int,

        var creator_iid :Int?,

        var  _long :Double?,

        var lat :Double?,

        var B :Double?,

        var L :Double?,

        var H :Double?,

        var comment:String?
)


