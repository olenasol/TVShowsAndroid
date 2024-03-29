package com.kotlin.olena.tvshowsapp.di

import android.app.Application
import com.kotlin.olena.auth.AuthModule
import com.kotlin.olena.database.DatabaseModule
import com.kotlin.olena.network.NetworkModule
import com.kotlin.olena.tvshowsapp.presentation.main.MainViewModel
import com.kotlin.olena.tvshowsapp.presentation.prelogin.login.LoginViewModel
import com.kotlin.olena.tvshowsapp.presentation.prelogin.registration.RegistrationViewModel
import com.kotlin.olena.tvshowsapp.presentation.show.detail.ShowDetailFragment
import com.kotlin.olena.tvshowsapp.presentation.show.list.ShowsViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    RepositoryModule::class,
    UseCaseModule::class,
    CoroutineModule::class,
    NetworkModule::class,
    DatabaseModule::class,
    AuthModule::class,
])
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance fun application(application: Application): Builder
        fun build(): ApplicationComponent
    }

    fun getLoginViewModelFactory(): ViewModelFactory<LoginViewModel>

    fun getRegistrationViewModelFactory():ViewModelFactory<RegistrationViewModel>

    fun getShowsViewModelFactory():ViewModelFactory<ShowsViewModel>

    fun getMainViewModelFactory(): ViewModelFactory<MainViewModel>

    fun inject(fragment: ShowDetailFragment)
}
