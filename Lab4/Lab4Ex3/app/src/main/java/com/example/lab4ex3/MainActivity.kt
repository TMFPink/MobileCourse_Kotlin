package com.example.lab4ex3

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView;
import com.example.lab4ex3.MovieAdapter
import com.example.lab4ex3.MovieResponse
import com.example.lab4ex3.R
import com.example.lab4ex3.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var editSearch: EditText
    private lateinit var buttonSearch: Button
    private lateinit var recyclerView: RecyclerView
    private val apiKey = "c6b545b3001bc76c9c57613ef44a8d1a"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editSearch = findViewById(R.id.editSearch)
        buttonSearch = findViewById(R.id.buttonSearch)
        recyclerView = findViewById(R.id.recyclerViewMovies)

        recyclerView.layoutManager = LinearLayoutManager(this)

        buttonSearch.setOnClickListener {
            val query = editSearch.text.toString().trim()
            if (query.isNotEmpty()) {
                searchMovies(query)
            } else {
                Toast.makeText(this, "Please enter a movie name", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun searchMovies(query: String) {
        RetrofitClient.api.searchMovies(apiKey, query).enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val movies = response.body()!!.results
                    recyclerView.adapter = MovieAdapter(movies)
                } else {
                    Toast.makeText(this@MainActivity, "No movies found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
