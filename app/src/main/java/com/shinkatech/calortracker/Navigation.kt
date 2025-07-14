package com.shinkatech.calortracker

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shinkatech.calortracker.View.authScreen.signInScreen.SignInScreen
import com.shinkatech.calortracker.View.onBoardScreens.OnboardingScreens
import com.shinkatech.calortracker.View.questionsScreens.QuesScreen
import com.shinkatech.calortracker.View.splashScreen.SplashScreen


@Composable
fun MainApp() {

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "SplashScreen") {
            composable("SplashScreen") {
                SplashScreen(navController)
            }
//
            composable("Onboarding") {
                OnboardingScreens(navController)
            }

            composable("QuesScreen") {
                QuesScreen(navController)
            }

            composable("SignInScreen") {
                SignInScreen(navController)
            }
        }
    }

}