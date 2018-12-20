package com.szbm.wh.x.bmlogg.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.szbm.wh.x.bmlogg.vo.Bh_begin_info


@Dao
interface bh_begin_infoDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(projectInfo: Bh_begin_info)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserts(list:List<Bh_begin_info>)

    @Query("SELECT * FROM Bh_begin_info")
    fun selectAll(): LiveData<List<Bh_begin_info>>

    @Query("SELECT * FROM Bh_begin_info WHERE iid = :iid")
    fun select(iid:Long): LiveData<Bh_begin_info>

    @Query("SELECT * FROM Bh_begin_info WHERE iid = :iid")
    fun selectEntity(iid:Long): Bh_begin_info?

    @Query("SELECT * FROM Bh_begin_info WHERE borehole_extra_iid = :extra_iid")
    fun selectByExtra(extra_iid :Long) :List<Bh_begin_info>

    @Query("SELECT Bh_geo_date.time FROM Bh_begin_info JOIN Bh_geo_date ON Bh_geo_date.instanceoft = Bh_begin_info.iid AND Bh_geo_date.typeoft = 'Bh_begin_info' WHERE Bh_begin_info.iid =:iid")
    fun selectTime(iid :Long):LiveData<String>

    @Query("SELECT MAX(iid) FROM Bh_begin_info")
    fun selectMaxWithoutLife(): Long

}
