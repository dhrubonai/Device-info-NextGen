package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
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
    onShowSplash: () -> Unit,
    onCopy: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 96.dp)
    ) {
        // Hero Brand & Device Identity Card with user's App Icon Artwork
        item {
            LiquidGlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .liquidParallaxScroll(index = 0, lazyListState = listState, speedFactor = 0.035f, tiltFactor = 1.6f)
                    .testTag("dashboard_hero_card"),
                cornerRadius = 24.dp
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // App Logo Art from user's provided exact Google Drive image
                    Box(
                        modifier = Modifier
                            .size(74.dp)
                            .clip(RoundedCornerShape(18.dp))
                            .background(Color.Black),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.berserk_app_logo),
                            contentDescription = "Berserk Device Logo",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }

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

                // Quick Telemetry Badges
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    QuickBadge(label = "UPTIME", value = generalInfo.uptimeFormatted)
                    QuickBadge(label = "RADIO / BB", value = generalInfo.radioVersion.take(10))
                    QuickBadge(label = "REFRESH", value = "${displayInfo.refreshRateHz.toInt()} Hz")
                    QuickBadge(label = "SECURITY", value = generalInfo.securityPatch)
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Replay Splash Screen Button
                OutlinedButton(
                    onClick = onShowSplash,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("replay_splash_btn"),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, BerserkGlassBorder)
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Splash Screen",
                        tint = BerserkCrimsonGlow,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "VIEW BERSERK SPLASH SCREEN",
                        fontFamily = CinzelFontFamily,
                        fontSize = 11.sp,
                        letterSpacing = 0.5.sp
                    )
                }
            }
        }

        // Dual Liquid Gauges (RAM & Storage)
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .liquidParallaxScroll(index = 1, lazyListState = listState, speedFactor = 0.05f, tiltFactor = 2.0f),
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
                            label = "RAM POOL",
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

        // Live Battery Status & Counter Card
        item {
            LiquidGlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .liquidParallaxScroll(index = 2, lazyListState = listState, speedFactor = 0.055f, tiltFactor = 2.2f)
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
                            text = "BRAND FLAME • ${batteryInfo.percentage}% (${batteryInfo.chargeCounterMah} mAh)",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontFamily = CinzelFontFamily,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                        Text(
                            text = "${batteryInfo.chargeStatusDetailed} • ${batteryInfo.temperatureCelsius}°C",
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
                    .liquidParallaxScroll(index = 3, lazyListState = listState, speedFactor = 0.05f, tiltFactor = 2.0f)
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
            LiquidGlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .liquidParallaxScroll(index = 4, lazyListState = listState, speedFactor = 0.06f, tiltFactor = 2.2f)
            ) {
                Text(
                    text = "DEVICE STATUS & SPECIFICATIONS",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontFamily = CinzelFontFamily,
                        color = BerserkCrimsonGlow,
                        letterSpacing = 1.sp
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                BerserkDetailRow(
                    title = "Board & Hardware",
                    value = "${generalInfo.board} (${generalInfo.hardware})",
                    icon = Icons.Default.DeveloperBoard,
                    onCopy = { onCopy("Board & Hardware", "${generalInfo.board} (${generalInfo.hardware})") }
                )

                BerserkDetailRow(
                    title = "Display Matrix",
                    value = "${displayInfo.widthPixels}x${displayInfo.heightPixels} • ${displayInfo.densityDpi} DPI (${displayInfo.densityBucket})",
                    icon = Icons.Default.Tv,
                    onCopy = { onCopy("Display Matrix", "${displayInfo.widthPixels}x${displayInfo.heightPixels} • ${displayInfo.densityDpi} DPI") }
                )

                BerserkDetailRow(
                    title = "Android Build Fingerprint",
                    value = generalInfo.fingerprint.take(36) + "...",
                    icon = Icons.Default.Fingerprint,
                    onCopy = { onCopy("Fingerprint", generalInfo.fingerprint) }
                )

                BerserkDetailRow(
                    title = "Kernel & Security Status",
                    value = "${generalInfo.kernelVersion.take(24)}... • ${generalInfo.securityStatus}",
                    icon = Icons.Default.Build,
                    onCopy = { onCopy("Kernel Version", generalInfo.kernelVersion) }
                )
            }
        }

        // Navigation Quick Portals
        item {
            LiquidGlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .liquidParallaxScroll(index = 5, lazyListState = listState, speedFactor = 0.04f, tiltFactor = 1.8f)
            ) {
                Text(
                    text = "ASTRAL GATEWAYS",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontFamily = CinzelFontFamily,
                        color = BerserkBehelitGold,
                        letterSpacing = 1.sp
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    PortalButton(
                        label = "Sensors",
                        icon = Icons.Default.Explore,
                        color = BerserkCyanPulse,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigateTab(NavTab.SENSORS) }
                    )
                    PortalButton(
                        label = "Diagnostics",
                        icon = Icons.Default.Speed,
                        color = BerserkBloodRed,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigateTab(NavTab.TESTS) }
                    )
                    PortalButton(
                        label = "Network",
                        icon = Icons.Default.Wifi,
                        color = BerserkEmeraldSafe,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigateTab(NavTab.NETWORK) }
                    )
                    PortalButton(
                        label = "Sacred Codex",
                        icon = Icons.Default.Info,
                        color = BerserkBehelitGold,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigateTab(NavTab.APP_INFO) }
                    )
                }
            }
        }
    }
}

@Composable
private fun QuickBadge(label: String, value: String) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0x22FFFFFF))
            .padding(horizontal = 8.dp, vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
                fontFamily = CinzelFontFamily,
                color = BerserkSteelDim,
                fontSize = 9.sp,
                letterSpacing = 0.5.sp
            )
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall.copy(
                fontFamily = RajdhaniFontFamily,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 11.sp
            )
        )
    }
}

@Composable
private fun PortalButton(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(54.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color.copy(alpha = 0.22f)),
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = color,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = label,
                fontFamily = RajdhaniFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp,
                color = Color.White
            )
        }
    }
}
