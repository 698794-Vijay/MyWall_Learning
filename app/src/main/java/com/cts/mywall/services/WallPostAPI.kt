package com.cts.mywall.services

import com.cts.mywall.entity.PostReaction
import com.cts.mywall.entity.WallItem
import retrofit2.Call
import retrofit2.http.GET

interface WallPostAPI {
    @GET("myWall")
    fun getPostData(): Call<ArrayList<WallItem>>

    @GET("reaction")
    fun getPostReactions(): Call<ArrayList<PostReaction>>
}
