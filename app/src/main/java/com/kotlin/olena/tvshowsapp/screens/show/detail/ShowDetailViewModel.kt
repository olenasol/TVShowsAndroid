package com.kotlin.olena.tvshowsapp.screens.show.detail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kotlin.olena.tvshowsapp.base.BaseViewModel
import com.kotlin.olena.tvshowsapp.data.models.ShowDetails
import com.kotlin.olena.tvshowsapp.data.networking.Resource
import javax.inject.Inject

class ShowDetailViewModel @Inject constructor(application: Application, val repo:ShowDetailRepository)
    : BaseViewModel(application) {

    private var showDetails: LiveData<Resource<ShowDetails>> = MutableLiveData()

    fun getShowDetails() = showDetails as LiveData<Resource<ShowDetails>>

    fun loadDetails(id: Int?) {
        id?.let {
            showDetails = repo.getShowDetails(id)
        }
    }

}