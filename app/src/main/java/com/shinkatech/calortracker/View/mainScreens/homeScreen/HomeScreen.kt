package com.shinkatech.calortracker.View.mainScreens.homeScreen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
            modifier = Modifier.fillMaxSize().background(Color.Red)
                .verticalScroll(rememberScrollState())
        ) {

            for (i in 0..100){
                Text("hello this $i")
            }

            // this space will be cover by bottom nav view
            Spacer(modifier = Modifier.padding(top = 80.dp))
        }



}