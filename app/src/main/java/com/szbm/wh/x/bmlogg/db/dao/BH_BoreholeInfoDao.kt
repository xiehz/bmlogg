package com.szbm.wh.x.bmlogg.db.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.szbm.wh.x.bmlogg.pojo.BoreholeAndExtra
import com.szbm.wh.x.bmlogg.vo.BH_BoreholeInfo
import com.szbm.wh.x.bmlogg.vo.Re_BH_BoreholeInfo


@Dao
interface BH_BoreholeInfoDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(projectInfo: BH_BoreholeInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserts(list:List<BH_BoreholeInfo>)

    @Query("SELECT * FROM BH_BoreholeInfo")
    fun selectAll(): LiveData<List<BH_BoreholeInfo>>

    @Query("SELECT COUNT(*) FROM BH_BoreholeInfo WHERE Project_ID = :project")
    fun count(project:Long):Int

    @Query("SELECT * FROM BH_BoreholeInfo WHERE iid = :iid")
    fun select(iid:Long): LiveData<BH_BoreholeInfo>

    @Query("SELECT * FROM BH_BoreholeInfo WHERE iid = :iid")
    fun selectWithoutLife(iid:Long): BH_BoreholeInfo?

    @Query("SELECT MAX(iid) FROM BH_BoreholeInfo")
    fun selectMaxWithoutLife(): Long

    @Query("SELECT * FROM BH_BoreholeInfo ORDER BY iid COLLATE NOCASE ASC")
    fun factoryAll(): DataSource.Factory<Int, BH_BoreholeInfo>

    @Query("SELECT * FROM BH_BoreholeInfo WHERE Project_ID =:project ORDER BY iid ASC")
//    @Query("select *from BH_BoreholeInfo where Project_ID = :project AND iid in (select borehole_iid from BH_Logger_BoreholeInfo where number = :number) ORDER BY iid ASC")
    fun boreholesByProject(project:Long) : DataSource.Factory<Int, BH_BoreholeInfo>

    @Query("SELECT * FROM BH_BoreholeInfo WHERE  Project_ID =:project AND code LIKE:code ORDER BY iid ASC")
//@Query("select *from BH_BoreholeInfo where Project_ID = :project " +
//        "AND iid in (select borehole_iid from BH_Logger_BoreholeInfo where number = :number) " +
//        "AND code LIKE:code ORDER BY iid ASC")
    fun boreholesByProjectAndCode(project:Long,code : String) : DataSource.Factory<Int, BH_BoreholeInfo>

    @Query("SELECT BH_BoreholeInfo.iid,BH_BoreholeInfo.code,BH_BoreholeInfo.HoleDepth,Bh_extra_info.iid as extra_iid, Bh_extra_info._long ,Bh_extra_info.lat " +
            "FROM BH_BoreholeInfo JOIN Bh_extra_info ON BH_BoreholeInfo.iid = Bh_extra_info.borehole_iid WHERE Project_ID =:project ORDER BY BH_BoreholeInfo.iid ASC")
    fun listBoreholesByProject(project: Long):List<BoreholeAndExtra>

    @Query("SELECT BH_BoreholeInfo.iid,BH_BoreholeInfo.code,BH_BoreholeInfo.HoleDepth,Bh_extra_info.iid as extra_iid, Bh_extra_info._long ,Bh_extra_info.lat " +
            "FROM BH_BoreholeInfo JOIN Bh_extra_info ON BH_BoreholeInfo.iid = Bh_extra_info.borehole_iid WHERE  BH_BoreholeInfo.iid = :iid ORDER BY BH_BoreholeInfo.iid ASC")
    fun boreholesByIID(iid :Long):LiveData<BoreholeAndExtra>

    @Query("SELECT BH_BoreholeInfo.iid,BH_BoreholeInfo.code,BH_BoreholeInfo.HoleDepth,Bh_extra_info.iid as extra_iid, Bh_extra_info._long ,Bh_extra_info.lat " +
            "FROM BH_BoreholeInfo JOIN Bh_extra_info ON BH_BoreholeInfo.iid = Bh_extra_info.borehole_iid WHERE  BH_BoreholeInfo.iid = :iid ORDER BY BH_BoreholeInfo.iid ASC")
    fun boreholesByIIDEntity(iid :Long):BoreholeAndExtra?


}
