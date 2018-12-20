package com.szbm.wh.x.bmlogg.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.szbm.wh.x.bmlogg.vo.BoreholeSet
import com.szbm.wh.x.bmlogg.vo.ProjectBoreholes

@Dao
interface  ProjectBoreholesDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(projectBoreholes: ProjectBoreholes?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list:List<ProjectBoreholes>?)

    @Query("SELECT * FROM ProjectBoreholes")
    fun selectAll(): LiveData<List<ProjectBoreholes>>

    @Query("SELECT * FROM ProjectBoreholes WHERE iid = :iid")
    fun select(iid:Long): LiveData<ProjectBoreholes>

    @Query("SELECT * FROM ProjectBoreholes WHERE iid = :iid")
    fun selectWithoutLive(iid:Long): ProjectBoreholes?
}