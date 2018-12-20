package com.szbm.wh.x.bmlogg.vo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/*

 */
@Entity(foreignKeys = [ForeignKey(entity = Sec_lineinfo::class,
        parentColumns = ["IID"],childColumns = ["line_id"]),
ForeignKey(entity = BH_BoreholeInfo::class,parentColumns = ["iid"],childColumns = ["borehole_id"])
])
data class Sec_linebh
(

        var line_id :Long,

        var borehole_id :Long,

        var display_order :Int?

){
        @PrimaryKey(autoGenerate = true)
        var iid :Int = 0
        var from_server:Int? = 0 // 默认从服务器上来 <>0 则是本地新建
}