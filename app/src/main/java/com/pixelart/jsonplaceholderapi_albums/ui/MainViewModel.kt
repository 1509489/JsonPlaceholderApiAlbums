package com.pixelart.jsonplaceholderapi_albums.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.pixelart.jsonplaceholderapi_albums.data.entities.AlbumEntity
import com.pixelart.jsonplaceholderapi_albums.data.model.Albums
import com.pixelart.jsonplaceholderapi_albums.data.repository.RepositoryImpl

class MainViewModel(private val repository: RepositoryImpl): ViewModel() {

    fun getAlbumsNetwork():LiveData<List<Albums>> = repository.getAlbumsNetwork()
    fun getAlbumsASC():LiveData<List<AlbumEntity>> = repository.getAlbumsLocalASC()
    fun getAlbumsDESC():LiveData<List<AlbumEntity>> = repository.getAlbumsLocalDESC()
    fun insertAlbum(userId: Int, id: Int, title: String){
        repository.insertAlbum(AlbumEntity(userId, id, title))
    }
}