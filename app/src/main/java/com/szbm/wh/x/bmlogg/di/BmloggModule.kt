package com.szbm.wh.x.bmlogg.di

import android.app.Application
import androidx.room.Room
import com.szbm.wh.x.bmlogg.BmloggSharedPreference
import com.szbm.wh.x.bmlogg.api.BmloggApi
import com.szbm.wh.x.bmlogg.api.BmloggService
import com.szbm.wh.x.bmlogg.db.BmLoggDb
import com.szbm.wh.x.bmlogg.db.dao.BH_loggerDao
import com.szbm.wh.x.bmlogg.db.dao.ProjectInfoDao
import com.szbm.wh.x.bmlogg.util.LiveDataCallAdapterFactory
import com.szbm.wh.x.bmlogg.vo.BH_Logger
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/*

 */
@Module(includes = [ViewModelModule::class])
class BmloggModule(val bmlogg_db:String = "bmlogg.db"){

    @Singleton
    @Provides
    fun provideSharedPreferences(app:Application):BmloggSharedPreference{
        return BmloggSharedPreference.Builder(app).build()
    }

    @Singleton
    @Provides
    fun provideService(): BmloggService {
        return BmloggApi.getInstance().bmloggService
    }

    /*
    提供全局访问
    */
    @Singleton
    @Provides
    fun providerLogger(sharedPreference: BmloggSharedPreference): BH_Logger {
         return sharedPreference.readLogin()
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): BmLoggDb {
        return BmLoggDb.getInstance(app)
    }


    @Singleton
    @Provides
    fun provideLoggUserDao(db: BmLoggDb): BH_loggerDao {
        return db.loggerDao()
    }

    @Singleton
    @Provides
    fun providerProjectInfoDao(db:BmLoggDb): ProjectInfoDao {
        return db.projectInfoDao()
    }

}