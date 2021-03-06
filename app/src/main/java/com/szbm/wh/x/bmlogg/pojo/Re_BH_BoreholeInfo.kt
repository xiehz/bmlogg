package com.szbm.wh.x.bmlogg.vo

data class Re_BH_BoreholeInfo
(
        var BH_CoreCatalog: List<Re_BH_CoreCatalog>?,

        var bh_extra_info :List<Re_bh_extra_info>?,

        var BH_SampleRecord:List<Re_SampleRecord>?,

        var sec_linebh:List<Sec_linebh>?,

        var ts_sptinfo:List<Re_ts_sptinfo>?,

        var completed :Long?,

        var  iid :Long,

        var Project_ID :Long,

        var ProjectPhase_ID :Long,

        var ProjectArea_ID :Long,

        var code :String?,

        var BoreholeLoc :String?,

        var X :Double?,

        var Y :Double?,

        var Z:Double?,

        var HoleDepth :Float?,

        var OpeningAngle :Float?,

        var FinalHoleAngle :Float?,

        var HoleDiameter :Float?,

        var EndHoleDiameter :Float?
){
    companion object {
        fun convertTo(re_BH_BoreholeInfo: Re_BH_BoreholeInfo):BH_BoreholeInfo{
            return BH_BoreholeInfo(
                    re_BH_BoreholeInfo.completed,
            re_BH_BoreholeInfo.iid ,
            re_BH_BoreholeInfo.Project_ID ,
            re_BH_BoreholeInfo.ProjectPhase_ID ,
            re_BH_BoreholeInfo.ProjectArea_ID ,
            re_BH_BoreholeInfo.code ,

            re_BH_BoreholeInfo.BoreholeLoc ,

            re_BH_BoreholeInfo.X ,

            re_BH_BoreholeInfo.Y ,

            re_BH_BoreholeInfo.Z,

            re_BH_BoreholeInfo.HoleDepth ,

            re_BH_BoreholeInfo.OpeningAngle ,

            re_BH_BoreholeInfo.FinalHoleAngle ,

            re_BH_BoreholeInfo.HoleDiameter ,

            re_BH_BoreholeInfo.EndHoleDiameter
            )
        }

        fun from(BH_BoreholeInfo: BH_BoreholeInfo,
                 bH_CoreCatalog: List<Re_BH_CoreCatalog>?,
                 bh_extra_info :List<Re_bh_extra_info>?,
                 BH_SampleRecord:List<Re_SampleRecord>?,
                 sec_linebh:List<Sec_linebh>?,
                 ts_sptinfo:List<Re_ts_sptinfo>?):Re_BH_BoreholeInfo{
            return Re_BH_BoreholeInfo(
                    bH_CoreCatalog ,
                    bh_extra_info,
                    BH_SampleRecord,
                    sec_linebh,
                    ts_sptinfo,
                    BH_BoreholeInfo.completed,
                    BH_BoreholeInfo.iid ,
                    BH_BoreholeInfo.Project_ID ,
                    BH_BoreholeInfo.ProjectPhase_ID ,
                    BH_BoreholeInfo.ProjectArea_ID ,
                    BH_BoreholeInfo.code ,
                    BH_BoreholeInfo.BoreholeLoc ,
                    BH_BoreholeInfo.X ,
                    BH_BoreholeInfo.Y ,
                    BH_BoreholeInfo.Z,
                    BH_BoreholeInfo.HoleDepth ,
                    BH_BoreholeInfo.OpeningAngle ,
                    BH_BoreholeInfo.FinalHoleAngle ,
                    BH_BoreholeInfo.HoleDiameter ,
                    BH_BoreholeInfo.EndHoleDiameter
            )
        }
    }
}

