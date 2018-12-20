package com.szbm.wh.x.bmlogg.vo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
        foreignKeys = [ForeignKey(entity = ProjectInfo::class,
                parentColumns = ["iid"],
                childColumns = ["project_id"])]
)
data class C_Project_Lithology
(
        @PrimaryKey
        var iid :Long,

        var project_id :Long ,

        var lithology_name :String? ,

        var pattern :String?,

        var path :String?,

        var display_order :Int?,

        var mi:Int?

)

