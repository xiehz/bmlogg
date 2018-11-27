package com.szbm.wh.x.bmlogg.di

import com.szbm.wh.x.bmlogg.ui.BhActivity
import com.szbm.wh.x.bmlogg.ui.BoreholesActivity
import com.szbm.wh.x.bmlogg.ui.LoginActivity2
import com.szbm.wh.x.bmlogg.ui.MainActivity
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
}