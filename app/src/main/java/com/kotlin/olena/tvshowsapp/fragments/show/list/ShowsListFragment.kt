package com.kotlin.olena.tvshowsapp.fragments.show.list

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.kotlin.olena.tvshowsapp.R
import com.kotlin.olena.tvshowsapp.models.ShowModel
import com.kotlin.olena.tvshowsapp.ui.adapter.ShowsAdapter
import kotlinx.android.synthetic.main.fragment_shows_list.*

class ShowsListFragment : Fragment() {

    val ADAPTER_STATE = "SHOWS_POSITION"

    companion object {
        fun newInstance(): ShowsListFragment {
            return ShowsListFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_shows_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initShowsResView()
    }

    private fun initShowsResView() {
        showsRecyclerView.adapter = ShowsAdapter(mutableListOf())
        val gridLayoutManager= GridLayoutManager(context, 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                val adapter = (showsRecyclerView.adapter as ShowsAdapter)
                return if(adapter.getItemViewType(position) == adapter.VIEW_SHOW){
                    1
                } else{
                    2
                }
            }
        }
        showsRecyclerView.layoutManager = gridLayoutManager
        val showsViewModel: ShowsViewModel = ViewModelProvider.AndroidViewModelFactory
                .getInstance(activity!!.application).create(ShowsViewModel::class.java)
        observeViewModel(showsViewModel)
    }

    private fun observeViewModel(showsViewModel: ShowsViewModel) {
        showsViewModel.listShowsObservable.observe(this, Observer<MutableList<ShowModel?>> { shows ->
            (showsRecyclerView.adapter as ShowsAdapter).addShowsList(shows!!)
            (showsRecyclerView.adapter as ShowsAdapter).addProgress()
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val recyclerState = showsRecyclerView.layoutManager.onSaveInstanceState()
        outState.putParcelable(ADAPTER_STATE,recyclerState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        val savedLayoutState:Parcelable? = savedInstanceState?.getParcelable(ADAPTER_STATE)
        showsRecyclerView.layoutManager.onRestoreInstanceState(savedLayoutState)
    }
}
