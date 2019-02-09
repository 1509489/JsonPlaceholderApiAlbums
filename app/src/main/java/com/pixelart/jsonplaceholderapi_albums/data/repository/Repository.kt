package com.pixelart.jsonplaceholderapi_albums.data.repository

import androidx.lifecycle.LiveData
import com.pixelart.jsonplaceholderapi_albums.data.entities.AlbumEntity
import com.pixelart.jsonplaceholderapi_albums.data.model.Albums

interface Repository {

    fun getAlbumsNetwork(): LiveData<List<Albums>>
    fun getAlbumsLocalASC():LiveData<List<AlbumEntity>>
    fun getAlbumsLocalDESC():LiveData<List<AlbumEntity>>
    fun insertAlbum(album: AlbumEntity)
}