package com.shinkatech.calortracker.View.onBoardScreens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OnBoardScreen2() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Nutritional Breakdown",
            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 28.sp),
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "See a clear breakdown of your food's protein, carbs, fats, and more.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White.copy(alpha = 0.8f),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Simple representation of a chart (you'd replace this with a real charting library)
        ChartPlaceholder()

        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Detailed insights for better health.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White.copy(alpha = 0.7f),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun ChartPlaceholder() {
    val data = mapOf(
        "Protein" to 0.3f,
        "Carbs" to 0.4f,
        "Fats" to 0.2f,
        "Fiber" to 0.1f
    )
    val colors = listOf(Color(0xFF4CAF50), Color(0xFF2196F3), Color(0xFFFFC107), Color(0xFF9C27B0))

    Canvas(modifier = Modifier
        .size(200.dp)
        .padding(16.dp)) {
        val radius = size.minDimension / 2
        val center = Offset(size.width / 2, size.height / 2)
        var startAngle = -90f // Start from top

        data.forEach { (label, fraction) ->
            val sweepAngle = fraction * 360f
            val color = colors[data.keys.indexOf(label) % colors.size]

            drawArc(
                color = color,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = true,
                topLeft = Offset(center.x - radius, center.y - radius),
                size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2)
            )
            startAngle += sweepAngle
        }

        // Optional: Draw a white circle in the middle for a "donut" effect
        drawCircle(color = Color.White.copy(alpha = 0.2f), radius = radius * 0.6f, center = center)
    }

    Spacer(modifier = Modifier.height(16.dp))
    Column(horizontalAlignment = Alignment.Start) {
        data.keys.forEachIndexed { index, label ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier
                    .size(10.dp)
                    .background(colors[index % colors.size], shape = CircleShape))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = label, color = Color.White.copy(alpha = 0.9f))
            }
        }
    }
}