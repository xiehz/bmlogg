package com.szbm.wh.x.bmlogg.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import com.szbm.wh.x.bmlogg.vo.C_Project_Lithology
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface C_Project_LithologyDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(c_Project_Lithology: C_Project_Lithology?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserts(list:List<C_Project_Lithology>?)

    @Query("SELECT * FROM C_Project_Lithology")
    fun selectAll(): LiveData<List<C_Project_Lithology>>

    @Query("SELECT * FROM C_Project_Lithology WHERE iid = :iid")
    fun select(iid:Int): LiveData<C_Project_Lithology>
}
