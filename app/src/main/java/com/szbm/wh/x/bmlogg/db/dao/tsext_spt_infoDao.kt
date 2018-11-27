package com.szbm.wh.x.bmlogg.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.szbm.wh.x.bmlogg.vo.tsext_spt_info


@Dao
interface tsext_spt_infoDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(projectInfo: tsext_spt_info)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserts(list:List<tsext_spt_info>)

    @Query("SELECT * FROM tsext_spt_info")
    fun selectAll(): LiveData<List<tsext_spt_info>>

    @Query("SELECT * FROM tsext_spt_info WHERE iid = :iid")
    fun select(iid:Int): LiveData<tsext_spt_info>
}
