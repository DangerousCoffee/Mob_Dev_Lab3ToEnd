package com.example.lab1

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.coroutines.*
import java.net.HttpURLConnection
import kotlin.collections.ArrayList


const val SENTMOVIECARD: String = "Movie Card"

class MyAdapter(val movieCards: ArrayList<MovieCard>, val context: Context, val progressBar: ProgressBar):
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    val movieCardsCopy = ArrayList<MovieCard>()

    init {
        movieCardsCopy.addAll(movieCards)
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val layout: ConstraintLayout
        val imageView: ImageView
        val titleText: TextView
        val yearText: TextView
        val typeText: TextView
        val exitButton: Button

        init {
            layout = view.findViewById(R.id.constraintLayout)
            imageView = layout.findViewById(R.id.posterImage)
            titleText = layout.findViewById(R.id.titleText)
            yearText = layout.findViewById(R.id.yearText)
            typeText = layout.findViewById(R.id.typeText)
            exitButton = layout.findViewById(R.id.exitButton)
        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.card, viewGroup, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = movieCards.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        if (movieCards[position].Poster != null) {
            if (movieCards[position].Poster == "") {
                viewHolder.imageView.setImageResource(R.drawable.poster_error)
            } else {
                progressBar.visibility = View.VISIBLE
                GlobalScope.launch {
                    getBitmap(movieCards[position].Poster!!, viewHolder.imageView)
                }
                progressBar.visibility = View.INVISIBLE
            }
        }

        var placeholderString: String = "Title: " + movieCards[position].Title

        viewHolder.titleText.text = placeholderString

        placeholderString = "Year: " + movieCards[position].Year
        viewHolder.yearText.text = placeholderString

        placeholderString = "Type: " + movieCards[position].Type
        viewHolder.typeText.text = placeholderString

        viewHolder.layout.setOnClickListener {
            showDetails(movieCards[position])
        }

        viewHolder.exitButton.setOnClickListener {
            removeItem(position)
        }

    }

    fun addItem(movieCard: MovieCard) {
        this.movieCards.add(movieCard)
        movieCardsCopy.add(movieCard)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        movieCards.removeAt(position)
        movieCardsCopy.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, movieCards.size)
    }

    fun showDetails(movieCard: MovieCard) {
        val intent: Intent = Intent(context, MovieCardDetails::class.java)
        intent.putExtra(SENTMOVIECARD, movieCard)
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    suspend fun filter(text: String) {
        movieCards.clear()

        if (text.length > 3) {
            progressBar.visibility = View.VISIBLE
            text.replace(" ", "+")
            val url = "http://www.omdbapi.com/?apikey=7c12e60b&s=" + text + "&page=1"
            val result = GlobalScope.async(Dispatchers.IO) {
                val connection = java.net.URL(url).openConnection() as HttpURLConnection
                try {
                    val data = connection.inputStream.bufferedReader().use { it.readText() }
                    val container = Gson().fromJson<ContainerClass>(data, ContainerClass::class.java)
                    if (container.Search != null) {
                        if (container.Search.isNotEmpty()) {
                            this@MyAdapter.movieCards.addAll(container.Search)
                        }
                    }
                } finally {
                    connection.disconnect()
                }
            }
            result.await()
            progressBar.visibility = View.INVISIBLE
            notifyDataSetChanged()
        } else {
            notifyDataSetChanged()
        }
    }

    suspend fun getBitmap(url: String, imageView: ImageView) {
        GlobalScope.async(Dispatchers.IO) {
            val connection = java.net.URL(url).openConnection() as HttpURLConnection
            try {
                val data = connection.inputStream
                val bitmap = BitmapFactory.decodeStream(data)
                CoroutineScope(Dispatchers.Main).launch {
                    imageView.setImageBitmap(bitmap)
                }
            } finally {
                connection.disconnect()
            }
        }
    }
}

