package com.szbm.wh.x.bmlogg.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.szbm.wh.x.bmlogg.vo.BoreholeSet

@Dao
interface  BoreholeSetDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(boreholeSet: BoreholeSet)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list:List<BoreholeSet>?)

    @Query("SELECT * FROM BoreholeSet")
    fun selectAll(): LiveData<List<BoreholeSet>>

    @Query("SELECT * FROM BoreholeSet WHERE iid = :iid")
    fun select(iid:Long): LiveData<BoreholeSet>
}