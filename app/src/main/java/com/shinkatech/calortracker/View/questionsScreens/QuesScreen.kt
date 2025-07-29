package com.shinkatech.calortracker.View.questionsScreens


import android.util.Log
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.shinkatech.calortracker.ui.theme.CalorTrackerTheme
import androidx.hilt.navigation.compose.hiltViewModel
import com.shinkatech.calortracker.Model.QuesDataUserModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuesScreen(navController: NavHostController) {
    CalorTrackerTheme {
        // Form state variables
        var name by remember { mutableStateOf("") }
        var age by remember { mutableStateOf("") }
        var gender by remember { mutableStateOf("") }
        var height by remember { mutableStateOf("") }
        var weight by remember { mutableStateOf("") }
        var fitnessGoal by remember { mutableStateOf("") }
        var targetDate by remember { mutableStateOf("") }
        var activityLevel by remember { mutableStateOf("") }

        // Error state variables
        var nameError by remember { mutableStateOf<String?>(null) }
        var ageError by remember { mutableStateOf<String?>(null) }
        var genderError by remember { mutableStateOf<String?>(null) }
        var heightError by remember { mutableStateOf<String?>(null) }
        var weightError by remember { mutableStateOf<String?>(null) }
        var fitnessGoalError by remember { mutableStateOf<String?>(null) }
        var targetDateError by remember { mutableStateOf<String?>(null) }
        var activityLevelError by remember { mutableStateOf<String?>(null) }


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

        val viewModel: QuesScreenViewModel = hiltViewModel() // Use hiltViewModel() function

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                // Header
                Text(
                    text = "Fitness Profile",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp, top = 16.dp)
                )

                Text(
                    text = "Tell us about yourself to create your personalized fitness plan",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Personal Information Section
                SectionHeader(title = "Personal Information")

                // Name Input
                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                        nameError = null // Clear error on change
                    },
                    label = { Text("Full Name") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                        focusedTextColor = MaterialTheme.colorScheme.primary,
                        unfocusedTextColor = MaterialTheme.colorScheme.primary,
                        cursorColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                    ),
                    shape = RoundedCornerShape(30.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                        )
                    },
                    maxLines = 1,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    isError = nameError != null,
                    supportingText = {
                        nameError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                    }
                )

                // Age Input
                OutlinedTextField(
                    value = age,
                    onValueChange = {
                        if (it.all { char -> char.isDigit() }) age = it
                        ageError = null // Clear error on change
                    },
                    label = { Text("Age") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                        focusedTextColor = MaterialTheme.colorScheme.primary,
                        unfocusedTextColor = MaterialTheme.colorScheme.primary,
                        cursorColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                    ),
                    shape = RoundedCornerShape(30.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                        )
                    },
                    maxLines = 1,
                    singleLine = true,
                    isError = ageError != null,
                    supportingText = {
                        ageError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                    }
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
                        label = { Text("Gender") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = genderExpanded)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                            focusedTextColor = MaterialTheme.colorScheme.primary,
                            unfocusedTextColor = MaterialTheme.colorScheme.primary,
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                        ),
                        shape = RoundedCornerShape(30.dp),
                        isError = genderError != null,
                        supportingText = {
                            genderError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                        }
                    )

                    ExposedDropdownMenu(
                        expanded = genderExpanded,
                        onDismissRequest = { genderExpanded = false },
                        modifier = Modifier.background(MaterialTheme.colorScheme.surface)
                    ) {
                        genderOptions.forEach { option ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        option,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                },
                                onClick = {
                                    gender = option
                                    genderExpanded = false
                                    genderError = null // Clear error on selection
                                }
                            )
                        }
                    }
                }

                // Physical Attributes Section
                SectionHeader(title = "Physical Attributes")

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Height Input
                    OutlinedTextField(
                        value = height,
                        onValueChange = {
                            height = it
                            heightError = null // Clear error on change
                        },
                        label = { Text("Height (cm)") },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                            focusedTextColor = MaterialTheme.colorScheme.primary,
                            unfocusedTextColor = MaterialTheme.colorScheme.primary,
                            cursorColor = MaterialTheme.colorScheme.primary,
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                        ),
                        shape = RoundedCornerShape(30.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        maxLines = 1,
                        singleLine = true,
                        isError = heightError != null,
                        supportingText = {
                            heightError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                        }
                    )

                    // Weight Input
                    OutlinedTextField(
                        value = weight,
                        onValueChange = {
                            weight = it
                            weightError = null // Clear error on change
                        },
                        label = { Text("Weight (kg)") },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                            focusedTextColor = MaterialTheme.colorScheme.primary,
                            unfocusedTextColor = MaterialTheme.colorScheme.primary,
                            cursorColor = MaterialTheme.colorScheme.primary,
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                        ),
                        shape = RoundedCornerShape(30.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        maxLines = 1,
                        singleLine = true,
                        isError = weightError != null,
                        supportingText = {
                            weightError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                        }
                    )
                }

                // Fitness Goals Section
                SectionHeader(title = "Fitness Goals")

                // Fitness Goal Dropdown
                ExposedDropdownMenuBox(
                    expanded = fitnessGoalExpanded,
                    onExpandedChange = { fitnessGoalExpanded = it }
                ) {
                    OutlinedTextField(
                        value = fitnessGoal,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Fitness Goal") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = fitnessGoalExpanded)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                            focusedTextColor = MaterialTheme.colorScheme.primary,
                            unfocusedTextColor = MaterialTheme.colorScheme.primary,
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                        ),
                        shape = RoundedCornerShape(30.dp),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.FitnessCenter,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                            )
                        },
                        isError = fitnessGoalError != null,
                        supportingText = {
                            fitnessGoalError?.let {
                                Text(
                                    it,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    )

                    ExposedDropdownMenu(
                        expanded = fitnessGoalExpanded,
                        onDismissRequest = { fitnessGoalExpanded = false },
                        modifier = Modifier.background(MaterialTheme.colorScheme.surface)
                    ) {
                        fitnessGoalOptions.forEach { option ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        option,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                },
                                onClick = {
                                    fitnessGoal = option
                                    fitnessGoalExpanded = false
                                    fitnessGoalError = null // Clear error on selection
                                }
                            )
                        }
                    }
                }

                // Target Date Input
                OutlinedTextField(
                    value = targetDate,
                    onValueChange = {
                        targetDate = it
                        targetDateError = null // Clear error on change
                    },
                    label = { Text("Target Date (DD/MM/YYYY)") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                        focusedTextColor = MaterialTheme.colorScheme.primary,
                        unfocusedTextColor = MaterialTheme.colorScheme.primary,
                        cursorColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                    ),
                    shape = RoundedCornerShape(30.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Event,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                        )
                    },
                    maxLines = 1,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    isError = targetDateError != null,
                    supportingText = {
                        targetDateError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                    }
                )

                // Activity Level Section
                SectionHeader(title = "Activity Level")

                // Activity Level Dropdown
                ExposedDropdownMenuBox(
                    expanded = activityLevelExpanded,
                    onExpandedChange = { activityLevelExpanded = it }
                ) {
                    OutlinedTextField(
                        value = activityLevel,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Current Activity Level") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = activityLevelExpanded)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                            focusedTextColor = MaterialTheme.colorScheme.primary,
                            unfocusedTextColor = MaterialTheme.colorScheme.primary,
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                        ),
                        shape = RoundedCornerShape(30.dp),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.DirectionsRun,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                            )
                        },
                        isError = activityLevelError != null,
                        supportingText = {
                            activityLevelError?.let {
                                Text(
                                    it,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    )

                    ExposedDropdownMenu(
                        expanded = activityLevelExpanded,
                        onDismissRequest = { activityLevelExpanded = false },
                        modifier = Modifier.background(MaterialTheme.colorScheme.surface)
                    ) {
                        activityLevelOptions.forEach { option ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = option,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                },
                                onClick = {
                                    activityLevel = option
                                    activityLevelExpanded = false
                                    activityLevelError = null // Clear error on selection
                                }
                            )
                        }
                    }
                }

                // Submit Button
                Button(
                    onClick = {
                        // Reset all errors before validation
                        nameError = null
                        ageError = null
                        genderError = null
                        heightError = null
                        weightError = null
                        fitnessGoalError = null
                        targetDateError = null
                        activityLevelError = null

                        val isValid = validateFields(
                            name,
                            age,
                            gender,
                            height,
                            weight,
                            fitnessGoal,
                            targetDate,
                            activityLevel,
                            setNameError = { nameError = it },
                            setAgeError = { ageError = it },
                            setGenderError = { genderError = it },
                            setHeightError = { heightError = it },
                            setWeightError = { weightError = it },
                            setFitnessGoalError = { fitnessGoalError = it },
                            setTargetDateError = { targetDateError = it },
                            setActivityLevelError = { activityLevelError = it }
                        )

                        if (isValid) {
                            val data = QuesDataUserModel(
                                name,
                                age,
                                gender,
                                height,
                                weight,
                                fitnessGoal,
                                targetDate,
                                activityLevel
                            )
                            viewModel.saveTempData(data)
                            Log.d("quesScreen", "QuesScreen: $data")
                            navController.navigate("SignInScreen") {
                                launchSingleTop = true
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.9f),
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    shape = RoundedCornerShape(30.dp)
                ) {
                    Text(
                        text = "Create My Fitness Plan",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.onBackground,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
    )
}

private fun validateFields(
    name: String,
    age: String,
    gender: String,
    height: String,
    weight: String,
    fitnessGoal: String,
    targetDate: String,
    activityLevel: String,
    setNameError: (String?) -> Unit,
    setAgeError: (String?) -> Unit,
    setGenderError: (String?) -> Unit,
    setHeightError: (String?) -> Unit,
    setWeightError: (String?) -> Unit,
    setFitnessGoalError: (String?) -> Unit,
    setTargetDateError: (String?) -> Unit,
    setActivityLevelError: (String?) -> Unit
): Boolean {
    var isValid = true

    // 1. Name validation
    if (name.isBlank()) {
        setNameError("Name cannot be empty.")
        isValid = false
    } else {
        setNameError(null)
    }

    // 2. Age validation
    val ageInt = age.toIntOrNull()
    if (ageInt == null || ageInt <= 0 || ageInt > 120) {
        setAgeError("Please enter a valid age (1-120).")
        isValid = false
    } else {
        setAgeError(null)
    }

    // 3. Gender validation
    if (gender.isBlank()) {
        setGenderError("Please select a gender.")
        isValid = false
    } else {
        setGenderError(null)
    }

    // 4. Height validation
    val heightFloat = height.toFloatOrNull()
    if (heightFloat == null || heightFloat <= 0) {
        setHeightError("Please enter a valid height (e.g., 170.5).")
        isValid = false
    } else {
        setHeightError(null)
    }

    // 5. Weight validation
    val weightFloat = weight.toFloatOrNull()
    if (weightFloat == null || weightFloat <= 0) {
        setWeightError("Please enter a valid weight (e.g., 70.2).")
        isValid = false
    } else {
        setWeightError(null)
    }

    // 6. Fitness Goal validation
    if (fitnessGoal.isBlank()) {
        setFitnessGoalError("Please select a fitness goal.")
        isValid = false
    } else {
        setFitnessGoalError(null)
    }

    // 7. Target Date validation (DD/MM/YYYY)
    if (targetDate.isBlank()) {
        setTargetDateError("Please enter a target date (DD/MM/YYYY).")
        isValid = false
    } else {
        try {
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val parsedDate = LocalDate.parse(targetDate, formatter)
            if (parsedDate.isBefore(LocalDate.now())) {
                setTargetDateError("Target date cannot be in the past.")
                isValid = false
            } else {
                setTargetDateError(null)
            }
        } catch (e: DateTimeParseException) {
            setTargetDateError("Invalid date format. Use DD/MM/YYYY.")
            isValid = false
        }
    }

    // 8. Activity Level validation
    if (activityLevel.isBlank()) {
        setActivityLevelError("Please select an activity level.")
        isValid = false
    } else {
        setActivityLevelError(null)
    }

    return isValid
}