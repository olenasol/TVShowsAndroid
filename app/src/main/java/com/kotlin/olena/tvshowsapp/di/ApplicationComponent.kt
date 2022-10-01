package com.kotlin.olena.tvshowsapp.di

import android.app.Application
import com.kotlin.olena.tvshowsapp.presentation.main.MainViewModel
import com.kotlin.olena.tvshowsapp.presentation.prelogin.login.LoginViewModel
import com.kotlin.olena.tvshowsapp.presentation.prelogin.registration.RegistrationViewModel
import com.kotlin.olena.tvshowsapp.presentation.show.detail.ShowDetailViewModel
import com.kotlin.olena.tvshowsapp.presentation.show.list.ShowsViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * I like to consider that as the Dagger configuration entry point. It unfortunately doesn't look
 * simple (even though it doesn't look that bad either) and it doesn't get much better than that.
 */
@Singleton
@Component(modules = [AppModule::class, RepositoryModule::class, UseCaseModule::class])
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance fun application(application: Application): Builder
        fun build(): ApplicationComponent
    }
    /**
     * We could've chosen to create an inject() method instead and do field injection in the
     * Activity, but for this case this seems less verbose to me in the end.
     */
    fun getLoginViewModelFactory(): ViewModelFactory<LoginViewModel>

    fun getRegistrationViewModelFactory():ViewModelFactory<RegistrationViewModel>

    fun getShowsViewModelFactory():ViewModelFactory<ShowsViewModel>

    fun getShowDetailViewModelFactory():ViewModelFactory<ShowDetailViewModel>

    fun getMainViewModelFactory(): ViewModelFactory<MainViewModel>
}
