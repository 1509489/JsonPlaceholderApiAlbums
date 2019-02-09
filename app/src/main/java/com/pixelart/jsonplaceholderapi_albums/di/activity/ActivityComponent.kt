package com.pixelart.jsonplaceholderapi_albums.di.activity

import com.pixelart.jsonplaceholderapi_albums.factories.ViewModelFactory
import com.pixelart.jsonplaceholderapi_albums.ui.MainActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {
    fun getViewModelFactory():ViewModelFactory
    fun inject(mainActivity: MainActivity)
}