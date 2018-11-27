package com.szbm.wh.x.bmlogg.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.szbm.wh.x.bmlogg.vo.bhext_rocksoil_info


@Dao
interface bhext_rocksoil_infoDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(projectInfo: bhext_rocksoil_info)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserts(list:List<bhext_rocksoil_info>)

    @Query("SELECT * FROM bhext_rocksoil_info")
    fun selectAll(): LiveData<List<bhext_rocksoil_info>>

    @Query("SELECT * FROM bhext_rocksoil_info WHERE iid = :iid")
    fun select(iid:Int): LiveData<bhext_rocksoil_info>
}
