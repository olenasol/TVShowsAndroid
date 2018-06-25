package com.kotlin.olena.tvshowsapp.fragments.shows_list


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.kotlin.olena.tvshowsapp.R
import com.kotlin.olena.tvshowsapp.models.ImageModel
import com.kotlin.olena.tvshowsapp.models.ShowModel
import com.kotlin.olena.tvshowsapp.rest.ApiClient
import com.kotlin.olena.tvshowsapp.rest.ApiInterface
import com.kotlin.olena.tvshowsapp.ui.ShowsAdapter
import kotlinx.android.synthetic.main.fragment_shows_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShowsListFragment : Fragment() {
    companion object {
        fun newInstance(): ShowsListFragment {
            return ShowsListFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shows_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initShowsResView()
    }
    fun initShowsResView(){
        showsRecyclerView.layoutManager = LinearLayoutManager(context)
        //showsRecyclerView.adapter = ShowsAdapter(getShowsList())
        getShowsFromServer()
    }

    fun getShowsFromServer(){
        val apiService = ApiClient.getClient().create(ApiInterface::class.java)
        val call:Call<List<ShowModel>> = apiService.getShowsByPage(0)
        call.enqueue(object : Callback<List<ShowModel>> {

            override fun onResponse(call: Call<List<ShowModel>>?, response: Response<List<ShowModel>>?) {
                updateUi(response?.body())
            }

            override fun onFailure(call: Call<List<ShowModel>>?, t: Throwable?) {
                //
            }
        })
    }

    fun updateUi(list:List<ShowModel>?){
        showsRecyclerView.adapter = ShowsAdapter(list)
    }

    fun getShowsList():List<ShowModel>{
        val listOfShows: MutableList<ShowModel> = ArrayList()
        listOfShows.add(ShowModel(1,"Under the Dome", ImageModel("http://static.tvmaze.com/uploads/images/medium_portrait/0/1.jpg")))
        listOfShows.add(ShowModel(2,"Person of Interest",ImageModel("http://static.tvmaze.com/uploads/images/medium_portrait/55/137682.jpg")))
        listOfShows.add(ShowModel(3,"Arrow", ImageModel("http://static.tvmaze.com/uploads/images/medium_portrait/147/369303.jpg")))
        listOfShows.add(ShowModel(4,"Glee", ImageModel( "http://static.tvmaze.com/uploads/images/medium_portrait/0/73.jpg")))
        listOfShows.add(ShowModel(5,"Revenge", ImageModel("http://static.tvmaze.com/uploads/images/medium_portrait/82/206879.jpg")))
        return listOfShows
    }
}
