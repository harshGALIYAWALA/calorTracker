package com.shinkatech.calortracker.View.mainScreens.mainLayout

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
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

    var selectedItem by remember { mutableStateOf(0) }

    // Remove the Box background and let individual screens handle their backgrounds
    Box(modifier = Modifier.fillMaxSize()) {
        // Main content
        Column(modifier = Modifier.fillMaxSize()) {
            // Navigation content - remove background here
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
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

            // Custom Bottom Bar - remove background wrapper
            CustomBottomBar(
                selectedItem = selectedItem,
                onItemSelected = { index ->
                    selectedItem = index
                    when (index) {
                        0 -> {
                            bottomNavController.navigate(Screen.HOME_SCREEN) {
                                popUpTo(Screen.HOME_SCREEN) { inclusive = false }
                                launchSingleTop = true
                            }
                        }
                        1 -> {
                            bottomNavController.navigate(Screen.HISTORY_SCREEN) {
                                popUpTo(Screen.HOME_SCREEN)
                                launchSingleTop = true
                            }
                        }
                        2 -> {
                            bottomNavController.navigate(Screen.DISCOVER_SCREEN) {
                                popUpTo(Screen.HOME_SCREEN)
                                launchSingleTop = true
                            }
                        }
                        3 -> {
                            bottomNavController.navigate(Screen.PROFILE_SCREEN) {
                                popUpTo(Screen.HOME_SCREEN)
                                launchSingleTop = true
                            }
                        }
                    }
                }
            )
        }

        // Enhanced Floating Action Button
        FloatingActionButton(
            onClick = {
                Toast.makeText(context, "Add Food", Toast.LENGTH_SHORT).show()
            },
            shape = CircleShape,
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = (-45).dp)
                .shadow(
                    elevation = 8.dp,
                    shape = CircleShape,
                    ambientColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.25f),
                    spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.25f)
                )
        ) {
            Icon(
                Icons.Filled.Add,
                contentDescription = "Add Food",
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
fun CustomBottomBar(
    selectedItem: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    // Remove the background Box wrapper - let Surface handle everything
    Surface(
        modifier = modifier
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 16.dp,
        tonalElevation = 8.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 20.dp), // Reduced vertical padding
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left side icons (Home, History)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Home Button
                BottomNavItem(
                    isSelected = selectedItem == 0,
                    selectedIcon = Icons.Filled.Home,
                    unselectedIcon = Icons.Outlined.Home,
                    contentDescription = "Home",
                    onClick = { if (selectedItem != 0) onItemSelected(0) }
                )

                // History Button
                BottomNavItem(
                    isSelected = selectedItem == 1,
                    selectedIcon = Icons.Filled.History,
                    unselectedIcon = Icons.Outlined.History,
                    contentDescription = "History",
                    onClick = { if (selectedItem != 1) onItemSelected(1) }
                )
            }

            // Spacer for FAB
            Spacer(modifier = Modifier.width(64.dp))

            // Right side icons (Discover, Profile)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Discover Button
                BottomNavItem(
                    isSelected = selectedItem == 2,
                    selectedIcon = Icons.Filled.Lightbulb,
                    unselectedIcon = Icons.Outlined.Lightbulb,
                    contentDescription = "Discover",
                    onClick = { if (selectedItem != 2) onItemSelected(2) }
                )

                // Profile Button
                BottomNavItem(
                    isSelected = selectedItem == 3,
                    selectedIcon = Icons.Filled.Person,
                    unselectedIcon = Icons.Outlined.Person,
                    contentDescription = "Profile",
                    onClick = { if (selectedItem != 3) onItemSelected(3) }
                )
            }
        }
    }
}

@Composable
private fun BottomNavItem(
    isSelected: Boolean,
    selectedIcon: androidx.compose.ui.graphics.vector.ImageVector,
    unselectedIcon: androidx.compose.ui.graphics.vector.ImageVector,
    contentDescription: String,
    onClick: () -> Unit
) {
    val iconColor = if (isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
    }

    IconButton(
        onClick = onClick,
        modifier = Modifier.size(48.dp)
    ) {
        Icon(
            imageVector = if (isSelected) selectedIcon else unselectedIcon,
            contentDescription = contentDescription,
            tint = iconColor,
            modifier = Modifier.size(26.dp)
        )
    }
}