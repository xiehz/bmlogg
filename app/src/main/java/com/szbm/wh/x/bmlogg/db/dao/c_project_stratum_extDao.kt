package com.szbm.wh.x.bmlogg.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import com.szbm.wh.x.bmlogg.vo.c_project_stratum_ext
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface c_project_stratum_extDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(c_pr: c_project_stratum_ext?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserts(list:List<c_project_stratum_ext>?)

    @Query("SELECT * FROM c_project_stratum_ext")
    fun selectAll(): LiveData<List<c_project_stratum_ext>>

    @Query("SELECT * FROM c_project_stratum_ext WHERE iid = :iid")
    fun select(iid:Int): LiveData<c_project_stratum_ext>
}
