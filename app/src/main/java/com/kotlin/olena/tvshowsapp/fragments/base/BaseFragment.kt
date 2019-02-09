package com.kotlin.olena.tvshowsapp.fragments.base

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.AnimRes
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.kotlin.olena.tvshowsapp.R

abstract class BaseFragment<VM : BaseViewModel>():Fragment(){
    protected val viewModel by lazy { provideViewModel() }

    abstract fun provideViewModel(): VM

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

    protected fun replaceFragment(fragment: BaseFragment<*>) {
        replaceFragment(fragment, true,
                R.anim.frag_fade_in, R.anim.frag_fade_out,
                R.anim.frag_fade_in, R.anim.frag_fade_out)
    }

    protected fun replaceFragment(fragment: BaseFragment<*>,
                                  addToBackStack: Boolean) {
        replaceFragment(fragment, addToBackStack,
                R.anim.frag_fade_in, R.anim.frag_fade_out,
                R.anim.frag_fade_in, R.anim.frag_fade_out)
    }

    protected fun replaceFragment(fragment: BaseFragment<*>,
                                  @AnimRes enterAnimId: Int,
                                  @AnimRes exitAnimId: Int,
                                  @AnimRes popEnterAnimId: Int,
                                  @AnimRes popExitAnimId: Int) {
        replaceFragment(fragment, true, enterAnimId, exitAnimId, popEnterAnimId, popExitAnimId)
    }

    protected fun replaceFragment(fragment: BaseFragment<*>,
                                  addToBackStack: Boolean,
                                  @AnimRes enterAnimId: Int,
                                  @AnimRes exitAnimId: Int,
                                  @AnimRes popEnterAnimId: Int,
                                  @AnimRes popExitAnimId: Int) {
        val transaction = fragmentManager?.beginTransaction()
        transaction?.setCustomAnimations(enterAnimId, exitAnimId, popEnterAnimId, popExitAnimId)
        transaction?.replace(R.id.main_container, fragment)
        if (addToBackStack) {
            transaction?.addToBackStack(fragment.javaClass.simpleName)
        }
        transaction?.commit()
    }

    protected fun replaceFragment(fragment: BaseFragment<*>, addToBackStack: Boolean = true, sharedElement: View) {
        val transaction = fragmentManager?.beginTransaction()
        transaction?.setCustomAnimations(
                R.anim.frag_fade_in,
                R.anim.frag_fade_out,
                R.anim.frag_fade_in,
                R.anim.frag_fade_out)
        ViewCompat.getTransitionName(sharedElement)?.let { transaction?.addSharedElement(sharedElement, it) }
        transaction?.replace(R.id.main_container, fragment)
        if (addToBackStack) {
            transaction?.addToBackStack(fragment.javaClass.simpleName)
        }
        transaction?.commit()
    }

}