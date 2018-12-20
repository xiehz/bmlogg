package com.szbm.wh.x.bmlogg.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.szbm.wh.x.bmlogg.vo.Bh_end_info


@Dao
interface bh_end_infoDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(projectInfo: Bh_end_info)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserts(list:List<Bh_end_info>)

    @Query("SELECT * FROM Bh_end_info")
    fun selectAll(): LiveData<List<Bh_end_info>>

    @Query("SELECT * FROM Bh_end_info WHERE iid = :iid")
    fun select(iid:Long): LiveData<Bh_end_info>

    @Query("SELECT * FROM Bh_end_info WHERE borehole_extra_iid = :extra_iid")
    fun selectByExtra(extra_iid :Long) :List<Bh_end_info>

    @Query("SELECT Bh_geo_date.time FROM Bh_end_info JOIN Bh_geo_date ON Bh_geo_date.instanceoft = Bh_end_info.iid AND Bh_geo_date.typeoft = 'Bh_end_info' WHERE Bh_end_info.iid =:iid")
    fun selectTime(iid :Long):LiveData<String>

    @Query("SELECT MAX(iid) FROM Bh_end_info")
    fun selectMaxWithoutLife(): Long
}
