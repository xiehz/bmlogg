package com.szbm.wh.x.bmlogg.di

import androidx.work.Worker
import com.szbm.wh.x.bmlogg.worker.ProjectWorker
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Subcomponent
import javax.inject.Singleton


@Subcomponent()
interface BmloggWorker{
    fun inject(projectWorker: ProjectWorker)
}