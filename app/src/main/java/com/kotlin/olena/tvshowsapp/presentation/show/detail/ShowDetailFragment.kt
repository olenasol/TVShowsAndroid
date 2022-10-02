package com.kotlin.olena.tvshowsapp.presentation.show.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.kotlin.olena.tvshowsapp.GlideApp
import com.kotlin.olena.tvshowsapp.R
import com.kotlin.olena.tvshowsapp.databinding.FragmentShowDetailBinding
import com.kotlin.olena.tvshowsapp.di.GenericSavedStateViewModelFactory
import com.kotlin.olena.tvshowsapp.di.injector
import com.kotlin.olena.tvshowsapp.domain.models.ShowInfo
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

const val ARG_ID = "arg_id"

class ShowDetailFragment : Fragment(R.layout.fragment_show_detail) {

    @Inject
    internal lateinit var showDetailsViewModelFactory: ShowDetailViewModelFactory

    private val viewModel: ShowDetailViewModel by viewModels {
        GenericSavedStateViewModelFactory(showDetailsViewModelFactory, this, arguments)
    }

    private var _binding: FragmentShowDetailBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.injector?.inject(this)
        _binding = FragmentShowDetailBinding.bind(view)
        subscribeToObservers()
    }

    override fun onDestroyView() {
        // Consider not storing the binding instance in a field, if not needed.
        _binding = null
        super.onDestroyView()
    }

    private fun subscribeToObservers() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.showDetails.onEach { details ->
                   updateScreen(details)
                }.launchIn(this)
            }
        }
    }

    private fun updateScreen(details: ShowInfo) {
        _binding?.apply {
            txtName.text = details.name
            txtLanguage.text = getString(R.string.language, details.language)
            txtRating.text = getString(R.string.rating, details.rating)
            txtStatus.text = getString(R.string.status, details.status)
            context?.let { context ->
                GlideApp.with(context).load(details.imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .into(posterImgView)
            }
        }
        hideLoading()
    }


    private fun hideLoading() {
        _binding?.loader?.visibility = View.GONE
        _binding?.detailsLayout?.visibility = View.VISIBLE
    }


    companion object {

        fun newInstance(id: Int): ShowDetailFragment {
            val fragment = ShowDetailFragment()
            val bundle =  Bundle()
            bundle.putInt(ARG_ID,id)
            fragment.arguments = bundle
            return fragment
        }
    }
}
