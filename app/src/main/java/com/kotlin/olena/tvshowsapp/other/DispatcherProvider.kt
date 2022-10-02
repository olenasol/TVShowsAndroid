package com.kotlin.olena.tvshowsapp.other

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

interface DispatcherProvider {
    fun main(): CoroutineDispatcher
    fun io(): CoroutineDispatcher
    fun computation(): CoroutineDispatcher
    fun unconfined(): CoroutineDispatcher
}

class DefaultCoroutineContextProvider @Inject constructor(): DispatcherProvider {
    override fun main() = Dispatchers.Main

    override fun io() = Dispatchers.IO

    override fun computation() = Dispatchers.Default

    override fun unconfined() = Dispatchers.Unconfined

}