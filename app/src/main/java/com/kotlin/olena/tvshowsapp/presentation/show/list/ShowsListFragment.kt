package com.kotlin.olena.tvshowsapp.presentation.show.list

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kotlin.olena.tvshowsapp.R
import com.kotlin.olena.tvshowsapp.presentation.show.list.rv.OnShowClickedListener
import com.kotlin.olena.tvshowsapp.databinding.FragmentShowsListBinding
import com.kotlin.olena.tvshowsapp.di.injector
import com.kotlin.olena.tvshowsapp.other.replaceFragment
import com.kotlin.olena.tvshowsapp.presentation.prelogin.login.LoginFragment
import com.kotlin.olena.tvshowsapp.presentation.show.detail.ShowDetailFragment
import com.kotlin.olena.tvshowsapp.presentation.show.list.rv.ShowsAdapter
import kotlinx.android.synthetic.main.fragment_shows_list.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ShowsListFragment : Fragment(R.layout.fragment_shows_list), OnShowClickedListener {

    private val viewModel: ShowsViewModel by viewModels {
        activity?.injector?.getShowsViewModelFactory() as ViewModelProvider.Factory
    }
    private val adapter = ShowsAdapter(this)

    private var _binding: FragmentShowsListBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentShowsListBinding.bind(view)
        _binding?.mainToolbar?.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.item_logout -> {
                    viewModel.logout()
                    replaceFragment(LoginFragment.newInstance(),R.id.main_container)
                }
            }
            return@setOnMenuItemClickListener true
        }
        initShowsResView()
        observeViewModel()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    /**
     * need to have adapter as separate variable due to bug
     * E/RecyclerView: No adapter attached; skipping layout
     * on Huawei Y7 (problem with reference in spanSizeLookup)
     */
    private fun initShowsResView() {
        showsRecyclerView.adapter = adapter
        val gridLayoutManager = androidx.recyclerview.widget.GridLayoutManager(context, 2)
        showsRecyclerView.layoutManager = gridLayoutManager
    }

    override fun onShowClicked(showId: Int) {
        val fragment: ShowDetailFragment = ShowDetailFragment.newInstance(showId)
        replaceFragment(fragment, R.id.main_container,true)
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.screenState.onEach { screenState ->
                    updateScreenState(screenState)
                }.launchIn(this)
            }
        }
    }

    private fun updateScreenState(screenState: ShowsViewModel.ScreenState) {
        when(screenState) {
            is ShowsViewModel.ScreenState.Loading ->{
                _binding?.progressBar?.visibility = View.VISIBLE
                _binding?.showsRecyclerView?.visibility = View.GONE
                _binding?.txtError?.visibility = View.GONE
            }
            is ShowsViewModel.ScreenState.Error -> {
                _binding?.progressBar?.visibility = View.GONE
                _binding?.showsRecyclerView?.visibility = View.GONE
                _binding?.txtError?.visibility = View.VISIBLE
            }
            is ShowsViewModel.ScreenState.Success -> {
                _binding?.progressBar?.visibility = View.GONE
                _binding?.showsRecyclerView?.visibility = View.VISIBLE
                _binding?.txtError?.visibility = View.GONE
                adapter.submitList(screenState.list)
            }
        }
    }

    companion object {
        fun newInstance(): ShowsListFragment {
            return ShowsListFragment()
        }
    }
}
