package com.shinkatech.calortracker.View.questionsScreens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuesScreen(navController: NavHostController) {
    val textColor = Color.Black
    val backgroundColor = Color.White

    // Form state variables
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var fitnessGoal by remember { mutableStateOf("") }
    var targetDate by remember { mutableStateOf("") }
    var activityLevel by remember { mutableStateOf("") }

    val genderOptions = listOf("Male", "Female")
    val fitnessGoalOptions = listOf("Lose weight", "Maintain weight", "Gain weight/muscle")
    val activityLevelOptions = listOf(
        "Sedentary (little or no exercise)",
        "Lightly active (light exercise/sports 1–3 days/week)",
        "Moderately active (moderate exercise 3–5 days/week)",
        "Very active (hard exercise 6–7 days/week)",
        "Super active (very hard exercise or physical job)"
    )

    // Dropdown states
    var genderExpanded by remember { mutableStateOf(false) }
    var fitnessGoalExpanded by remember { mutableStateOf(false) }
    var activityLevelExpanded by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {

            // Header
            Text(
                text = "Fitness Profile",
                style = MaterialTheme.typography.headlineLarge,
                color = textColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp, top = 16.dp)
            )

            Text(
                text = "Tell us about yourself to create your personalized fitness plan",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Personal Information Section
            SectionHeader(title = "Personal Information", textColor = textColor)

            // Name Input
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Full Name", color = Color.Gray) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF2196F3),
                    unfocusedBorderColor = Color.LightGray,
                    focusedTextColor = textColor,
                    unfocusedTextColor = textColor,
                    cursorColor = Color(0xFF2196F3)
                ),
                shape = RoundedCornerShape(12.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = Color.Gray
                    )
                },
                maxLines = 1,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
            )

            // Age Input
            OutlinedTextField(
                value = age,
                onValueChange = { if (it.all { char -> char.isDigit() }) age = it },
                label = { Text("Age", color = Color.Gray) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF2196F3),
                    unfocusedBorderColor = Color.LightGray,
                    focusedTextColor = textColor,
                    unfocusedTextColor = textColor,
                    cursorColor = Color(0xFF2196F3)
                ),
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null,
                        tint = Color.Gray
                    )
                },
                maxLines = 1,
                singleLine = true,
            )

            // Gender Dropdown
            ExposedDropdownMenuBox(
                expanded = genderExpanded,
                onExpandedChange = { genderExpanded = it }
            ) {
                OutlinedTextField(
                    value = gender,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Gender", color = Color.Gray) },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = genderExpanded)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF2196F3),
                        unfocusedBorderColor = Color.LightGray,
                        focusedTextColor = textColor,
                        unfocusedTextColor = textColor
                    ),
                    shape = RoundedCornerShape(12.dp)
                )

                ExposedDropdownMenu(
                    expanded = genderExpanded,
                    onDismissRequest = { genderExpanded = false },
                    modifier = Modifier.background(Color.White)
                ) {
                    genderOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option, color = textColor) },
                            onClick = {
                                gender = option
                                genderExpanded = false
                            }
                        )
                    }
                }
            }

            // Physical Attributes Section
            SectionHeader(title = "Physical Attributes", textColor = textColor)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Height Input
                OutlinedTextField(
                    value = height,
                    onValueChange = { height = it },
                    label = { Text("Height (cm)", color = Color.Gray) },
                    modifier = Modifier.weight(1f),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF2196F3),
                        unfocusedBorderColor = Color.LightGray,
                        focusedTextColor = textColor,
                        unfocusedTextColor = textColor,
                        cursorColor = Color(0xFF2196F3)
                    ),
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    maxLines = 1,
                    singleLine = true
                )

                // Weight Input
                OutlinedTextField(
                    value = weight,
                    onValueChange = { weight = it },
                    label = { Text("Weight (kg)", color = Color.Gray) },
                    modifier = Modifier.weight(1f),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF2196F3),
                        unfocusedBorderColor = Color.LightGray,
                        focusedTextColor = textColor,
                        unfocusedTextColor = textColor,
                        cursorColor = Color(0xFF2196F3)
                    ),
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    maxLines = 1,
                    singleLine = true
                )
            }

            // Fitness Goals Section
            SectionHeader(title = "Fitness Goals", textColor = textColor)

            // Fitness Goal Dropdown
            ExposedDropdownMenuBox(
                expanded = fitnessGoalExpanded,
                onExpandedChange = { fitnessGoalExpanded = it }
            ) {
                OutlinedTextField(
                    value = fitnessGoal,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Fitness Goal", color = Color.Gray) },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = fitnessGoalExpanded)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF2196F3),
                        unfocusedBorderColor = Color.LightGray,
                        focusedTextColor = textColor,
                        unfocusedTextColor = textColor
                    ),
                    shape = RoundedCornerShape(12.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.FitnessCenter,
                            contentDescription = null,
                            tint = Color.Gray
                        )
                    }
                )

                ExposedDropdownMenu(
                    expanded = fitnessGoalExpanded,
                    onDismissRequest = { fitnessGoalExpanded = false },
                    modifier = Modifier.background(Color.White)
                ) {
                    fitnessGoalOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option, color = textColor) },
                            onClick = {
                                fitnessGoal = option
                                fitnessGoalExpanded = false
                            }
                        )
                    }
                }
            }

            // Target Date Input
            OutlinedTextField(
                value = targetDate,
                onValueChange = { targetDate = it },
                label = { Text("Target Date (DD/MM/YYYY)", color = Color.Gray) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF2196F3),
                    unfocusedBorderColor = Color.LightGray,
                    focusedTextColor = textColor,
                    unfocusedTextColor = textColor,
                    cursorColor = Color(0xFF2196F3)
                ),
                shape = RoundedCornerShape(12.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Event,
                        contentDescription = null,
                        tint = Color.Gray
                    )
                },
                maxLines = 1,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )

            // Activity Level Section
            SectionHeader(title = "Activity Level", textColor = textColor)

            // Activity Level Dropdown
            ExposedDropdownMenuBox(
                expanded = activityLevelExpanded,
                onExpandedChange = { activityLevelExpanded = it }
            ) {
                OutlinedTextField(
                    value = activityLevel,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Current Activity Level", color = Color.Gray) },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = activityLevelExpanded)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF2196F3),
                        unfocusedBorderColor = Color.LightGray,
                        focusedTextColor = textColor,
                        unfocusedTextColor = textColor
                    ),
                    shape = RoundedCornerShape(12.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.DirectionsRun,
                            contentDescription = null,
                            tint = Color.Gray
                        )
                    }
                )

                ExposedDropdownMenu(
                    expanded = activityLevelExpanded,
                    onDismissRequest = { activityLevelExpanded = false },
                    modifier = Modifier.background(Color.White)
                ) {
                    activityLevelOptions.forEach { option ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = option,
                                    color = textColor,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            },
                            onClick = {
                                activityLevel = option
                                activityLevelExpanded = false
                            }
                        )
                    }
                }
            }

            // Submit Button
            Button(
                onClick = {
                    
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2196F3)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Create My Fitness Plan",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String, textColor: Color) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineSmall,
        color = textColor,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
    )
}
