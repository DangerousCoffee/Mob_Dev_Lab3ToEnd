package com.example.lab1

import android.os.Parcel
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity

open class MovieCard(val Title: String?,
                     val Year: String?,
                     val imdbID: String?,
                     val Type: String?,
                     val Poster: String?): Parcelable
{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Title)
        parcel.writeString(Year)
        parcel.writeString(imdbID)
        parcel.writeString(Type)
        parcel.writeString(Poster)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MovieCard> {
        override fun createFromParcel(parcel: Parcel): MovieCard {
            return MovieCard(parcel)
        }

        override fun newArray(size: Int): Array<MovieCard?> {
            return arrayOfNulls(size)
        }
    }

}