package com.szbm.wh.x.bmlogg.vo

data class Re_bh_end_info
(
        var bh_logger: BH_Logger?,

        var iid :Int,

        var borehole_extra_iid :Int,

        var logger_iid :Int,

        var comment :String?
)
{
    companion object {
        fun converTo(re_bh_end_info: Re_bh_end_info):bh_end_info{
            return bh_end_info(
                    re_bh_end_info.iid,
                    re_bh_end_info.borehole_extra_iid ,
                    re_bh_end_info.logger_iid ,
                    re_bh_end_info.comment
            )
        }
        fun from(bh_end_info: bh_end_info,
                 bh_logger: BH_Logger?):Re_bh_end_info{
            return Re_bh_end_info(
                    bh_logger,
                    bh_end_info.iid,
                    bh_end_info.borehole_extra_iid ,
                    bh_end_info.logger_iid ,
                    bh_end_info.comment)
        }
    }
}

