package com.example.lab1

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList


const val SENTMOVIECARD: String = "Movie Card"

class MyAdapter(val movieCards: ArrayList<MovieCard>, val context: Context):
    RecyclerView.Adapter<MyAdapter.ViewHolder>()  {
    val movieCardsCopy = ArrayList<MovieCard>()

    init {
        movieCardsCopy.addAll(movieCards)
    }


    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

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

        when (movieCards[position].Poster) {
            "Poster_01.jpg" -> viewHolder.imageView.setImageResource(R.drawable.poster_01)
            "Poster_02.jpg" -> viewHolder.imageView.setImageResource(R.drawable.poster_02)
            "Poster_03.jpg" -> viewHolder.imageView.setImageResource(R.drawable.poster_03)
            "Poster_05.jpg" -> viewHolder.imageView.setImageResource(R.drawable.poster_05)
            "Poster_06.jpg" -> viewHolder.imageView.setImageResource(R.drawable.poster_06)
            "Poster_07.jpg" -> viewHolder.imageView.setImageResource(R.drawable.poster_07)
            "Poster_08.jpg" -> viewHolder.imageView.setImageResource(R.drawable.poster_08)
            "Poster_10.jpg" -> viewHolder.imageView.setImageResource(R.drawable.poster_10)
            "" -> viewHolder.imageView.setImageResource(R.drawable.poster_error)
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

        viewHolder.exitButton.setOnClickListener{
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

    fun filter(text: String) {
        var textCopy = text

        movieCards.clear()
        if(textCopy.isEmpty()) {
            movieCards.addAll(movieCardsCopy)
        } else {
            textCopy = textCopy.toLowerCase(Locale.getDefault())
            for (card in movieCardsCopy) {
                if (card.Title!!.toLowerCase(Locale.getDefault()).contains(textCopy)) {
                    movieCards.add(card)
                }
            }
        }

        notifyDataSetChanged()
    }

}