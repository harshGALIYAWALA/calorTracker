package com.shinkatech.calortracker.View.mainScreens.discoverScreen.components

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.GeneratingTokens
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.LocalGroceryStore
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun TopCardView(
    fitnessGoal: String = "Gain muscle",
    currentCalorieTrack: Int = 450
) {
    // Variables to control what shows on screen
    var isExpanded by remember { mutableStateOf(false) } // shows the input form
    var showMealPlan by remember { mutableStateOf(false) } // shows the meal plan
    var isGenerating by remember { mutableStateOf(false) } // shows loading

    // User input variables
    var dietaryPreference by remember { mutableStateOf("") }
    var caloriesGoal by remember { mutableStateOf("") }
    var ingredients by remember { mutableStateOf("") }

    // Context for toast messages
    val context = LocalContext.current.applicationContext

    // Main gradient card
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(animationSpec = tween(durationMillis = 300)),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF6366F1),
                            Color(0xFF8B5CF6),
                            Color(0xFF06B6D4)
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(1000f, 1000f)
                    ),
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(18.dp)
        ) {
            Column {
                // Title text
                Text(
                    text = "Discover for You",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 22.sp
                    )
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Goal and calorie text
                Text(
                    text = "Goal: $fitnessGoal | $currentCalorieTrack kcal to go",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium,
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 14.sp
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Top button - changes between "Start with AI" and "Close"
                Button(
                    onClick = {
                        // If meal plan is showing, close everything and reset
                        if (showMealPlan) {
                            showMealPlan = false
                            isExpanded = false
                            // Clear all input fields
                            dietaryPreference = ""
                            caloriesGoal = ""
                            ingredients = ""
                        } else {
                            // If meal plan is not showing, toggle the input form
                            isExpanded = !isExpanded
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White.copy(alpha = 0.25f),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    modifier = Modifier.height(36.dp)
                ) {
                    // Icon changes based on state
                    Icon(
                        imageVector = if (showMealPlan) Icons.Default.Refresh else Icons.Default.AutoAwesome,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    // Text changes based on state
                    Text(
                        text = if (showMealPlan || isExpanded) "Close" else "Start with AI",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp
                        )
                    )
                }

                // Call the extendedCardView function to show input form
                extendedCardView(
                    isExpanded = isExpanded,
                    dietaryPreference = dietaryPreference,
                    onDietaryPreferenceChange = { dietaryPreference = it },
                    caloriesGoal = caloriesGoal,
                    onCaloriesGoalChange = { caloriesGoal = it },
                    ingredients = ingredients,
                    onIngredientsChange = { ingredients = it },
                    isGenerating = isGenerating,
                    onGenerateClick = {
                        isGenerating = true // Start generating process
                    }
                )

                // Call the extendedCardView2 function to show meal plan
                if (showMealPlan) {
                    extendedCardView2(
                        onSaveClick = {
                            Toast.makeText(context, "Diet Plan Saved Successfully!", Toast.LENGTH_SHORT).show()
                        },
                        onRegenerateClick = {
                            showMealPlan = false // Hide meal plan
                            isExpanded = true // Show input form
                        }
                    )
                }
            }
        }
    }

    // Handle the generation process with delay
    LaunchedEffect(isGenerating) {
        if (isGenerating) {
            delay(2000) // Wait 2 seconds to simulate API call
            isGenerating = false // Stop loading
            showMealPlan = true // Show the meal plan
            isExpanded = false // Hide the input form
        }
    }
}

// Function to show the input form with three fields
@Composable
private fun extendedCardView(
    isExpanded: Boolean,
    dietaryPreference: String,
    caloriesGoal: String,
    ingredients: String,
    onDietaryPreferenceChange: (String) -> Unit,
    onCaloriesGoalChange: (String) -> Unit,
    onIngredientsChange: (String) -> Unit,
    isGenerating: Boolean,
    onGenerateClick: () -> Unit
) {
    // Show input form when expanded
    if (isExpanded) {
        Column {
            Spacer(modifier = Modifier.height(16.dp))

            // First input field - dietary preference using custom function
            customFieldText(
                value = dietaryPreference,
                onValueChange = onDietaryPreferenceChange,
                label = "Dietary Preference",
                placeholder = "e.g., Vegetarian, Vegan",
                leadingIcon = Icons.Default.Restaurant,
                keyboardType = KeyboardType.Text,
                maxLine = 2
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Second input field - calories goal using custom function
            customFieldText(
                value = caloriesGoal,
                onValueChange = onCaloriesGoalChange,
                label = "Calorie Goal",
                placeholder = "e.g., 2000",
                leadingIcon = Icons.Default.LocalFireDepartment,
                keyboardType = KeyboardType.Number,
                maxLine = 1
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Third input field - ingredients using custom function
            customFieldText(
                value = ingredients,
                onValueChange = onIngredientsChange,
                label = "Available Ingredients",
                placeholder = "e.g., Chicken, Rice, Broccoli",
                leadingIcon = Icons.Default.LocalGroceryStore,
                keyboardType = KeyboardType.Text,
                maxLine = 3
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Generate Diet button
            Button(
                onClick = onGenerateClick,
                enabled = !isGenerating, // Disable when generating
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White.copy(alpha = 0.2f),
                    contentColor = Color.White,
                    disabledContainerColor = Color.White.copy(alpha = 0.1f),
                    disabledContentColor = Color.White.copy(alpha = 0.7f)
                ),
                contentPadding = PaddingValues()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    // Show loading spinner when generating
                    if (isGenerating) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(18.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.GeneratingTokens,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    // Change text when generating
                    Text(
                        text = if (isGenerating) "Generating..." else "Generate Diet",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp
                        )
                    )
                }
            }
        }
    }
}

// Function to show the meal plan with all meals
@Composable
private fun extendedCardView2(
    onSaveClick: () -> Unit,
    onRegenerateClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        // Header for meal plan
        Text(
            text = "Your AI Meal Plan 🍽️",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.White
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Show all meal cards using custom function
        aiMealSuggestionCard(
            mealType = "Breakfast",
            suggestion = "• Oatmeal with banana & almonds\n• Boiled egg\n• Green tea",
            calories = "320 kcal"
        )

        Spacer(modifier = Modifier.height(8.dp))

        aiMealSuggestionCard(
            mealType = "Lunch",
            suggestion = "• Grilled chicken breast\n• Brown rice\n• Steamed broccoli",
            calories = "450 kcal"
        )

        Spacer(modifier = Modifier.height(8.dp))

        aiMealSuggestionCard(
            mealType = "Snack",
            suggestion = "• Greek yogurt with berries\n• Handful of walnuts",
            calories = "180 kcal"
        )

        Spacer(modifier = Modifier.height(8.dp))

        aiMealSuggestionCard(
            mealType = "Dinner",
            suggestion = "• Quinoa salad with chickpeas\n• Sautéed spinach\n• Herbal tea",
            calories = "380 kcal"
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Bottom buttons - Regenerate and Save Plan
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Regenerate button - shows input form again
            Button(
                onClick = onRegenerateClick,
                modifier = Modifier
                    .weight(1f)
                    .height(45.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White.copy(alpha = 0.15f),
                    contentColor = Color.White
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Refresh",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium
                    )
                )
            }

            // Save Plan button - shows toast message
            Button(
                onClick = onSaveClick,
                modifier = Modifier
                    .weight(1f)
                    .height(45.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White.copy(alpha = 0.25f),
                    contentColor = Color.White
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Save Plan",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    )
                )
            }
        }
    }
}

// Function to create individual meal cards
@Composable
private fun aiMealSuggestionCard(
    mealType: String,
    suggestion: String,
    calories: String
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f)),
        elevation = CardDefaults.cardElevation(0.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = mealType,
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 16.sp
                    )
                )
                Text(
                    text = calories,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.Medium,
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 12.sp
                    )
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = suggestion,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.9f)
                )
            )
        }
    }
}

// Function to create custom input text fields
@Composable
private fun customFieldText(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    leadingIcon: ImageVector,
    keyboardType: KeyboardType,
    maxLine: Int
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(30.dp),
        label = { Text(text = label, color = Color.White.copy(alpha = 0.9f)) },
        placeholder = { Text(text = placeholder, color = Color.White.copy(alpha = 0.6f)) },
        leadingIcon = {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.8f)
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.White.copy(alpha = 0.8f),
            unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White.copy(alpha = 0.9f),
            cursorColor = Color.White,
            focusedLabelColor = Color.White.copy(alpha = 0.9f),
            unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
            focusedLeadingIconColor = Color.White.copy(alpha = 0.9f),
            unfocusedLeadingIconColor = Color.White.copy(alpha = 0.7f),
            focusedPlaceholderColor = Color.White.copy(alpha = 0.6f),
            unfocusedPlaceholderColor = Color.White.copy(alpha = 0.5f)
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Done
        ),
        maxLines = maxLine
    )
}