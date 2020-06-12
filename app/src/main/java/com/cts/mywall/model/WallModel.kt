package com.cts.mywall.model

class WallModel (
    val category : String,
    val id : String,
    val name : String,
    val timestamp : String,
    val profile_pic : String,
    val postType : String,
    val message : String,
    val imageUrl : String,
    val feed_id : String?,
    val total_reactions : Int?,
    val like : Int?,
    val love : Int?,
    val hate : Int?
)