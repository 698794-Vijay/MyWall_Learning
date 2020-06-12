package com.cts.mywall.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cts.mywall.entity.WallModelJson
import com.cts.mywall.entity.WallReactionsJson
import com.cts.mywall.services.ConnectsAPI
import com.cts.mywall.services.ServiceBuilder
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class WallList(var items: ArrayList<WallModelJson>) {
    constructor() : this(ArrayList())
    val wallList = MutableLiveData<ArrayList<WallModelJson>>()

    init {
        fetchWallData()
    }

//    suspend fun getWallList() {
//
//        val result = getData()
//        withContext(Dispatchers.Main) {
//            items.addAll(result)
//        }
//    }

//    suspend fun getData(): ArrayList<WallModelJson> {
//        return withContext(Dispatchers.Default) {
//            val request = ServiceBuilder.buildService(ConnectsAPI::class.java)
//            val call = request.getMyWallData()
//            var response = call.execute()
//            Log.d("App", "Wall API Response: ${response.body()!!}")
//            return@withContext response.body()!!
//        }
//    }


    private suspend fun fetchWallDataFromAPI(): ArrayList<WallModelJson>? =

        suspendCoroutine { continuation ->
            val request = ServiceBuilder.buildService(ConnectsAPI::class.java)
            val call = request.getMyWallData()
            call.enqueue(object : Callback<ArrayList<WallModelJson>> {
                override fun onFailure(call: Call<ArrayList<WallModelJson>>, t: Throwable) {
                    continuation.resume(null)
                }

                override fun onResponse(
                    call: Call<ArrayList<WallModelJson>>,
                    response: Response<ArrayList<WallModelJson>>
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

    fun getUsers(): LiveData<ArrayList<WallModelJson>> {
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
                    items.add(it)
                }
            }

           wallList.postValue(wallData)
        }

    }
}
