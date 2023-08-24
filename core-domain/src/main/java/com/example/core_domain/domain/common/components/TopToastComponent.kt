package com.example.core_domain.domain.common.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun SlideAnimationDemo(hideAfterAnimation: () -> Unit) {
    var isVisible by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (isVisible) {
            SlideUpBox(
                text = "Hello, this is a sliding message box!",
                color = Color.Blue,
            )

            // Automatically slide back up after 2 seconds
            LaunchedEffect(isVisible) {
                delay(2000L)
                isVisible = false
                hideAfterAnimation
            }
        }
    }
}

@Composable
fun SlideUpBox(
    text: String,
    color: Color,
) {
    val slideOffset by animateDpAsState(
        targetValue = 100.dp,
        animationSpec = tween(durationMillis = 1000),
        label = "",
    )

    Column(
        modifier = Modifier
            .offset(y = slideOffset)
            .fillMaxWidth()
            .padding(16.dp)
            .background(color = color, shape = RoundedCornerShape(16.dp))
            .padding(16.dp)
            .animateContentSize(),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 16.sp,
            lineHeight = 24.sp,
        )
    }
}
