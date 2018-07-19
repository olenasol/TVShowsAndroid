package com.kotlin.olena.tvshowsapp.ui.layout

import com.kotlin.olena.tvshowsapp.activities.MainActivity
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.verticalLayout

class MainActivityUI : AnkoComponent<MainActivity> {
    companion object {
        val mainLayoutId = 222222
    }

    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
        verticalLayout {
            id = mainLayoutId
        }
    }
}