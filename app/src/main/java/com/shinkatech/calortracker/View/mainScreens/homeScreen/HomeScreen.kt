package com.shinkatech.calortracker.View.mainScreens.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(navController: NavHostController) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Red)
        ) {
            Text(
                text = "hello this is home screen",
                modifier = Modifier.padding(16.dp),
                maxLines = 1
            )
        }

}