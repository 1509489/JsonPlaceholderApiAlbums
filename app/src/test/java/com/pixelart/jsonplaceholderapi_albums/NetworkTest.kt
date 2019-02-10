package com.pixelart.jsonplaceholderapi_albums

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.pixelart.jsonplaceholderapi_albums.data.database.AlbumDatabase
import com.pixelart.jsonplaceholderapi_albums.data.model.AlbumResponse
import com.pixelart.jsonplaceholderapi_albums.data.network.NetworkService
import com.pixelart.jsonplaceholderapi_albums.data.repository.RepositoryImpl
import com.pixelart.jsonplaceholderapi_albums.ui.MainViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.Executor

@RunWith(MockitoJUnitRunner::class)
class NetworkTest {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var albumResponse: List<AlbumResponse>

    @Mock private lateinit var networkService: NetworkService
    @Mock private lateinit var albumDatabase: AlbumDatabase
    @Mock private lateinit var albumResponseObserver: Observer<List<AlbumResponse>>
    @Mock private lateinit var stateObserver: Observer<RepositoryImpl.State>

    @Captor private lateinit var albumResponseCaptor: ArgumentCaptor<List<AlbumResponse>>
    @Captor private lateinit var stateCaptor: ArgumentCaptor<RepositoryImpl.State>

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    companion object {
        @BeforeClass
        @JvmStatic
        fun setupSchedulers(){
            val scheduler = object : Scheduler(){
                override fun createWorker(): Worker {
                    return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
                }
            }
            RxJavaPlugins.setInitIoSchedulerHandler { scheduler }
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler }
        }
    }

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)

        val repositoryImpl = RepositoryImpl(networkService, albumDatabase)
        mainViewModel = MainViewModel(repositoryImpl)
        albumResponse = ArrayList()

        for (i in 0 until 3){
            (albumResponse as ArrayList).add(AlbumResponse(1, i, "Album $i"))
        }
    }

    @Test
    fun testNetworkSuccess(){
        Mockito.`when`(networkService.getAlbums()).thenReturn(Single.just(albumResponse))

        mainViewModel.getAlbumsNetwork().observeForever(albumResponseObserver)

        Mockito.verify(albumResponseObserver).onChanged(albumResponseCaptor.capture())
        Assert.assertTrue(albumResponseCaptor.value.size == 3)
        val album = albumResponseCaptor.value[0]
        Assert.assertEquals("Album 0", album.title)
    }

    @Test
    fun testNetworkFailure(){
        Mockito.`when`(networkService.getAlbums()).thenReturn(Single.error(Throwable()))
        mainViewModel.getAlbumsNetwork().observeForever(albumResponseObserver)
        mainViewModel.getState().observeForever(stateObserver)

        Mockito.verify(stateObserver).onChanged(stateCaptor.capture())
        Assert.assertEquals(RepositoryImpl.State.FAILURE, stateCaptor.value)
    }
}