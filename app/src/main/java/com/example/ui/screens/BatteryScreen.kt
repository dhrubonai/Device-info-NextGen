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
        // Battery Flame Meter Hero
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

                    Spacer(modifier = Modifier.height(14.dp))

                    LiquidArcGauge(
                        percentage = batteryInfo.percentage.toFloat(),
                        label = if (batteryInfo.isCharging) "CHARGING" else "DISCHARGING",
                        subValue = "${batteryInfo.chargeCounterMah} mAh",
                        modifier = Modifier.size(165.dp),
                        barColor = if (batteryInfo.isCharging) BerserkBehelitGold else BerserkBloodRed
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Dynamic Charge Status Pill
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (batteryInfo.isCharging) Color(0x33FFB300) else Color(0x33FF1744))
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = batteryInfo.chargeStatusDetailed.uppercase(),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontFamily = RajdhaniFontFamily,
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                color = if (batteryInfo.isCharging) BerserkBehelitGold else BerserkCrimsonGlow,
                                letterSpacing = 0.8.sp
                            )
                        )
                    }
                }
            }
        }

        // Real-Time Capacity & Charge Counter
        item {
            LiquidGlassCard(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "CHARGE COUNTER & CURRENT CAPACITY",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontFamily = CinzelFontFamily,
                        color = BerserkCrimsonGlow,
                        letterSpacing = 1.sp
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                BerserkDetailRow(
                    title = "BATTERY CHARGE COUNTER",
                    value = "${batteryInfo.chargeCounterMah} mAh" + if (batteryInfo.chargeCounterUah > 0) " (${batteryInfo.chargeCounterUah} µAh)" else "",
                    icon = Icons.Default.BatteryChargingFull,
                    highlight = true,
                    onCopy = { onCopy("Charge Counter", "${batteryInfo.chargeCounterMah} mAh") }
                )
                HorizontalDivider(color = Color(0x1FFFFFFF), thickness = 0.5.dp)

                BerserkDetailRow(
                    title = "ESTIMATED / DESIGN CAPACITY",
                    value = "${batteryInfo.batteryCapacityMah} mAh",
                    icon = Icons.Default.BatteryStd,
                    onCopy = { onCopy("Design Capacity", "${batteryInfo.batteryCapacityMah} mAh") }
                )
                HorizontalDivider(color = Color(0x1FFFFFFF), thickness = 0.5.dp)

                BerserkDetailRow(
                    title = "INSTANTANEOUS CURRENT (NOW)",
                    value = "${if (batteryInfo.currentNowMa > 0) "+" else ""}${batteryInfo.currentNowMa} mA",
                    icon = Icons.Default.Speed,
                    highlight = true,
                    onCopy = { onCopy("Current Now", "${batteryInfo.currentNowMa} mA") }
                )
                HorizontalDivider(color = Color(0x1FFFFFFF), thickness = 0.5.dp)

                BerserkDetailRow(
                    title = "AVERAGE CURRENT DRAW",
                    value = "${if (batteryInfo.currentAverageMa > 0) "+" else ""}${batteryInfo.currentAverageMa} mA",
                    icon = Icons.Default.Timeline,
                    onCopy = { onCopy("Current Average", "${batteryInfo.currentAverageMa} mA") }
                )
                HorizontalDivider(color = Color(0x1FFFFFFF), thickness = 0.5.dp)

                if (batteryInfo.energyCounterNwh > 0) {
                    BerserkDetailRow(
                        title = "ENERGY COUNTER",
                        value = "${batteryInfo.energyCounterNwh / 1000000L} mWh",
                        icon = Icons.Default.Bolt,
                        onCopy = { onCopy("Energy Counter", "${batteryInfo.energyCounterNwh} nWh") }
                    )
                    HorizontalDivider(color = Color(0x1FFFFFFF), thickness = 0.5.dp)
                }

                BerserkDetailRow(
                    title = "POWER SOURCE & PLUG",
                    value = batteryInfo.plugged,
                    icon = Icons.Default.Power,
                    onCopy = { onCopy("Plugged State", batteryInfo.plugged) }
                )
            }
        }

        // Thermal & Electrical Health
        item {
            LiquidGlassCard(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "ELECTRICAL, THERMAL & CELL HEALTH",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontFamily = CinzelFontFamily,
                        color = BerserkCrimsonGlow,
                        letterSpacing = 1.sp
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                BerserkDetailRow(
                    title = "BATTERY HEALTH EVALUATION",
                    value = batteryInfo.health,
                    icon = Icons.Default.Favorite,
                    highlight = true,
                    onCopy = { onCopy("Battery Health", batteryInfo.health) }
                )
                HorizontalDivider(color = Color(0x1FFFFFFF), thickness = 0.5.dp)

                BerserkDetailRow(
                    title = "CELL TEMPERATURE",
                    value = "${batteryInfo.temperatureCelsius} °C  /  ${String.format("%.1f", tempFahrenheit)} °F",
                    icon = Icons.Default.Thermostat,
                    onCopy = { onCopy("Temperature", "${batteryInfo.temperatureCelsius} °C") }
                )
                HorizontalDivider(color = Color(0x1FFFFFFF), thickness = 0.5.dp)

                BerserkDetailRow(
                    title = "TERMINAL VOLTAGE",
                    value = "${batteryInfo.voltageMillivolts} mV  (${String.format("%.3f", batteryInfo.voltageMillivolts / 1000f)} V)",
                    icon = Icons.Default.ElectricBolt,
                    onCopy = { onCopy("Voltage", "${batteryInfo.voltageMillivolts} mV") }
                )
                HorizontalDivider(color = Color(0x1FFFFFFF), thickness = 0.5.dp)

                BerserkDetailRow(
                    title = "CELL CHEMISTRY",
                    value = batteryInfo.technology,
                    icon = Icons.Default.Shield,
                    onCopy = { onCopy("Technology", batteryInfo.technology) }
                )
                HorizontalDivider(color = Color(0x1FFFFFFF), thickness = 0.5.dp)

                BerserkDetailRow(
                    title = "BATTERY POWER SAVER",
                    value = if (batteryInfo.isPowerSaveMode) "Enabled (Low Power Mode)" else "Disabled (Full Performance)",
                    icon = Icons.Default.Savings,
                    onCopy = { onCopy("Power Saver", if (batteryInfo.isPowerSaveMode) "Active" else "Inactive") }
                )
                HorizontalDivider(color = Color(0x1FFFFFFF), thickness = 0.5.dp)

                BerserkDetailRow(
                    title = "CYCLE COUNT",
                    value = if (batteryInfo.cycleCount >= 0) "${batteryInfo.cycleCount} Cycles" else "Managed by BMS",
                    icon = Icons.Default.Autorenew,
                    onCopy = { onCopy("Cycle Count", "${batteryInfo.cycleCount}") }
                )
            }
        }
    }
}
