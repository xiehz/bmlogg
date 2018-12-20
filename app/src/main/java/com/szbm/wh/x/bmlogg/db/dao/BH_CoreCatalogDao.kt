package com.szbm.wh.x.bmlogg.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.szbm.wh.x.bmlogg.vo.BH_CoreCatalog


@Dao
interface BH_CoreCatalogDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(projectInfo: BH_CoreCatalog)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserts(list:List<BH_CoreCatalog>)

    @Query("SELECT * FROM BH_CoreCatalog")
    fun selectAll(): LiveData<List<BH_CoreCatalog>>

    @Query("SELECT * FROM BH_CoreCatalog WHERE iid = :iid")
    fun select(iid:Long): LiveData<BH_CoreCatalog>

    @Query("SELECT MAX(iid) FROM BH_CoreCatalog")
    fun selectMaxWithoutLife(): Long

    @Query("SELECT * FROM BH_CoreCatalog WHERE borehole_id = :borehole ORDER BY depth")
    fun selectByBorehole(borehole:Long): LiveData<List<BH_CoreCatalog>>

    @Query("DELETE FROM BH_CoreCatalog WHERE iid = :iid")
    fun delete(iid:Long)

    @Query("SELECT * FROM BH_CoreCatalog WHERE iid = :iid")
    fun selectWithoutLive(iid:Long): BH_CoreCatalog
}
