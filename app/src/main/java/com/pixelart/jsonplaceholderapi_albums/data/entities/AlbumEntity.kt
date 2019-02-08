package com.pixelart.jsonplaceholderapi_albums.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pixelart.jsonplaceholderapi_albums.common.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class AlbumEntity(
    var userId: Int,
    @PrimaryKey var id: Int? = null,
    var title: String
)