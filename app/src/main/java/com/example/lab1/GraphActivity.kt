package com.example.lab1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.activity_drawing_graph.*
import kotlinx.android.synthetic.main.activity_main.navigation
import kotlin.math.pow

class GraphActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawing_graph)

        val graph: GraphView = findViewById(R.id.graph)
        if (graph == null) {
            Log.d("GRAPH", "no graph")
        }

        var series = LineGraphSeries<DataPoint>()

        var x = -3.0
        var y = 0.0

        while (x <= 3.0) {
            y = x.pow(3)
            series.appendData(DataPoint(x, y), true, 5000)
            x+=0.1
        }

        graph.addSeries(series)


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
