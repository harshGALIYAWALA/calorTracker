package com.shinkatech.calortracker.View.onBoardScreens

import androidx.compose.animation.animateColorAsState

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.runtime.mutableFloatStateOf
import kotlin.math.abs

@Composable
fun SwipeNavigatorScreen(
    navController: NavController,
    screens: List<@Composable () -> Unit>,
    screenColors: List<Color>,
    startIndex: Int = 0
) {
    var currentIndex by remember { mutableIntStateOf(startIndex) }
    var offsetX by remember { mutableFloatStateOf(0f) }
    var isDragging by remember { mutableStateOf(false) }

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val swipeThreshold = screenWidth * 0.5f // 50% of screen width

    // Animate background color transition
    val backgroundColor by animateColorAsState(
        targetValue = screenColors.getOrElse(currentIndex) { Color.Gray },
        animationSpec = tween(300),
        label = "BackgroundColorTransition"
    )

    // Animate offset back to 0 when not dragging
    val animatedOffsetX by animateFloatAsState(
        targetValue = if (isDragging) offsetX else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "OffsetAnimation"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .pointerInput(currentIndex) {
                detectHorizontalDragGestures(
                    onDragStart = {
                        isDragging = true
                    },
                    onHorizontalDrag = { _, dragAmount ->
                        val newOffset = offsetX + dragAmount

                        // Prevent swiping beyond boundaries
                        offsetX = when {
                            currentIndex == 0 && newOffset > 0 -> 0f // Can't swipe right from first screen
                            currentIndex == screens.lastIndex && newOffset < 0 -> 0f // Can't swipe left from last screen
                            else -> newOffset
                        }
                    },
                    onDragEnd = {
                        isDragging = false

                        val thresholdPx = with(density) { swipeThreshold.toPx() }

                        when {
                            // Swipe left (next screen)
                            offsetX < -thresholdPx && currentIndex < screens.lastIndex -> {
                                currentIndex++
                                offsetX = 0f
                            }
                            // Swipe right (previous screen)
                            offsetX > thresholdPx && currentIndex > 0 -> {
                                currentIndex--
                                offsetX = 0f
                            }
                            // Bounce back
                            else -> {
                                offsetX = 0f
                            }
                        }
                    }
                )
            }
    ) {
        // Current screen with card-like animations
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    // Translate with finger drag
                    translationX = animatedOffsetX

                    // 3D rotation effect (Y-axis tilt)
                    rotationY = animatedOffsetX * 0.05f

                    // Slight scale and opacity changes while dragging
                    val dragProgress = abs(animatedOffsetX) / 500f
                    scaleX = lerp(1f, 0.95f, dragProgress.coerceIn(0f, 1f))
                    scaleY = lerp(1f, 0.95f, dragProgress.coerceIn(0f, 1f))
                    alpha = lerp(1f, 0.8f, dragProgress.coerceIn(0f, 1f))

                    // Add some depth with camera distance
                    cameraDistance = 8 * density
                }
        ) {
            screens.getOrNull(currentIndex)?.invoke()
        }

        // Page indicators
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(24.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            screens.forEachIndexed { index, _ ->
                val isSelected = index == currentIndex
                val indicatorSize by animateDpAsState(
                    targetValue = if (isSelected) 12.dp else 8.dp,
                    label = "IndicatorSize"
                )
                val indicatorColor by animateColorAsState(
                    targetValue = if (isSelected) Color.White else Color.White.copy(alpha = 0.5f),
                    label = "IndicatorColor"
                )

                Box(
                    modifier = Modifier
                        .size(indicatorSize)
                        .background(
                            color = indicatorColor,
                            shape = CircleShape
                        )
                )
            }
        }
    }
}

@Composable
fun OnboardingScreens(navController: NavController) {
    val screens = listOf<@Composable () -> Unit>(
        {
            OnboardingScreen(
                title = "Welcome to FitTracker",
                subtitle = "Your personal health companion",
                icon = "🏃‍♂️"
            )
        },
        {
            OnboardingScreen(
                title = "Track Your Calories",
                subtitle = "Monitor your daily intake effortlessly",
                icon = "🥗"
            )
        },
        {
            OnboardingScreen(
                title = "Stay Healthy",
                subtitle = "Build lasting healthy habits",
                icon = "💪"
            )
        },
        {
            OnboardingScreen(
                title = "Let's Get Started!",
                subtitle = "Begin your fitness journey today",
                icon = "🎯"
            )
        }
    )

    val colors = listOf(
        Color(0xFF667eea), // Purple gradient
        Color(0xFF4facfe), // Blue gradient
        Color(0xFF43e97b), // Green gradient
        Color(0xFFfa709a)  // Pink gradient
    )

    SwipeNavigatorScreen(
        navController = navController,
        screens = screens,
        screenColors = colors
    )
}

@Composable
private fun OnboardingScreen(
    title: String,
    subtitle: String,
    icon: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Icon
        Text(
            text = icon,
            fontSize = 80.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Title
        Text(
            text = title,
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Subtitle
        Text(
            text = subtitle,
            color = Color.White.copy(alpha = 0.8f),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            lineHeight = 24.sp
        )
    }
}

// Helper function for linear interpolation
private fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return start + fraction * (stop - start)
}

