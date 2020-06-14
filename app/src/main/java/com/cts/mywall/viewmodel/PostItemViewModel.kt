package com.cts.mywall.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cts.mywall.entity.WallItem
import com.cts.mywall.model.WallPostList
import com.cts.mywall.util.Constants
import java.util.*
import kotlin.collections.ArrayList

class PostItemViewModel : ViewModel() {
    private lateinit var wallList: MutableLiveData<ArrayList<WallItem>>
    private val repository = WallPostList()
    fun getMyWallData(): MutableLiveData<ArrayList<WallItem>> {
        wallList = repository.getMyWalls()
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