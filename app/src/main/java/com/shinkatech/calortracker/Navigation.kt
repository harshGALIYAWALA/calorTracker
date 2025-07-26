package com.shinkatech.calortracker

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shinkatech.calortracker.View.authScreen.loginScreen.LoginScreen
import com.shinkatech.calortracker.View.authScreen.signInScreen.SignInScreen
import com.shinkatech.calortracker.View.mainScreens.discoverScreen.DiscoverScreen
import com.shinkatech.calortracker.View.mainScreens.historyScreen.HistoryScreen
import com.shinkatech.calortracker.View.mainScreens.homeScreen.HomeScreen
import com.shinkatech.calortracker.View.mainScreens.mainLayout.MainLayout
import com.shinkatech.calortracker.View.mainScreens.profileScreen.ProfileScreen
import com.shinkatech.calortracker.View.onBoardScreens.OnboardingScreens
import com.shinkatech.calortracker.View.questionsScreens.QuesScreen
import com.shinkatech.calortracker.View.splashScreen.SplashScreen


object Screen{
    const val SPLASH_SCREEN = "splashScreen"
    const val ONBOARDING_SCREEN = "onboarding"
    const val QUESTIONS_SCREEN = "quesScreen"
    const val SIGN_IN_SCREEN = "signInScreen"
    const val LOGIN_SCREEN = "loginScreen"
    const val MAIN_LAYOUT = "mainLayout"
    const val HOME_SCREEN = "homeScreen"
    const val HISTORY_SCREEN = "historyScreen"
    const val DISCOVER_SCREEN = "discoverScreen"
    const val PROFILE_SCREEN = "profileScreen"
}

@Composable
fun MainApp() {

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = Screen.SIGN_IN_SCREEN) {

            composable(Screen.SPLASH_SCREEN) {
                SplashScreen(navController)
            }

            composable(Screen.ONBOARDING_SCREEN) {
                BackHandler(enabled = true) {
                    navController.popBackStack()
                }
                OnboardingScreens(navController)
            }

            composable(Screen.QUESTIONS_SCREEN) {
                QuesScreen(navController)
            }

            composable(Screen.SIGN_IN_SCREEN) {
                SignInScreen(navController)
            }

            composable(Screen.LOGIN_SCREEN) {
                LoginScreen(navController)
            }

            composable(Screen.MAIN_LAYOUT) {
                MainLayout(navController)
            }

            composable(Screen.HOME_SCREEN) {
                HomeScreen(navController)
            }

            composable(Screen.HISTORY_SCREEN) {
                HistoryScreen(navController)
            }

            composable(Screen.DISCOVER_SCREEN) {
                DiscoverScreen(navController)
            }

            composable(Screen.PROFILE_SCREEN) {
                ProfileScreen(navController)
            }

        }
    }

}
