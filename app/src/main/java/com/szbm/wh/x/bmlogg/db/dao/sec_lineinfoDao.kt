package com.szbm.wh.x.bmlogg.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.szbm.wh.x.bmlogg.vo.sec_lineinfo

@Dao
interface sec_lineinfoDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(s: sec_lineinfo?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserts(list:List<sec_lineinfo>?)

    @Query("SELECT * FROM sec_lineinfo")
    fun selectAll(): LiveData<List<sec_lineinfo>>

    @Query("SELECT * FROM sec_lineinfo WHERE iid = :iid")
    fun select(iid:Int): LiveData<sec_lineinfo>
}
