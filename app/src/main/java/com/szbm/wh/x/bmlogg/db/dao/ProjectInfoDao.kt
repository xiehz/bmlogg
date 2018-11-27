package com.szbm.wh.x.bmlogg.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.szbm.wh.x.bmlogg.vo.ProjectInfo

@Dao
interface ProjectInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(projectInfo: ProjectInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserts(list:List<ProjectInfo>)

    @Query("SELECT * FROM ProjectInfo")
    fun selectAll():LiveData<List<ProjectInfo>>

    @Query("SELECT * FROM ProjectInfo WHERE iid = :iid")
    fun select(iid:Int):LiveData<ProjectInfo>

    @Query("SELECT * FROM ProjectInfo WHERE iid = :iid")
    fun selectEntity(iid:Int):ProjectInfo
}
