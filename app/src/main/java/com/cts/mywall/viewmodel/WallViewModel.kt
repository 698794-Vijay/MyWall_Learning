package com.cts.mywall.viewmodel

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cts.mywall.R
import com.cts.mywall.entity.WallItem
import com.cts.mywall.model.WallList

class WallViewModel: ViewModel() {

    companion object {

        @JvmStatic
        @BindingAdapter("loadImage")
        fun loadImage(view: ImageView, url: String) {

            val options: RequestOptions = RequestOptions()
                .fitCenter()
                .error(R.mipmap.ic_launcher)
            Glide.with(view.context).load(url).apply(options).into(view)
        }
    }
    private lateinit var wallList : MutableLiveData<ArrayList<WallItem>>
    private val repository = WallList()
    fun getMyWallData() : MutableLiveData<ArrayList<WallItem>> {
        wallList = repository.getUsers()
        return wallList
    }
}