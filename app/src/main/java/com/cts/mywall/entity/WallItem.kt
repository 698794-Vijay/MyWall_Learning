package com.cts.mywall.entity

import com.google.gson.annotations.SerializedName

data class WallItem (
    @SerializedName("category") val category : String,
    @SerializedName("id") val id : String,
    @SerializedName("name") val name : String,
    @SerializedName("timestamp") val timestamp : String,
    @SerializedName("profile_pic") val profile_pic : String,
    @SerializedName("postType") val postType : String,
    @SerializedName("message") val message : String,
    @SerializedName("imageUrl") val imageUrl : String,
    var reaction: WallReactionsJson?,
    var color: String
)
