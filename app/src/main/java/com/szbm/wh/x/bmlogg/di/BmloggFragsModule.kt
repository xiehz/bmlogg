package com.szbm.wh.x.bmlogg.di

import com.szbm.wh.x.bmlogg.ui.*
import com.szbm.wh.x.bmlogg.ui.ui.bh.*
import com.szbm.wh.x.bmlogg.ui.ui.boreholes.BoreholesFragment
import com.szbm.wh.x.bmlogg.ui.ui.loginactivity2.LoginActivity2Fragment
import com.szbm.wh.x.bmlogg.ui.ui.main.MainFragment
import com.szbm.wh.x.bmlogg.ui.ui.project.ProjectFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
interface BmloggFragsModule{

    @ContributesAndroidInjector
    fun contributeLoginFragmentInjector() :LoginActivity2Fragment

    @ContributesAndroidInjector
    fun contributeMainFragmentInjector() :MainFragment

    @ContributesAndroidInjector
    fun contributeProjectFragmentInjector(): ProjectFragment

    @ContributesAndroidInjector
    fun contributeStatisticFragmentInjector():StatisticFragment

    @ContributesAndroidInjector
    fun contributeToolsFragmentInjector():ToolsFragment

    @ContributesAndroidInjector
    fun contributeMapFragmentInjector():MapFragment

    @ContributesAndroidInjector
    fun contributeAboutFragmentInjector():AboutFragment

    @ContributesAndroidInjector
    fun contributeShareFragmentInjector():ShareFragment

    @ContributesAndroidInjector
    fun contributeBoreholesFragmentInjector():BoreholesFragment

    @ContributesAndroidInjector
    fun contributeBhFragmentInjector():BhFragment

    @ContributesAndroidInjector
    fun contributeBhBeginFragmentInjector():BhBeginFragment

    @ContributesAndroidInjector
    fun contributeRockSoilFragmentInjector():RockSoilFragment

    @ContributesAndroidInjector
    fun contributeSampleFragmentInjector():SampleFragment

    @ContributesAndroidInjector
    fun contributeSptFragmentInjector():SptFragment

    @ContributesAndroidInjector
    fun contributeBhEndFragmentInjector():BhEndFragment
}