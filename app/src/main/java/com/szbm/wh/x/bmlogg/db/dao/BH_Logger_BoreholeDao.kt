package com.szbm.wh.x.bmlogg.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.szbm.wh.x.bmlogg.vo.BH_Logger_BoreholeInfo

@Dao
interface BH_Logger_BoreholeDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(projectInfo: BH_Logger_BoreholeInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserts(list:List<BH_Logger_BoreholeInfo>)
}