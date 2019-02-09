package com.pixelart.jsonplaceholderapi_albums.di.activity

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.pixelart.jsonplaceholderapi_albums.factories.ViewModelFactory
import com.pixelart.jsonplaceholderapi_albums.ui.MainViewModel
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(val activity: AppCompatActivity) {

    @Provides
    @ActivityScope
    fun providesMainViewModel(viewModelFactory: ViewModelFactory) = ViewModelProviders.of(activity, viewModelFactory)
        .get(MainViewModel::class.java)
}