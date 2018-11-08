package com.szbm.wh.x.bmlogg.di

import android.app.Application
import com.szbm.wh.x.bmlogg.BmloggApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AndroidInjectionModule::class,
            BmloggModule::class,
            BmloggActivityModule::class
        ]
)
interface BmloggComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): BmloggComponent
    }

    fun inject(bmloggApp: BmloggApp)
}