package com.kotlin.olena.tvshowsapp.fragments.show.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.navigation.NavigationView
import com.kotlin.olena.tvshowsapp.R
import com.kotlin.olena.tvshowsapp.callbacks.OnShowClickedListener
import com.kotlin.olena.tvshowsapp.fragments.base.BaseFragment
import com.kotlin.olena.tvshowsapp.fragments.login.LoginFragment
import com.kotlin.olena.tvshowsapp.fragments.shared.LoginViewModel
import com.kotlin.olena.tvshowsapp.fragments.show.detail.ShowDetailFragment
import com.kotlin.olena.tvshowsapp.fragments.show.detail.ShowDetailViewModel
import com.kotlin.olena.tvshowsapp.fragments.show.list.rv.ShowsAdapter
import com.kotlin.olena.tvshowsapp.models.ShowModel
import kotlinx.android.synthetic.main.fragment_shows_list.*

class ShowsListFragment : BaseFragment<ShowsViewModel>(), OnShowClickedListener, NavigationView.OnNavigationItemSelectedListener {

    override fun provideViewModel(): ShowsViewModel {
        return ViewModelProviders.of(this).get(ShowsViewModel::class.java)
    }

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
        observeViewModel()
        initShowsResView()
        navigationView.setNavigationItemSelectedListener(this)
    }

    /**
     * need to have adapter as separate variable due to bug
     * E/RecyclerView: No adapter attached; skipping layout
     * on Huawei Y7 (problem with reference in spanSizeLookup)
     */
    private fun initShowsResView() {
        val adapter = ShowsAdapter(this)
        showsRecyclerView.adapter = adapter
        val gridLayoutManager = androidx.recyclerview.widget.GridLayoutManager(context, 2)

        gridLayoutManager.spanSizeLookup = object : androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (adapter.getItemViewType(position) == ShowsAdapter.VIEW_SHOW) {
                    1
                } else {
                    2
                }
            }
        }
        showsRecyclerView.layoutManager = gridLayoutManager
        if (viewModel.verifyIfScrollNeeded(
                        (showsRecyclerView.layoutManager as androidx.recyclerview.widget.GridLayoutManager).findFirstVisibleItemPosition(),
                        (showsRecyclerView.layoutManager as androidx.recyclerview.widget.GridLayoutManager).findFirstVisibleItemPosition())) {
            (showsRecyclerView.layoutManager as androidx.recyclerview.widget.GridLayoutManager).scrollToPosition(viewModel.position)
        }
        showsRecyclerView.addOnScrollListener(object : androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: androidx.recyclerview.widget.RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager: androidx.recyclerview.widget.LinearLayoutManager = (recyclerView.layoutManager as androidx.recyclerview.widget.LinearLayoutManager)
                val totalItem: Int = linearLayoutManager.itemCount
                val lastVisibleItem: Int = linearLayoutManager.findLastCompletelyVisibleItemPosition()
                if (totalItem <= lastVisibleItem + 1 && (showsRecyclerView.adapter as ShowsAdapter).listOfShows[lastVisibleItem] == null) {
                    viewModel.addToShows()
                }
            }
        })

    }

    override fun onShowClicked(position: Int, show: ShowModel, view: ImageView) {
        val transitionName: String = ViewCompat.getTransitionName(view)!!
        val fragment: ShowDetailFragment = ShowDetailFragment.newInstance(show.id, show.image.original, transitionName)
        val showDetailVM: ShowDetailViewModel = ViewModelProviders.of(activity!!).get(ShowDetailViewModel::class.java)
        showDetailVM.selectShow(show.id, show.image.original)
        viewModel.position = position
        replaceFragment(fragment,true,view)
    }

    override fun onFavouriteClicked(position: Int) {
        viewModel.setShowToFavourite(position)
    }


    private fun observeViewModel() {
        viewModel.listShowsObservable.observe(this, Observer<MutableList<ShowModel?>> { shows ->
            if (shows != null) {
                (showsRecyclerView.adapter as ShowsAdapter).setShowsList(shows)
            }
        })
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        when (id) {
            R.id.nav_logout -> {
                ViewModelProviders.of(activity!!).get(LoginViewModel::class.java).logout()
                replaceFragment(LoginFragment.newInstance())
            }
        }
        return true
    }
}
