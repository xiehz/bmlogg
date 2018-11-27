package com.szbm.wh.x.bmlogg.vo

data class Re_SampleRecord
(
        var bhext_sampling_info :List<bhext_sampling_info>?,

        var iid :Int,

        var project_id :Int,

        var belong_type :Int,

        var belong_iid :Int,

        var sample_code :String?,

        var sample_depth :Float?,

        var sample_depth_end :Float?,

        var sample_type:Int?
)
{
    companion object {
        fun convertTo(re_SampleRecord: Re_SampleRecord):SampleRecord{
            return SampleRecord(
                    re_SampleRecord.iid ,
                    re_SampleRecord.project_id ,
                    re_SampleRecord.belong_type ,
                    re_SampleRecord.belong_iid ,
                    re_SampleRecord.sample_code ,
                    re_SampleRecord.sample_depth ,
                    re_SampleRecord.sample_depth_end ,
                    re_SampleRecord.sample_type
            )
        }
        fun from(sampleRecord: SampleRecord,
                 bhext_sampling_info: List<bhext_sampling_info>?):Re_SampleRecord{
            return Re_SampleRecord(
                    bhext_sampling_info,
                    sampleRecord.iid ,
                    sampleRecord.project_id ,
                    sampleRecord.belong_type ,
                    sampleRecord.belong_iid ,
                    sampleRecord.sample_code ,
                    sampleRecord.sample_depth ,
                    sampleRecord.sample_depth_end ,
                    sampleRecord.sample_type
               )
        }
    }
}