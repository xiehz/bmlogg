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
data class Sec_lineinfo
(
        @PrimaryKey
        var IID :Long,

        var Project_ID :Long,

        var ProjectPhase_ID :Long,

        var ProjectArea_ID :Long,

        var code :String?,

        var type :Int?
)
{
    var from_server:Int? = 0 // 默认从服务器上来 <>0 则是本地新建
}