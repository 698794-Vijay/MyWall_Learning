package com.cts.mywall.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cts.mywall.model.WallItem
import com.cts.mywall.services.PostRepository
import com.cts.mywall.util.Constants
import java.util.*
import kotlin.collections.ArrayList

class PostItemViewModel : ViewModel() {
    private lateinit var wallList: MutableLiveData<ArrayList<WallItem>>
    private val repository = PostRepository()
    fun getMyWallData(): MutableLiveData<ArrayList<WallItem>> {
        wallList = repository.getMyWallPosts()
        return wallList
    }

    fun getCategoryTypes(): Array<String>? =
        wallList.value?.let {
            it.groupBy { it.category }.keys.toTypedArray().plusElement(Constants.AllText)
                .sortedArray()
        }

    fun getCategoryTypesByType(type: String): ArrayList<WallItem>? =
        wallList.value?.let {
            if (type.toLowerCase(Locale.ROOT) == Constants.AllText.toLowerCase(Locale.ROOT))
                it
            else {
                ArrayList(it.filter { it.category == type })
            }
        }
}