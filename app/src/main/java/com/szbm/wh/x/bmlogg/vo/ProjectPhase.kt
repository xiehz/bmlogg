package com.szbm.wh.x.bmlogg.vo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = ProjectInfo::class,
        parentColumns = ["iid"],
        childColumns = ["project_id"])])
data class ProjectPhase
(
        @PrimaryKey
        var  iid :Long,

        var  project_id :Long,

        var  phase :Long,

        var  survey_unit :String?,

        var  start_time :String?,

        var  end_time :String?,

        var  xmin :Double?,

        var  ymin :Double?,

        var  zmin :Double?,

        var  xmax :Double?,

        var  ymax :Double?,

        var  zmax :Double?,

        var  deleted :Int?
){
//        var checked:Int = 0
}