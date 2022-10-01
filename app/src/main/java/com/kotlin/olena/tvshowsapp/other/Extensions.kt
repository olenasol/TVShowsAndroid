package com.kotlin.olena.tvshowsapp.other

import android.app.Activity
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.snackbar.Snackbar
import com.kotlin.olena.tvshowsapp.R


inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int, addToBackStack: Boolean = false) {
    if (addToBackStack) {
        supportFragmentManager.inTransaction {
            replace(frameId, fragment).addToBackStack(fragment.javaClass.simpleName)
        }
    } else
        supportFragmentManager.inTransaction {
            replace(frameId, fragment)
        }
}

fun FragmentTransaction.replaceWithAnimation(frameId: Int, fragment: Fragment): FragmentTransaction {
    return this.replace(frameId, fragment).setCustomAnimations(
            R.anim.frag_fade_in,
            R.anim.frag_fade_out,
            R.anim.frag_fade_out,
            R.anim.frag_fade_in)
}

fun Fragment.replaceFragment(fragment: Fragment, frameId: Int, addToBackStack: Boolean = false) {
    (activity as AppCompatActivity).replaceFragment(fragment, frameId, addToBackStack)
}

fun Fragment.showSuccessSnackBar(text: String) {
    view?.let { view ->
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(Color.YELLOW)
            .setTextColor(Color.BLACK)
            .show()
    }
}

fun Fragment.showErrorSnackBar(text: String) {
    view?.let { view ->
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(Color.RED)
            .setTextColor(Color.WHITE)
            .show()
    }
}