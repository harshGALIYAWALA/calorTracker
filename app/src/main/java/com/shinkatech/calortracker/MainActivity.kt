package com.shinkatech.calortracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.shinkatech.calortracker.ui.theme.CalorTrackerTheme
import dagger.hilt.android.AndroidEntryPoint




@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalorTrackerTheme {
                FirebaseApp.initializeApp(this)
                MainApp()
            }
        }
    }
}


