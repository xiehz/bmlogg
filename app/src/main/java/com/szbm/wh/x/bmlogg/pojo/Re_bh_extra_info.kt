package com.szbm.wh.x.bmlogg.vo

data class Re_bh_extra_info
(
        var bh_begin_info:List<Re_bh_begin_info>?,
        var bh_end_info:List<Re_bh_end_info>?,
        var bh_geo_date:List<Bh_geo_date>?,
        var bh_imagesinfo:List<Bh_imagesinfo>?,

        var iid :Long,

        var borehole_iid :Long,

        var  _long :Double?,

        var lat :Double?,

        var B :Double?,

        var L :Double?,

        var H :Double?,

        var comment:String?
){
    companion object {
        fun converTo(re_bh_extra_info: Re_bh_extra_info):Bh_extra_info{
            return Bh_extra_info(
                    re_bh_extra_info.iid,
                    re_bh_extra_info.borehole_iid,
                    re_bh_extra_info. _long,
                    re_bh_extra_info.lat ,
                    re_bh_extra_info.B ,
                    re_bh_extra_info.L ,
                    re_bh_extra_info.H ,
                    re_bh_extra_info.comment
            )
        }
        fun from(bh_extra_info: Bh_extra_info,
                 bh_begin_info: List<Re_bh_begin_info>?,
                 bh_end_info: List<Re_bh_end_info>?,
                 bh_geo_date:List<Bh_geo_date>?,
                 bh_imagesinfo:List<Bh_imagesinfo>?):Re_bh_extra_info{
            return Re_bh_extra_info(
                    bh_begin_info,
                    bh_end_info,
                    bh_geo_date,
                    bh_imagesinfo,
                    bh_extra_info.iid,
                    bh_extra_info.borehole_iid,
                    bh_extra_info. _long,
                    bh_extra_info.lat ,
                    bh_extra_info.B ,
                    bh_extra_info.L ,
                    bh_extra_info.H ,
                    bh_extra_info.comment)
        }
    }
}


