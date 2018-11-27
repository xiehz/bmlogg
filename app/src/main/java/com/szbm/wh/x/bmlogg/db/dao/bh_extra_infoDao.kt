package com.szbm.wh.x.bmlogg.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.szbm.wh.x.bmlogg.vo.bh_extra_info


@Dao
interface bh_extra_infoDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(projectInfo: bh_extra_info)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserts(list:List<bh_extra_info>)

    @Query("SELECT * FROM bh_extra_info")
    fun selectAll(): LiveData<List<bh_extra_info>>

    @Query("SELECT * FROM bh_extra_info WHERE iid = :iid")
    fun select(iid:Int): LiveData<bh_extra_info>
}
