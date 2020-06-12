package com.cts.mywall.model

import androidx.lifecycle.MutableLiveData
import com.cts.mywall.entity.WallItem
import com.cts.mywall.entity.WallReactionsJson
import com.cts.mywall.services.ConnectsAPI
import com.cts.mywall.services.ServiceBuilder
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class WallList() {
    private val wallList = MutableLiveData<ArrayList<WallItem>>()

    init {
        fetchWallData()
    }

    private suspend fun fetchWallDataFromAPI(): ArrayList<WallItem>? =

        suspendCoroutine { continuation ->
            val request = ServiceBuilder.buildService(ConnectsAPI::class.java)
            val call = request.getMyWallData()
            call.enqueue(object : Callback<ArrayList<WallItem>> {
                override fun onFailure(call: Call<ArrayList<WallItem>>, t: Throwable) {
                    continuation.resume(null)
                }

                override fun onResponse(
                    call: Call<ArrayList<WallItem>>,
                    response: Response<ArrayList<WallItem>>
                ) {
                    continuation.resume(response.body())
                }
            })
        }

    private suspend fun fetchWallReactionsDataFromAPI(): ArrayList<WallReactionsJson>? =
        suspendCoroutine { continuation ->
            val request = ServiceBuilder.buildService(ConnectsAPI::class.java)
            val call = request.getMyWallReactions()
            call.enqueue(object : Callback<ArrayList<WallReactionsJson>> {
                override fun onFailure(call: Call<ArrayList<WallReactionsJson>>, t: Throwable) {
                    continuation.resume(null)
                }

                override fun onResponse(
                    call: Call<ArrayList<WallReactionsJson>>,
                    response: Response<ArrayList<WallReactionsJson>>
                ) {
                    continuation.resume(response.body())
                }
            })
        }

    fun getUsers(): MutableLiveData<ArrayList<WallItem>> {
        return wallList
    }

    private fun fetchWallData() {
       GlobalScope.launch {
            val wallDataResponse = async { fetchWallDataFromAPI() }
            val wallReactionsResponse = async { fetchWallReactionsDataFromAPI() }

            val wallData = wallDataResponse.await()
            val wallReactions = wallReactionsResponse.await()
            wallData.let { data ->
                data?.forEach{
                    it.reaction = wallReactions?.filter { x -> x.feed_id == it.id }?.first()
                }
            }

           wallList.postValue(wallData)
        }
    }
}
