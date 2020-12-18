package com.example.lab1

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_images.*
import kotlinx.android.synthetic.main.activity_main.navigation
import kotlinx.android.synthetic.main.image_layout.view.*
import java.lang.Exception

class ImagesActivity : AppCompatActivity() {
    val ADD_IMAGE_CODE: Int = 1

    val imagesArray: ArrayList<Drawable> = ArrayList<Drawable>()
    var imageViewArray: ArrayList<ImageView> = ArrayList<ImageView>()

    var imagePointer = 0
    var imageViewPointer = 0

    lateinit var myLayoutInflater: LayoutInflater


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_images)






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

                R.id.action_placeholder -> {
                    return@setOnNavigationItemSelectedListener false
                }

                else -> {
                    return@setOnNavigationItemSelectedListener false
                }
            }
        }
        if (savedInstanceState == null) {
            myLayoutInflater = LayoutInflater.from(applicationContext)
            val layout: View = myLayoutInflater.inflate(R.layout.image_layout, linearLayout, true)
            this.imageViewArray = arrayListOf(
                layout.image1,
                layout.image2,
                layout.image3,
                layout.image4,
                layout.image5,
                layout.image6
            )
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
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
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

}
