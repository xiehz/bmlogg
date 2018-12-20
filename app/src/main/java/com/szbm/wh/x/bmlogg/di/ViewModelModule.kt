/*
 * Copyright (C) 2018 The Android Open Source ProjectArea
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

package com.szbm.wh.x.bmlogg.di


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.example.github.viewmodel.BmloggViewModelFactory
import com.szbm.wh.x.bmlogg.ui.ToolsViewModel
import com.szbm.wh.x.bmlogg.ui.ui.bh.*
import com.szbm.wh.x.bmlogg.ui.ui.map.MapViewModel
import com.szbm.wh.x.bmlogg.ui.ui.boreholes.BoreholesViewModel
import com.szbm.wh.x.bmlogg.ui.ui.loginactivity2.LoginActivity2ViewModel
import com.szbm.wh.x.bmlogg.ui.ui.main.MainViewModel
import com.szbm.wh.x.bmlogg.ui.ui.project.ProjectViewModel
import com.szbm.wh.x.bmlogg.ui.ui.loggerbh.LoggerBhViewModel
import com.szbm.wh.x.bmlogg.ui.ui.loggingdetail.LoggingDetailViewModel

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(LoginActivity2ViewModel::class)
    abstract fun bindLoginViewModel(loginActivity2ViewModel: LoginActivity2ViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProjectViewModel::class)
    abstract fun bindProjectViewModel(projectViewModel: ProjectViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BoreholesViewModel::class)
    abstract fun bindBoreholesViewModel(boreholesViewModel: BoreholesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BhViewModel::class)
    abstract fun bindBhViewModel(bhViewModel: BhViewModel): ViewModel



    @Binds
    @IntoMap
    @ViewModelKey(BhBasicViewModel::class)
    abstract fun bindBhBasicViewModel(basicViewModel: BhBasicViewModel): ViewModel



    @Binds
    @IntoMap
    @ViewModelKey(MapViewModel::class)
    abstract fun bindMapViewModel(mapViewModel: MapViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ToolsViewModel::class)
    abstract fun bindToolsViewModel(toolsViewModel: ToolsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoggerBhViewModel::class)
    abstract fun bindLoggerBhViewModel(loggerBhViewModel: LoggerBhViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoggingDetailViewModel::class)
    abstract fun bindloggingDetailViewModel(loggingDetailViewModel: LoggingDetailViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(BhBeginViewModel::class)
    abstract fun bindBhBeginViewModel(bhBeginViewModel: BhBeginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RockSoilViewModel::class)
    abstract fun bindRockSoilViewModel(rockSoilViewModel: RockSoilViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(SampleViewModel::class)
    abstract fun bindSampleViewModel(sampleViewModel: SampleViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SptViewModel::class)
    abstract fun bindSptViewModel(sptViewModel: SptViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BhEndViewModel::class)
    abstract fun bindBhEndViewModel(bhEndViewModel: BhEndViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: BmloggViewModelFactory): ViewModelProvider.Factory

}
