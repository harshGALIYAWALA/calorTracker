package com.shinkatech.calortracker.View.splashScreen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState

import com.airbnb.lottie.compose.rememberLottieComposition
import com.shinkatech.calortracker.R
import com.shinkatech.calortracker.Screen

@Composable
fun SplashScreen(navController: NavHostController) {


    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.splash_animation)
    )
    val progress by animateLottieCompositionAsState(
        composition,
        speed = 2f,
        iterations = 1
    )


    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        LottieAnimation(
            composition = composition,
            progress = progress,
            modifier = Modifier.fillMaxSize()
        )
    }

    LaunchedEffect(key1 = progress) {
       if (progress == 1f){
           navController.navigate("Onboarding"){
               popUpTo(Screen.SPLASH_SCREEN){ inclusive = true }
               launchSingleTop = true
           }
       }
    }



}