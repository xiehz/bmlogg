/*
 * Copyright (C) 2017 The Android Open Source ProjectArea
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.szbm.wh.x.bmlogg.db


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.szbm.wh.x.bmlogg.db.dao.*
import com.szbm.wh.x.bmlogg.vo.*


/**
 * Main database description.
 */
@Database(
    entities = [
        bh_begin_info::class,
        BH_Logger::class,
        BH_BoreholeInfo::class,
    BH_CoreCatalog::class,
    bh_end_info::class,
    bh_extra_info::class,
    bh_geo_date::class,
    bh_imagesinfo::class,
    bhext_rocksoil_info::class,
    bhext_sampling_info::class,
    C_Project_Stratum::class,
    C_Project_Lithology::class,
    c_project_stratum_ext::class,
    C_PubDic::class,
    c_stratum_ext::class,
    ProjectArea::class,
    ProjectInfo::class,
    ProjectPhase::class,
    SampleRecord::class,
    sec_linebh::class,
    sec_lineinfo::class,
    ts_sptdata::class,
    ts_sptinfo::class,
    tsext_spt_info::class
    ],
    version = 1,
    exportSchema = false
)
abstract class BmLoggDb : RoomDatabase() {
    abstract fun loggerDao(): BH_loggerDao
    abstract fun projectInfoDao(): ProjectInfoDao
    abstract fun projectPhaseDao(): ProjectPhaseDao
    abstract fun projectAreaDao(): ProjectAreaDao
    abstract fun sec_lineinfoDao(): sec_lineinfoDao
    abstract fun c_Project_LithologyDao():C_Project_LithologyDao
    abstract fun c_Project_StratumDao():C_Project_StratumDao
    abstract fun c_project_stratum_extDao():c_project_stratum_extDao
    abstract fun bhext_rocksoil_infoDao():bhext_rocksoil_infoDao
    abstract fun bhext_sampling_infoDao():bhext_sampling_infoDao
    abstract fun bh_begin_infoDao():bh_begin_infoDao
    abstract fun bh_BoreholeInfoDao():BH_BoreholeInfoDao
    abstract fun bh_CoreCatalogDao():BH_CoreCatalogDao
    abstract fun bh_end_infoDao():bh_end_infoDao
    abstract fun bh_extra_infoDao():bh_extra_infoDao
    abstract fun bh_geo_dateDao():bh_geo_dateDao
    abstract fun bh_imagesinfoDao():bh_imagesinfoDao
    abstract fun c_PubDicDao():C_PubDicDao
    abstract fun c_stratum_extDao():c_stratum_extDao
    abstract fun sampleRecordDao():SampleRecordDao
    abstract fun sec_linebhDao():sec_linebhDao
    abstract fun tsext_spt_infoDao():tsext_spt_infoDao
    abstract fun ts_sptdataDao():ts_sptdataDao
    abstract fun ts_sptinfoDao():ts_sptinfoDao

    companion object {
        private val bmlogg_db:String = "bmlogg.db"
        @Volatile private var instance: BmLoggDb? = null

        fun getInstance(context: Context): BmLoggDb {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): BmLoggDb {
            return Room.databaseBuilder(context, BmLoggDb::class.java, bmlogg_db)
                    .fallbackToDestructiveMigration()
                    .build()
        }
    }
}
