package com.example.lab2_exercise3

import android.util.Log
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import com.google.gson.Gson
import java.io.IOException

// Match your FastAPI response
data class SentimentResponse(val label: String, val scores: List<Float>)

object SentimentAnalyzer {
    private val client = OkHttpClient()

    // 👇 Emulator to host (use your local IP if on real device)
    private const val API_URL = "http://10.0.2.2:8000/sentiment"

    fun analyzeSentiment(text: String, callback: (String) -> Unit) {
        val jsonRequest = """
            {
                "text": "$text"
            }
        """.trimIndent()

        val request = Request.Builder()
            .url(API_URL)
            .post(jsonRequest.toRequestBody("application/json".toMediaTypeOrNull()))
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("LocalPhoBERTResponse", "Failed to call API", e)
                callback("Error: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                val bodyString = response.body?.string()

                if (!response.isSuccessful || bodyString == null) {
                    Log.e("LocalPhoBERTResponse", "Bad response: $response")
                    callback("Error: Bad response")
                    return
                }

                Log.d("LocalPhoBERTResponse", "Raw response: $bodyString")

                try {
                    val gson = Gson()
                    val sentiment = gson.fromJson(bodyString, SentimentResponse::class.java)
                    callback(sentiment.label)
                } catch (e: Exception) {
                    Log.e("LocalPhoBERTResponse", "Parsing error", e)
                    callback("Error parsing response: ${e.message}")
                }
            }
        })
    }
}
