package com.szbm.wh.x.bmlogg.vo

data class Re_bh_extra_info
(
        var bh_begin_info:List<Re_bh_begin_info>?,
        var bh_end_info:List<Re_bh_end_info>?,

        var iid :Int,

        var borehole_iid :Int,

        var creator_iid :Int?,

        var  _long :Double?,

        var lat :Double?,

        var B :Double?,

        var L :Double?,

        var H :Double?,

        var comment:String?
){
    companion object {
        fun converTo(re_bh_extra_info: Re_bh_extra_info):bh_extra_info{
            return bh_extra_info(
                    re_bh_extra_info.iid,
                    re_bh_extra_info.borehole_iid,
                    re_bh_extra_info.creator_iid,
                    re_bh_extra_info. _long,
                    re_bh_extra_info.lat ,
                    re_bh_extra_info.B ,
                    re_bh_extra_info.L ,
                    re_bh_extra_info.H ,
                    re_bh_extra_info.comment
            )
        }
        fun from(bh_extra_info: bh_extra_info,
                 bh_begin_info: List<Re_bh_begin_info>?,
                 bh_end_info: List<Re_bh_end_info>?):Re_bh_extra_info{
            return Re_bh_extra_info(
                    bh_begin_info,
                    bh_end_info,
                    bh_extra_info.iid,
                    bh_extra_info.borehole_iid,
                    bh_extra_info.creator_iid,
                    bh_extra_info. _long,
                    bh_extra_info.lat ,
                    bh_extra_info.B ,
                    bh_extra_info.L ,
                    bh_extra_info.H ,
                    bh_extra_info.comment)
        }
    }
}


