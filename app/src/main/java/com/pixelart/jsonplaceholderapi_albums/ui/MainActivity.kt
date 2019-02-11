package com.pixelart.jsonplaceholderapi_albums.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.test.espresso.idling.CountingIdlingResource
import com.pixelart.jsonplaceholderapi_albums.AppController
import com.pixelart.jsonplaceholderapi_albums.R
import com.pixelart.jsonplaceholderapi_albums.adapter.AlbumAdapter
import com.pixelart.jsonplaceholderapi_albums.data.repository.RepositoryImpl
import com.pixelart.jsonplaceholderapi_albums.di.activity.ActivityModule
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject



class MainActivity : AppCompatActivity(), AlbumAdapter.OnItemClickedListener, SortFragment.OnInteractionListener,
    MessageFragment.OnInteractionListener{
    private val TAG = "MainActivity"

    @Inject lateinit var viewModel: MainViewModel

    private lateinit var adapter: AlbumAdapter
    private var message = ""

    private val countingIdlingResource = CountingIdlingResource("Network_Call")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = AlbumAdapter(this)

        val activityComponent = (application as AppController)
            .applicationComponent
            .newActivityComponent(ActivityModule(this))
        activityComponent.inject(this)

        countingIdlingResource.increment()
        viewModel.getAlbumsNetwork().observe(this, Observer {
            for (album in it){
                viewModel.insertAlbum(album.userId, album.id, album.title)
            }
        })
        countingIdlingResource.decrement()

        viewModel.getAlbumsASC().observe(this, Observer {
            adapter.submitList(it)
        })

        viewModel.getState().observe(this, Observer {
            when(it!!){
                RepositoryImpl.State.LOADING ->{
                    Log.d(TAG, "Fetching Data")
                }
                RepositoryImpl.State.SUCCESS ->{
                    Log.d(TAG, "Data Fetch Success")
                }
                RepositoryImpl.State.FAILURE ->{
                    message = resources.getString(R.string.error_message)

                    val fragment = MessageFragment()
                    fragment.show(supportFragmentManager, "Message_Fragment")
                }
            }
        })

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        recyclerView.adapter = adapter
    }

    override fun onItemClicked(position: Int) {
        Toast.makeText(this, "Item clicked at Position: $position", Toast.LENGTH_SHORT).show()
    }

    override fun onSort(sortAtoZ: Boolean) {
        if (sortAtoZ){
            viewModel.getAlbumsASC().observe(this, Observer {
                adapter.submitList(it)
            })
        }else{
            viewModel.getAlbumsDESC().observe(this, Observer {
                adapter.submitList(it)
            })
        }
    }

    override fun setMessage(tvMessage: TextView) {
        tvMessage.text = message
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_sort -> {
                val fragment = SortFragment()
                fragment.show(supportFragmentManager, "Sort_Fragment")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun getIdlingResource(): CountingIdlingResource = countingIdlingResource
}
