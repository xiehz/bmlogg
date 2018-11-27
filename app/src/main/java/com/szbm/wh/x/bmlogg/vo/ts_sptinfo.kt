package com.szbm.wh.x.bmlogg.vo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = BH_BoreholeInfo::class,
        parentColumns = ["iid"],childColumns = ["ref_id"])])
data class ts_sptinfo
(
        @PrimaryKey
        var iid :Int,

        var ref_id :Int,

        var ref_type :Int?
)
