package com.example.lab1

class ContainerClass(val Search: ArrayList<MovieCard>) {

    fun getCard(index: Int): MovieCard {
        return this.Search[index]
    }

}