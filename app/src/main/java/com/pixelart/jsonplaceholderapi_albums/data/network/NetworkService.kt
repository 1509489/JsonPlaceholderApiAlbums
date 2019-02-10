package com.pixelart.jsonplaceholderapi_albums.data.network

import com.pixelart.jsonplaceholderapi_albums.data.model.AlbumResponse
import io.reactivex.Single
import retrofit2.http.GET

interface NetworkService {
    @GET("albums")
    fun getAlbums(): Single<List<AlbumResponse>>
}