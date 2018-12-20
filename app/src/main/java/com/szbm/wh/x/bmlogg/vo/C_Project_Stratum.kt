package com.szbm.wh.x.bmlogg.vo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
        foreignKeys = [ForeignKey(entity = ProjectInfo::class,
                parentColumns = ["iid"],
                childColumns = ["project_id"])]
)
data class C_Project_Stratum
(
        @PrimaryKey
        var  iid :Long,

        var  project_id :Long,

        var  stratum_age :String?,

        var  stratum_code :String?,

        var  lithology_name :String?,

        var  jie :Int?,

        var  xi :Int?,

        var  tong :Int?,

        var  origin :Int?,

        var  status :Int?,

        var  density :Int?,

        var  wet :Int?,

        var  geology_desc :String?,

        var  display_order :Int?,

        var  parent_code :String?,

        var  stratum_level :Int?,

        var  stratum_type :Int?,

        var  weather_level :Int?,

        var  MoYuanDu :Int?,

        var  YanSe :String?,

        var  TianChongWu :String?,

        var  GuJiaChengFen :String?,

        var  abnormal:Int?
)