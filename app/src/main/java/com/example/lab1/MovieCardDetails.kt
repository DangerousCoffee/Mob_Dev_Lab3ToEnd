package com.example.lab1

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_movie_card_details.*
import kotlinx.android.synthetic.main.card.titleText
import kotlinx.android.synthetic.main.card.yearText
import kotlinx.coroutines.*
import java.net.HttpURLConnection

class MovieCardDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_card_details)

        val movieCard: MovieCard? = intent.getParcelableExtra(SENTMOVIECARD)

        progressBar.visibility = View.VISIBLE

        if (movieCard!!.imdbID != null) {
            if (movieCard!!.imdbID != "") {
                val url: String = "http://www.omdbapi.com/?apikey=7c12e60b&i=" + movieCard!!.imdbID
                CoroutineScope(Dispatchers.Default).launch {
                    val result = GlobalScope.async(Dispatchers.IO) {
                        val connection = java.net.URL(url).openConnection() as HttpURLConnection
                        try {
                            val data = connection.inputStream.bufferedReader().use { it.readText() }
                            val container = Gson().fromJson<DetailedMovieCard>(
                                data,
                                DetailedMovieCard::class.java
                            )
                            if (container.Title != null) {
                                CoroutineScope(Dispatchers.Main).launch {
                                    titleText.append(container.Title)
                                }
                            }
                            if (container.Year != null) {
                                CoroutineScope(Dispatchers.Main).launch {
                                    yearText.append(container.Year)
                                }
                            }
                            if (container.Released != null) {
                                CoroutineScope(Dispatchers.Main).launch {
                                    releasedText.append(container.Released)
                                }
                            }
                            if (container.Runtime != null) {
                                CoroutineScope(Dispatchers.Main).launch {
                                    runtimeText.append(container.Runtime)
                                }
                            }
                            if (container.Genre != null) {
                                CoroutineScope(Dispatchers.Main).launch {
                                    genreText.append(container.Genre)
                                }
                            }
                            if (container.Director != null) {
                                CoroutineScope(Dispatchers.Main).launch {
                                    directorText.append(container.Director)
                                }
                            }
                            if (container.Actors != null) {
                                CoroutineScope(Dispatchers.Main).launch {
                                    actorsText.append(container.Actors)
                                }
                            }
                            if (container.Plot != null) {
                                CoroutineScope(Dispatchers.Main).launch {
                                    descriptionText.append(container.Plot)
                                }
                            }
                            if (container.Language != null) {
                                CoroutineScope(Dispatchers.Main).launch {
                                    languageText.append(container.Language)
                                }
                            }
                            if (container.Country != null) {
                                CoroutineScope(Dispatchers.Main).launch {
                                    countryText.append(container.Country)
                                }
                            }
                            if (container.Awards != null) {
                                CoroutineScope(Dispatchers.Main).launch {
                                    awardsText.append(container.Awards)
                                }
                            }
                            if (container.Poster != null) {
                                if (movieCard.Poster == "") {
                                    poster.setImageResource(R.drawable.poster_error)
                                } else {
                                    GlobalScope.launch {
                                        getBitmap(movieCard.Poster!!)
                                    }
                                }
                            }
                            if (container.imdbRating != null) {
                                CoroutineScope(Dispatchers.Main).launch {
                                    ratingText.append(container.imdbRating)
                                }
                            }
                            if (container.Plot != null) {
                                CoroutineScope(Dispatchers.Main).launch {
                                    descriptionText.append(container.Plot)
                                }
                            }
                        } finally {
                            connection.disconnect()
                        }
                    }
                    result.await()
                    progressBar.visibility = View.INVISIBLE
                }
            } else {
                if (movieCard.Title != null) {
                    titleText.append(movieCard.Title)
                }
                if (movieCard.Year != null) {
                    yearText.append(movieCard.Year)
                }
                if (movieCard.Poster != null) {
                    poster.setImageResource(R.drawable.poster_error)
                }
            }
        }
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }


    suspend fun getBitmap(url: String) {
        GlobalScope.async(Dispatchers.IO) {
            val connection = java.net.URL(url).openConnection() as HttpURLConnection
            try {
                val data = connection.inputStream
                val bitmap = BitmapFactory.decodeStream(data)
                CoroutineScope(Dispatchers.Main).launch {
                    poster.setImageBitmap(bitmap)
                }
            } finally {
                connection.disconnect()
            }
        }
    }
}
