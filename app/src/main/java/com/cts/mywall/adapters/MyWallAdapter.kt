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
import com.bumptech.glide.request.RequestOptions
import com.cts.mywall.R
import com.cts.mywall.databinding.WallImageCardBinding
import com.cts.mywall.databinding.WallTextCardBinding
import com.cts.mywall.entity.WallItem


class MyWallAdapter(var wallItemsList: ArrayList<WallItem>, context: Activity) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val activity: Activity? = context

    private val viewTypeImage = 1
    private val viewTypeText = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1) {
            return MyWallImageViewHolder(WallImageCardBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
        else {
            return MyWallTextViewHolder(WallTextCardBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (wallItemsList[position].postType  == "image") {
            (holder as MyWallImageViewHolder).wallImageCardBinding.viewModel = wallItemsList[position]
        } else {
            (holder as MyWallTextViewHolder).walltextCardBinding.viewModel = wallItemsList[position]
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (wallItemsList[position].postType == "image") viewTypeImage else viewTypeText
    }

    override fun getItemCount(): Int {
        return wallItemsList.size
    }


    inner class MyWallImageViewHolder(val wallImageCardBinding: WallImageCardBinding):RecyclerView.ViewHolder(wallImageCardBinding.root)
    inner class MyWallTextViewHolder(val walltextCardBinding: WallTextCardBinding):RecyclerView.ViewHolder(walltextCardBinding.root)


    private inner class MyWallViewHolder1(itemView: View, context: Activity?) : RecyclerView.ViewHolder(itemView) {
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

            val wallItem = wallItemsList[position]
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


    private inner class WallTextViewHolder1(itemView: View, context: Activity?) : RecyclerView.ViewHolder(itemView) {
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
            val wallItem = wallItemsList[position]
            val options: RequestOptions = RequestOptions()
                .centerCrop()
                .error(R.mipmap.ic_launcher)
            Glide.with(itemView.context).load(wallItem.profile_pic).apply(options).into(photo)

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


