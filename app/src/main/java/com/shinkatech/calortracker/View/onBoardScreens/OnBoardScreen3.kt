package com.shinkatech.calortracker.View.onBoardScreens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.shinkatech.calortracker.R
import com.shinkatech.calortracker.Screen
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun OnBoardScreen3() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background gradient overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF8B5CF6).copy(alpha = 0.3f),
                            Color.Transparent
                        ),
                        radius = 800f
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            // Hero Section with Card
            Card(
                modifier = Modifier
                    .size(280.dp)
                    .clip(RoundedCornerShape(24.dp)),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.1f)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    // Decorative neural network pattern
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val centerX = size.width * 0.5f
                        val centerY = size.height * 0.5f

                        // Draw connecting lines
                        drawLine(
                            color = Color(0xFF8B5CF6).copy(alpha = 0.3f),
                            start = Offset(centerX - 60, centerY - 40),
                            end = Offset(centerX, centerY),
                            strokeWidth = 2.dp.toPx()
                        )
                        drawLine(
                            color = Color(0xFF8B5CF6).copy(alpha = 0.3f),
                            start = Offset(centerX + 60, centerY - 40),
                            end = Offset(centerX, centerY),
                            strokeWidth = 2.dp.toPx()
                        )
                        drawLine(
                            color = Color(0xFF8B5CF6).copy(alpha = 0.3f),
                            start = Offset(centerX - 60, centerY + 40),
                            end = Offset(centerX, centerY),
                            strokeWidth = 2.dp.toPx()
                        )
                        drawLine(
                            color = Color(0xFF8B5CF6).copy(alpha = 0.3f),
                            start = Offset(centerX + 60, centerY + 40),
                            end = Offset(centerX, centerY),
                            strokeWidth = 2.dp.toPx()
                        )

                        // Draw nodes
                        drawCircle(
                            color = Color(0xFF8B5CF6).copy(alpha = 0.4f),
                            radius = 8.dp.toPx(),
                            center = Offset(centerX - 60, centerY - 40)
                        )
                        drawCircle(
                            color = Color(0xFF8B5CF6).copy(alpha = 0.4f),
                            radius = 8.dp.toPx(),
                            center = Offset(centerX + 60, centerY - 40)
                        )
                        drawCircle(
                            color = Color(0xFF8B5CF6).copy(alpha = 0.4f),
                            radius = 8.dp.toPx(),
                            center = Offset(centerX - 60, centerY + 40)
                        )
                        drawCircle(
                            color = Color(0xFF8B5CF6).copy(alpha = 0.4f),
                            radius = 8.dp.toPx(),
                            center = Offset(centerX + 60, centerY + 40)
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Main AI Icon with background
                        Box(
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.linearGradient(
                                        colors = listOf(
                                            Color(0xFF8B5CF6),
                                            Color(0xFF7C3AED)
                                        )
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Psychology,
                                contentDescription = "AI Recommendations",
                                modifier = Modifier.size(60.dp),
                                tint = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Smart indicator dots
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            repeat(3) { index ->
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(
                                            Color(0xFF8B5CF6).copy(
                                                alpha = if (index == 1) 1f else 0.5f
                                            )
                                        )
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Text Content
            Text(
                text = "Smart AI Recommendations",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Personalized meal suggestions",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                ),
                color = Color(0xFF8B5CF6),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Get intelligent nutritional advice tailored to your goals, dietary preferences, and lifestyle",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp),
                color = Color.White.copy(alpha = 0.8f),
                textAlign = TextAlign.Center,
                lineHeight = 24.sp
            )

            Spacer(modifier = Modifier.height(32.dp))


        }
    }
}
