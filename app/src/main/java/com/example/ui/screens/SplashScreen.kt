package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
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
import com.example.ui.theme.CinzelFontFamily
import com.example.ui.theme.RajdhaniFontFamily
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
            .background(Color.White)
            .clickable { onSplashFinished() } // Tap to skip
            .testTag("splash_screen_root")
    ) {
        // Top-left and bottom-right decorative ink splatters
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            // Top-left sumi-e ink streaks
            drawCircle(Color.Black, radius = 22.dp.toPx(), center = Offset(10.dp.toPx(), 20.dp.toPx()))
            drawCircle(Color.Black, radius = 14.dp.toPx(), center = Offset(35.dp.toPx(), 45.dp.toPx()))
            drawCircle(Color.Black, radius = 8.dp.toPx(), center = Offset(60.dp.toPx(), 30.dp.toPx()))
            drawCircle(Color.Black, radius = 5.dp.toPx(), center = Offset(80.dp.toPx(), 15.dp.toPx()))
            drawLine(
                Color.Black,
                start = Offset(-20f, -20f),
                end = Offset(110.dp.toPx(), 80.dp.toPx()),
                strokeWidth = 18.dp.toPx(),
                cap = StrokeCap.Round
            )
            drawLine(
                Color.Black,
                start = Offset(-10f, 60.dp.toPx()),
                end = Offset(70.dp.toPx(), 120.dp.toPx()),
                strokeWidth = 6.dp.toPx(),
                cap = StrokeCap.Round
            )

            // Bottom-right sumi-e ink streaks
            drawCircle(Color.Black, radius = 24.dp.toPx(), center = Offset(w - 15.dp.toPx(), h - 25.dp.toPx()))
            drawCircle(Color.Black, radius = 16.dp.toPx(), center = Offset(w - 40.dp.toPx(), h - 50.dp.toPx()))
            drawCircle(Color.Black, radius = 9.dp.toPx(), center = Offset(w - 70.dp.toPx(), h - 35.dp.toPx()))
            drawLine(
                Color.Black,
                start = Offset(w + 20f, h + 20f),
                end = Offset(w - 120.dp.toPx(), h - 90.dp.toPx()),
                strokeWidth = 20.dp.toPx(),
                cap = StrokeCap.Round
            )
            drawLine(
                Color.Black,
                start = Offset(w - 40.dp.toPx(), h + 10f),
                end = Offset(w - 100.dp.toPx(), h - 140.dp.toPx()),
                strokeWidth = 8.dp.toPx(),
                cap = StrokeCap.Round
            )
        }

        // Center Content Stack matching user's splash image exactly
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .statusBarsPadding()
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(0.7f))

            // Framed Guts Smiling Manga Panel
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .aspectRatio(1.15f)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.berserk_splash_art),
                    contentDescription = "Berserk Guts Splash",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // "Device info" Header
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Device ",
                    style = MaterialTheme.typography.displayMedium.copy(
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF111111),
                        fontSize = 34.sp,
                        letterSpacing = (-0.5).sp
                    )
                )
                Text(
                    text = "info",
                    style = MaterialTheme.typography.displayMedium.copy(
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF616161),
                        fontSize = 34.sp,
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
                    fontSize = 17.sp,
                    letterSpacing = 2.sp
                ),
                modifier = Modifier.padding(top = 4.dp)
            )

            // Divider Dash
            Box(
                modifier = Modifier
                    .padding(vertical = 14.dp)
                    .width(28.dp)
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
                    color = Color(0xFF555555),
                    fontSize = 13.sp,
                    lineHeight = 19.sp,
                    textAlign = TextAlign.Center
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            // Circular Spinner Ring matching user's image
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .rotate(rotation),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val strokeW = 3.5.dp.toPx()
                    drawArc(
                        brush = Brush.sweepGradient(
                            listOf(
                                Color.Transparent,
                                Color(0x33000000),
                                Color(0xFF1A1A1A)
                            )
                        ),
                        startAngle = 0f,
                        sweepAngle = 280f,
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
                    fontSize = 12.sp,
                    letterSpacing = 1.sp
                )
            )

            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}
