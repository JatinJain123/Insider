package com.example.insider

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Calendar

class MainViewModelFactory(
    private val repository: NewsRepository,
    private val searchRepository: SearchRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository, searchRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


class MainViewModel(
    private val repository: NewsRepository,
    private val searchRepository: SearchRepository
): ViewModel() {
    private val _status = mutableStateOf(LoadState())
    val status: MutableState<LoadState> = _status

    private val _searchResponse = MutableStateFlow<List<Article>>(emptyList())
    val searchResponse: StateFlow<List<Article>> = _searchResponse

    fun fetchTopHeadlines(country: String) {
        _status.value = LoadState(loading = true)
        viewModelScope.launch {
            try {
                val data = repository.fetchTopHeadlines(country)
                _status.value = LoadState(loading = false, data = data)
            } catch (e: Exception) {
                _status.value = LoadState(loading = false, error = "error while loading: ${e.message}")
            }
        }
    }

    fun fetchEverything(q: String, from: String = Calendar.DATE.toString(), sortBy: String = "popularity") {
        _status.value = LoadState(loading = true)
        viewModelScope.launch {
            try {
                val data = repository.fetchEverything(q, from, sortBy)
                _status.value = LoadState(loading = false, data = data)
            } catch (e: Exception) {
                _status.value = LoadState(loading = false, error = "error while loading: ${e.message}")
            }
        }
    }

    fun fetchFromSources(source: String) {
        _status.value = LoadState(loading = true)
        viewModelScope.launch {
            try {
                val data = repository.fetchFromSources(source)
                _status.value = LoadState(loading = false, data = data)
            } catch (e: Exception) {
                _status.value = LoadState(loading = false, error = "error while loading: ${e.message}")
            }
        }
    }

    fun fetchSearchResult(query: SearchQuery){
        viewModelScope.launch {
            try {
                val data: List<Article> = searchRepository.fetchSearchResult(query)
                _searchResponse.value = data
            } catch (e: Exception) {
                _searchResponse.value = emptyList()
            }
        }
    }
}
