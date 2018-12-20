package com.szbm.wh.x.bmlogg.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.szbm.wh.x.bmlogg.vo.Ts_sptdata


@Dao
interface ts_sptdataDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(projectInfo: Ts_sptdata)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserts(list:List<Ts_sptdata>)

    @Query("SELECT * FROM Ts_sptdata")
    fun selectAll(): LiveData<List<Ts_sptdata>>

    @Query("SELECT * FROM Ts_sptdata WHERE iid = :iid")
    fun select(iid:Int): LiveData<Ts_sptdata>

    @Query("SELECT MAX(iid) FROM Ts_sptdata")
    fun selectMaxWithoutLife(): Long
}
