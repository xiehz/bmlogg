package com.szbm.wh.x.bmlogg.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.szbm.wh.x.bmlogg.vo.Tsext_spt_info


@Dao
interface tsext_spt_infoDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(projectInfo: Tsext_spt_info)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserts(list:List<Tsext_spt_info>)

    @Query("SELECT * FROM Tsext_spt_info")
    fun selectAll(): LiveData<List<Tsext_spt_info>>

    @Query("SELECT * FROM Tsext_spt_info WHERE iid = :iid")
    fun select(iid:Long): LiveData<Tsext_spt_info>

    @Query("SELECT COUNT(*) FROM Tsext_spt_info WHERE borehole_extra_iid = :extra_iid")
    fun countByExtra(extra_iid :Long) :Int

    @Query("SELECT MAX(iid) FROM Tsext_spt_info")
    fun selectMaxWithoutLife(): Long
}
