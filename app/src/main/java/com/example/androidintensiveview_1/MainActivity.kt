package com.example.androidintensiveview_1

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.net.URL
import java.util.concurrent.Executors

private const val IMAGE_URL = "https://picsum.photos/200/300"

class MainActivity : AppCompatActivity() {

    private lateinit var image: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageView = findViewById<ImageView>(R.id.random_image)
        val editTextView = findViewById<EditText>(R.id.edit_text_view)
        editTextView.setText(IMAGE_URL)

        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())

        editTextView.setOnKeyListener { _, actionID, _ ->
            when(actionID) {
                KeyEvent.KEYCODE_ENTER -> {
                    executor.execute {
                        try {
                            loadImage(editTextView, handler, imageView)
                        } catch (e: Exception) {
                            handler.post {
                                Toast.makeText(this, "${e.message}", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                    true
                }
                else -> false
            }
        }
    }

    private fun loadImage(editTextView: EditText, handler: Handler, imageView: ImageView) {
        val stream = URL(editTextView.text.toString()).openStream()
        image = BitmapFactory.decodeStream(stream)
        handler.post {
            imageView.setImageBitmap(image)
        }
    }
}