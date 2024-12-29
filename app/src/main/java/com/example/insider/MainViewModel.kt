package com.example.insider

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.util.Calendar

data class LoadState(
    val loading: Boolean = true,
    val error: String? = null,
    val data: NewsResponse? = null
)

class MainViewModelFactory(
    private val repository: NewsRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


class MainViewModel(
    private val repository: NewsRepository,
): ViewModel() {
    private val _status = mutableStateOf(LoadState())
    val status: MutableState<LoadState> = _status

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
}
