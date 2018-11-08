/*
 * Copyright (C) 2017 The Android Open Source Project
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

package com.szbm.wh.x.bmlogg.api

import androidx.lifecycle.LiveData
import com.szbm.wh.x.bmlogg.vo.BH_Logger
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.logging.Logger

/**
 * REST API access points
 */
interface BmloggService {

    @GET("bh_logger/{tel}")
    fun getLogger(@Path("tel")tel:String):LiveData<ApiResponse<BH_Logger>>

//    @GET("users/{login}")
//    fun getUser(@Path("login") login: String): LiveData<ApiResponse<Logger>>

//    @GET("users/{login}/repos")
//    fun getRepos(@Path("login") login: String): LiveData<ApiResponse<List<Logger>>>

//
//    @GET("repos/{owner}/{name}")
//    fun getRepo(
//        @Path("owner") owner: String,
//        @Path("name") name: String
//    ): LiveData<ApiResponse<Logger>>
//
//    @GET("repos/{owner}/{name}/contributors")
//    fun getContributors(
//        @Path("owner") owner: String,
//        @Path("name") name: String
//    ): LiveData<ApiResponse<List<Logger>>>
//
//    @GET("search/repositories")
//    fun searchRepos(@Query("q") query: String): LiveData<ApiResponse<RepoSearchResponse>>
//
//    @GET("search/repositories")
//    fun searchRepos(@Query("q") query: String, @Query("page") page: Int): Call<RepoSearchResponse>
}
