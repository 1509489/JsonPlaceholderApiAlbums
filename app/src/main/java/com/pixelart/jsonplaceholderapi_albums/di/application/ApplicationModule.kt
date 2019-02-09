package com.pixelart.jsonplaceholderapi_albums.di.application

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.pixelart.jsonplaceholderapi_albums.common.DATABASE_NAME
import com.pixelart.jsonplaceholderapi_albums.data.database.AlbumDatabase
import com.pixelart.jsonplaceholderapi_albums.data.network.NetworkService
import com.pixelart.jsonplaceholderapi_albums.data.repository.RepositoryImpl
import com.pixelart.jsonplaceholderapi_albums.di.network.NetworkModule
import dagger.Module
import dagger.Provides

@Module(includes = [NetworkModule::class])
class ApplicationModule(private val application: Application) {

    @Provides
    @ApplicationScope
    fun providesContext(): Context = application

    @Provides
    @ApplicationScope
    fun providesDatabase(): AlbumDatabase = Room.databaseBuilder(application.applicationContext,
        AlbumDatabase::class.java, DATABASE_NAME).build()

    @Provides
    @ApplicationScope
    fun providesRepository(networkService: NetworkService, database: AlbumDatabase): RepositoryImpl =
        RepositoryImpl(networkService, database)
}