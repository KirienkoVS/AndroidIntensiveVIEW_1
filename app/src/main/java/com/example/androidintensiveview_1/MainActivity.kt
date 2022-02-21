package com.example.androidintensiveview_1

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import java.util.concurrent.Executors

private const val IMAGE_URL = "https://picsum.photos/200/300"

class MainActivity : AppCompatActivity() {

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
                            handler.post {
                                loadImage(editTextView, imageView)
                            }
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

    private fun loadImage(editTextView: EditText, imageView: ImageView) {
        Glide.with(this)
            .load(editTextView.text.toString())
            .override(600, 800)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(imageView)
    }
}