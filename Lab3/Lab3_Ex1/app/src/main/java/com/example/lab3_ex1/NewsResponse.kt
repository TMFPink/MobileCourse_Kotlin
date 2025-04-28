package com.example.lab3_ex1

data class NewsResponse(
    val articles: List<Article>,
    val totalResult: Int
)

data class Article(
    val title: String,
    val content: String?,
    val urlToImage: String?,
    val publishedAt: String
)