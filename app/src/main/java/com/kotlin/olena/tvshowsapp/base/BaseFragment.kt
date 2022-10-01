package com.kotlin.olena.tvshowsapp.base

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.kotlin.olena.tvshowsapp.presentation.main.MainActivity

abstract class BaseFragment<VM : BaseViewModel>() : Fragment() {
    protected lateinit var viewModel: VM

    abstract fun provideViewModel(): VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = provideViewModel()
    }

    protected fun hideKeyboard() {
        val view = activity?.currentFocus
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    protected fun showKeyboard(view: View) {
        if (view.requestFocus()) {
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    protected open fun showLoading() {
        hideKeyboard()
        (activity as MainActivity).changeLoadingState(true)
    }

    protected open fun hideLoading() {
        (activity as MainActivity).changeLoadingState(false)
    }

    protected fun showError(description: String?) {
        view?.let { view ->
            Snackbar.make(view, description.toString(), Snackbar.LENGTH_SHORT)
                .setBackgroundTint(Color.RED)
                .setTextColor(Color.WHITE)
                .show()
        }
    }

    protected fun showSuccess(text: String) {
        view?.let { view ->
            Snackbar.make(view, text, Snackbar.LENGTH_SHORT)
                .setBackgroundTint(Color.GREEN)
                .setTextColor(Color.WHITE)
                .show()
        }
    }
}