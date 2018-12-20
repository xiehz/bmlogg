package com.szbm.wh.x.bmlogg.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.szbm.wh.x.bmlogg.vo.Bh_geo_date


@Dao
interface bh_geo_dateDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(projectInfo: Bh_geo_date)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserts(list:List<Bh_geo_date>)

    @Query("SELECT * FROM Bh_geo_date")
    fun selectAll(): LiveData<List<Bh_geo_date>>

    @Query("SELECT * FROM Bh_geo_date WHERE iid = :iid")
    fun select(iid:Int): LiveData<Bh_geo_date>

    @Query("SELECT * FROM Bh_geo_date WHERE instanceoft = :oft AND typeoft = :type")
    fun selectByBegin(oft:Long,type:String):LiveData<Bh_geo_date>
}
