package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.*
import com.example.ui.components.*
import com.example.ui.theme.*

@Composable
fun DashboardScreen(
    generalInfo: GeneralInfo,
    cpuInfo: CpuInfo,
    memoryInfo: MemoryInfoData,
    batteryInfo: BatteryInfoData,
    displayInfo: DisplayInfoData,
    onNavigateTab: (NavTab) -> Unit,
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
        // Hero Brand & Device Identity Card
        item {
            LiquidGlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("dashboard_hero_card"),
                cornerRadius = 24.dp
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BrandOfSacrificeCanvas(
                        modifier = Modifier
                            .size(72.dp)
                            .testTag("brand_of_sacrifice_rune")
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(BerserkBloodRed)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "BERSERK PROTOCOL ONLINE",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontFamily = CinzelFontFamily,
                                    color = BerserkCrimsonGlow,
                                    letterSpacing = 1.sp
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "${generalInfo.manufacturer} ${generalInfo.model}",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontFamily = CinzelFontFamily,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 19.sp
                            )
                        )

                        Text(
                            text = "${generalInfo.codeName} • API ${generalInfo.apiLevel}",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontFamily = RajdhaniFontFamily,
                                color = BerserkSteelDim
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Quick stats pill row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    QuickBadge(label = "UPTIME", value = generalInfo.uptimeFormatted)
                    QuickBadge(label = "SECURITY", value = generalInfo.securityPatch)
                    QuickBadge(label = "REFRESH", value = "${displayInfo.refreshRateHz.toInt()} Hz")
                }
            }
        }

        // Dual Liquid Gauges (RAM & Storage)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                val ramUsedMb = memoryInfo.usedRamBytes / (1024 * 1024)
                val ramTotalMb = memoryInfo.totalRamBytes / (1024 * 1024)
                LiquidGlassCard(
                    modifier = Modifier
                        .weight(1f)
                        .testTag("ram_gauge_card")
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        LiquidArcGauge(
                            percentage = memoryInfo.ramUsedPercent,
                            label = "RAM UTIL",
                            subValue = "$ramUsedMb / $ramTotalMb MB",
                            barColor = BerserkBloodRed
                        )
                    }
                }

                val storageUsedGb = memoryInfo.usedStorageBytes / (1024 * 1024 * 1024)
                val storageTotalGb = memoryInfo.totalStorageBytes / (1024 * 1024 * 1024)
                LiquidGlassCard(
                    modifier = Modifier
                        .weight(1f)
                        .testTag("storage_gauge_card")
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        LiquidArcGauge(
                            percentage = memoryInfo.storageUsedPercent,
                            label = "STORAGE",
                            subValue = "$storageUsedGb / $storageTotalGb GB",
                            barColor = BerserkEclipseSun
                        )
                    }
                }
            }
        }

        // Live Battery Status Card
        item {
            LiquidGlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("dashboard_battery_card")
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                Brush.linearGradient(
                                    listOf(BerserkBloodRed.copy(0.3f), Color(0x221A0A10))
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (batteryInfo.isCharging) Icons.Default.Bolt else Icons.Default.BatteryStd,
                            contentDescription = "Battery Status",
                            tint = if (batteryInfo.isCharging) BerserkBehelitGold else BerserkCrimsonGlow,
                            modifier = Modifier.size(26.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "BRAND FLAME • BATTERY ${batteryInfo.percentage}%",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontFamily = CinzelFontFamily,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                        Text(
                            text = "${batteryInfo.status} • ${batteryInfo.plugged} • ${batteryInfo.temperatureCelsius}°C",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontFamily = RajdhaniFontFamily,
                                color = BerserkSteelDim
                            )
                        )
                    }

                    TextButton(
                        onClick = { onNavigateTab(NavTab.BATTERY) },
                        modifier = Modifier.testTag("dashboard_goto_battery")
                    ) {
                        Text(
                            text = "DETAILS",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontFamily = RajdhaniFontFamily,
                                fontWeight = FontWeight.Bold,
                                color = BerserkCrimsonGlow
                            )
                        )
                    }
                }
            }
        }

        // Dragon Slayer CPU Engine Quick Peek
        item {
            LiquidGlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("dashboard_cpu_card")
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0x3300E5FF)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Memory,
                            contentDescription = "CPU Info",
                            tint = BerserkCyanPulse,
                            modifier = Modifier.size(26.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "DRAGON SLAYER CPU",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontFamily = CinzelFontFamily,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                        Text(
                            text = "${cpuInfo.coreCount} Cores • ${cpuInfo.architecture} • ${cpuInfo.processorName}",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontFamily = RajdhaniFontFamily,
                                color = BerserkSteelDim
                            )
                        )
                    }

                    TextButton(
                        onClick = { onNavigateTab(NavTab.CPU) },
                        modifier = Modifier.testTag("dashboard_goto_cpu")
                    ) {
                        Text(
                            text = "CORES",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontFamily = RajdhaniFontFamily,
                                fontWeight = FontWeight.Bold,
                                color = BerserkCyanPulse
                            )
                        )
                    }
                }
            }
        }

        // Hardware Highlights List
        item {
            LiquidGlassCard(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "HARDWARE TELEMETRY SPECIFICATIONS",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontFamily = CinzelFontFamily,
                        color = BerserkCrimsonGlow,
                        letterSpacing = 1.sp
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                BerserkDetailRow(
                    title = "BOARD & HARDWARE",
                    value = "${generalInfo.board} / ${generalInfo.hardware}",
                    icon = Icons.Default.DeveloperBoard,
                    onCopy = { onCopy("Hardware", "${generalInfo.board} / ${generalInfo.hardware}") }
                )
                HorizontalDivider(color = Color(0x1FFFFFFF), thickness = 0.5.dp)

                BerserkDetailRow(
                    title = "DISPLAY RESOLUTION",
                    value = "${displayInfo.widthPixels} x ${displayInfo.heightPixels} (~${displayInfo.physicalSizeInches}\")",
                    icon = Icons.Default.Smartphone,
                    onCopy = { onCopy("Display", "${displayInfo.widthPixels}x${displayInfo.heightPixels}") }
                )
                HorizontalDivider(color = Color(0x1FFFFFFF), thickness = 0.5.dp)

                BerserkDetailRow(
                    title = "KERNEL ARCHITECTURE",
                    value = generalInfo.kernelVersion,
                    icon = Icons.Default.Terminal,
                    onCopy = { onCopy("Kernel", generalInfo.kernelVersion) }
                )
                HorizontalDivider(color = Color(0x1FFFFFFF), thickness = 0.5.dp)

                BerserkDetailRow(
                    title = "BUILD FINGERPRINT",
                    value = generalInfo.fingerprint,
                    icon = Icons.Default.Fingerprint,
                    onCopy = { onCopy("Fingerprint", generalInfo.fingerprint) }
                )
            }
        }
    }
}

@Composable
private fun QuickBadge(label: String, value: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0x2B37474F))
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontFamily = CinzelFontFamily,
                    fontSize = 9.sp,
                    color = BerserkSteelDim
                )
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontFamily = RajdhaniFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = Color.White
                )
            )
        }
    }
}
