package com.pixelart.jsonplaceholderapi_albums.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.pixelart.jsonplaceholderapi_albums.AppController
import com.pixelart.jsonplaceholderapi_albums.R
import com.pixelart.jsonplaceholderapi_albums.adapter.AlbumAdapter
import com.pixelart.jsonplaceholderapi_albums.common.BOTTOM_SHEET
import com.pixelart.jsonplaceholderapi_albums.di.activity.ActivityModule
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject



class MainActivity : AppCompatActivity(), AlbumAdapter.OnItemClickedListener, SortFragment.OnInteractionListener {

    @Inject lateinit var viewModel: MainViewModel

    private lateinit var adapter: AlbumAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = AlbumAdapter(this)

        val activityComponent = (application as AppController)
            .applicationComponent
            .newActivityComponent(ActivityModule(this))
        activityComponent.inject(this)

        viewModel.getAlbumsNetwork().observe(this, Observer {
            //Toast.makeText(this@MainActivity, albumList[0].title, Toast.LENGTH_SHORT).show()
            for (album in it){
                viewModel.insertAlbum(album.userId, album.id, album.title)
            }
        })

        viewModel.getAlbumsASC().observe(this, Observer {
            //Toast.makeText(this@MainActivity, it[0].title, Toast.LENGTH_SHORT).show()
            adapter.submitList(it)
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
                //Toast.makeText(this@MainActivity, it[0].title, Toast.LENGTH_SHORT).show()
                adapter.submitList(it)
            })
        }else{
            viewModel.getAlbumsDESC().observe(this, Observer {
                //Toast.makeText(this@MainActivity, it[0].title, Toast.LENGTH_SHORT).show()
                adapter.submitList(it)
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_sort -> {
                val fragment = SortFragment()
                fragment.show(supportFragmentManager, BOTTOM_SHEET)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
