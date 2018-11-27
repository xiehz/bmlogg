package com.szbm.wh.x.bmlogg.vo

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(foreignKeys = [ForeignKey(entity = ProjectInfo::class,
        parentColumns = ["iid"],
        childColumns = ["project_id"]),
ForeignKey(entity = ProjectPhase::class,
        parentColumns = ["iid"],
        childColumns = ["phase_id"])])
data class ProjectArea(

        @PrimaryKey
        var iid:Int,
        var name:String,
        var phase_id:Int,
        var project_id:Int
)
{
        var checked:Int = 0
}