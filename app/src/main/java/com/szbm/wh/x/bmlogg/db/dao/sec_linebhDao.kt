package com.szbm.wh.x.bmlogg.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.szbm.wh.x.bmlogg.vo.sec_linebh


@Dao
interface sec_linebhDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(projectInfo: sec_linebh?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserts(list:List<sec_linebh>?)

    @Query("SELECT * FROM sec_linebh")
    fun selectAll(): LiveData<List<sec_linebh>>

    @Query("SELECT * FROM sec_linebh WHERE iid = :iid")
    fun select(iid:Int): LiveData<sec_linebh>
}
