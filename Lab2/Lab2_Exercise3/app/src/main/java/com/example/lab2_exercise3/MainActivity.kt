package com.example.lab2_exercise3

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.lab2_exercise3.SentimentAnalyzer.analyzeSentiment
import java.util.Locale

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputText = findViewById<EditText>(R.id.inputText)
        val analyzeButton = findViewById<Button>(R.id.analyzeButton)
        val rootLayout = findViewById<LinearLayout>(R.id.rootLayout)
        val emojiView = findViewById<ImageView>(R.id.emojiView)

        analyzeButton.setOnClickListener { v: View? ->
            val text = inputText.text.toString()
            if (!text.isEmpty()) {
                analyzeSentiment(text) { sentiment: String ->
                    runOnUiThread {
                        when (sentiment.trim().lowercase(Locale.getDefault())) {
                            "positive" -> {
                                rootLayout.setBackgroundColor(Color.GREEN)
                                emojiView.setImageResource(R.drawable.happy_emoji)
                            }
                            "neutral" -> {
                                rootLayout.setBackgroundColor(Color.YELLOW)
                                emojiView.setImageResource(R.drawable.neutral_emoji)
                            }
                            "negative" -> {
                                rootLayout.setBackgroundColor(Color.RED)
                                emojiView.setImageResource(R.drawable.sad_emoji)
                            }
                            else -> {
                                rootLayout.setBackgroundColor(Color.WHITE)
                                emojiView.setImageDrawable(null)
                            }
                        }
                    }
                }

            } else {
                rootLayout.setBackgroundColor(Color.WHITE)
                emojiView.setImageDrawable(null) // Reset if empty
            }
        }
    }
}