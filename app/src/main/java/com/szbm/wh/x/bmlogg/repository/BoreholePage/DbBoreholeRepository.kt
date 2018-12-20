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

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.annotation.MainThread
import androidx.paging.DataSource
import androidx.paging.toLiveData
import com.szbm.wh.x.bmlogg.api.BmloggService
import com.szbm.wh.x.bmlogg.db.BmLoggDb
import com.szbm.wh.x.bmlogg.page.Listing
import com.szbm.wh.x.bmlogg.page.NetworkState
import com.szbm.wh.x.bmlogg.vo.BH_BoreholeInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor


class DbBoreholeRepository constructor(
        val db: BmLoggDb,
        private val bmloggService: BmloggService,
        private val ioExecutor: Executor,
        private val networkPageSize: Int = DEFAULT_NETWORK_PAGE_SIZE)
{
    companion object {
        private const val DEFAULT_NETWORK_PAGE_SIZE = 20
    }

    /**
     * Inserts the response into the database while also assigning position indices to items.
     */
    private fun insertResultIntoDb(code: String?, body: BmloggService.ListingResponse?) {
        body!!.data.children.let { posts ->
            db.runInTransaction {
                val items = posts.mapIndexed { index, child ->
                    child.data.iid
                    child.data
                }
                db.bh_BoreholeInfoDao().inserts(items)
            }
        }
    }

    /**
     * When refresh is called, we simply run a fresh network request and when it arrives, clear
     * the database table and insert all new items in a transaction.
     * <p>
     * Since the PagedList already uses a database bound data source, it will automatically be
     * updated after the database transaction is finished.
     */
    @MainThread
    private fun refresh(project: Long,code: String?,number:Long): LiveData<NetworkState> {
        val networkState = MutableLiveData<NetworkState>()
        networkState.value = NetworkState.LOADING
        if(code.isNullOrEmpty()){
            bmloggService.getTop(project, networkPageSize,number).enqueue(
                    object : Callback<BmloggService.ListingResponse> {
                        override fun onFailure(call: Call<BmloggService.ListingResponse>, t: Throwable) {
                            // retrofit calls this on main thread so safe to call set value
                            networkState.value = NetworkState.error(t.message)
                        }

                        override fun onResponse(
                                call: Call<BmloggService.ListingResponse>,
                                response: Response<BmloggService.ListingResponse>) {
                            ioExecutor.execute {
                                db.runInTransaction {
                                    insertResultIntoDb(code, response.body())
                                }
                                // since we are in bg thread now, post the result.
                                networkState.postValue(NetworkState.LOADED)
                            }
                        }
                    }
            )
        }else{
            bmloggService.getTopByCode(project,code, networkPageSize,number).enqueue(
                    object : Callback<BmloggService.ListingResponse> {
                        override fun onFailure(call: Call<BmloggService.ListingResponse>, t: Throwable) {
                            // retrofit calls this on main thread so safe to call set value
                            networkState.value = NetworkState.error(t.message)
                        }

                        override fun onResponse(
                                call: Call<BmloggService.ListingResponse>,
                                response: Response<BmloggService.ListingResponse>) {
                            ioExecutor.execute {
                                db.runInTransaction {
//                                    db.bh_BoreholeInfoDao().
//                                            deleteByProjectAndCode(project,"%"+code+"%")

                                    insertResultIntoDb(code, response.body())
                                }
                                // since we are in bg thread now, post the result.
                                networkState.postValue(NetworkState.LOADED)
                            }
                        }
                    }
            )
        }

        return networkState
    }

    /**
     * Returns a Listing for the given subreddit.
     */
    @MainThread
    fun postsOfSubreddit(project: Long,code: String?, pageSize: Int,number: Long): Listing<BH_BoreholeInfo> {
        // create a boundary callback which will observe when the user reaches to the edges of
        // the list and update the database with extra data.
        val boundaryCallback = BoreholeBoundaryCallback(
                webservice = bmloggService,
                code = code,
                number = number,
                handleResponse = this::insertResultIntoDb,
                ioExecutor = ioExecutor,
                project = project,
                networkPageSize = networkPageSize)
        // we are using a mutable live data to trigger refresh requests which eventually calls
        // refresh method and gets a new live data. Each refresh request by the user becomes a newly
        // dispatched data in refreshTrigger
        val refreshTrigger = MutableLiveData<Unit>()
        val refreshState = Transformations.switchMap(refreshTrigger) {
            refresh(project,code,number)
        }

        // We use toLiveData Kotlin extension function here, you could also use LivePagedListBuilder

        if(code.isNullOrEmpty()){
           val livePagedList =db.bh_BoreholeInfoDao().
                    boreholesByProject(project).
                    toLiveData(
                            pageSize = pageSize,
                            boundaryCallback = boundaryCallback)

            return Listing(
                    pagedList = livePagedList,
                    networkState = boundaryCallback.networkState,
                    retry = {
                        boundaryCallback.helper.retryAllFailed()
                    },
                    refresh = {
                        refreshTrigger.value = null
                    },
                    refreshState = refreshState
            )
        }
        else{
            val livePagedList = db.bh_BoreholeInfoDao().
                    boreholesByProjectAndCode(project,"%"+code +"%").
                    toLiveData(
                            pageSize = pageSize,
                            boundaryCallback = boundaryCallback)
            return Listing(
                    pagedList = livePagedList,
                    networkState = boundaryCallback.networkState,
                    retry = {
                        boundaryCallback.helper.retryAllFailed()
                    },
                    refresh = {
                        refreshTrigger.value = null
                    },
                    refreshState = refreshState
            )
        }
    }


}

