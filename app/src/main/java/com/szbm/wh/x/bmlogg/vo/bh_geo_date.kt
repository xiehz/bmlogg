package com.szbm.wh.x.bmlogg.vo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class bh_geo_date
(

        var longi :Double,

        var lat :Double,

        var time :String,

        var typeoft :String,

        var instanceoft :Int
)
{
    @PrimaryKey(autoGenerate = true)
    var iid :Int = 0
}
