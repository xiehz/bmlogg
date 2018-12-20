package com.szbm.wh.x.bmlogg.db.dao

import androidx.room.*
import com.szbm.wh.x.bmlogg.vo.BH_Logger_Project

@Dao
interface BH_Logger_ProjectDao{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(projectInfo: BH_Logger_Project)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun inserts(list:List<BH_Logger_Project>)

    @Query("DELETE FROM BH_Logger_Project WHERE number = :number")
    fun delete(number:Long)
}