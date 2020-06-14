package com.cts.mywall.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cts.mywall.databinding.WallImageCardBinding
import com.cts.mywall.databinding.WallTextCardBinding
import com.cts.mywall.entity.WallItem
import com.cts.mywall.util.Constants


class WallPostAdapter(var wallItemsList: ArrayList<WallItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val viewTypeImage = 1
    private val viewTypeText = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1) {
            return MyWallImageViewHolder(
                WallImageCardBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            return MyWallTextViewHolder(
                WallTextCardBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (wallItemsList[position].postType == Constants.postType) {
            (holder as MyWallImageViewHolder).wallImageCardBinding.postItemModel =
                wallItemsList[position]
        } else {
            (holder as MyWallTextViewHolder).walltextCardBinding.postItemModel =
                wallItemsList[position]
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (wallItemsList[position].postType == Constants.postType) viewTypeImage else viewTypeText
    }

    override fun getItemCount(): Int {
        return wallItemsList.size
    }

    inner class MyWallImageViewHolder(val wallImageCardBinding: WallImageCardBinding) :
        RecyclerView.ViewHolder(wallImageCardBinding.root)

    inner class MyWallTextViewHolder(val walltextCardBinding: WallTextCardBinding) :
        RecyclerView.ViewHolder(walltextCardBinding.root)
}


