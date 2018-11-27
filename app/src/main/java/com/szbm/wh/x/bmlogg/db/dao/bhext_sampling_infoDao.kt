package com.szbm.wh.x.bmlogg.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.szbm.wh.x.bmlogg.vo.bhext_sampling_info


@Dao
interface bhext_sampling_infoDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(projectInfo: bhext_sampling_info)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserts(list:List<bhext_sampling_info>)

    @Query("SELECT * FROM bhext_sampling_info")
    fun selectAll(): LiveData<List<bhext_sampling_info>>

    @Query("SELECT * FROM bhext_sampling_info WHERE iid = :iid")
    fun select(iid:Int): LiveData<bhext_sampling_info>
}
