package com.shinkatech.calortracker.View.mainScreens.discoverScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.shinkatech.calortracker.Screen
import kotlinx.coroutines.launch

// Data classes for the content )
data class TrendingMeal(
    val name: String,
    val calories: String,
    val prepTime: String,
    val trendingInfo: String,
    val emoji: String
)


data class Challenge(
    val name: String,
    val description: String,
    val duration: String,
    val emoji: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenerateDietCard(navController: NavHostController) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("Trending Meals", "Challenge")

    // Sample data for each tab
    val trendingMeals = listOf(
        TrendingMeal("Avocado Toast Bowl", "280 kcal", "5 min", "1.2k made this week", "🥑"),
        TrendingMeal("Protein Smoothie Bowl", "320 kcal", "3 min", "856 made this week", "🍓"),
        TrendingMeal("Mediterranean Quinoa", "450 kcal", "15 min", "743 made this week", "🍲"),
        TrendingMeal("Green Power Salad", "220 kcal", "8 min", "692 made this week", "🥗"),
        TrendingMeal("Buddha Bowl Supreme", "380 kcal", "12 min", "587 made this week", "🍜"),
        TrendingMeal("Overnight Oats", "300 kcal", "2 min prep", "534 made this week", "🥣")
    )

    val challenges = listOf(
        Challenge("7-Day Hydration", "Drink 8 glasses of water daily", "1 week", "💧"),
        Challenge("Protein Challenge", "Hit your protein goal for 5 days", "5 days", "💪"),
        Challenge("Veggie Boost", "Include vegetables in every meal", "3 days", "🥦"),
        Challenge("No Sugar Week", "Avoid added sugars completely", "1 week", "🚫")
    )

    var selectedMeal by remember { mutableStateOf<TrendingMeal?>(null) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Custom Tab Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(25.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                    .padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                tabTitles.forEachIndexed { index, title ->
                    val isSelected = selectedTabIndex == index
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .weight(1f)
                            .background(
                                if (isSelected) {
                                    Brush.linearGradient(
                                        colors = listOf(
                                            Color(0xFF6366F1),
                                            Color(0xFF8B5CF6)
                                        )
                                    )
                                } else {
                                    Brush.linearGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            Color.Transparent
                                        )
                                    )
                                }
                            )
                            .clickable { selectedTabIndex = index }
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = title,
                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface.copy(
                                alpha = 0.7f
                            ),
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
                            textAlign = TextAlign.Center,
                            maxLines = 1
                        )
                    }
                }
            }


            Spacer(modifier = Modifier.height(16.dp))

            // Content based on selected tab
            when (selectedTabIndex) {
                0 -> TrendingMealsContent(trendingMeals) {
                    selectedMeal = it
                }

                1 -> ChallengesContent(challenges)
            }
        }
    }
    selectedMeal?.let { meal ->
        mealBottomSheets(
            meal = meal,
            onDismiss = { selectedMeal = null },
            nav = navController
        )
    }
}


@Composable
private fun TrendingMealsContent(
    trendingMeals: List<TrendingMeal>,
    onMealClick: (TrendingMeal) -> Unit // add this
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.height(350.dp)
    ) {
        itemsIndexed(trendingMeals) { _, meal ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onMealClick(meal) }, // handle click
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                ),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.padding(end = 12.dp)) {
                            Text(text = meal.emoji, fontSize = 32.sp)
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = meal.name,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = meal.calories,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                                fontWeight = FontWeight.Medium
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(top = 2.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AccessTime,
                                    contentDescription = null,
                                    modifier = Modifier.size(12.dp),
                                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = meal.prepTime,
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = null,
                                    modifier = Modifier.size(12.dp),
                                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = meal.trendingInfo,
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun ChallengesContent(challenges: List<Challenge>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.height(350.dp)
    ) {
        items(challenges) { challenge ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                ),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = challenge.emoji,
                        fontSize = 32.sp,
                        modifier = Modifier.padding(end = 12.dp)
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = challenge.name,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = challenge.description,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                            modifier = Modifier.padding(top = 2.dp)
                        )
                        Text(
                            text = "Duration: ${challenge.duration}",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                            modifier = Modifier.padding(top = 4.dp),
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Button(
                        onClick = { /* Start challenge */ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6366F1)
                        ),
                        shape = RoundedCornerShape(20.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
                        modifier = Modifier.height(32.dp)
                    ) {
                        Text(
                            text = "Start",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun mealBottomSheets(
    meal: TrendingMeal,
    onDismiss: () -> Unit,
    nav: NavHostController
    ) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = {
            scope.launch { sheetState.hide() }
            onDismiss()
        },
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, start = 24.dp, end = 24.dp, bottom = 24.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                Text(meal.emoji, fontSize = 40.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = meal.name,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text("Calories: ${meal.calories}", fontSize = 14.sp)
            Text("Prep Time: ${meal.prepTime}", fontSize = 14.sp)
            Text("Trending: ${meal.trendingInfo}", fontSize = 14.sp)

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    scope.launch {
                        sheetState.hide()
                        onDismiss()
                    }
                    nav.navigate(Screen.MEAL_DETAIL_SCREEN)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6366F1))
            ) {
                Text("View Full Recipe", color = Color.White)
            }
        }
    }
}