package com.example.lab1

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var linearLayoutManager: LinearLayoutManager

    private val AddItemCode = 1
    private lateinit var myAdapter: MyAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager

        recyclerView.addItemDecoration(MyDecoration(40))

        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )


        val jsonText: String = applicationContext.assets.open("MoviesList.txt").bufferedReader().use{
            it.readText()
        }
        
        val movieCardArray: ContainerClass = Gson().fromJson<ContainerClass>(jsonText, ContainerClass::class.java)

        myAdapter = MyAdapter(movieCardArray.Search, applicationContext)
        recyclerView.adapter = myAdapter



        floatingActionButton.setOnClickListener {
            fabOnClick()
        }

    }

    fun fabOnClick() {
        val intent: Intent = Intent(this, AddItemActivity::class.java)
        startActivityForResult(intent, AddItemCode)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == AddItemCode && resultCode == Activity.RESULT_OK) {
            val movieTitle = data?.getStringExtra(TITLE)
            val movieYear = data?.getStringExtra(YEAR)
            val movieType = data?.getStringExtra(TYPE)

            val movieCard: MovieCard = MovieCard(movieTitle, movieYear, "", movieType, "")

            myAdapter.addItem(movieCard)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        val menuItem: MenuItem = menu.findItem(R.id.search)

        val searchView: SearchView = menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)


        return true
    }

    override fun onQueryTextChange(query: String): Boolean {
        myAdapter.filter(query)
        return true
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        myAdapter.filter(query)
        return true
    }

}
