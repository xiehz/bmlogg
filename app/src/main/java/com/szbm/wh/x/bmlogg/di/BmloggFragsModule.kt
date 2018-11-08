package com.szbm.wh.x.bmlogg.di

import com.szbm.wh.x.bmlogg.ui.ui.loginactivity2.LoginActivity2Fragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
interface BmloggFragsModule{

    @ContributesAndroidInjector
    fun contributeLoginFragmentInjector() :LoginActivity2Fragment
}