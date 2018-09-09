package com.kotlin.olena.tvshowsapp.ui.layout

import com.kotlin.olena.tvshowsapp.activities.MainActivity
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.verticalLayout

class ShowsListFragmentUI : AnkoComponent<MainActivity> {
    companion object {
        val showsRecyclerView:Int = 3333
    }

    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
        verticalLayout {
            recyclerView {
                id = ShowsListFragmentUI.showsRecyclerView
            }
        }
    }
}