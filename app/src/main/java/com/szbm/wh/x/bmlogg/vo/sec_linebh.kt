package com.szbm.wh.x.bmlogg.vo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/*

 */
@Entity(foreignKeys = [ForeignKey(entity = sec_lineinfo::class,
        parentColumns = ["IID"],childColumns = ["line_id"]),
ForeignKey(entity = BH_BoreholeInfo::class,parentColumns = ["iid"],childColumns = ["borehole_id"])
])
data class sec_linebh
(

        var line_id :Int,

        var borehole_id :Int,

        var display_order :Int?

){
        @PrimaryKey(autoGenerate = true)
        var iid :Int = 0
}