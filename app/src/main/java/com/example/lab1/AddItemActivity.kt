package com.example.lab1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_add_item.*

const val TITLE = "Title"
const val YEAR = "Year"
const val TYPE = "Type"

class AddItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        addItemButton.setOnClickListener{
            addMovieCard()
        }
    }

    fun addMovieCard() {
        val resultIntent: Intent = Intent()

        if (TitleInput.text.isNullOrEmpty()) {
            resultIntent.putExtra(TITLE, "")
        } else {
            resultIntent.putExtra(TITLE, TitleInput.text.toString())
        }
        if (YearInput.text.isNullOrEmpty()) {
            resultIntent.putExtra(YEAR, "")
        } else {
            resultIntent.putExtra(YEAR, YearInput.text.toString())
        }
        if (TypeInput.text.isNullOrEmpty()) {
            resultIntent.putExtra(TYPE, "")
        } else {
            resultIntent.putExtra(TYPE, TypeInput.text.toString())
        }

        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }
}
