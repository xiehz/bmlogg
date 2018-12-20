package com.szbm.wh.x.bmlogg.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.szbm.wh.x.bmlogg.vo.Sec_linebh


@Dao
interface sec_linebhDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(projectInfo: Sec_linebh?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserts(list:List<Sec_linebh>?)

    @Query("SELECT * FROM Sec_linebh")
    fun selectAll(): LiveData<List<Sec_linebh>>

    @Query("SELECT * FROM Sec_linebh WHERE iid = :iid")
    fun select(iid:Int): LiveData<Sec_linebh>
}
