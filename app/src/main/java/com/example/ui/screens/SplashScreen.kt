package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import kotlinx.coroutines.delay

@Composable
fun BerserkSplashScreen(
    onSplashFinished: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Automatically transition to the dashboard after 2.8 seconds
    LaunchedEffect(Unit) {
        delay(2800)
        onSplashFinished()
    }

    val infiniteTransition = rememberInfiniteTransition(label = "splash_spin")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing)
        ),
        label = "rotation"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFCFCFC))
            .clickable { onSplashFinished() } // Tap to skip
            .testTag("splash_screen_root")
    ) {
        // Full splash screen artwork containing authentic sumi-e ink corners and Guts framed portrait
        Image(
            painter = painterResource(id = R.drawable.berserk_splash_screen_full),
            contentDescription = "Berserk NextGen Splash",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        // Overlay Content Stack: Typography & Dynamic Animated Spinner
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .statusBarsPadding()
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Space pushing down past Guts framed artwork and Japanese ベルセルク title
            Spacer(modifier = Modifier.weight(1.35f))

            // "Device info" Header
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Device ",
                    style = MaterialTheme.typography.displayMedium.copy(
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF0F0F0F),
                        fontSize = 38.sp,
                        letterSpacing = (-0.5).sp
                    )
                )
                Text(
                    text = "info",
                    style = MaterialTheme.typography.displayMedium.copy(
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF666666),
                        fontSize = 38.sp,
                        letterSpacing = (-0.5).sp
                    )
                )
            }

            // "N e x t G e n"
            Text(
                text = "N  e  x  t  G  e  n",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1E1E1E),
                    fontSize = 18.sp,
                    letterSpacing = 2.sp
                ),
                modifier = Modifier.padding(top = 4.dp)
            )

            // Divider Dash
            Box(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .width(32.dp)
                    .height(3.5.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Color(0xFF222222))
            )

            // Tagline: "Know Your Device. Deeper Than Ever."
            Text(
                text = "Know  Your  Device.\nDeeper  Than  Ever.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF4B4B4B),
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    textAlign = TextAlign.Center
                )
            )

            Spacer(modifier = Modifier.weight(0.55f))

            // Circular Spinner Ring matching user's image exactly
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .rotate(rotation),
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
                    val strokeW = 4.dp.toPx()
                    drawArc(
                        brush = Brush.sweepGradient(
                            listOf(
                                Color.Transparent,
                                Color(0x33000000),
                                Color(0xFF1A1A1A)
                            )
                        ),
                        startAngle = 0f,
                        sweepAngle = 290f,
                        useCenter = false,
                        style = Stroke(width = strokeW, cap = StrokeCap.Round)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Loading...",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF757575),
                    fontSize = 13.sp,
                    letterSpacing = 1.sp
                )
            )

            Spacer(modifier = Modifier.height(36.dp))
        }
    }
}
