package com.cts.mywall.services

import com.cts.mywall.entity.WallItem
import com.cts.mywall.entity.WallReactionsJson
import retrofit2.Call
import retrofit2.http.GET

interface ConnectsAPI
{
    @GET("myWall")
    fun getMyWallData(): Call<ArrayList<WallItem>>

    @GET("reaction")
    fun getMyWallReactions(): Call<ArrayList<WallReactionsJson>>
}
