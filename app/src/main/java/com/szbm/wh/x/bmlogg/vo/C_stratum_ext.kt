package com.szbm.wh.x.bmlogg.vo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class C_stratum_ext
(

        var  name :String? ,

        var  fieldname :String?,

        var  valuetype :Int?,

        var  data_provider :String?,

        var  display_order :Int?
){
    @PrimaryKey(autoGenerate = true)
    var iid :Long = 0
}