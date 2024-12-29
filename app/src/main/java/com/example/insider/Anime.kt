package com.example.insider

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onAnimationEnd: () -> Unit) {
    var startAnimation by remember { mutableStateOf(false) }
    val transition = updateTransition(targetState = startAnimation, label = "")

    LaunchedEffect(Unit) {
        delay(500)
        startAnimation = true
        delay(2500)
        onAnimationEnd()
    }

    val letterIAlpha by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 1000) },
        label = "letterIAlpha"
    ) { state -> if (state) 1f else 0f }

    val letterIOffsetY by transition.animateDp(
        transitionSpec = { tween(durationMillis = 1000, easing = LinearOutSlowInEasing) },
        label = "letterIOffsetY"
    ) { state -> if (state) 0.dp else (-60).dp }

    val insiderAlpha by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 1500, delayMillis = 500) },
        label = "insiderAlpha"
    ) { state -> if (state) 1f else 0f }

    val screenOffsetX by transition.animateIntOffset(
        transitionSpec = { tween(durationMillis = 300, delayMillis = 1500, easing = FastOutLinearInEasing) },
        label = "screenOffsetX"
    ) { state -> if (state) IntOffset(-1600, 0) else IntOffset(0, 0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .offset { screenOffsetX }
            .background(Color(0xFF0288D1)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "I",
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = Color.White.copy(alpha = letterIAlpha),
                    fontSize = 90.sp
                ),
                modifier = Modifier.offset(y = letterIOffsetY)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Insider",
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color.White.copy(alpha = insiderAlpha),
                    fontSize = 50.sp
                )
            )
        }
    }
}
