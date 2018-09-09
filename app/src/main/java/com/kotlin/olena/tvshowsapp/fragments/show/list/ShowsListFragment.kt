package com.kotlin.olena.tvshowsapp.fragments.show.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.SharedElementCallback
import android.support.v4.view.ViewCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.kotlin.olena.tvshowsapp.R
import com.kotlin.olena.tvshowsapp.callbacks.OnShowClickedListener
import com.kotlin.olena.tvshowsapp.fragments.show.detail.ShowDetailFragment
import com.kotlin.olena.tvshowsapp.fragments.show.detail.ShowDetailViewModel
import com.kotlin.olena.tvshowsapp.models.ShowModel
import com.kotlin.olena.tvshowsapp.ui.adapter.ShowsAdapter
import com.kotlin.olena.tvshowsapp.ui.layout.MainActivityUI
import kotlinx.android.synthetic.main.fragment_shows_list.*

class ShowsListFragment : Fragment(), OnShowClickedListener {

    var showsViewModel: ShowsViewModel? = null

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
        val gridLayoutManager = GridLayoutManager(context, 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val adapter = (showsRecyclerView.adapter as ShowsAdapter)
                return if (adapter.getItemViewType(position) == adapter.VIEW_SHOW) {
                    1
                } else {
                    2
                }
            }
        }
        showsRecyclerView.layoutManager = gridLayoutManager
        showsRecyclerView.adapter = ShowsAdapter(this)
        showsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager: LinearLayoutManager = LinearLayoutManager::class.java.cast(recyclerView?.layoutManager)
                val totalItem: Int = linearLayoutManager.itemCount
                val lastVisibleItem: Int = linearLayoutManager.findLastCompletelyVisibleItemPosition()
                if (totalItem <= lastVisibleItem + 1 && (showsRecyclerView.adapter as ShowsAdapter).listOfShows[lastVisibleItem] == null) {
                    showsViewModel?.addToShows()
                }
            }
        })
        showsViewModel = ViewModelProviders.of(this).get(ShowsViewModel::class.java)
        observeViewModel(showsViewModel)

    }

    override fun onShowClicked(position:Int, show: ShowModel,view: ImageView) {
        val transitionName: String = ViewCompat.getTransitionName(view)
        val fragment: ShowDetailFragment = ShowDetailFragment.newInstance(show.id,show.image.original, transitionName)
        fragment.sharedElementEnterTransition = TransitionInflater.from(context)
                .inflateTransition(android.R.transition.move)
        fragment.sharedElementReturnTransition = (TransitionInflater.from(context))
                .inflateTransition(android.R.transition.move)
        val showDetailVM: ShowDetailViewModel  = ViewModelProviders.of(activity!!).get(ShowDetailViewModel::class.java)
        showDetailVM.selectShow(show.id,show.image.original)
        fragmentManager ?. beginTransaction ()
                ?.addSharedElement(view,transitionName)
                ?.replace(MainActivityUI.mainLayoutId,
                fragment)
                ?.addToBackStack(null)
                ?.commit()
    }

    private fun observeViewModel(showsViewModel: ShowsViewModel?) {
        showsViewModel?.listShowsObservable?.observe(this, Observer<MutableList<ShowModel?>> { shows ->
            (showsRecyclerView.adapter as ShowsAdapter).setShowsList(shows!!)
        })
    }

}
