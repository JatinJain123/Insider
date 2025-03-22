package com.example.insider

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@OptIn(FlowPreview::class)
@Composable
fun Search(
    model: MainViewModel,
    navigateBackToMainScreen: () -> Unit
) {
    val searchQuery = remember { mutableStateOf("") }
    val searchQueryExecuting = remember { mutableStateOf(false) }

    LaunchedEffect(searchQuery.value) {
        snapshotFlow { searchQuery.value }
            .filter { it.isNotBlank() }
            .debounce(800)
            .distinctUntilChanged()
            .collect { query ->
                model.fetchSearchResult(SearchQuery(query = query))
                searchQueryExecuting.value = true
            }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize()
                .background(MaterialTheme.colorScheme.background)
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(horizontal = 16.dp, vertical = 4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = rememberAsyncImagePainter(R.drawable.baseline_arrow_back_24),
                    contentDescription = "Back",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { navigateBackToMainScreen() }
                )

                OutlinedTextField(
                    value = searchQuery.value,
                    onValueChange = {
                        searchQuery.value = it
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 12.dp),
                    placeholder = { Text("Search Article ...") },
                    singleLine = true,
                    shape = RoundedCornerShape(15.dp),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    model.fetchSearchResult(query = SearchQuery(searchQuery.value))
                                    searchQueryExecuting.value = true
                                }
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { },
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(
                        painter = rememberAsyncImagePainter(R.drawable.baseline_location_searching_24),
                        contentDescription = "Search by Location",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Search by Location")
                }
            }
        }

        if(searchQueryExecuting.value) {
            SearchResponseUI(model)
        }
    }
}

@Composable
fun SearchResponseUI(model: MainViewModel) {
    val searchResults by model.searchResponse.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (searchResults.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 2.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(searchResults) { article ->
                    NewsCard(article)
                }
            }
        }
    }
}

