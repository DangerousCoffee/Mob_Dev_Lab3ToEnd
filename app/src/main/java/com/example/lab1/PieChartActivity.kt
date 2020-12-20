package com.example.lab1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_drawing_piechart.*
import kotlinx.android.synthetic.main.activity_main.navigation

class PieChartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawing_piechart)


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

        layoutSwitch.setOnCheckedChangeListener({
                buttonView, isChecked ->
            if (isChecked) {
                startActivity(Intent(this, GraphActivity::class.java))
            } else {
                startActivity(Intent(this, PieChartActivity::class.java))
            }
        })
    }


}
