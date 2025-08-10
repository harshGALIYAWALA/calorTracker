package com.shinkatech.calortracker.View.mainScreens.discoverScreen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun MealDetailScreen(navController: NavHostController) {

    Box(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = "Recipe dekhega bharve 🍆",
            fontSize = 20.sp,
            fontStyle = FontStyle.Italic,
            color = MaterialTheme.colorScheme.onSurface
        )
    }

}