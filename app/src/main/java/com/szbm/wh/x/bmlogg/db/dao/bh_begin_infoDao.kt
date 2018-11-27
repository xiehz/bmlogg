package com.szbm.wh.x.bmlogg.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.szbm.wh.x.bmlogg.vo.bh_begin_info


@Dao
interface bh_begin_infoDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(projectInfo: bh_begin_info)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserts(list:List<bh_begin_info>)

    @Query("SELECT * FROM bh_begin_info")
    fun selectAll(): LiveData<List<bh_begin_info>>

    @Query("SELECT * FROM bh_begin_info WHERE iid = :iid")
    fun select(iid:Int): LiveData<bh_begin_info>
}
