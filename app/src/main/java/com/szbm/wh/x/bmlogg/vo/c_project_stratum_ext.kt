package com.szbm.wh.x.bmlogg.vo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
        foreignKeys = [ForeignKey(entity = ProjectInfo::class,
                parentColumns = ["iid"],
                childColumns = ["project_id"])]
)
data class c_project_stratum_ext
(

        var  project_id :Int,

        var name :String?,

        var display_order :Int?
)
{
    @PrimaryKey(autoGenerate = true)
    var iid :Int = 0
}