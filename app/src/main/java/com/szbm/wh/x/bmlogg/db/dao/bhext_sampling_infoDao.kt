package com.szbm.wh.x.bmlogg.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.szbm.wh.x.bmlogg.vo.Bhext_sampling_info


@Dao
interface bhext_sampling_infoDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(projectInfo: Bhext_sampling_info)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserts(list:List<Bhext_sampling_info>)

    @Query("SELECT * FROM Bhext_sampling_info")
    fun selectAll(): LiveData<List<Bhext_sampling_info>>

    @Query("SELECT * FROM Bhext_sampling_info WHERE iid = :iid")
    fun select(iid:Long): LiveData<Bhext_sampling_info>

    @Query("SELECT COUNT(*) FROM Bhext_sampling_info WHERE borehole_extra_iid = :extra_iid")
    fun countByExtra(extra_iid :Long) :Int

    @Query("SELECT MAX(iid) FROM Bhext_sampling_info")
    fun selectMaxWithoutLife(): Long
}
