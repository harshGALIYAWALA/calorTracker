package com.shinkatech.calortracker.View.mainScreens.discoverScreen

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
fun DiscoverScreen(navController: NavHostController) {
    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(Color.Red)
        ) {
            Text(
                text = "hello this is discover screen",
                modifier = Modifier.padding(16.dp),
                maxLines = 1
            )
        }
    }
}