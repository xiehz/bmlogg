package com.szbm.wh.x.bmlogg.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import com.szbm.wh.x.bmlogg.vo.C_Project_Stratum
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface C_Project_StratumDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(c_Project_Stratum: C_Project_Stratum?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserts(list:List<C_Project_Stratum>?)

    @Query("SELECT * FROM C_Project_Stratum")
    fun selectAll(): LiveData<List<C_Project_Stratum>>

    @Query("SELECT * FROM C_Project_Stratum WHERE iid = :iid")
    fun select(iid:Int): LiveData<C_Project_Stratum>
}
