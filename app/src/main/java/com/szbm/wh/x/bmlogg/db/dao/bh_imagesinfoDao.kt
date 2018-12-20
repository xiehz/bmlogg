package com.szbm.wh.x.bmlogg.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.szbm.wh.x.bmlogg.vo.Bh_imagesinfo


@Dao
interface bh_imagesinfoDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(projectInfo: Bh_imagesinfo):Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserts(list:List<Bh_imagesinfo>)

    @Query("SELECT * FROM Bh_imagesinfo")
    fun selectAll(): LiveData<List<Bh_imagesinfo>>

    @Query("SELECT * FROM Bh_imagesinfo WHERE iid = :iid")
    fun select(iid:Long): LiveData<Bh_imagesinfo>

    @Query("SELECT * FROM Bh_imagesinfo WHERE instanceoft = :oft AND typeoft = :type")
    fun selectImages(oft:Long,type:String): LiveData<List<Bh_imagesinfo>>

    @Query("DELETE FROM Bh_imagesinfo WHERE iid = :iid")
    fun delete(iid: Long)
}
