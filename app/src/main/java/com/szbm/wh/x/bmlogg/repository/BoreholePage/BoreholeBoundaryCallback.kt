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

package com.szbm.wh.x.bmlogg.repository.BoreholePage

import androidx.paging.PagedList
import androidx.annotation.MainThread
import com.szbm.wh.x.bmlogg.api.BmloggApi
import com.szbm.wh.x.bmlogg.api.BmloggService
import com.szbm.wh.x.bmlogg.page.PagingRequestHelper
import com.szbm.wh.x.bmlogg.page.createStatusLiveData
import com.szbm.wh.x.bmlogg.vo.BH_BoreholeInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor

/**
 * This boundary callback gets notified when user reaches to the edges of the list such that the
 * database cannot provide any more data.
 * <p>
 * The boundary callback might be called multiple times for the same direction so it does its own
 * rate limiting using the PagingRequestHelper class.
 */
class BoreholeBoundaryCallback(
        private val project:Int,
        private val code: String?,
        private val webservice: BmloggService,
        private val handleResponse: (String?, BmloggService.ListingResponse?) -> Unit,
        private val ioExecutor: Executor,
        private val networkPageSize: Int)
    : PagedList.BoundaryCallback<BH_BoreholeInfo>() {

    val helper = PagingRequestHelper(ioExecutor)
    val networkState = helper.createStatusLiveData()

    /**
     * Database returned 0 items. We should query the backend for more items.
     */
    @MainThread
    override fun onZeroItemsLoaded() {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) {
            if(code.isNullOrEmpty()){
                webservice.getTop(
                        project = project,
                        limit = networkPageSize)
                        .enqueue(createWebserviceCallback(it))
            }
            else{
                webservice.getTopByCode(
                        project = project,
                        code = code,
                        limit = networkPageSize)
                        .enqueue(createWebserviceCallback(it))
            }

        }
    }

    /**
     * User reached to the end of the list.
     */
    @MainThread
    override fun onItemAtEndLoaded(itemAtEnd: BH_BoreholeInfo) {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) {
            if(code.isNullOrEmpty()){
                webservice.getTopAfter(
                        project = project,
                        after = itemAtEnd.iid,
                        limit = networkPageSize)
                        .enqueue(createWebserviceCallback(it))
            }else{
                webservice.getTopAfterByCode(
                        project = project,
                        code = code,
                        after = itemAtEnd.iid,
                        limit = networkPageSize)
                        .enqueue(createWebserviceCallback(it))
            }
        }
    }

    /**
     * every time it gets new items, boundary callback simply inserts them into the database and
     * paging library takes care of refreshing the list if necessary.
     */
    private fun insertItemsIntoDb(
            response: Response<BmloggService.ListingResponse>,
            it: PagingRequestHelper.Request.Callback) {
        ioExecutor.execute {
            handleResponse(code, response.body())
            it.recordSuccess()
        }
    }

    override fun onItemAtFrontLoaded(itemAtFront: BH_BoreholeInfo) {
        // ignored, since we only ever append to what's in the DB
    }

    private fun createWebserviceCallback(it: PagingRequestHelper.Request.Callback)
            : Callback<BmloggService.ListingResponse> {
        return object : Callback<BmloggService.ListingResponse> {
            override fun onFailure(
                    call: Call<BmloggService.ListingResponse>,
                    t: Throwable) {
                it.recordFailure(t)
            }

            override fun onResponse(
                    call: Call<BmloggService.ListingResponse>,
                    response: Response<BmloggService.ListingResponse>) {
                insertItemsIntoDb(response, it)
            }
        }
    }
}