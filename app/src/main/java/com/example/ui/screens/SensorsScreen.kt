package com.example.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.LiveSensorTelemetry
import com.example.model.SensorItem
import com.example.ui.components.BerserkDetailRow
import com.example.ui.components.LiquidGlassCard
import com.example.ui.theme.*
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun SensorsScreen(
    sensorsList: List<SensorItem>,
    liveTelemetry: LiveSensorTelemetry,
    onCopy: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 96.dp)
    ) {
        // Interactive 3D Tilt Bubble & Compass Dial Row
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Tilt Bubble Level
                LiquidGlassCard(
                    modifier = Modifier
                        .weight(1f)
                        .testTag("sensor_tilt_card")
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "ASTRAL TILT LEVEL",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontFamily = CinzelFontFamily,
                                color = BerserkCrimsonGlow,
                                letterSpacing = 0.5.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Box(
                            modifier = Modifier.size(110.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                val cx = size.width / 2f
                                val cy = size.height / 2f
                                val r = size.width * 0.44f

                                // Outer ring
                                drawCircle(
                                    color = Color(0x33FF1744),
                                    radius = r,
                                    style = Stroke(width = 2.dp.toPx())
                                )
                                // Crosshairs
                                drawLine(Color(0x33FFFFFF), Offset(cx - r, cy), Offset(cx + r, cy), 1f)
                                drawLine(Color(0x33FFFFFF), Offset(cx, cy - r), Offset(cx, cy + r), 1f)

                                // Bubble offset from accel (clamp)
                                val maxOffset = r * 0.75f
                                val bx = cx - (liveTelemetry.accelX / 9.8f).coerceIn(-1f, 1f) * maxOffset
                                val by = cy + (liveTelemetry.accelY / 9.8f).coerceIn(-1f, 1f) * maxOffset

                                drawCircle(
                                    brush = Brush.radialGradient(
                                        listOf(BerserkBloodRed, BerserkCrimsonGlow.copy(0.4f)),
                                        center = Offset(bx, by),
                                        radius = 12.dp.toPx()
                                    ),
                                    center = Offset(bx, by),
                                    radius = 10.dp.toPx()
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "X: ${String.format("%.1f", liveTelemetry.accelX)}  Y: ${String.format("%.1f", liveTelemetry.accelY)}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontFamily = RajdhaniFontFamily,
                                fontWeight = FontWeight.Bold,
                                color = BerserkCyanPulse
                            )
                        )
                    }
                }

                // Compass Azimuth Dial
                LiquidGlassCard(
                    modifier = Modifier
                        .weight(1f)
                        .testTag("sensor_compass_card")
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "COMPASS AZIMUTH",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontFamily = CinzelFontFamily,
                                color = BerserkCrimsonGlow,
                                letterSpacing = 0.5.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        val animatedAzimuth by animateFloatAsState(
                            targetValue = liveTelemetry.azimuthDegrees,
                            animationSpec = tween(400),
                            label = "azimuthAnim"
                        )

                        Box(
                            modifier = Modifier.size(110.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                val cx = size.width / 2f
                                val cy = size.height / 2f
                                val r = size.width * 0.44f

                                drawCircle(
                                    color = Color(0x3300E5FF),
                                    radius = r,
                                    style = Stroke(width = 2.dp.toPx())
                                )

                                val rad = Math.toRadians(animatedAzimuth.toDouble() - 90.0)
                                val nx = cx + (cos(rad) * r * 0.75).toFloat()
                                val ny = cy + (sin(rad) * r * 0.75).toFloat()

                                drawLine(
                                    color = BerserkBehelitGold,
                                    start = Offset(cx, cy),
                                    end = Offset(nx, ny),
                                    strokeWidth = 3.dp.toPx(),
                                    cap = StrokeCap.Round
                                )
                                drawCircle(
                                    color = BerserkBehelitGold,
                                    radius = 4.dp.toPx(),
                                    center = Offset(cx, cy)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "${animatedAzimuth.toInt()}° DEG",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontFamily = RajdhaniFontFamily,
                                fontWeight = FontWeight.Bold,
                                color = BerserkBehelitGold
                            )
                        )
                    }
                }
            }
        }

        // Live Telemetry Readout
        item {
            LiquidGlassCard(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "REAL-TIME TELEMETRY VALUES",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontFamily = CinzelFontFamily,
                        color = BerserkCrimsonGlow,
                        letterSpacing = 1.sp
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                BerserkDetailRow(
                    title = "ACCELEROMETER (X, Y, Z)",
                    value = "${String.format("%.2f", liveTelemetry.accelX)},  ${String.format("%.2f", liveTelemetry.accelY)},  ${String.format("%.2f", liveTelemetry.accelZ)} m/s²",
                    icon = Icons.Default.DirectionsRun,
                    onCopy = { onCopy("Accel", "${liveTelemetry.accelX}, ${liveTelemetry.accelY}, ${liveTelemetry.accelZ}") }
                )
                HorizontalDivider(color = Color(0x1FFFFFFF), thickness = 0.5.dp)

                BerserkDetailRow(
                    title = "GYROSCOPE (X, Y, Z)",
                    value = "${String.format("%.2f", liveTelemetry.gyroX)},  ${String.format("%.2f", liveTelemetry.gyroY)},  ${String.format("%.2f", liveTelemetry.gyroZ)} rad/s",
                    icon = Icons.Default.RotateRight,
                    onCopy = { onCopy("Gyro", "${liveTelemetry.gyroX}, ${liveTelemetry.gyroY}, ${liveTelemetry.gyroZ}") }
                )
                HorizontalDivider(color = Color(0x1FFFFFFF), thickness = 0.5.dp)

                BerserkDetailRow(
                    title = "MAGNETIC FIELD (X, Y, Z)",
                    value = "${String.format("%.1f", liveTelemetry.magX)},  ${String.format("%.1f", liveTelemetry.magY)},  ${String.format("%.1f", liveTelemetry.magZ)} µT",
                    icon = Icons.Default.Explore,
                    onCopy = { onCopy("Magnetometer", "${liveTelemetry.magX}, ${liveTelemetry.magY}, ${liveTelemetry.magZ}") }
                )
            }
        }

        // Hardware Sensor Inventory Header
        item {
            Text(
                text = "DETECTED HARDWARE SENSORS (${sensorsList.size})",
                style = MaterialTheme.typography.labelLarge.copy(
                    fontFamily = CinzelFontFamily,
                    color = BerserkCrimsonGlow,
                    letterSpacing = 1.sp
                ),
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }

        items(sensorsList) { sensor ->
            SensorItemCard(sensor = sensor, onCopy = onCopy)
        }
    }
}

@Composable
private fun SensorItemCard(
    sensor: SensorItem,
    onCopy: (String, String) -> Unit
) {
    LiquidGlassCard(
        modifier = Modifier.fillMaxWidth(),
        cornerRadius = 16.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0x3300E5FF)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Sensors,
                    contentDescription = sensor.name,
                    tint = BerserkCyanPulse,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = sensor.name,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontFamily = CinzelFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
                Text(
                    text = "${sensor.typeName} • ${sensor.vendor}",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontFamily = RajdhaniFontFamily,
                        color = BerserkSteelDim
                    )
                )
                Text(
                    text = "Power: ${sensor.powerMa} mA • Max: ${sensor.maxRange} • Res: ${sensor.resolution}",
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
