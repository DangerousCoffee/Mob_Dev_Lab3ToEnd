package com.example.lab1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_movie_card_details.*
import kotlinx.android.synthetic.main.card.*
import kotlinx.android.synthetic.main.card.titleText
import kotlinx.android.synthetic.main.card.yearText

class MovieCardDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_card_details)

        val movieCard: MovieCard? = intent.getParcelableExtra(SENTMOVIECARD)

        val filename: String = movieCard?.imdbID!! + ".txt"

        try {

            val jsonText: String = applicationContext.assets.open(filename).bufferedReader().use{
                it.readText()
            }

            val detailedMovieCard: DetailedMovieCard =
                Gson().fromJson<DetailedMovieCard>(jsonText, DetailedMovieCard::class.java)

            when (detailedMovieCard.Poster) {
                "Poster_01.jpg" -> poster.setImageResource(R.drawable.poster_01)
                "Poster_02.jpg" -> poster.setImageResource(R.drawable.poster_02)
                "Poster_03.jpg" -> poster.setImageResource(R.drawable.poster_03)
                "Poster_05.jpg" -> poster.setImageResource(R.drawable.poster_05)
                "Poster_06.jpg" -> poster.setImageResource(R.drawable.poster_06)
                "Poster_07.jpg" -> poster.setImageResource(R.drawable.poster_07)
                "Poster_08.jpg" -> poster.setImageResource(R.drawable.poster_08)
                "Poster_10.jpg" -> poster.setImageResource(R.drawable.poster_10)
                "" -> poster.setImageResource(R.drawable.poster_error)
            }

            titleText.append(detailedMovieCard.Title)
            yearText.append(detailedMovieCard.Year)
            genreText.append(detailedMovieCard.Genre)
            directorText.append(detailedMovieCard.Director)
            actorsText.append(detailedMovieCard.Actors)
            countryText.append(detailedMovieCard.Country)
            languageText.append(detailedMovieCard.Language)
            productionText.append(detailedMovieCard.Production)
            releasedText.append(detailedMovieCard.Released)
            runtimeText.append(detailedMovieCard.Runtime)
            awardsText.append(detailedMovieCard.Awards)
            ratingText.append(detailedMovieCard.imdbRating)
            descriptionText.append(detailedMovieCard.Plot)

        } catch (e: java.io.FileNotFoundException) {

            when (movieCard.Poster) {
                "Poster_01.jpg" -> poster.setImageResource(R.drawable.poster_01)
                "Poster_02.jpg" -> poster.setImageResource(R.drawable.poster_02)
                "Poster_03.jpg" -> poster.setImageResource(R.drawable.poster_03)
                "Poster_05.jpg" -> poster.setImageResource(R.drawable.poster_05)
                "Poster_06.jpg" -> poster.setImageResource(R.drawable.poster_06)
                "Poster_07.jpg" -> poster.setImageResource(R.drawable.poster_07)
                "Poster_08.jpg" -> poster.setImageResource(R.drawable.poster_08)
                "Poster_10.jpg" -> poster.setImageResource(R.drawable.poster_10)
                "" -> poster.setImageResource(R.drawable.poster_error)
            }

            titleText.append(movieCard.Title)
            yearText.append(movieCard.Year)

        } finally {

            actionBar?.setDisplayHomeAsUpEnabled(true)

        }

    }
}
