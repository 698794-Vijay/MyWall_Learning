package com.cts.mywall.services

import com.cts.mywall.model.PostReaction
import com.cts.mywall.model.WallItem
import retrofit2.Call
import retrofit2.http.GET

interface WallPostAPI {
    @GET("myWall")
    fun getPostData(): Call<ArrayList<WallItem>>

    @GET("reaction")
    fun getPostReactions(): Call<ArrayList<PostReaction>>
}
