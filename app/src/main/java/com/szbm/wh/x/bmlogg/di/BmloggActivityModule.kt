package com.szbm.wh.x.bmlogg.di

import com.szbm.wh.x.bmlogg.ui.*
import com.szbm.wh.x.bmlogg.ui.location.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface BmloggActivityModule{
    @ContributesAndroidInjector(modules = [BmloggFragsModule::class])
    fun contributeLoginActivityInjector(): LoginActivity2

    @ContributesAndroidInjector(modules = [BmloggFragsModule::class])
    fun contributeMainActivityInjector(): MainActivity

    @ContributesAndroidInjector(modules = [BmloggFragsModule::class])
    fun contributeBoreholesActivityInjector():BoreholesActivity

    @ContributesAndroidInjector(modules = [BmloggFragsModule::class])
    fun contributeBhActivityInjector():BhActivity

    @ContributesAndroidInjector(modules = [BmloggFragsModule::class])
    fun contributeCustomLocationActivity():CustomLocationActivity

    @ContributesAndroidInjector(modules = [BmloggFragsModule::class])
    fun contributeCustomLocationModeActivity():CustomLocationModeActivity

    @ContributesAndroidInjector(modules = [BmloggFragsModule::class])
    fun contributeLocationActivity():Location_Activity

    @ContributesAndroidInjector(modules = [BmloggFragsModule::class])
    fun contributeLocationModeSourceActivity():LocationModeSourceActivity

    @ContributesAndroidInjector(modules = [BmloggFragsModule::class])
    fun conotributePurpose_SignIn_Activity():Purpose_SignIn_Activity

    @ContributesAndroidInjector(modules = [BmloggFragsModule::class])
    fun conotributeLoggerBHActivity(): LoggerBHActivity

    @ContributesAndroidInjector(modules = [BmloggFragsModule::class])
    fun conotributeLoggingDetailActivity(): LoggingDetailActivity

}