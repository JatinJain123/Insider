package com.example.insider

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.absoluteValue

@Composable
fun MainScreen(
    model: MainViewModel,
    navigateToSearch: () -> Unit,
    navigateToPlatform: () -> Unit
) {
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
            TopHeader(
                navigateToSearch = navigateToSearch,
                navigateToPlatform = navigateToPlatform
            )

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
fun TopHeader(
    navigateToSearch: () -> Unit,
    navigateToPlatform: () -> Unit
) {
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
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Insider",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Color(0xFF0288D1),
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center
                    ),
                    fontWeight = FontWeight.Bold,
                )

                Spacer(Modifier.weight(1f))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier
                            .size(30.dp)
                            .clickable{ navigateToSearch() }
                    )

                    Spacer(Modifier.width(8.dp))

                    Image(
                        painter = painterResource(R.drawable.golobal_icon_image),
                        contentDescription = null,
                        modifier = Modifier
                            .size(30.dp)
                            .clickable { navigateToPlatform() }
                    )

                    Spacer(Modifier.width(8.dp))

                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier
                            .size(30.dp)
                            .clickable{  }
                    )
                }
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