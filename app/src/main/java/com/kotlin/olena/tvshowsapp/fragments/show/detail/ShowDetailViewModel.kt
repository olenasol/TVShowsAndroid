package com.kotlin.olena.tvshowsapp.fragments.show.detail

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.kotlin.olena.tvshowsapp.models.ImageModel
import com.kotlin.olena.tvshowsapp.models.ShowModel

class ShowDetailViewModel : ViewModel() {
    var show: MutableLiveData<ShowModel> = MutableLiveData()

    fun selectShow(id:Int,url: String){
        show.postValue(ShowModel(id, ImageModel(url),false))
    }
    fun getShowImage():String?{
        return show.value?.image?.original
    }
    fun getShowDetail(id: Int?) {
        ShowDetailRepository().getShowDetail(id!!, show)
    }

}