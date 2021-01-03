package com.example.lab1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var linearLayoutManager: LinearLayoutManager

    private val ADD_ITEM_CODE = 1
    private lateinit var myAdapter: MyAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar.visibility = View.INVISIBLE

        val navigationListener = navigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.action_home -> {
                    startActivity(Intent(this, MainActivity::class.java))

                    return@setOnNavigationItemSelectedListener true
                }

                R.id.action_images -> {
                    startActivity(Intent(this, ImagesActivity::class.java))

                    return@setOnNavigationItemSelectedListener true
                }

                R.id.action_drawing -> {
                    startActivity(Intent(this, PieChartActivity::class.java))

                    return@setOnNavigationItemSelectedListener true
                }

                else -> {
                    return@setOnNavigationItemSelectedListener false
                }
            }
        }


        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager

        recyclerView.addItemDecoration(MyDecoration(40))

        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )


        myAdapter = MyAdapter(ArrayList<MovieCard>(), applicationContext, progressBar)
        recyclerView.adapter = myAdapter

        floatingActionButton.setOnClickListener {
            fabOnClick()
        }

    }

    fun fabOnClick() {
        val intent: Intent = Intent(this, AddItemActivity::class.java)
        startActivityForResult(intent, ADD_ITEM_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_ITEM_CODE && resultCode == Activity.RESULT_OK) {
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
        progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.Main).launch {
            myAdapter.filter(query)
        }
        return true
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.Main).launch {
            myAdapter.filter(query)
        }
        return true
    }

}
