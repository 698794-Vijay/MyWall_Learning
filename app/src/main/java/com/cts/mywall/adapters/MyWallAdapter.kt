package com.cts.myconnectsapp.adapters

import android.app.Activity
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cts.mywall.R
import com.cts.mywall.entity.WallModelJson


class MyWallAdapter(val profiles: ArrayList<WallModelJson>, context: Activity) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val activity: Activity? = context

    private val viewTypeImage = 1
    private val viewTypeText = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {


        if (viewType == 1)
            return MyWallViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.wall_image_card, parent, false), activity
            )
        else return WallTextViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.wall_text_card, parent, false),
            activity
        )
    }


    override fun getItemViewType(position: Int): Int {
        // here you can get decide from your model's ArrayList, which type of view you need to load. Like
        return if (profiles[position].postType == "image") { // put your condition, according to your requirements
            viewTypeImage
        } else viewTypeText
    }

    override fun getItemCount(): Int {
        return profiles.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (profiles[position].postType  == "image") { // put your condition, according to your requirements
            (holder as MyWallViewHolder).bind(position)
        } else {
            (holder as WallTextViewHolder).bind(position)
        }
    }


    private inner class MyWallViewHolder(itemView: View, context: Activity?) : RecyclerView.ViewHolder(itemView) {
        val activity: Activity? = context
        private val photo: ImageView = itemView.findViewById(R.id.imgProfile)
        private val firstName: TextView = itemView.findViewById(R.id.txtFirstName)
        private val date: TextView = itemView.findViewById(R.id.txtDate)
        private val wall: ImageView = itemView.findViewById(R.id.imgWall)
        private val message: TextView = itemView.findViewById(R.id.txtMessage)

        private val like: ImageView = itemView.findViewById(R.id.imgLike)
        private val love: ImageView = itemView.findViewById(R.id.imgLove)
        private val hate: ImageView = itemView.findViewById(R.id.imgHate)

        private val txtLike: TextView = itemView.findViewById(R.id.txtLike)
        private val txtLove: TextView = itemView.findViewById(R.id.txtLove)
        private val txtHate: TextView = itemView.findViewById(R.id.txtHate)


        fun bind(position: Int) {

            val wallItem = profiles[position]
                Glide.with(itemView.context).load(wallItem.profile_pic).into(photo)
            Glide.with(itemView.context).load(wallItem.imageUrl).into(wall)

            firstName.text = wallItem.name
            message.text = wallItem.message
            like.setColorFilter(Color.parseColor("#2A84FC"))
            love.setColorFilter(Color.parseColor("#2A84FC"))
            hate.setColorFilter(Color.parseColor("#2A84FC"))

            txtLike.text = wallItem.reaction?.like.toString()
            txtLove.text = wallItem.reaction?.love.toString()
            txtHate.text = wallItem.reaction?.hate.toString()
        }
    }


    private inner class WallTextViewHolder(itemView: View, context: Activity?) : RecyclerView.ViewHolder(itemView) {
        val activity: Activity? = context
        private val photo: ImageView = itemView.findViewById(R.id.imgProfile)
        private val firstName: TextView = itemView.findViewById(R.id.txtFirstName)
        private val message: TextView = itemView.findViewById(R.id.txtMessage)

        private val like: ImageView = itemView.findViewById(R.id.imgLike)
        private val love: ImageView = itemView.findViewById(R.id.imgLove)
        private val hate: ImageView = itemView.findViewById(R.id.imgHate)

        private val txtLike: TextView = itemView.findViewById(R.id.txtLike)
        private val txtLove: TextView = itemView.findViewById(R.id.txtLove)
        private val txtHate: TextView = itemView.findViewById(R.id.txtHate)


        fun bind(position: Int) {


            val wallItem = profiles[position]
            Glide.with(itemView.context).load(wallItem.profile_pic).into(photo)

            firstName.text = wallItem.name
            message.text = wallItem.message
            like.setColorFilter(Color.parseColor("#2A84FC"))
            love.setColorFilter(Color.parseColor("#2A84FC"))
            hate.setColorFilter(Color.parseColor("#2A84FC"))

            txtLike.text = wallItem.reaction?.like.toString()
            txtLove.text = wallItem.reaction?.love.toString()
            txtHate.text = wallItem.reaction?.hate.toString()
        }
    }

}



