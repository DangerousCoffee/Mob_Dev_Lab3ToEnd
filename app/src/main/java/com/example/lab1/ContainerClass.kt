package com.example.lab1

class ContainerClass(val Search: ArrayList<MovieCard>) {
    val movieCardList = Search

    fun getCard(index: Int): MovieCard {
        return this.Search[index]
    }

}