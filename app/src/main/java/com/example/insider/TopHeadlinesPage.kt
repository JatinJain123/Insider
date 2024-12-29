package com.example.insider

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun TopHeadlinesPage(model: MainViewModel) {
    val status by model.status
    val q = remember { mutableStateOf("apple") }

    LaunchedEffect(Unit) {
        model.fetchEverything(q.value)
    }

    Column(modifier = Modifier
        .fillMaxSize()
    ) {
        when {
            status.loading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = Color.Black)
                }
            }

            status.error != null -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "${status.error}",
                        modifier = Modifier.align(Alignment.Center),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Gray
                    )
                }
            }

            else -> {
                ShowNews(status.data)
            }
        }
    }
}
