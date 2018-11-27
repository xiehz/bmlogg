package com.szbm.wh.x.bmlogg.vo

data class Re_BH_CoreCatalog
(
        var bhext_rocksoil_info:List<bhext_rocksoil_info>?,

        var  iid :Int,

        var  borehole_id :Int,

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
    companion object {
        fun convertTo(re_BH_CoreCatalog: Re_BH_CoreCatalog):BH_CoreCatalog {
            return BH_CoreCatalog(re_BH_CoreCatalog.iid,

                    re_BH_CoreCatalog.borehole_id,

                    re_BH_CoreCatalog.step,

                    re_BH_CoreCatalog.depth,

                    re_BH_CoreCatalog.core_acquire_rate,

                    re_BH_CoreCatalog.coresample_take_rate,

                    re_BH_CoreCatalog.rqd,

                    re_BH_CoreCatalog.core_length,

                    re_BH_CoreCatalog.coresample_length,

                    re_BH_CoreCatalog.coresample_length_10,

                    re_BH_CoreCatalog.fissure_num,

                    re_BH_CoreCatalog.weather_level,

                    re_BH_CoreCatalog.begin_no,

                    re_BH_CoreCatalog.end_no,

                    re_BH_CoreCatalog.geology_desc,

                    re_BH_CoreCatalog.line_density,

                    re_BH_CoreCatalog.unloading_level,

                    re_BH_CoreCatalog.box_code,

                    re_BH_CoreCatalog.cake_level,
                    re_BH_CoreCatalog.jointingstatus_low,
                    re_BH_CoreCatalog.jointingstatus_high,
                    re_BH_CoreCatalog.underwater_status,
                    re_BH_CoreCatalog.rock_color,

                    re_BH_CoreCatalog.rock_struct,

                    re_BH_CoreCatalog.picture_link,

                    re_BH_CoreCatalog.stratum_type,

                    re_BH_CoreCatalog.lithology_type,

                    re_BH_CoreCatalog.dip_direction,

                    re_BH_CoreCatalog.dip_angle,

                    re_BH_CoreCatalog.origin,

                    re_BH_CoreCatalog.status,

                    re_BH_CoreCatalog.density,

                    re_BH_CoreCatalog.wet,

                    re_BH_CoreCatalog.MoYuanDu,

                    re_BH_CoreCatalog.YanSe,

                    re_BH_CoreCatalog.TianChongWu,
                    re_BH_CoreCatalog.GuJiaChengFen)
        }

        fun from(bH_CoreCatalog: BH_CoreCatalog,
                 bhext_rocksoil_info:List<bhext_rocksoil_info>? ):Re_BH_CoreCatalog {
            return Re_BH_CoreCatalog(
                    bhext_rocksoil_info,
                    bH_CoreCatalog.iid,

                    bH_CoreCatalog.borehole_id,

                    bH_CoreCatalog.step,

                    bH_CoreCatalog.depth,

                    bH_CoreCatalog.core_acquire_rate,

                    bH_CoreCatalog.coresample_take_rate,

                    bH_CoreCatalog.rqd,

                    bH_CoreCatalog.core_length,

                    bH_CoreCatalog.coresample_length,

                    bH_CoreCatalog.coresample_length_10,

                    bH_CoreCatalog.fissure_num,

                    bH_CoreCatalog.weather_level,

                    bH_CoreCatalog.begin_no,

                    bH_CoreCatalog.end_no,

                    bH_CoreCatalog.geology_desc,

                    bH_CoreCatalog.line_density,

                    bH_CoreCatalog.unloading_level,

                    bH_CoreCatalog.box_code,

                    bH_CoreCatalog.cake_level,
                    bH_CoreCatalog.jointingstatus_low,
                    bH_CoreCatalog.jointingstatus_high,
                    bH_CoreCatalog.underwater_status,
                    bH_CoreCatalog.rock_color,

                    bH_CoreCatalog.rock_struct,

                    bH_CoreCatalog.picture_link,

                    bH_CoreCatalog.stratum_type,

                    bH_CoreCatalog.lithology_type,

                    bH_CoreCatalog.dip_direction,

                    bH_CoreCatalog.dip_angle,

                    bH_CoreCatalog.origin,

                    bH_CoreCatalog.status,

                    bH_CoreCatalog.density,

                    bH_CoreCatalog.wet,

                    bH_CoreCatalog.MoYuanDu,

                    bH_CoreCatalog.YanSe,

                    bH_CoreCatalog.TianChongWu,
                    bH_CoreCatalog.GuJiaChengFen)
        }
    }
}
