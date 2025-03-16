package com.example.lab_1exercise2

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import com.google.gson.Gson
import java.io.IOException
// Define data class based on Google AI Studio Sentiment Analysis response format
data class GoogleSentimentResponse(val candidates: List<GoogleSentimentPrediction>)
data class GoogleSentimentPrediction(val content: Content)
data class Content(val parts: List<Part>)
data class Part(val text: String)


object SentimentAnalyzer {
    private val client = OkHttpClient()
    private const val API_KEY = "AIzaSyCnyV-5BV6neMwLQH97XlIqWImJPEvG2h4"  // Replace with your actual API Key
    private const val API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=${API_KEY}"
    fun analyzeSentiment(text: String, callback: (String) -> Unit) {
        val jsonRequest = """
            {
                "contents": [
                    {
                        "parts": [
                            {"text": "Analyze the sentiment of this text: '$text'. Reply only with Positive, Neutral, or Negative."}
                        ]
                    }
                ]
            }
        """.trimIndent()


        val request = Request.Builder()
            .url(API_URL)
            .post(jsonRequest.toRequestBody("application/json".toMediaTypeOrNull()))
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback("Error: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let { responseData ->

                    try {
                        val sentimentResponse: GoogleSentimentResponse = Gson().fromJson(responseData, GoogleSentimentResponse::class.java)

                        if (sentimentResponse.candidates.isNullOrEmpty()) {
                            callback("Error: No sentiment detected")
                            return
                        }

                        // Extract sentiment text correctly from parts
                        val sentiment = sentimentResponse.candidates
                            .firstOrNull()?.content?.parts?.firstOrNull()?.text?.trim() ?: "Error: No sentiment detected"

                        callback(" $sentiment")

                    } catch (e: Exception) {
                        callback("Error parsing response: ${e.message}")
                    }
                } ?: callback("Error: Empty response")
            }

        })
    }
}
