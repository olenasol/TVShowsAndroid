package com.kotlin.olena.tvshowsapp.screens.show.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.kotlin.olena.tvshowsapp.R
import com.kotlin.olena.tvshowsapp.base.BaseFragment
import com.kotlin.olena.tvshowsapp.data.models.ImageModel
import com.kotlin.olena.tvshowsapp.screens.show.list.rv.OnShowClickedListener
import com.kotlin.olena.tvshowsapp.data.models.Show
import com.kotlin.olena.tvshowsapp.data.networking.Resource
import com.kotlin.olena.tvshowsapp.data.networking.Status
import com.kotlin.olena.tvshowsapp.di.injector
import com.kotlin.olena.tvshowsapp.other.replaceFragment
import com.kotlin.olena.tvshowsapp.screens.prelogin.login.LoginFragment
import com.kotlin.olena.tvshowsapp.screens.show.detail.ShowDetailFragment
import com.kotlin.olena.tvshowsapp.screens.show.list.rv.ShowsAdapter
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_shows_list.*

class ShowsListFragment : BaseFragment<ShowsViewModel>(), OnShowClickedListener, NavigationView.OnNavigationItemSelectedListener {

    override fun provideViewModel(): ShowsViewModel {
        return ViewModelProviders.of(this, activity?.injector?.getShowsViewModelFactory())
                .get(ShowsViewModel::class.java)
    }

    companion object {
        fun newInstance(): ShowsListFragment {
            return ShowsListFragment()
        }
    }

    //region Observers
    val listOfShowsObserver =
            Observer<Resource<List<Show>>> { resource ->
                if (resource != null) {
                    if (resource.status == Status.ERROR) {
                        error(resource.message.toString())
                    } else {
                        if (resource.data != null) {
                            (showsRecyclerView.adapter as ShowsAdapter).setShowsList(resource.data)
                        }
                    }
                }
            }

    //endregion
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_shows_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        initShowsResView()
        initSearch()
        navigationView.setNavigationItemSelectedListener(this)
    }

    private fun initSearch(){
        appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            floatingSearchView.translationY = verticalOffset.toFloat() })
        floatingSearchView.setOnQueryChangeListener { _, newQuery ->
            viewModel.onSearchInputChanged(newQuery)
        }
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
        showsRecyclerView.addOnScrollListener(object : androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: androidx.recyclerview.widget.RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager:LinearLayoutManager = (recyclerView.layoutManager as LinearLayoutManager)
                val totalItem: Int = linearLayoutManager.itemCount
                val lastVisibleItem: Int = linearLayoutManager.findLastCompletelyVisibleItemPosition()
                if (totalItem <= lastVisibleItem + 1 && (showsRecyclerView.adapter as ShowsAdapter).listOfShows[lastVisibleItem] == null) {
                    viewModel.fetchNextPage()
                }
            }
        })

    }

    override fun onShowClicked(position: Int, show: Show) {
        val fragment: ShowDetailFragment = ShowDetailFragment.newInstance(show.id, show.image.originalImageUrl)
        replaceFragment(fragment, R.id.main_container,true)
    }

    override fun onFavouriteClicked(position: Int) {
        viewModel.setShowToFavourite(position)
    }


    private fun observeViewModel() {
        viewModel.listOfShows.observe(this.viewLifecycleOwner, listOfShowsObserver)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_logout -> {
                FirebaseAuth.getInstance().signOut()
                replaceFragment(LoginFragment.newInstance(),R.id.main_container)
            }
        }
        return true
    }
}
