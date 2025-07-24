package com.shinkatech.calortracker.View.mainScreens.mainLayout


import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.HistoryToggleOff
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shinkatech.calortracker.Screen
import com.shinkatech.calortracker.View.mainScreens.discoverScreen.DiscoverScreen
import com.shinkatech.calortracker.View.mainScreens.historyScreen.HistoryScreen
import com.shinkatech.calortracker.View.mainScreens.homeScreen.HomeScreen
import com.shinkatech.calortracker.View.mainScreens.profileScreen.ProfileScreen

@Composable
fun MainLayout(rootNavController: NavHostController) {
    val bottomNavController = rememberNavController()
    val context = LocalContext.current

    Scaffold(
        bottomBar = {
            BottomAppBar(containerColor = MaterialTheme.colorScheme.background) {
                IconButton(
                    onClick = {
                        bottomNavController.navigate(Screen.HOME_SCREEN) {
                            popUpTo(Screen.HOME_SCREEN) { inclusive = false }
                            launchSingleTop = true
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Home, contentDescription = "Home", tint = MaterialTheme.colorScheme.primary)
                }

                IconButton(
                    onClick = {
                        bottomNavController.navigate(Screen.HISTORY_SCREEN) {
                            popUpTo(Screen.HOME_SCREEN)
                            launchSingleTop = true
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.HistoryToggleOff, contentDescription = "History", tint = MaterialTheme.colorScheme.primary)
                }

                Box(modifier = Modifier.weight(1f).padding(16.dp), contentAlignment = Alignment.Center) {
                    FloatingActionButton(onClick = {
                        Toast.makeText(context, "action button clicked", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(Icons.Default.Add, contentDescription = "Add")
                    }
                }

                IconButton(
                    onClick = {
                        bottomNavController.navigate(Screen.DISCOVER_SCREEN) {
                            popUpTo(Screen.HOME_SCREEN)
                            launchSingleTop = true
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Search, contentDescription = "Search", tint = MaterialTheme.colorScheme.primary)
                }

                IconButton(
                    onClick = {
                        bottomNavController.navigate(Screen.PROFILE_SCREEN) {
                            popUpTo(Screen.HOME_SCREEN)
                            launchSingleTop = true
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Person, contentDescription = "Profile", tint = MaterialTheme.colorScheme.primary)
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = Screen.HOME_SCREEN,
            modifier = Modifier.padding(innerPadding)
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
