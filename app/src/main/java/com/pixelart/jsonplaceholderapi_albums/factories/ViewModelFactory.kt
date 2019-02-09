package com.pixelart.jsonplaceholderapi_albums.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pixelart.jsonplaceholderapi_albums.data.repository.RepositoryImpl
import com.pixelart.jsonplaceholderapi_albums.ui.MainViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(val repositoryImpl: RepositoryImpl): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) MainViewModel(repositoryImpl) as T
        else throw IllegalArgumentException("ViewModel not found")
    }
}