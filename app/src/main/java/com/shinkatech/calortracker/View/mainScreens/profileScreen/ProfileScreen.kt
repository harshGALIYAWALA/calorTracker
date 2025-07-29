package com.shinkatech.calortracker.View.mainScreens.profileScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.shinkatech.calortracker.Screen


@Composable
fun ProfileScreen(navController: NavHostController) {

    val auth = FirebaseAuth.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(
            onClick = {
                auth.signOut() // Log out from Firebase
                navController.navigate(Screen.LOGIN_SCREEN) {
                    popUpTo(0) { inclusive = true } // Clear backstack
                }
            }
        ) {
            Text(text = "Log Out")
        }
    }

}