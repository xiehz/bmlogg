package com.szbm.wh.x.bmlogg.vo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = BH_BoreholeInfo::class,
        parentColumns = ["iid"],childColumns = ["ref_id"])])
data class Ts_sptinfo
(
        @PrimaryKey
        var iid :Long,

        var ref_id :Long,

        var ref_type :Int?
){
        var from_server:Int? = 0 // 默认从服务器上来 <>0 则是本地新建
}
