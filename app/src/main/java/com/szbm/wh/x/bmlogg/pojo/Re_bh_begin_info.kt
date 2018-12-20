package com.szbm.wh.x.bmlogg.vo

data class Re_bh_begin_info
(
        var bh_logger: BH_Logger?,

        var iid :Long,

        var borehole_extra_iid :Long,

        var logger_iid :Long,

        var comment :String?
)
{
    companion object {
        fun converTo(re_bh_begin_info: Re_bh_begin_info):Bh_begin_info{
            return Bh_begin_info(
                    re_bh_begin_info.iid,
                    re_bh_begin_info.borehole_extra_iid ,
                    re_bh_begin_info.logger_iid ,
                    re_bh_begin_info.comment
            )
        }
        fun from(bh_begin_info: Bh_begin_info,
                 bh_logger: BH_Logger?):Re_bh_begin_info{
            return Re_bh_begin_info(
                    bh_logger,
                    bh_begin_info.iid,
                    bh_begin_info.borehole_extra_iid ,
                    bh_begin_info.logger_iid ,
                    bh_begin_info.comment)
        }
    }
}

