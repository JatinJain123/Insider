package com.example.insider

class NewsRepository(private val call: ApiService) {
    suspend fun fetchTopHeadlines(country: String): NewsResponse {
        return call.fetchCountryTopHeadlines(
            country = country,
            apiKey = BuildConfig.NEWS_API_KEY
        )
    }

    suspend fun fetchEverything(q: String, from: String, sortBy: String): NewsResponse {
        return call.fetchEverythingData(
            q = q,
            from = from,
            sortBy = sortBy,
            apiKey = BuildConfig.NEWS_API_KEY
        )
    }

    suspend fun fetchFromSources(source: String): NewsResponse {
        return call.fetchFromSources(
            source = source,
            apiKey = BuildConfig.NEWS_API_KEY
        )
    }
}

class SearchRepository(private val searchCall: BackendApiService) {
    suspend fun fetchSearchResult(query: SearchQuery): List<Article> {
        return searchCall.fetchSearchResult(query)
    }
}