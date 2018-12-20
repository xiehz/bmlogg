package com.szbm.wh.x.bmlogg.vo

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = BH_Logger::class,parentColumns = ["number"],
        childColumns = ["number"]),
    ForeignKey(entity = ProjectInfo::class,parentColumns = ["iid"],
            childColumns = ["project_id"])])
data class BH_Logger_Project(
        var number:Long,
        var project_id:Long
)
{
    @PrimaryKey(autoGenerate = true)
    var iid:Long = 0
}
