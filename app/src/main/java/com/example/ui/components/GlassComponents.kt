package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import kotlinx.coroutines.launch

// Fluid iOS/iPhone-style spring bounce on touch
fun Modifier.iphoneBounce(onClick: (() -> Unit)? = null): Modifier = this.then(
    Modifier.pointerInput(onClick) {
        if (onClick == null) return@pointerInput
    }
)

@Composable
fun LiquidGlassCard(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 22.dp,
    borderColor: Color = BerserkGlassBorder,
    borderWidth: Dp = 1.dp,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val scaleAnim = remember { Animatable(1f) }

    // Fluid liquid light shimmer across the glass border
    val infiniteTransition = rememberInfiniteTransition(label = "glass_shimmer")
    val shimmerOffset by infiniteTransition.animateFloat(
        initialValue = -100f,
        targetValue = 900f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmerOffset"
    )

    val shimmerAlpha by infiniteTransition.animateFloat(
        initialValue = 0.35f,
        targetValue = 0.70f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    Box(
        modifier = modifier
            .scale(scaleAnim.value)
            .clip(RoundedCornerShape(cornerRadius))
            .border(
                width = borderWidth,
                brush = Brush.linearGradient(
                    colors = listOf(
                        borderColor.copy(alpha = shimmerAlpha),
                        BerserkGlassHighlight.copy(alpha = 0.25f),
                        borderColor.copy(alpha = shimmerAlpha * 0.8f),
                        BerserkGlassBorderDim
                    ),
                    start = Offset(shimmerOffset * 0.4f, shimmerOffset * 0.4f),
                    end = Offset(shimmerOffset + 400f, shimmerOffset + 400f)
                ),
                shape = RoundedCornerShape(cornerRadius)
            )
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0x3E23141F), // Ultra-frosted liquid dark glass
                        Color(0x28120B13),
                        Color(0x3B190C15)
                    )
                )
            )
            .then(
                if (onClick != null) {
                    Modifier.pointerInput(Unit) {
                        awaitEachGesture {
                            awaitFirstDown()
                            coroutineScope.launch {
                                scaleAnim.animateTo(
                                    targetValue = 0.96f,
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessLow
                                    )
                                )
                            }
                            val up = waitForUpOrCancellation()
                            coroutineScope.launch {
                                scaleAnim.animateTo(
                                    targetValue = 1f,
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessMedium
                                    )
                                )
                            }
                            if (up != null) {
                                onClick()
                            }
                        }
                    }
                } else Modifier
            )
    ) {
        // Specular top highlight gleam (iPhone liquid glass reflection)
        Canvas(modifier = Modifier.fillMaxWidth().height(2.5.dp)) {
            drawLine(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color.Transparent,
                        BerserkGlassHighlight.copy(alpha = 0.45f),
                        BerserkCrimsonGlow.copy(alpha = 0.4f),
                        Color.Transparent
                    )
                ),
                start = Offset.Zero,
                end = Offset(size.width, 0f),
                strokeWidth = 2.5f
            )
        }

        Column(
            modifier = Modifier.padding(16.dp),
            content = content
        )
    }
}

@Composable
fun BrandOfSacrificeCanvas(
    modifier: Modifier = Modifier,
    glowColor: Color = BerserkBloodRed
) {
    val infiniteTransition = rememberInfiniteTransition(label = "brand_pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.55f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val cx = w / 2f
        val cy = h / 2f

        // Outer glow circle
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    glowColor.copy(alpha = 0.38f * pulseAlpha),
                    glowColor.copy(alpha = 0.08f * pulseAlpha),
                    Color.Transparent
                ),
                center = Offset(cx, cy),
                radius = w * 0.48f
            )
        )

        // Outer thin ring
        drawCircle(
            color = glowColor.copy(alpha = 0.4f * pulseAlpha),
            radius = w * 0.42f,
            style = Stroke(width = 2.dp.toPx())
        )

        val strokeWidth = 3.2.dp.toPx()
        val cap = StrokeCap.Round

        // Central vertical blade/spine
        drawLine(
            color = glowColor.copy(alpha = pulseAlpha),
            start = Offset(cx, h * 0.16f),
            end = Offset(cx, h * 0.86f),
            strokeWidth = strokeWidth * 1.2f,
            cap = cap
        )

        // Top V horns
        drawLine(
            color = glowColor.copy(alpha = pulseAlpha),
            start = Offset(w * 0.30f, h * 0.28f),
            end = Offset(cx, h * 0.44f),
            strokeWidth = strokeWidth,
            cap = cap
        )
        drawLine(
            color = glowColor.copy(alpha = pulseAlpha),
            start = Offset(w * 0.70f, h * 0.28f),
            end = Offset(cx, h * 0.44f),
            strokeWidth = strokeWidth,
            cap = cap
        )

        // Mid diamond intersection
        val midY1 = h * 0.36f
        val midY2 = h * 0.62f
        val midX1 = w * 0.28f
        val midX2 = w * 0.72f

        drawLine(color = BerserkEclipseSun.copy(alpha = pulseAlpha), start = Offset(midX1, cy), end = Offset(cx, midY1), strokeWidth = strokeWidth, cap = cap)
        drawLine(color = BerserkEclipseSun.copy(alpha = pulseAlpha), start = Offset(cx, midY1), end = Offset(midX2, cy), strokeWidth = strokeWidth, cap = cap)
        drawLine(color = BerserkEclipseSun.copy(alpha = pulseAlpha), start = Offset(midX2, cy), end = Offset(cx, midY2), strokeWidth = strokeWidth, cap = cap)
        drawLine(color = BerserkEclipseSun.copy(alpha = pulseAlpha), start = Offset(cx, midY2), end = Offset(midX1, cy), strokeWidth = strokeWidth, cap = cap)

        // Bottom hooks
        drawLine(
            color = glowColor.copy(alpha = pulseAlpha),
            start = Offset(w * 0.32f, h * 0.76f),
            end = Offset(cx, h * 0.62f),
            strokeWidth = strokeWidth,
            cap = cap
        )
        drawLine(
            color = glowColor.copy(alpha = pulseAlpha),
            start = Offset(w * 0.68f, h * 0.76f),
            end = Offset(cx, h * 0.62f),
            strokeWidth = strokeWidth,
            cap = cap
        )

        // Central bright core
        drawCircle(
            color = Color.White.copy(alpha = 0.9f * pulseAlpha),
            radius = 3.dp.toPx(),
            center = Offset(cx, cy)
        )
    }
}

@Composable
fun LiquidArcGauge(
    percentage: Float,
    label: String,
    subValue: String,
    modifier: Modifier = Modifier,
    barColor: Color = BerserkBloodRed,
    trackColor: Color = Color(0x3337474F)
) {
    // Smooth fluid spring animation like Apple Watch / iPhone activity rings
    val animatedPercent by animateFloatAsState(
        targetValue = percentage.coerceIn(0f, 100f),
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "gaugeAnim"
    )

    Box(
        modifier = modifier.size(130.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeW = 10.dp.toPx()
            val startAngle = 140f
            val sweepAngle = 260f
            val arcSize = size.width - strokeW * 2
            val topLeft = Offset(strokeW, strokeW)

            // Background Track
            drawArc(
                color = trackColor,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                topLeft = topLeft,
                size = Size(arcSize, arcSize),
                style = Stroke(width = strokeW, cap = StrokeCap.Round)
            )

            // Active Progress with glowing sweep
            val currentSweep = (animatedPercent / 100f) * sweepAngle
            if (currentSweep > 0) {
                drawArc(
                    brush = Brush.sweepGradient(
                        colors = listOf(
                            barColor.copy(alpha = 0.85f),
                            BerserkCrimsonGlow,
                            BerserkBehelitGold,
                            barColor
                        )
                    ),
                    startAngle = startAngle,
                    sweepAngle = currentSweep,
                    useCenter = false,
                    topLeft = topLeft,
                    size = Size(arcSize, arcSize),
                    style = Stroke(width = strokeW, cap = StrokeCap.Round)
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "${animatedPercent.toInt()}%",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = RajdhaniFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = Color.White
                )
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontFamily = CinzelFontFamily,
                    color = BerserkSteelDim,
                    letterSpacing = 0.5.sp
                )
            )
            if (subValue.isNotEmpty()) {
                Text(
                    text = subValue,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontFamily = RajdhaniFontFamily,
                        color = BerserkCrimsonGlow,
                        fontSize = 11.sp
                    )
                )
            }
        }
    }
}

@Composable
fun BerserkDetailRow(
    title: String,
    value: String,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    highlight: Boolean = false,
    onCopy: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .clickable(enabled = onCopy != null) { onCopy?.invoke() }
            .padding(vertical = 8.dp, horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0x33FF1744)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = BerserkCrimsonGlow,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontFamily = CinzelFontFamily,
                    color = BerserkSteelDim,
                    letterSpacing = 0.5.sp
                )
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontFamily = RajdhaniFontFamily,
                    fontWeight = if (highlight) FontWeight.Bold else FontWeight.Medium,
                    color = if (highlight) BerserkCrimsonGlow else Color.White,
                    fontSize = 15.sp
                )
            )
        }

        if (onCopy != null) {
            IconButton(
                onClick = onCopy,
                modifier = Modifier
                    .size(48.dp)
                    .testTag("copy_${title.lowercase().replace(" ", "_")}")
            ) {
                Icon(
                    imageVector = Icons.Default.ContentCopy,
                    contentDescription = "Copy $title",
                    tint = BerserkSteelDim,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
fun BerserkAtmosphericBackground(
    content: @Composable BoxScope.() -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "ambient_glow")
    val glowPulse by infiniteTransition.animateFloat(
        initialValue = 0.18f,
        targetValue = 0.32f,
        animationSpec = infiniteRepeatable(
            animation = tween(4500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glowPulse"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BerserkObsidian)
            .drawBehind {
                // Animated blood moon eclipse ambient radial gradient at top center
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFB71C1C).copy(alpha = glowPulse),
                            Color(0xFFFF1744).copy(alpha = glowPulse * 0.45f),
                            Color.Transparent
                        ),
                        center = Offset(size.width * 0.5f, 0f),
                        radius = size.width * 0.90f
                    )
                )
                // Bottom subtle armor slate ambient glow
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0x1837474F),
                            Color.Transparent
                        ),
                        center = Offset(size.width * 0.8f, size.height),
                        radius = size.width * 0.6f
                    )
                )
            },
        content = content
    )
}
