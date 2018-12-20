package com.szbm.wh.x.bmlogg.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.szbm.wh.x.bmlogg.vo.Ts_sptinfo


@Dao
interface ts_sptinfoDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(projectInfo: Ts_sptinfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserts(list:List<Ts_sptinfo>)

    @Query("SELECT * FROM Ts_sptinfo")
    fun selectAll(): LiveData<List<Ts_sptinfo>>

    @Query("SELECT * FROM Ts_sptinfo WHERE iid = :iid")
    fun select(iid:Int): LiveData<Ts_sptinfo>

    @Query("SELECT MAX(iid) FROM Ts_sptinfo")
    fun selectMaxWithoutLife(): Long
}
