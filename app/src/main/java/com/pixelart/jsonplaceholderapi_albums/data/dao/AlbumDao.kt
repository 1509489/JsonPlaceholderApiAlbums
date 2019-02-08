package com.pixelart.jsonplaceholderapi_albums.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pixelart.jsonplaceholderapi_albums.data.entities.AlbumEntity

@Dao
interface AlbumDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(album: AlbumEntity)

    @Query("SELECT * FROM album_table ORDER BY title ASC")
    fun getAlbumsASC():LiveData<List<AlbumEntity>>

    @Query("SELECT * FROM album_table ORDER BY title DESC")
    fun getAlbumsDESC():LiveData<List<AlbumEntity>>
}