package com.szbm.wh.x.bmlogg.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.szbm.wh.x.bmlogg.vo.SampleRecord


@Dao
interface SampleRecordDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(projectInfo: SampleRecord)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserts(list:List<SampleRecord>)

    @Query("SELECT * FROM SampleRecord")
    fun selectAll(): LiveData<List<SampleRecord>>

    @Query("SELECT * FROM SampleRecord WHERE iid = :iid")
    fun select(iid:Int): LiveData<SampleRecord>

    @Query("SELECT MAX(iid) FROM SampleRecord")
    fun selectMaxWithoutLife(): Long
}
