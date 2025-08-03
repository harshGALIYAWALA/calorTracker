package com.shinkatech.calortracker.View.mainScreens.mainLayout

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shinkatech.calortracker.Screen
import com.shinkatech.calortracker.View.mainScreens.addScreen.AddScreen
import com.shinkatech.calortracker.View.mainScreens.discoverScreen.DiscoverScreen
import com.shinkatech.calortracker.View.mainScreens.historyScreen.HistoryScreen
import com.shinkatech.calortracker.View.mainScreens.homeScreen.HomeScreen
import com.shinkatech.calortracker.View.mainScreens.profileScreen.ProfileScreen

// Main composable that holds everything together
@Composable
fun MainLayout(rootNavController: NavHostController) {
    // Create a navigation controller for bottom navigation
    val bottomNavController = rememberNavController()
    val context = LocalContext.current

    // Use Scaffold - it's like a template that gives us structure
    Scaffold(
        bottomBar = {
            // This is where our bottom navigation bar goes
            BottomNavigationBar(bottomNavController)
        }
    ) { paddingValues ->
        // paddingValues makes sure content doesn't go behind navigation bar
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // This is important!
        ) {
            // NavHost manages which screen to show
            NavHost(
                navController = bottomNavController, // Use bottom nav controller here
                startDestination = Screen.HOME_SCREEN // Start with home screen
            ) {
                // Define each screen route
                composable(Screen.HOME_SCREEN) {
                    HomeScreen(rootNavController)
                }
                composable(Screen.HISTORY_SCREEN) {
                    HistoryScreen(rootNavController)
                }
                composable(Screen.ADD_SCREEN) {
                    // Add your AddScreen composable here
                    AddScreen(rootNavController)
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
}

// This composable creates our bottom navigation bar
@Composable
fun BottomNavigationBar(navController: NavHostController) {
    // Get current route to know which tab is selected
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // List of navigation items - this makes it easy to manage
    val navigationItems = listOf(
        NavigationItem(
            route = Screen.HOME_SCREEN,
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            label = "Home"
        ),
        NavigationItem(
            route = Screen.HISTORY_SCREEN,
            selectedIcon = Icons.Filled.History,
            unselectedIcon = Icons.Outlined.History,
            label = "History"
        ),
        NavigationItem(
            route = Screen.ADD_SCREEN,
            selectedIcon = Icons.Filled.Add,
            unselectedIcon = Icons.Outlined.Add,
            label = ""
        ),
        NavigationItem(
            route = Screen.DISCOVER_SCREEN,
            selectedIcon = Icons.Filled.Lightbulb,
            unselectedIcon = Icons.Outlined.Lightbulb,
            label = "Discover"
        ),
        NavigationItem(
            route = Screen.PROFILE_SCREEN,
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
            label = "Profile"
        )
    )


    // Material 3 NavigationBar
    NavigationBar(
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
    ) {
        // Create a NavigationBarItem for each item in our list
        navigationItems.forEach { item ->
            NavigationBarItem(
                // Check if this item is currently selected
                selected = currentRoute == item.route,

                // What happens when user taps this item
                onClick = {
                    // Only navigate if we're not already on this screen
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            // Clear back stack to home screen
                            popUpTo(Screen.HOME_SCREEN) {
                                saveState = true
                            }
                            // Don't create multiple copies of same screen
                            launchSingleTop = true
                            // Restore state when coming back to this screen
                            restoreState = true
                        }
                    }
                },

                // The icon that shows in the navigation bar
                icon = {
                    Icon(
                        imageVector = if (currentRoute == item.route) {
                            item.selectedIcon // Show filled icon when selected
                        } else {
                            item.unselectedIcon // Show outlined icon when not selected
                        },
                        contentDescription = item.label
                    )
                },

                // The text label below the icon
                label = {
                    Text(text = item.label)
                }
            )
        }
    }
}

// Data class to hold navigation item information
// This makes our code cleaner and easier to manage
data class NavigationItem(
    val route: String,
    val selectedIcon: androidx.compose.ui.graphics.vector.ImageVector,
    val unselectedIcon: androidx.compose.ui.graphics.vector.ImageVector,
    val label: String
)