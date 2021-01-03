package com.example.lab1

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Layout
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import kotlinx.android.synthetic.main.activity_images.*
import kotlinx.android.synthetic.main.activity_main.navigation
import kotlinx.android.synthetic.main.image_layout.view.*
import kotlinx.coroutines.*
import org.json.JSONObject
import java.lang.Exception
import java.net.HttpURLConnection

class ImagesActivity : AppCompatActivity() {
    val ADD_IMAGE_CODE: Int = 1

    val imagesArray: ArrayList<Drawable> = ArrayList<Drawable>()
    var imageViewArray: ArrayList<ImageView> = ArrayList<ImageView>()

    var imagePointer = 0
    var imageViewPointer = 0

    lateinit var myLayoutInflater: LayoutInflater

    val context = this

    val TAG = "IMAGES"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_images)


        if (savedInstanceState == null) {
            myLayoutInflater = LayoutInflater.from(applicationContext)
        }

        for (i in 0 until 3) {
            val layout: View = myLayoutInflater.inflate(R.layout.image_layout, linearLayout, false)
            linearLayout.addView(layout)
            this.imageViewArray.addAll(
                arrayListOf(
                    layout.image1,
                    layout.image2,
                    layout.image3,
                    layout.image4,
                    layout.image5,
                    layout.image6
                )
            )
        }
        this.calculateSizes()

        val url = "https://pixabay.com/api/?key=19193969-87191e5db266905fe8936d565&q=small+animals&image_type=photo&per_page=18"
        val imageURLArray = ArrayList<String>()
        GlobalScope.async(Dispatchers.IO) {
            val connection = java.net.URL(url).openConnection() as HttpURLConnection
            try {
                val data = connection.inputStream.bufferedReader().use { it.readText() }
                val json = JSONObject(data)
                val imagesArray = json.getJSONArray("hits")
                for (index in 0 until imagesArray.length()) {
                    val imageData = JSONObject(imagesArray.get(index).toString())
                    imageURLArray.add(imageData.getString("webformatURL"))
                }
                CoroutineScope(Dispatchers.Main).async {
                    for (index in 0 until imageURLArray.size) {
                        getBitmap(imageURLArray.get(index), imageViewArray.get(index))
                    }
                }
            } finally {
                connection.disconnect()
            }
        }


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
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.images_menu, menu)

        val add_image: MenuItem = menu.findItem(R.id.add_image)
        add_image.setOnMenuItemClickListener {
            val intent: Intent = Intent(Intent.ACTION_PICK)
            intent.setType("image/*")
            startActivityForResult(intent, ADD_IMAGE_CODE)

            return@setOnMenuItemClickListener true
        }

        return true
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_IMAGE_CODE && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val uri = data.data

            try {
                uri?.let {
                    if (Build.VERSION.SDK_INT < 28) {
                        val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
                        val drawable: Drawable = BitmapDrawable(resources, bitmap)
                        this.imagesArray.add(drawable)
                        addImage()
                    } else {
                        val source = ImageDecoder.createSource(this.contentResolver, uri)
                        val drawable: Drawable = ImageDecoder.decodeDrawable(source)
                        this.imagesArray.add(drawable)
                        addImage()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addImage() {
        this.imageViewArray[imageViewPointer].setImageDrawable(this.imagesArray[imagePointer])

        this.imagePointer++
        this.imageViewPointer++

        if (this.imageViewPointer % 6 == 0) {
            val layout = this.myLayoutInflater.inflate(R.layout.image_layout, null)
            linearLayout.addView(layout)
            this.imageViewArray.addAll(arrayListOf(layout.image1, layout.image2, layout.image3, layout.image4, layout.image5, layout.image6))

            this.calculateSizes()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        this.calculateSizes()
    }

    fun calculateSizes() {
        val image0width = resources.displayMetrics.widthPixels * 2/3
        val image0height = image0width * 3/4
        var layoutParams: ViewGroup.LayoutParams
        var localPointer = 0
        for (image in imageViewArray) {
            if (localPointer % 6 == 0) {
                localPointer = 0
            }
            layoutParams = image.layoutParams
            when (localPointer) {
                0 -> {
                    layoutParams.width = image0width
                    layoutParams.height = image0height
                }
                1, 2 -> {
                    layoutParams.width = resources.displayMetrics.widthPixels - image0width
                    layoutParams.height = image0height/2 + image0height * 1/6
                }
                3, 4, 5 -> {
                    layoutParams.width = resources.displayMetrics.widthPixels * 2/3/3
                    layoutParams.height = image0height * 1/3
                }
            }
            image.layoutParams = layoutParams
            localPointer++
        }
    }

    suspend fun getBitmap(url: String, imageView: ImageView) {
        GlobalScope.async(Dispatchers.IO) {
            val connection = java.net.URL(url).openConnection() as HttpURLConnection
            try {
                val data = connection.inputStream
                val bitmap = BitmapFactory.decodeStream(data)
                CoroutineScope(Dispatchers.Main).launch {
                    progressBar.visibility = View.VISIBLE
                    imageView.setImageBitmap(bitmap)
                    progressBar.visibility = View.INVISIBLE
                }
            } finally {
                connection.disconnect()
            }
        }
    }
}
