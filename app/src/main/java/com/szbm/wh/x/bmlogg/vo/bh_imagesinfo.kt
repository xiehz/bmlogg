package com.szbm.wh.x.bmlogg.vo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class bh_imagesinfo
(

        var name :String,

        var typeoft :String,

        var instanceoft :String,

        var des :String?,

        var url :String
)
{
    @PrimaryKey(autoGenerate = true)
    var iid :Int = 0
}

