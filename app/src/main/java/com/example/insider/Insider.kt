package com.example.insider

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.absoluteValue

@Composable
fun Insider(model: MainViewModel) {
    var showSplash by remember { mutableStateOf(true) }

    if (showSplash) {
         SplashScreen {
            showSplash = false
         }
    } else {
        MainScreen(model)
    }
}

@Composable
fun MainScreen(model: MainViewModel) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf(
        TabItem("News", Icons.Default.Settings),
        TabItem("Highlights", Icons.Default.Star),
        TabItem("Sources", Icons.Default.Home)
    )

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color( 0xFFFFFFFF))
    ) {
        Column{
            TopHeader()

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .weight(1f)
            ) {
                when (selectedTab.absoluteValue) {
                    0 -> TopStoriesPage(model = model)
                    1 -> TopHeadlinesPage(model = model)
                    2 -> TopSources(model = model)
                }
            }

            BottomTabs(tabs, selectedTab, { index -> selectedTab = index })
        }
    }
}

data class TabItem(val title: String, val icon: ImageVector)


@Composable
fun TopHeader() {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val rotation by infiniteTransition.animateFloat(
        label = "top header animation",
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, delayMillis = 1500, easing = LinearEasing)
        )
    )

    val showSearch = remember { mutableStateOf(false) }
    if(showSearch.value) {
        Search()
        showSearch.value = false
    }

    Box {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .windowInsetsPadding(WindowInsets.statusBars)
        ) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier
                        .size(30.dp)
                        .clickable{ showSearch.value = true }
                )

                Text(
                    text = "Insider",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Color(0xFF0288D1),
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center
                    ),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "the animated icon",
                    modifier = Modifier.size(36.dp).rotate(rotation),
                    tint = Color.Black
                )
            }
        }
    }
}

@Composable
fun BottomTabs(tabs: List<TabItem>, selectedTab: Int, onTabSelected: (Int) -> Unit) {
    Box {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .windowInsetsPadding(WindowInsets.navigationBars)
        ) {
            HorizontalDivider(
                color = Color.DarkGray,
                thickness = 2.dp
            )

            TabRow(
                selectedTabIndex = selectedTab,
                indicator = { tabPositions ->
                    SecondaryIndicator(
                        Modifier
                            .tabIndicatorOffset(tabPositions[selectedTab])
                            .height(2.dp)
                            .background(Color.Gray),
                        color = Color.Black
                    )
                }
            ) {
                tabs.forEachIndexed { index, tabItem ->
                    Tab(
                        selected = (selectedTab == index),
                        onClick = { onTabSelected(index) },
                        modifier = Modifier.background(Color(0xFFF5F5F5)),
                        text = {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    imageVector = tabItem.icon,
                                    contentDescription = null,
                                    tint = Color.Black
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    tabItem.title,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}