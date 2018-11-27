package com.szbm.wh.x.bmlogg.vo

import com.szbm.wh.x.bmlogg.pojo.Re_tsext_spt_info

data class Re_ts_sptdata
(
        var tsext_spt_info :List<Re_tsext_spt_info>?,

        var iid :Int,

        var spt_id :Int,

        var begin_depth :Float?,

        var end_depth :Float?,

        var pole_length :Float?,

        var hit_count :Float?,

        var hit_count_once :Float?,

        var ignore :Boolean?
)
{
    companion object {
        fun converTo(re_ts_sptdata: Re_ts_sptdata):ts_sptdata{
            return ts_sptdata(
                    re_ts_sptdata.iid ,
                    re_ts_sptdata.spt_id,
                    re_ts_sptdata.begin_depth,
                    re_ts_sptdata.end_depth,
                    re_ts_sptdata.pole_length,
                    re_ts_sptdata.hit_count,
                    re_ts_sptdata.hit_count_once,
                    re_ts_sptdata.ignore
            )
        }
        fun from(ts_sptdata: ts_sptdata,
                 tsext_spt_info :List<Re_tsext_spt_info>?):Re_ts_sptdata{
            return Re_ts_sptdata(
                    tsext_spt_info,
                    ts_sptdata.iid ,
                    ts_sptdata.spt_id,
                    ts_sptdata.begin_depth,
                    ts_sptdata.end_depth,
                    ts_sptdata.pole_length,
                    ts_sptdata.hit_count,
                    ts_sptdata.hit_count_once,
                    ts_sptdata.ignore
            )
        }
    }
}