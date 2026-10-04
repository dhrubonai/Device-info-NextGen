package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BatteryInfoData
import com.example.ui.components.BerserkDetailRow
import com.example.ui.components.LiquidArcGauge
import com.example.ui.components.LiquidGlassCard
import com.example.ui.theme.*

@Composable
fun BatteryScreen(
    batteryInfo: BatteryInfoData,
    onCopy: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val tempFahrenheit = (batteryInfo.temperatureCelsius * 9f / 5f) + 32f

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 96.dp)
    ) {
        // Battery Flame Meter
        item {
            LiquidGlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("battery_hero_card"),
                cornerRadius = 24.dp
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "BRAND FLAME • POWER MATRIX",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontFamily = CinzelFontFamily,
                            color = BerserkCrimsonGlow,
                            letterSpacing = 1.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    LiquidArcGauge(
                        percentage = batteryInfo.percentage.toFloat(),
                        label = if (batteryInfo.isCharging) "CHARGING" else "BATTERY",
                        subValue = "${batteryInfo.temperatureCelsius}°C",
                        modifier = Modifier.size(170.dp),
                        barColor = if (batteryInfo.isCharging) BerserkBehelitGold else BerserkBloodRed
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Status Pill
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (batteryInfo.isCharging) Color(0x33FFB300) else Color(0x33FF1744))
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "${batteryInfo.status.uppercase()} • ${batteryInfo.plugged.uppercase()}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontFamily = RajdhaniFontFamily,
                                fontWeight = FontWeight.Bold,
                                color = if (batteryInfo.isCharging) BerserkBehelitGold else BerserkCrimsonGlow,
                                letterSpacing = 0.8.sp
                            )
                        )
                    }
                }
            }
        }

        // Detailed Electrical & Thermal Metrics
        item {
            LiquidGlassCard(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "ELECTRICAL & THERMAL METRICS",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontFamily = CinzelFontFamily,
                        color = BerserkCrimsonGlow,
                        letterSpacing = 1.sp
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                BerserkDetailRow(
                    title = "BATTERY HEALTH",
                    value = batteryInfo.health,
                    icon = Icons.Default.Favorite,
                    highlight = true,
                    onCopy = { onCopy("Battery Health", batteryInfo.health) }
                )
                HorizontalDivider(color = Color(0x1FFFFFFF), thickness = 0.5.dp)

                BerserkDetailRow(
                    title = "TEMPERATURE",
                    value = "${batteryInfo.temperatureCelsius} °C  /  ${String.format("%.1f", tempFahrenheit)} °F",
                    icon = Icons.Default.Thermostat,
                    onCopy = { onCopy("Temperature", "${batteryInfo.temperatureCelsius} °C") }
                )
                HorizontalDivider(color = Color(0x1FFFFFFF), thickness = 0.5.dp)

                BerserkDetailRow(
                    title = "TERMINAL VOLTAGE",
                    value = "${batteryInfo.voltageMillivolts} mV  (${String.format("%.2f", batteryInfo.voltageMillivolts / 1000f)} V)",
                    icon = Icons.Default.ElectricBolt,
                    onCopy = { onCopy("Voltage", "${batteryInfo.voltageMillivolts} mV") }
                )
                HorizontalDivider(color = Color(0x1FFFFFFF), thickness = 0.5.dp)

                BerserkDetailRow(
                    title = "CELL TECHNOLOGY",
                    value = batteryInfo.technology,
                    icon = Icons.Default.Shield,
                    onCopy = { onCopy("Technology", batteryInfo.technology) }
                )
                HorizontalDivider(color = Color(0x1FFFFFFF), thickness = 0.5.dp)

                BerserkDetailRow(
                    title = "POWER SOURCE",
                    value = batteryInfo.plugged,
                    icon = Icons.Default.Power,
                    onCopy = { onCopy("Power Source", batteryInfo.plugged) }
                )
            }
        }
    }
}
