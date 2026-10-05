package com.example.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CpuCoreInfo
import com.example.model.CpuInfo
import com.example.ui.components.BerserkDetailRow
import com.example.ui.components.LiquidGlassCard
import com.example.ui.theme.*

@Composable
fun CpuScreen(
    cpuInfo: CpuInfo,
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
        // CPU Hero Card
        item {
            LiquidGlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("cpu_hero_card"),
                cornerRadius = 22.dp
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(
                                Brush.radialGradient(
                                    listOf(BerserkBloodRed.copy(0.4f), Color(0x1B100B14))
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Memory,
                            contentDescription = "CPU",
                            tint = BerserkBloodRed,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "DRAGON SLAYER ENGINE",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontFamily = CinzelFontFamily,
                                color = BerserkCrimsonGlow,
                                letterSpacing = 1.sp
                            )
                        )
                        Text(
                            text = cpuInfo.processorName,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontFamily = CinzelFontFamily,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 18.sp
                            )
                        )
                        Text(
                            text = "${cpuInfo.coreCount} Physical / Logical Cores",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontFamily = RajdhaniFontFamily,
                                color = BerserkSteelDim
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                BerserkDetailRow(
                    title = "ARCHITECTURE",
                    value = cpuInfo.architecture + if (cpuInfo.is64Bit) " (64-Bit Arm/x86)" else " (32-Bit)",
                    icon = Icons.Default.SettingsApplications,
                    onCopy = { onCopy("Architecture", cpuInfo.architecture) }
                )
                HorizontalDivider(color = Color(0x1FFFFFFF), thickness = 0.5.dp)

                BerserkDetailRow(
                    title = "CPU GOVERNOR",
                    value = cpuInfo.governor,
                    icon = Icons.Default.Speed,
                    onCopy = { onCopy("Governor", cpuInfo.governor) }
                )
                HorizontalDivider(color = Color(0x1FFFFFFF), thickness = 0.5.dp)

                BerserkDetailRow(
                    title = "SUPPORTED ABIS",
                    value = cpuInfo.supportedAbis.joinToString(", "),
                    icon = Icons.Default.Code,
                    onCopy = { onCopy("ABIs", cpuInfo.supportedAbis.joinToString(", ")) }
                )
                HorizontalDivider(color = Color(0x1FFFFFFF), thickness = 0.5.dp)

                BerserkDetailRow(
                    title = "BOGOMIPS / FREQ SPEC",
                    value = cpuInfo.bogoMips,
                    icon = Icons.Default.Tune,
                    onCopy = { onCopy("BogoMIPS", cpuInfo.bogoMips) }
                )
            }
        }

        // Live CPU Core Frequencies Title
        item {
            Text(
                text = "ACTIVE CORE FREQUENCY TELEMETRY",
                style = MaterialTheme.typography.labelLarge.copy(
                    fontFamily = CinzelFontFamily,
                    color = BerserkCrimsonGlow,
                    letterSpacing = 1.sp
                ),
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }

        // Core List
        items(cpuInfo.cores) { core ->
            CpuCoreItem(core = core)
        }
    }
}

@Composable
private fun CpuCoreItem(core: CpuCoreInfo) {
    val max = if (core.maxFreqKHz > 0) core.maxFreqKHz else 2800000L
    val cur = if (core.currentFreqKHz > 0) core.currentFreqKHz else (max * 0.45f).toLong()
    val progress = (cur.toFloat() / max.toFloat()).coerceIn(0.05f, 1f)

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(600),
        label = "coreFreqProgress"
    )

    val curMhz = (cur / 1000).toInt()
    val maxMhz = (max / 1000).toInt()

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
                    .background(Color(0x28FF1744)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "#${core.coreIndex}",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontFamily = RajdhaniFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = BerserkCrimsonGlow
                    )
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "CORE ${core.coreIndex}",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontFamily = CinzelFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                    Text(
                        text = "$curMhz MHz / $maxMhz MHz",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontFamily = RajdhaniFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = BerserkCyanPulse
                        )
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Progress Bar
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color(0x3337474F))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(animatedProgress)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(4.dp))
                            .background(
                                Brush.horizontalGradient(
                                    listOf(
                                        BerserkBloodRed,
                                        BerserkEclipseSun,
                                        BerserkBehelitGold
                                    )
                                )
                            )
                    )
                }
            }
        }
    }
}
