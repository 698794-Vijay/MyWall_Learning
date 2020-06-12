package com.cts.mywall.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cts.mywall.entity.WallModelJson
import com.cts.mywall.model.WallList
import okhttp3.internal.wait


class WallViewModel: ViewModel() {

    val wallList = WallList()
    fun getMyWallData() : WallList {
        wallList.getUsers()
        return wallList
    }
}