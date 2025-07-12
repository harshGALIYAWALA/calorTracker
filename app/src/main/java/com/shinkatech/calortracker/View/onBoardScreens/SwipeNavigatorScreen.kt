package com.shinkatech.calortracker.View.onBoardScreens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlin.math.abs

@Composable
fun SwipeNavigatorScreen(
    navController: NavController,
    screens: List<@Composable () -> Unit>,
    screenColors: List<Color>,
    viewModel: OnBoardViewModel
) {
    val currentIndex by viewModel.currentIndex.collectAsState()
    var offsetX by remember { mutableFloatStateOf(0f) }
    var isDragging by remember { mutableStateOf(false) }

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val swipeThreshold = screenWidth * 0.5f

    val backgroundColor by animateColorAsState(
        targetValue = screenColors.getOrElse(currentIndex) { Color.Gray },
        animationSpec = tween(300),
        label = "BackgroundColorTransition"
    )

    val animatedOffsetX by animateFloatAsState(
        targetValue = if (isDragging) offsetX else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "OffsetAnimation"
    )

    // Get density here, within the Composable scope
    val density = LocalDensity.current.density

    // Calculate thresholdPx here, within the Composable scope
    val thresholdPx = with(LocalDensity.current) { swipeThreshold.toPx() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .pointerInput(currentIndex) {
                detectHorizontalDragGestures(
                    onDragStart = { isDragging = true },
                    onHorizontalDrag = { _, dragAmount ->
                        val newOffset = offsetX + dragAmount
                        offsetX = when {
                            currentIndex == 0 && newOffset > 0 -> 0f
                            currentIndex == screens.lastIndex && newOffset < 0 -> 0f
                            else -> newOffset
                        }
                    },
                    onDragEnd = {
                        isDragging = false
                        // Use the pre-calculated thresholdPx here
                        when {
                            offsetX < -thresholdPx && currentIndex < screens.lastIndex -> {
                                viewModel.next()
                                offsetX = 0f
                            }
                            offsetX > thresholdPx && currentIndex > 0 -> {
                                viewModel.previous()
                                offsetX = 0f
                            }
                            else -> {
                                offsetX = 0f
                            }
                        }
                    }
                )
            }
    ) {
        // Screen Content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    translationX = animatedOffsetX
                    rotationY = animatedOffsetX * 0.05f
                    val dragProgress = abs(animatedOffsetX) / 500f
                    scaleX = lerp(1f, 0.95f, dragProgress.coerceIn(0f, 1f))
                    scaleY = lerp(1f, 0.95f, dragProgress.coerceIn(0f, 1f))
                    alpha = lerp(1f, 0.8f, dragProgress.coerceIn(0f, 1f))
                    // Use the captured density here
                    cameraDistance = 8 * density
                }
        ) {
            screens.getOrNull(currentIndex)?.invoke()
        }

//        // Page indicators
//        Row(
//            modifier = Modifier
//                .align(Alignment.BottomCenter)
//                .padding(24.dp),
//            horizontalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            screens.forEachIndexed { index, _ ->
//                val isSelected = index == currentIndex
//                val indicatorSize by animateDpAsState(
//                    targetValue = if (isSelected) 12.dp else 8.dp,
//                    label = "IndicatorSize"
//                )
//                val indicatorColor by animateColorAsState(
//                    targetValue = if (isSelected) Color.White else Color.White.copy(alpha = 0.5f),
//                    label = "IndicatorColor"
//                )
//
//                Box(
//                    modifier = Modifier
//                        .size(indicatorSize)
//                        .background(indicatorColor, shape = CircleShape)
//                )
//            }
//        }
    }
}

private fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return start + fraction * (stop - start)
}