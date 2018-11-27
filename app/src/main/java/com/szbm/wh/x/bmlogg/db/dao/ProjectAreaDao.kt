package com.szbm.wh.x.bmlogg.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import com.szbm.wh.x.bmlogg.vo.ProjectArea
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.szbm.wh.x.bmlogg.vo.ProjectPhase

@Dao
interface ProjectAreaDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(projectArea: ProjectArea?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserts(list:List<ProjectArea>?)

    @Query("SELECT * FROM ProjectArea")
    fun selectAll(): LiveData<List<ProjectArea>>

    @Query("SELECT * FROM ProjectArea WHERE iid = :iid")
    fun select(iid:Int): LiveData<ProjectArea>

    @Query("SELECT * FROM ProjectArea WHERE iid = :iid")
    fun selectEntity(iid:Int): ProjectArea

    @Query("SELECT * FROM ProjectArea WHERE project_id = :project_id AND phase_id = :phase_id")
    fun selectByProject(project_id:Int,phase_id:Int): List<ProjectArea>
}
