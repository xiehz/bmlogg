/*
 * Copyright (C) 2017 The Android Open Source ProjectArea
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
import com.szbm.wh.x.bmlogg.pojo.Re_Project
import com.szbm.wh.x.bmlogg.vo.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * REST API access points
 */
interface BmloggService {

    @GET("bh_logger/login/{tel}/{pass}")
    fun getLogger(@Path("tel")tel:String,@Path("pass")pass:String):LiveData<ApiResponse<BH_Logger>>

    @GET("ProjectInfoes")
    fun getProjectInfos():LiveData<ApiResponse<List<ProjectInfo>>>

    @GET("ProjectInfoes/{id}")
    fun getProjectInfoWorker(@Path("id")id:Int):Call<Re_Project>

    @GET("c_stratum_ext")
    fun getc_stratum_extWorker():Call<List<c_stratum_ext>>

    @GET("c_project_stratum_ext/project/{id}")
    fun getc_project_stratum_extWorker(@Path("id") id:Int):Call<List<c_project_stratum_ext>>

    @GET("C_PubDic")
    fun getC_PubDicWorker():Call<List<C_PubDic>>

    @GET("BH_BoreholeInfo/{id}")
    fun getBoreholeWorker(@Path("id")id:Int):Call<Re_BH_BoreholeInfo>

    @GET("BH_BoreholeInfo/project/{id}")
    fun getBoreholeIndices(@Path("id")id:Int):Call<List<Int>>


    //-page
    @GET("BH_BoreholeInfo/project/{project}/top")
    fun getTop(
            @Path("project") project: Int,
            @Query("limit") limit: Int): Call<ListingResponse>

    @GET("BH_BoreholeInfo/project/{project}/after")
    fun getTopAfter(
            @Path("project") project: Int,
            @Query("after") after: Int,
            @Query("limit") limit: Int): Call<ListingResponse>

    @GET("BH_BoreholeInfo/project/{project}/code/{code}/top")
    fun getTopByCode(
            @Path("project") project: Int,
            @Path("code") code:String?,
            @Query("limit") limit: Int): Call<ListingResponse>

    @GET("BH_BoreholeInfo/project/{project}/code/{code}/after")
    fun getTopAfterByCode(
            @Path("project") project: Int,
            @Path("code") code:String?,
            @Query("after") after: Int,
            @Query("limit") limit: Int): Call<ListingResponse>

    class ListingResponse(val data: ListingData)

    class ListingData(
            val children: List<RedditChildrenResponse>,
            val after: String?,
            val before: String?
    )

    data class RedditChildrenResponse(val data: BH_BoreholeInfo)


}
