package com.example.insider

data class Source (
    val id: String?,
    val name: String
)

data class Article (
    val source: Source,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?
)

data class NewsResponse (
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)

data class LoadState(
    val loading: Boolean = true,
    val error: String? = null,
    val data: NewsResponse? = null
)

data class UserProfileData(
    var loginStatus: Boolean = false,
    var name: String? = "",
    var email: String? = "",
    var imageId: String? = ""
)

data class SearchQuery(val query: String)