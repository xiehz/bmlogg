package com.szbm.wh.x.bmlogg.vo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.szbm.wh.x.bmlogg.vo.ProjectArea

@Entity(
        foreignKeys = [
                ForeignKey(entity = ProjectInfo::class,parentColumns = ["iid"],childColumns = ["Project_ID"]),
                ForeignKey(entity = ProjectPhase::class,parentColumns = ["iid"],childColumns = ["ProjectPhase_ID"]),
                ForeignKey(entity = ProjectArea::class,parentColumns = ["iid"],childColumns = ["ProjectArea_ID"])]
)
data class sec_lineinfo
(
        @PrimaryKey
        var IID :Int,

        var Project_ID :Int,

        var ProjectPhase_ID :Int,

        var ProjectArea_ID :Int,

        var code :String?,

        var type :Int?
)
