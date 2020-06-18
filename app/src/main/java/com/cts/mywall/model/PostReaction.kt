package com.cts.mywall.model

import com.google.gson.annotations.SerializedName

data class PostReaction(

    @SerializedName("feed_id") val feed_id: String,
    @SerializedName("total_reactions") val total_reactions: Int,
    @SerializedName("like") val like: Int,
    @SerializedName("love") val love: Int,
    @SerializedName("hate") val hate: Int
)