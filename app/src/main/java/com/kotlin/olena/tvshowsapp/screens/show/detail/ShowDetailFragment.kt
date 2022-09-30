package com.kotlin.olena.tvshowsapp.screens.show.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kotlin.olena.tvshowsapp.GlideApp
import com.kotlin.olena.tvshowsapp.R
import com.kotlin.olena.tvshowsapp.base.BaseFragment
import com.kotlin.olena.tvshowsapp.data.models.ShowDetails
import com.kotlin.olena.tvshowsapp.data.networking.Resource
import com.kotlin.olena.tvshowsapp.data.networking.Status
import com.kotlin.olena.tvshowsapp.di.injector
import kotlinx.android.synthetic.main.fragment_show_detail.*


class ShowDetailFragment : BaseFragment<ShowDetailViewModel>() {

    override fun provideViewModel(): ShowDetailViewModel {
        return ViewModelProvider(this, activity?.injector?.getShowDetailViewModelFactory() as ViewModelProvider.Factory)
                .get(ShowDetailViewModel::class.java)
    }

    companion object {

        const val ARG_ID = "arg_id"
        const val ARGS_POSTER = "args_poster"

        fun newInstance(id: Int, poster: String?): ShowDetailFragment {
            val fragment = ShowDetailFragment()
            val bundle =  Bundle()
            bundle.putInt(ARG_ID,id)
            bundle.putString(ARGS_POSTER, poster)
            fragment.arguments = bundle
            return fragment
        }
    }

    //region Observers
    val showDetailsObserver = Observer<Resource<ShowDetails>> { resource ->
                if (resource != null) {
                    when(resource.status){
                        Status.LOADING->{
                            showLoading()
                        }
                        Status.SUCCESS ->{
                            hideLoading()
                            if(resource.data != null) {
                                txtName.text = resource.data.name
                                txtRating.text = getString(R.string.rating,resource.data.rating?.rating)
                                txtLanguage.text = getString(R.string.language,resource.data.language)
                                txtStatus.text = getString(R.string.status,resource.data.status)
                            }
                        }
                        Status.ERROR->{
                            hideLoading()
                            error(resource.message.toString())
                        }
                    }
                }
            }

    //endregion

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let { context ->
            GlideApp.with(context).load(arguments?.getString(ARGS_POSTER))
                    .into(posterImgView)
        }
        viewModel.loadDetails(arguments?.getInt(ARG_ID))
        subscribeToObservers()
    }

    private fun subscribeToObservers() {
        viewModel.getShowDetails().observe(this.viewLifecycleOwner,showDetailsObserver)
    }

    override fun showLoading(){
        loader.visibility = View.VISIBLE
        detailsLayout.visibility = View.GONE
    }

    override fun hideLoading() {
        loader.visibility = View.GONE
        detailsLayout.visibility = View.VISIBLE
    }

}
