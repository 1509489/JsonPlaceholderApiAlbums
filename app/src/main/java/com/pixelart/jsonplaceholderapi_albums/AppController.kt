package com.pixelart.jsonplaceholderapi_albums

import android.app.Application
import com.pixelart.jsonplaceholderapi_albums.di.application.ApplicationComponent
import com.pixelart.jsonplaceholderapi_albums.di.application.ApplicationModule
import com.pixelart.jsonplaceholderapi_albums.di.application.DaggerApplicationComponent

class AppController: Application() {
    val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }
}