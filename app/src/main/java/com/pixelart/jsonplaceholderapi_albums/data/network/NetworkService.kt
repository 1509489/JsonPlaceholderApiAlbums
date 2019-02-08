package com.pixelart.jsonplaceholderapi_albums.data.network

import com.pixelart.jsonplaceholderapi_albums.data.entities.AlbumEntity
import io.reactivex.Single
import retrofit2.http.GET

interface NetworkService {
    @GET("albums")
    fun getAlbums(): Single<List<AlbumEntity>>
}