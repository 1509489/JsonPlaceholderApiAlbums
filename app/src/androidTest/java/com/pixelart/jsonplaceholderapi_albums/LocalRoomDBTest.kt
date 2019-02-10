package com.pixelart.jsonplaceholderapi_albums

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pixelart.jsonplaceholderapi_albums.data.database.AlbumDatabase
import com.pixelart.jsonplaceholderapi_albums.data.entities.AlbumEntity
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LocalRoomDBTest {
    private lateinit var albumDatabase: AlbumDatabase

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        albumDatabase = Room.inMemoryDatabaseBuilder(context,
            AlbumDatabase::class.java).allowMainThreadQueries().build()
    }

    @After
    fun closeDB(){
        albumDatabase.close()
    }

    @Test
    fun testWriteAndRead(){
        val albumList = ArrayList<AlbumEntity>()
        for (i in 0 until 5){
            albumList.add(AlbumEntity(1, i, "Album $i"))
        }

        albumList.forEach {
            albumDatabase.albumDao().insert(it)
        }

        val albums:List<AlbumEntity> = ITestUtils.getValue(albumDatabase.albumDao().getAlbumsASC())
        assert(albums.isNotEmpty())
        Assert.assertEquals("Album 2", albums[2].title)
    }
}