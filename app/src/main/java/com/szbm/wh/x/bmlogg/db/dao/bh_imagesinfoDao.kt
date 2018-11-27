package com.szbm.wh.x.bmlogg.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.szbm.wh.x.bmlogg.vo.bh_imagesinfo


@Dao
interface bh_imagesinfoDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(projectInfo: bh_imagesinfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserts(list:List<bh_imagesinfo>)

    @Query("SELECT * FROM bh_imagesinfo")
    fun selectAll(): LiveData<List<bh_imagesinfo>>

    @Query("SELECT * FROM bh_imagesinfo WHERE iid = :iid")
    fun select(iid:Int): LiveData<bh_imagesinfo>
}
