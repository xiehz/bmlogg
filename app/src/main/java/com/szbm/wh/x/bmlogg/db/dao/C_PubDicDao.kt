package com.szbm.wh.x.bmlogg.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.szbm.wh.x.bmlogg.vo.C_PubDic

@Dao
interface C_PubDicDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(c_pubic: C_PubDic?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserts(list:List<C_PubDic>?)

    @Query("SELECT * FROM C_PubDic")
    fun selectAll(): LiveData<List<C_PubDic>>

    @Query("SELECT * FROM C_PubDic WHERE iid = :iid")
    fun select(iid:Int): LiveData<C_PubDic>

    @Query("SELECT text FROM C_PubDic WHERE value = :value")
    fun selectText(value:Int): String
}
