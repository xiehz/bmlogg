package com.szbm.wh.x.bmlogg.pojo

import com.szbm.wh.x.bmlogg.vo.BH_Logger
import com.szbm.wh.x.bmlogg.vo.Tsext_spt_info

data class Re_tsext_spt_info(
        var bh_logger: BH_Logger?,
        var iid :Long,
        var borehole_extra_iid :Long,
        var bhspt_iid :Long,
        var logger_iid :Long,
        var  comment :String?
        ){
    companion object {
        fun converTo(re: Re_tsext_spt_info):Tsext_spt_info{
            return Tsext_spt_info(
                    re.iid ,
                    re.borehole_extra_iid ,
                    re.bhspt_iid ,
                    re.logger_iid ,
                    re.comment
            )
        }
        fun from(tsext_spt_info: Tsext_spt_info,
                 bh_logger: BH_Logger?):Re_tsext_spt_info{
            return Re_tsext_spt_info(
                    bh_logger,
                    tsext_spt_info.iid ,
                    tsext_spt_info.borehole_extra_iid ,
                    tsext_spt_info.bhspt_iid ,
                    tsext_spt_info.logger_iid ,
                    tsext_spt_info.comment
            )
        }
    }
}