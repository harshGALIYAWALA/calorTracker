package com.shinkatech.calortracker.View.onBoardScreens


import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.runtime.getValue // <-- THIS FIXES THE ERROR

@Composable
fun OnboardingScreens(
    navController: NavController,
    viewModel: OnBoardViewModel = hiltViewModel()
) {
    val currentIndex by viewModel.currentIndex.collectAsState()

    val screens = listOf<@Composable () -> Unit>(
        { OnBoardScreen1() },  //
        { OnBoardScreen2() },   //
        { OnBoardScreen3() },
        { OnBoardScreen4(navController) }
    )

    val colors = listOf(
        Color(0xFF667eea),
        Color(0xFF4facfe),
        Color(0xFF43e97b),
        Color(0xFFfa709a)
    )

    SwipeNavigatorScreen(
        navController = navController,
        screens = screens,
        screenColors = colors,
        viewModel = viewModel
    )
}




