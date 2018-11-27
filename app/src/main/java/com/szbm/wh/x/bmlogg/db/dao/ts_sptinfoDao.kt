package com.szbm.wh.x.bmlogg.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.szbm.wh.x.bmlogg.vo.ts_sptinfo


@Dao
interface ts_sptinfoDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(projectInfo: ts_sptinfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserts(list:List<ts_sptinfo>)

    @Query("SELECT * FROM ts_sptinfo")
    fun selectAll(): LiveData<List<ts_sptinfo>>

    @Query("SELECT * FROM ts_sptinfo WHERE iid = :iid")
    fun select(iid:Int): LiveData<ts_sptinfo>
}
