package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import com.example.R
import kotlinx.coroutines.delay

@Composable
fun BerserkSplashScreen(
    onSplashFinished: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Automatically transition to the dashboard after 2.6 seconds
    LaunchedEffect(Unit) {
        delay(2600)
        onSplashFinished()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .clickable { onSplashFinished() } // Tap to skip immediately
            .testTag("splash_screen_root"),
        contentAlignment = Alignment.Center
    ) {
        // EXACT splash screen image created by the user (downloaded directly from Google Drive)
        Image(
            painter = painterResource(id = R.drawable.berserk_splash_screen_full),
            contentDescription = "Berserk NextGen Splash",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}
