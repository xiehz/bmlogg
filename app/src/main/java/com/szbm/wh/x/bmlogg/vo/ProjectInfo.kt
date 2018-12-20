package com.szbm.wh.x.bmlogg.vo
import androidx.annotation.Nullable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProjectInfo
(

        @PrimaryKey
        var   iid :Long,

        var   code :String?,

        var   name :String?,

        var   dev_mode :Int?,

        var   grade :Int?,

        var   province :Int?,

        var   city :String?,

        var   country :String?,

        var   watershed :String?,

        var   river_system :String?,

        var   river :String?,

        var   cascade_id :String?

){
//        var checked:Int = 0
}