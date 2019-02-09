package com.pixelart.jsonplaceholderapi_albums.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pixelart.jsonplaceholderapi_albums.data.database.AlbumDatabase
import com.pixelart.jsonplaceholderapi_albums.data.entities.AlbumEntity
import com.pixelart.jsonplaceholderapi_albums.data.model.Albums
import com.pixelart.jsonplaceholderapi_albums.data.network.NetworkService
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class RepositoryImpl(private val networkService: NetworkService, private val database: AlbumDatabase): Repository {

    private val compositeDisposable = CompositeDisposable()
    private val albums = MutableLiveData<List<Albums>>()

    override fun getAlbumsNetwork(): MutableLiveData<List<Albums>> {
        networkService.getAlbums()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<Albums>>{
                override fun onSuccess(t: List<Albums>) {
                    albums.value = t
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }
            })
        return albums
    }

    override fun getAlbumsLocalASC(): LiveData<List<AlbumEntity>> = database.albumDao().getAlbumsASC()
    override fun getAlbumsLocalDESC(): LiveData<List<AlbumEntity>> = database.albumDao().getAlbumsDESC()

    override fun insertAlbum(album: AlbumEntity) {
        Thread{
            database.albumDao().insert(album)
        }.start()
    }

    fun getAlbumList(albums: List<Albums>): MutableLiveData<List<Albums>>{
        this.albums.value = albums
        return this.albums
    }

    private fun onHandleError(e: Throwable){
        if (e.message != null){
            e.printStackTrace()
        }
    }
}