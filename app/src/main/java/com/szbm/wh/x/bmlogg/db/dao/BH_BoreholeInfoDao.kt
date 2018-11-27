package com.szbm.wh.x.bmlogg.db.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.szbm.wh.x.bmlogg.vo.BH_BoreholeInfo


@Dao
interface BH_BoreholeInfoDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(projectInfo: BH_BoreholeInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserts(list:List<BH_BoreholeInfo>)

    @Query("SELECT * FROM BH_BoreholeInfo")
    fun selectAll(): LiveData<List<BH_BoreholeInfo>>

    @Query("SELECT * FROM BH_BoreholeInfo WHERE iid = :iid")
    fun select(iid:Int): LiveData<BH_BoreholeInfo>

    @Query("SELECT * FROM BH_BoreholeInfo ORDER BY iid COLLATE NOCASE ASC")
    fun factoryAll(): DataSource.Factory<Int, BH_BoreholeInfo>

    @Query("SELECT * FROM BH_BoreholeInfo WHERE Project_ID =:project ORDER BY iid ASC")
    fun boreholesByProject(project:Int) : DataSource.Factory<Int, BH_BoreholeInfo>

    @Query("DELETE FROM BH_BoreholeInfo WHERE Project_ID =:project")
    fun deleteByProject(project:Int)

    @Query("SELECT MAX(indexInResponse) + 1 FROM BH_BoreholeInfo WHERE Project_ID =:project")
    fun getNextIndexInProject(project:Int) : Int

    @Query("SELECT * FROM BH_BoreholeInfo WHERE  Project_ID =:project AND code LIKE:code ORDER BY indexInResponse ASC")
    fun boreholesByProjectAndCode(project:Int,code : String) : DataSource.Factory<Int, BH_BoreholeInfo>

    @Query("DELETE FROM BH_BoreholeInfo WHERE Project_ID =:project AND code LIKE :code")
    fun deleteByProjectAndCode(project:Int,code: String)

    @Query("SELECT MAX(indexInResponse) + 1 FROM BH_BoreholeInfo WHERE Project_ID =:project AND code LIKE :code")
    fun getNextIndexInProjectAndCode(project:Int,code: String) : Int
}
