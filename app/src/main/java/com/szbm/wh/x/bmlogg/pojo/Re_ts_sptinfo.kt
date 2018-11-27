package com.szbm.wh.x.bmlogg.vo

data class Re_ts_sptinfo
(
        var ts_sptdata:List<Re_ts_sptdata>?,

        var iid :Int,

        var ref_id :Int,

        var ref_type :Int?
)
{
    companion object {
        fun converTo(re_ts_sptdata: Re_ts_sptinfo):ts_sptinfo{
            return ts_sptinfo(
                    re_ts_sptdata.iid,
                    re_ts_sptdata.ref_id,
                    re_ts_sptdata.ref_type
            )
        }
        fun from(ts_sptinfo: ts_sptinfo,
                 ts_sptdata:List<Re_ts_sptdata>?):Re_ts_sptinfo{
            return Re_ts_sptinfo(
                    ts_sptdata,
                    ts_sptinfo.iid,
                    ts_sptinfo.ref_id,
                    ts_sptinfo.ref_type)
        }
    }
}