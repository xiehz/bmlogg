package com.szbm.wh.x.bmlogg.vo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
        foreignKeys = [ForeignKey(entity = ProjectInfo::class,parentColumns = ["iid"],
                childColumns = ["project_id"]),
        ForeignKey(entity = BH_BoreholeInfo::class,
                parentColumns = ["iid"],
                childColumns = ["belong_iid"])]
)
data class SampleRecord
(
        @PrimaryKey
        var iid :Long,

        var project_id :Long,

        var belong_type :Int,

        var belong_iid :Long,

        var sample_code :String?,

        var sample_depth :Float?,

        var sample_depth_end :Float?,

        var sample_type:Int?
)
{
        var from_server:Int? = 0 // 默认从服务器上来 <>0 则是本地新建
}