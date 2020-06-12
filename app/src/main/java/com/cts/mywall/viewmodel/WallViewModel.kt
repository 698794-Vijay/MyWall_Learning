package com.cts.mywall.viewmodel

import android.graphics.Color
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cts.mywall.R
import com.cts.mywall.entity.WallItem
import com.cts.mywall.model.WallList
import java.util.*
import kotlin.collections.ArrayList

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

        @JvmStatic
        @BindingAdapter("imageColor")
        fun imageColor(view: ImageView, color: String) {
           view.setColorFilter(Color.parseColor(color))
        }
    }
    private lateinit var categoryTypes : Array<String>

    private lateinit var wallList : MutableLiveData<ArrayList<WallItem>>
    private val repository = WallList()
    fun getMyWallData() : MutableLiveData<ArrayList<WallItem>> {
        wallList = repository.getMyWalls()
        return wallList
    }

    fun getCategoryTypes(): Array<String>{
        if(wallList.value != null)
            return wallList.value!!.groupBy { it.category }.keys.toTypedArray().plusElement("All").sortedArray()
        else
            return emptyArray()
    }

    fun getCategoryTypesByType(type: String): ArrayList<WallItem>{
        if(type.toLowerCase(Locale.ROOT) == "all")
            return  wallList.value!!
        else
            return ArrayList(wallList.value!!.filter{it.category == type})
    }
}