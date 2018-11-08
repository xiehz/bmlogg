package com.szbm.wh.x.bmlogg.di

import com.szbm.wh.x.bmlogg.ui.LoginActivity2
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface BmloggActivityModule{
    @ContributesAndroidInjector(modules = [BmloggFragsModule::class])
    fun contributeLoginActivityInjector(): LoginActivity2
}