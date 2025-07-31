package com.shinkatech.calortracker.View.authScreen.loginScreen

import android.widget.Toast
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.LocalDining
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.shinkatech.calortracker.Screen
import com.shinkatech.calortracker.ui.theme.CalorTrackerTheme
import kotlin.math.cos
import kotlin.math.sin


@Composable
fun LoginScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var confirmPasswordVisibility by remember { mutableStateOf(false) }

    // error message
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current.applicationContext

    val viewModel : LoginScreenViewModel = hiltViewModel()

    var passwordIcon = if (passwordVisibility) {
        Icons.Default.Visibility
    } else {
        Icons.Default.VisibilityOff
    }


    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        CalorTrackerTheme {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                // Enhanced header with animated icons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(MaterialTheme.colorScheme.background),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    // Animated icon group
                    Box(
                        modifier = Modifier.size(100.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        val infiniteTransition = rememberInfiniteTransition()

                        // Pulsing background with color change
                        val pulseScale by infiniteTransition.animateFloat(
                            initialValue = 0.8f,
                            targetValue = 1.2f,
                            animationSpec = infiniteRepeatable(
                                animation = tween(3000, easing = FastOutSlowInEasing),
                                repeatMode = RepeatMode.Reverse
                            )
                        )

                        val pulseAlpha by infiniteTransition.animateFloat(
                            initialValue = 0.1f,
                            targetValue = 0.3f,
                            animationSpec = infiniteRepeatable(
                                animation = tween(3000, easing = FastOutSlowInEasing),
                                repeatMode = RepeatMode.Reverse
                            )
                        )

                        // Multiple background circles for depth
                        repeat(3) { index ->
                            Box(
                                modifier = Modifier
                                    .size((60 + index * 15).dp)
                                    .scale(pulseScale - (index * 0.1f))
                                    .background(
                                        MaterialTheme.colorScheme.primary.copy(alpha = pulseAlpha / (index + 1)),
                                        CircleShape
                                    )
                            )
                        }

                        // Main icon with rotation
                        val mainIconRotation by infiniteTransition.animateFloat(
                            initialValue = 0f,
                            targetValue = 360f,
                            animationSpec = infiniteRepeatable(
                                animation = tween(8000, easing = LinearEasing)
                            )
                        )

                        Icon(
                            imageVector = Icons.Default.ElectricBolt,
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                                .rotate(mainIconRotation),
                            tint = MaterialTheme.colorScheme.primary
                        )

                        // Floating icons with wave motion
                        val waveTime by infiniteTransition.animateFloat(
                            initialValue = 0f,
                            targetValue = 2f * Math.PI.toFloat(),
                            animationSpec = infiniteRepeatable(
                                animation = tween(4000, easing = LinearEasing)
                            )
                        )

                        listOf(
                            Icons.Default.LocalDining to 0f,
                            Icons.Default.FitnessCenter to 2f,
                            Icons.Default.Timeline to 4f
                        ).forEachIndexed { index, (icon, phaseOffset) ->
                            val waveX = 30f * cos(waveTime + phaseOffset)
                            val waveY = 15f * sin(waveTime * 2 + phaseOffset)

                            val iconScale by infiniteTransition.animateFloat(
                                initialValue = 0.8f,
                                targetValue = 1.2f,
                                animationSpec = infiniteRepeatable(
                                    animation = tween(2000 + index * 500, easing = FastOutSlowInEasing),
                                    repeatMode = RepeatMode.Reverse
                                )
                            )

                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                modifier = Modifier
                                    .offset(waveX.dp, waveY.dp)
                                    .size(16.dp)
                                    .scale(iconScale),
                                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(24.dp))
                    // row text
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "CalorTracker",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Track • Analyze • Achieve",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                Card(
                    modifier = Modifier
                        .fillMaxSize(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.surface)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Log In",
                                    style = MaterialTheme.typography.headlineMedium.copy(
                                        fontWeight = FontWeight.Bold
                                    ),
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Text(
                                    text = "Get ready to track your calories",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                                )

                                Spacer(modifier = Modifier.height(26.dp))
                                // email
                                OutlinedTextField(
                                    value = email,
                                    onValueChange = { email = it },
                                    label = { Text(text = "Email") },
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    shape = RoundedCornerShape(30.dp),
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.Default.Email,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                                        )
                                    },
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                                        unfocusedBorderColor = MaterialTheme.colorScheme.onBackground.copy(
                                            alpha = 0.5f
                                        ),
                                        focusedTextColor = MaterialTheme.colorScheme.primary,
                                        unfocusedTextColor = MaterialTheme.colorScheme.primary,
                                        cursorColor = MaterialTheme.colorScheme.primary,
                                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                                        unfocusedLabelColor = MaterialTheme.colorScheme.onBackground.copy(
                                            alpha = 0.5f
                                        ),
                                    ),
                                    maxLines = 1,
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Email,
                                        imeAction = ImeAction.Next
                                    ),
                                    isError = emailError != null,
                                    supportingText = {
                                        emailError?.let {
                                            Text(text = it , color = MaterialTheme.colorScheme.error)
                                        }
                                    }
                                )

                                Spacer(modifier = Modifier.height(8.dp))
                                // password
                                OutlinedTextField(
                                    value = password,
                                    onValueChange = { password = it },
                                    label = { Text(text = "password") },
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    shape = RoundedCornerShape(30.dp),
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.Default.Lock,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                                        )
                                    },
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                                        unfocusedBorderColor = MaterialTheme.colorScheme.onBackground.copy(
                                            alpha = 0.5f
                                        ),
                                        focusedTextColor = MaterialTheme.colorScheme.primary,
                                        unfocusedTextColor = MaterialTheme.colorScheme.primary,
                                        cursorColor = MaterialTheme.colorScheme.primary,
                                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                                        unfocusedLabelColor = MaterialTheme.colorScheme.onBackground.copy(
                                            alpha = 0.5f
                                        ),
                                    ),
                                    maxLines = 1,
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Password,
                                        imeAction = ImeAction.Next
                                    ),
                                    trailingIcon = {
                                        IconButton(onClick = {
                                            passwordVisibility = !passwordVisibility
                                        }) {
                                            Icon(
                                                imageVector = passwordIcon,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                                            )
                                        }
                                    },
                                    visualTransformation = if (passwordVisibility) VisualTransformation.None
                                    else PasswordVisualTransformation(),
                                    isError = passwordError != null,
                                    supportingText = {
                                        passwordError?.let {
                                            Text(text = it , color = MaterialTheme.colorScheme.error)
                                        }
                                    }
                                )
                                Spacer(modifier = Modifier.height(8.dp))

                                Spacer(modifier = Modifier.height(22.dp))

                                Button(
                                    onClick = {
                                        val isValid = viewModel.validateLoginField(
                                            email = email,
                                            password = password,
                                            setEmailError = { emailError = it },
                                            setPasswordError = { passwordError = it }
                                        )

                                        if (isValid){

                                            viewModel.loginWithemailAndPassword(
                                                email,
                                                password,
                                                onSucess = {
                                                    Toast.makeText(context, "login in sucessfull", Toast.LENGTH_SHORT).show()
                                                    navController.navigate(Screen.MAIN_LAYOUT){
                                                        popUpTo(Screen.LOGIN_SCREEN){
                                                            inclusive = true
                                                        }
                                                    }
                                                },
                                                onfailure = {
                                                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                                                }
                                            )
                                        }

                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.primary.copy(
                                            alpha = 0.9f
                                        ),
                                        contentColor = MaterialTheme.colorScheme.onPrimary
                                    ),
                                    shape = RoundedCornerShape(30.dp)
                                ) {
                                    Text(
                                        text = "Login",
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onPrimary
                                        )
                                    )
                                }
                                // divider
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 8.dp, vertical = 12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    HorizontalDivider(
                                        color = Color.Gray,
                                        thickness = 1.dp,
                                        modifier = Modifier.weight(0.4f)
                                    )
                                    Text(
                                        text = "Or",
                                        color = Color.Gray,
                                        modifier = Modifier
                                            .padding(horizontal = 8.dp)
                                            .weight(0.1f),
                                        style = MaterialTheme.typography.bodyMedium,
                                        textAlign = TextAlign.Center
                                    )
                                    HorizontalDivider(
                                        color = Color.Gray,
                                        thickness = 1.dp,
                                        modifier = Modifier.weight(0.4f)
                                    )
                                }
                                // Google sign in
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                ) {
                                    // Google Button
                                    Button(
                                        onClick = {},
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(50.dp)
                                            .weight(0.5f),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                                        ),
                                        shape = RoundedCornerShape(30.dp)
                                    ) {
                                        Text(
                                            text = "Google",
                                            style = MaterialTheme.typography.bodyLarge.copy(
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(16.dp))

                                    // Facebook Button
                                    Button(
                                        onClick = {},
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(50.dp)
                                            .weight(0.5f),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                                        ),
                                        shape = RoundedCornerShape(30.dp)
                                    ) {
                                        Text(
                                            text = "Facebook",
                                            style = MaterialTheme.typography.bodyLarge.copy(
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                // redirect to login screen
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "Don't have account?",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = MaterialTheme.colorScheme.onBackground
                                        ),
                                        textAlign = TextAlign.Center
                                    )
                                    TextButton(
                                        onClick = {
                                            navController.navigate(Screen.SIGN_IN_SCREEN)
                                        },
                                        modifier = Modifier,
                                        colors = ButtonDefaults.textButtonColors(
                                            contentColor = MaterialTheme.colorScheme.primary
                                        )
                                    ) {
                                        Text("SignIn")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}