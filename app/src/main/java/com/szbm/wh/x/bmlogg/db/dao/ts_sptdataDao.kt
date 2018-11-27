package com.szbm.wh.x.bmlogg.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.szbm.wh.x.bmlogg.vo.ts_sptdata


@Dao
interface ts_sptdataDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(projectInfo: ts_sptdata)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserts(list:List<ts_sptdata>)

    @Query("SELECT * FROM ts_sptdata")
    fun selectAll(): LiveData<List<ts_sptdata>>

    @Query("SELECT * FROM ts_sptdata WHERE iid = :iid")
    fun select(iid:Int): LiveData<ts_sptdata>
}
