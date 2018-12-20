package com.szbm.wh.x.bmlogg.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.szbm.wh.x.bmlogg.vo.Bh_extra_info


@Dao
interface bh_extra_infoDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(projectInfo: Bh_extra_info)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserts(list:List<Bh_extra_info>)

    @Query("SELECT * FROM Bh_extra_info")
    fun selectAll(): LiveData<List<Bh_extra_info>>

    @Query("SELECT * FROM Bh_extra_info WHERE iid = :iid")
    fun select(iid:Long): LiveData<Bh_extra_info>

    @Query("select Bh_begin_info.iid from Bh_extra_info join Bh_begin_info on Bh_begin_info.borehole_extra_iid = Bh_extra_info.iid where Bh_extra_info.iid = :iid")
    fun select_bh_begin_info(iid :Long) :Long?

    @Query("select Bh_end_info.iid from Bh_extra_info join Bh_end_info on Bh_end_info.borehole_extra_iid = Bh_extra_info.iid where Bh_extra_info.iid = :iid")
    fun select_bh_end_info(iid :Long) :Long?

    @Query("SELECT MAX(iid) FROM Bh_extra_info")
    fun selectMaxWithoutLife(): Long
}
