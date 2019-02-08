package com.pixelart.jsonplaceholderapi_albums.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pixelart.jsonplaceholderapi_albums.common.DATABASE_VERSION
import com.pixelart.jsonplaceholderapi_albums.data.dao.AlbumDao
import com.pixelart.jsonplaceholderapi_albums.data.entities.AlbumEntity

@Database(entities = [AlbumEntity::class], version = DATABASE_VERSION)
abstract class AlbumDatabase: RoomDatabase() {

    abstract fun albumDao(): AlbumDao
}