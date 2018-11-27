package com.szbm.wh.x.bmlogg.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.szbm.wh.x.bmlogg.vo.bh_geo_date


@Dao
interface bh_geo_dateDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(projectInfo: bh_geo_date)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserts(list:List<bh_geo_date>)

    @Query("SELECT * FROM bh_geo_date")
    fun selectAll(): LiveData<List<bh_geo_date>>

    @Query("SELECT * FROM bh_geo_date WHERE iid = :iid")
    fun select(iid:Int): LiveData<bh_geo_date>
}
