package com.szbm.wh.x.bmlogg.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import com.szbm.wh.x.bmlogg.vo.ProjectPhase
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProjectPhaseDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(projectPhase: ProjectPhase?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserts(list:List<ProjectPhase>?)

    @Query("SELECT * FROM ProjectPhase")
    fun selectAll(): LiveData<List<ProjectPhase>>

    @Query("SELECT * FROM ProjectPhase WHERE iid = :iid")
    fun select(iid:Int): LiveData<ProjectPhase>

    @Query("SELECT * FROM ProjectPhase WHERE iid = :iid")
    fun selectEntity(iid:Int): ProjectPhase

    @Query("SELECT * FROM ProjectPhase WHERE project_id = :project_id")
    fun selectByProject(project_id:Int): List<ProjectPhase>
}
