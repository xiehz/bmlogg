package com.szbm.wh.x.bmlogg.vo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class C_PubDic
(
        @PrimaryKey
        var  iid :Long ,

        var  catalog_name :String? ,

        var  value :Int?,

        var  text :String?,


        var  abbr :String?,

        var  display_order :Int?
)
