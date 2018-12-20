package com.szbm.wh.x.bmlogg.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.szbm.wh.x.bmlogg.vo.Sec_lineinfo

@Dao
interface sec_lineinfoDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(s: Sec_lineinfo?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserts(list:List<Sec_lineinfo>?)

    @Query("SELECT * FROM Sec_lineinfo")
    fun selectAll(): LiveData<List<Sec_lineinfo>>

    @Query("SELECT * FROM Sec_lineinfo WHERE iid = :iid")
    fun select(iid:Int): LiveData<Sec_lineinfo>
}
