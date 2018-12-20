package com.szbm.wh.x.bmlogg.db.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.szbm.wh.x.bmlogg.vo.BH_BoreholeInfo
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
    fun select(iid:Long):LiveData<ProjectInfo>

    @Query("SELECT * FROM ProjectInfo WHERE iid = :iid")
    fun selectEntity(iid:Long):ProjectInfo?

    @Query("select * from ProjectInfo join BH_Logger_Project on ProjectInfo.iid = BH_Logger_Project.project_id  where BH_Logger_Project.number = :number order by ProjectInfo.iid COLLATE NOCASE ASC")
    fun factoryAll(number:Long): DataSource.Factory<Int, ProjectInfo>
}
