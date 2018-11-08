package com.szbm.wh.x.bmlogg.di

import android.app.Application
import androidx.room.Room
import com.szbm.wh.x.bmlogg.BmloggSharedPreference
import com.szbm.wh.x.bmlogg.api.BmloggService
import com.szbm.wh.x.bmlogg.db.BmLoggDb
import com.szbm.wh.x.bmlogg.db.BH_loggerDao
import com.szbm.wh.x.bmlogg.util.LiveDataCallAdapterFactory
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
        return Retrofit.Builder()
                .baseUrl("http://192.168.0.116/bmlgser/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
                .create(BmloggService::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): BmLoggDb {
        return Room
                .databaseBuilder(app, BmLoggDb::class.java, bmlogg_db)
                .fallbackToDestructiveMigration()
                .build()
    }

    @Singleton
    @Provides
    fun provideLoggUserDao(db: BmLoggDb): BH_loggerDao {
        return db.loggerDao()
    }

}