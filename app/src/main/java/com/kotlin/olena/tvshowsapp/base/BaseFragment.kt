package com.kotlin.olena.tvshowsapp.base

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import es.dmoral.toasty.Toasty

abstract class BaseFragment<VM : BaseViewModel>() : Fragment() {
    protected lateinit var viewModel: VM

    abstract fun provideViewModel(): VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = provideViewModel()
    }

    protected fun hideKeyboard() {
        val view = activity?.currentFocus
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    protected fun showKeyboard(view: View) {
        if (view.requestFocus()) {
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm!!.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    protected fun showLoading() {
        hideKeyboard()
        (activity as MainActivity).changeLoadingState(true)
    }

    protected fun hideLoading() {
        (activity as MainActivity).changeLoadingState(false)
    }

    protected fun showError(description: String?) {
        context?.let { context ->
            Toasty.error(context, description.toString(), Toast.LENGTH_SHORT, true).show()
        }
    }

    protected fun showSuccess(text: String) {
        context?.let { context ->
            Toasty.success(context, text, Toast.LENGTH_SHORT, true).show()
        }
    }
}