package com.shinkatech.calortracker.View.mainScreens.discoverScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.shinkatech.calortracker.View.mainScreens.discoverScreen.components.GenerateDietCard
import com.shinkatech.calortracker.View.mainScreens.discoverScreen.components.TopCardView

@Composable
fun DiscoverScreen(navController: NavHostController) {
    // Basic variables for the screen
    var fitnessGoal by remember { mutableStateOf("Gain muscle") }
    var currentCalorieTrack by remember { mutableIntStateOf(450) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // AI-powered meal plan generator card at the top
        TopCardView(
            fitnessGoal = fitnessGoal,
            currentCalorieTrack = currentCalorieTrack
        )

        // Add some spacing between the cards
        Spacer(modifier = Modifier.height(24.dp))

        // Tabbed content card with meal ideas, snacks, recipes, etc.
        GenerateDietCard(navController)

        // Add bottom padding for better scroll experience
        Spacer(modifier = Modifier.height(16.dp))
    }
}