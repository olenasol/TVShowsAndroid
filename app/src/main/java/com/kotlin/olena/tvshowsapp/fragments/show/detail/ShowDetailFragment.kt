package com.kotlin.olena.tvshowsapp.fragments.show.detail


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.olena.tvshowsapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_show_detail.*


class ShowDetailFragment : Fragment() {

    var showDetailVM: ShowDetailViewModel? = null
    var showId:Int =0
    var transitionName:String = ""
    var posterUrl:String = ""


    companion object {

        fun newInstance(id: Int,poster:String, transitionName: String): ShowDetailFragment {
            val fragment = ShowDetailFragment()
            fragment.transitionName = transitionName
            fragment.showId = id
            fragment.posterUrl = poster
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        posterImgView.transitionName = transitionName
        showDetailVM = ViewModelProviders.of(activity!!).get(ShowDetailViewModel::class.java)
        Picasso.get().load(showDetailVM?.getShowImage()).into(posterImgView)
        showDetailVM?.show?.observe(this, Observer { show ->
            if (show != null)
                Picasso.get().load(show.image.original).into(posterImgView)
        })

    }
}
