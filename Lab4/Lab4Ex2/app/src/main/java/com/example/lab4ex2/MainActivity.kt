package com.example.lab4ex2


import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DictionaryDatabaseHelper
    private lateinit var editSearch: EditText
    private lateinit var buttonLookup: Button
    private lateinit var textResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DictionaryDatabaseHelper(this)

        editSearch = findViewById(R.id.editSearch)
        buttonLookup = findViewById(R.id.buttonLookup)
        textResult = findViewById(R.id.textResult)

        buttonLookup.setOnClickListener {
            val searchText = editSearch.text.toString().trim()
            if (searchText.isNotEmpty()) {
                val exactMeaning = dbHelper.findExactMeaning(searchText)
                if (exactMeaning != null) {
                    textResult.text = "Meaning:\n$exactMeaning"
                } else {
                    val matchingWords = dbHelper.findWordsContaining(searchText)
                    if (matchingWords.isNotEmpty()) {
                        textResult.text = "Words found:\n${matchingWords.joinToString(", ")}"
                    } else {
                        textResult.text = "No matches found."
                    }
                }
            }
        }
    }
}

