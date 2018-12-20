package com.szbm.wh.x.bmlogg.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import com.szbm.wh.x.bmlogg.vo.C_stratum_ext
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface c_stratum_extDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(c: C_stratum_ext?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserts(list:List<C_stratum_ext>?)

    @Query("SELECT * FROM C_stratum_ext")
    fun selectAll(): LiveData<List<C_stratum_ext>>

    @Query("SELECT * FROM C_stratum_ext WHERE iid = :iid")
    fun select(iid:Int): LiveData<C_stratum_ext>
}
