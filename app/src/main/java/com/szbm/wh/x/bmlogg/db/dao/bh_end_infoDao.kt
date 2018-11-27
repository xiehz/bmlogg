package com.szbm.wh.x.bmlogg.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.szbm.wh.x.bmlogg.vo.bh_end_info


@Dao
interface bh_end_infoDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(projectInfo: bh_end_info)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserts(list:List<bh_end_info>)

    @Query("SELECT * FROM bh_end_info")
    fun selectAll(): LiveData<List<bh_end_info>>

    @Query("SELECT * FROM bh_end_info WHERE iid = :iid")
    fun select(iid:Int): LiveData<bh_end_info>
}
