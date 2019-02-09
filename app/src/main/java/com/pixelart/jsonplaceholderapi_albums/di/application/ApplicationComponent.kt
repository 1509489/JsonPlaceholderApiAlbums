package com.pixelart.jsonplaceholderapi_albums.di.application

import com.pixelart.jsonplaceholderapi_albums.data.repository.RepositoryImpl
import com.pixelart.jsonplaceholderapi_albums.di.activity.ActivityComponent
import com.pixelart.jsonplaceholderapi_albums.di.activity.ActivityModule
import dagger.Component

@ApplicationScope
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun newActivityComponent(activityModule: ActivityModule): ActivityComponent
}