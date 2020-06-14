package com.cts.mywall.model

import androidx.lifecycle.MutableLiveData
import com.cts.mywall.entity.WallItem
import com.cts.mywall.entity.PostReaction
import com.cts.mywall.services.WallPostAPI
import com.cts.mywall.services.ServiceBuilder
import com.cts.mywall.util.Constants
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class WallPostList() {
    private val wallList = MutableLiveData<ArrayList<WallItem>>()

    init {
        fetchWallData()
    }

    private suspend fun fetchWallDataFromAPI(): ArrayList<WallItem>? =

        suspendCoroutine { continuation ->
            val request = ServiceBuilder.buildService(WallPostAPI::class.java)
            val call = request.getPostData()
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

    private suspend fun fetchWallReactionsDataFromAPI(): ArrayList<PostReaction>? =
        suspendCoroutine { continuation ->
            val request = ServiceBuilder.buildService(WallPostAPI::class.java)
            val call = request.getPostReactions()
            call.enqueue(object : Callback<ArrayList<PostReaction>> {
                override fun onFailure(call: Call<ArrayList<PostReaction>>, t: Throwable) {
                    continuation.resume(null)
                }

                override fun onResponse(
                    call: Call<ArrayList<PostReaction>>,
                    response: Response<ArrayList<PostReaction>>
                ) {
                    continuation.resume(response.body())
                }
            })
        }

    fun getMyWalls(): MutableLiveData<ArrayList<WallItem>> {
        return wallList
    }

    private fun fetchWallData() {
        GlobalScope.launch {
            val wallDataResponse = async { fetchWallDataFromAPI() }.await()
            val wallReactionsResponse = async { fetchWallReactionsDataFromAPI() }.await()

            wallDataResponse.let { data ->
                data?.forEach {
                    it.color = Constants.colorBlue
                    it.reaction = wallReactionsResponse?.first { x -> x.feed_id == it.id }
                }
            }

            wallList.postValue(wallDataResponse)
        }
    }
}