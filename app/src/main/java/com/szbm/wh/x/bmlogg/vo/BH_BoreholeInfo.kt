package com.szbm.wh.x.bmlogg.vo
import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.szbm.wh.x.bmlogg.vo.ProjectArea
import java.sql.Date

@Entity(
        foreignKeys = [
        ForeignKey(entity = ProjectInfo::class,parentColumns = ["iid"],childColumns = ["Project_ID"]),
        ForeignKey(entity = ProjectPhase::class,parentColumns = ["iid"],childColumns = ["ProjectPhase_ID"]),
        ForeignKey(entity = ProjectArea::class,parentColumns = ["iid"],childColumns = ["ProjectArea_ID"])]
)
data class BH_BoreholeInfo
(
        var completed :Long?,

        @PrimaryKey
        var  iid :Int,

        var Project_ID :Long,

        var ProjectPhase_ID :Long,

        var ProjectArea_ID :Long,

        var code :String?,

        var BoreholeLoc :String?,

        var X :Double?,

        var Y :Double?,

        var Z:Double? ,

        var HoleDepth :Float?,

        var OpeningAngle :Float?,

        var FinalHoleAngle :Float?,

        var HoleDiameter :Float?,

        var EndHoleDiameter :Float?
)
{
        var indexInResponse: Int = -1
}

