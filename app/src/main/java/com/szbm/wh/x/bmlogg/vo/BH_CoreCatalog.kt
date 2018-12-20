package com.szbm.wh.x.bmlogg.vo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
        foreignKeys = [ForeignKey(
                entity = BH_BoreholeInfo::class,
                parentColumns = ["iid"],
                childColumns =["borehole_id"])]
)
data class BH_CoreCatalog
(
        @PrimaryKey
        var  iid :Long,

        var  borehole_id :Long,

        var  step :Float?,

        var  depth :Float?,

        var  core_acquire_rate :Double?,

        var  coresample_take_rate :Double?,

        var  rqd:Double? ,

        var  core_length :Float?,

        var  coresample_length :Float?,

        var  coresample_length_10 :Float?,

        var  fissure_num :Int?,

        var  weather_level :Int?,

        var  begin_no :String?,

        var  end_no :String?,

        var  geology_desc :String?,

        var  line_density :Double?,

        var  unloading_level :Int? ,

        var  box_code :String?,

        var cake_level :Int?,

        var jointingstatus_low :Int?,

        var jointingstatus_high :Int?,

        var underwater_status :Int?,

        var  rock_color :String?,

        var  rock_struct :String?,

        var  picture_link :String?,

        var  stratum_type :String?,

        var  lithology_type :String?,

        var  dip_direction :Double?,

        var  dip_angle :Double?,

        var  origin :Int?,

        var  status :Int?,

        var  density :Int?,

        var  wet :Int?,

        var  MoYuanDu :Int?,

        var  YanSe :String?,

        var TianChongWu :String ?,

        var GuJiaChengFen :String?
)
{
        var from_server:Int? = 0 // 默认从服务器上来 <>0 则是本地新建
}