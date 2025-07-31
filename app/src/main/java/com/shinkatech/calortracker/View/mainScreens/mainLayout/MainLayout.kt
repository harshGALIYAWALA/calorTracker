package com.shinkatech.calortracker.View.mainScreens.mainLayout

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rahad.riobottomnavigation.composables.RioBottomNavItemData
import com.rahad.riobottomnavigation.composables.RioBottomNavigation
import com.shinkatech.calortracker.Screen
import com.shinkatech.calortracker.View.mainScreens.discoverScreen.DiscoverScreen
import com.shinkatech.calortracker.View.mainScreens.historyScreen.HistoryScreen
import com.shinkatech.calortracker.View.mainScreens.homeScreen.HomeScreen
import com.shinkatech.calortracker.View.mainScreens.profileScreen.ProfileScreen

@Composable
fun MainLayout(rootNavController: NavHostController) {
    val bottomNavController = rememberNavController()
    val context = LocalContext.current

    // Use rememberSaveable to retain state across configuration changes
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }

    // Navigation items data
    val navigationItems = listOf(
        NavigationItem(
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            label = "Home",
            route = Screen.HOME_SCREEN
        ),
        NavigationItem(
            selectedIcon = Icons.Filled.History,
            unselectedIcon = Icons.Outlined.History,
            label = "History",
            route = Screen.HISTORY_SCREEN
        ),
        NavigationItem(
            selectedIcon = Icons.Filled.Lightbulb,
            unselectedIcon = Icons.Outlined.Lightbulb,
            label = "Discover",
            route = Screen.DISCOVER_SCREEN
        ),
        NavigationItem(
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
            label = "Profile",
            route = Screen.PROFILE_SCREEN
        )
    )

    // Create RioBottomNavItemData for the bottom navigation buttons
    val buttons = navigationItems.mapIndexed { index, item ->
        RioBottomNavItemData(
            imageVector = if (index == selectedIndex) item.selectedIcon else item.unselectedIcon,
            selected = index == selectedIndex,
            onClick = {
                selectedIndex = index
                bottomNavController.navigate(item.route) {
                    popUpTo(Screen.HOME_SCREEN) {
                        inclusive = (item.route == Screen.HOME_SCREEN)
                    }
                    launchSingleTop = true
                }
            },
            label = item.label
        )
    }

    // Main Scaffold setup
    Scaffold(
        bottomBar = {
            BottomNavigationBar(buttons = buttons)
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->

                NavHost(
                    navController = bottomNavController,
                    startDestination = Screen.HOME_SCREEN,
                    modifier = Modifier.fillMaxSize()
                ) {
                    composable(Screen.HOME_SCREEN) {
                        HomeScreen(rootNavController)
                    }
                    composable(Screen.HISTORY_SCREEN) {
                        HistoryScreen(rootNavController)
                    }
                    composable(Screen.DISCOVER_SCREEN) {
                        DiscoverScreen(rootNavController)
                    }
                    composable(Screen.PROFILE_SCREEN) {
                        ProfileScreen(rootNavController)
                    }
                }


    }
}

@Composable
fun BottomNavigationBar(buttons: List<RioBottomNavItemData>) {
    val context = LocalContext.current

    RioBottomNavigation(
        fabIcon = Icons.Filled.Add, // Using your existing Add icon for FAB
        buttons = buttons,
        fabSize = 70.dp,
        barHeight = 70.dp,
        selectedItemColor = MaterialTheme.colorScheme.primary,
        fabBackgroundColor = MaterialTheme.colorScheme.primary,
        onFabClick = {
            Toast.makeText(context, "Add Food", Toast.LENGTH_SHORT).show()
        }
    )
}

// Data class to hold navigation item information
data class NavigationItem(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val label: String,
    val route: String
)
