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
import com.example.model.MemoryInfoData
import com.example.ui.components.BerserkDetailRow
import com.example.ui.components.LiquidArcGauge
import com.example.ui.components.LiquidGlassCard
import com.example.ui.theme.*

@Composable
fun MemoryScreen(
    memoryInfo: MemoryInfoData,
    onCopy: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val ramTotalMb = memoryInfo.totalRamBytes / (1024 * 1024)
    val ramUsedMb = memoryInfo.usedRamBytes / (1024 * 1024)
    val ramFreeMb = memoryInfo.availableRamBytes / (1024 * 1024)
    val ramThresholdMb = memoryInfo.lowMemoryThresholdBytes / (1024 * 1024)

    val storageTotalGb = memoryInfo.totalStorageBytes / (1024 * 1024 * 1024)
    val storageUsedGb = memoryInfo.usedStorageBytes / (1024 * 1024 * 1024)
    val storageFreeGb = memoryInfo.availableStorageBytes / (1024 * 1024 * 1024)

    val jvmMaxMb = memoryInfo.jvmMaxMemoryBytes / (1024 * 1024)
    val jvmTotalMb = memoryInfo.jvmTotalMemoryBytes / (1024 * 1024)
    val jvmFreeMb = memoryInfo.jvmFreeMemoryBytes / (1024 * 1024)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 96.dp)
    ) {
        // Dual Gauges Row
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                LiquidGlassCard(
                    modifier = Modifier
                        .weight(1f)
                        .testTag("memory_ram_card")
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

                LiquidGlassCard(
                    modifier = Modifier
                        .weight(1f)
                        .testTag("memory_storage_card")
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

        // RAM Deep Breakdown
        item {
            LiquidGlassCard(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "RAM (RANDOM ACCESS MEMORY) ALLOCATION",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontFamily = CinzelFontFamily,
                        color = BerserkCrimsonGlow,
                        letterSpacing = 1.sp
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                BerserkDetailRow(
                    title = "TOTAL RAM",
                    value = "$ramTotalMb MB  (${String.format("%.2f", ramTotalMb / 1024f)} GB)",
                    icon = Icons.Default.Memory,
                    onCopy = { onCopy("Total RAM", "$ramTotalMb MB") }
                )
                HorizontalDivider(color = Color(0x1FFFFFFF), thickness = 0.5.dp)

                BerserkDetailRow(
                    title = "USED RAM",
                    value = "$ramUsedMb MB  (${String.format("%.1f", memoryInfo.ramUsedPercent)}%)",
                    icon = Icons.Default.PieChart,
                    highlight = true,
                    onCopy = { onCopy("Used RAM", "$ramUsedMb MB") }
                )
                HorizontalDivider(color = Color(0x1FFFFFFF), thickness = 0.5.dp)

                BerserkDetailRow(
                    title = "AVAILABLE / FREE RAM",
                    value = "$ramFreeMb MB",
                    icon = Icons.Default.CheckCircle,
                    onCopy = { onCopy("Free RAM", "$ramFreeMb MB") }
                )
                HorizontalDivider(color = Color(0x1FFFFFFF), thickness = 0.5.dp)

                BerserkDetailRow(
                    title = "LOW MEMORY THRESHOLD",
                    value = "$ramThresholdMb MB (System Alert Limit)",
                    icon = Icons.Default.Warning,
                    onCopy = { onCopy("Low RAM Threshold", "$ramThresholdMb MB") }
                )
            }
        }

        // Storage Partition Breakdown
        item {
            LiquidGlassCard(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "INTERNAL FLASH STORAGE PARTITION",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontFamily = CinzelFontFamily,
                        color = BerserkCrimsonGlow,
                        letterSpacing = 1.sp
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                BerserkDetailRow(
                    title = "TOTAL STORAGE CAPACITY",
                    value = "$storageTotalGb GB",
                    icon = Icons.Default.Storage,
                    onCopy = { onCopy("Total Storage", "$storageTotalGb GB") }
                )
                HorizontalDivider(color = Color(0x1FFFFFFF), thickness = 0.5.dp)

                BerserkDetailRow(
                    title = "USED STORAGE",
                    value = "$storageUsedGb GB (${String.format("%.1f", memoryInfo.storageUsedPercent)}%)",
                    icon = Icons.Default.Folder,
                    onCopy = { onCopy("Used Storage", "$storageUsedGb GB") }
                )
                HorizontalDivider(color = Color(0x1FFFFFFF), thickness = 0.5.dp)

                BerserkDetailRow(
                    title = "FREE STORAGE AVAILABLE",
                    value = "$storageFreeGb GB",
                    icon = Icons.Default.CloudQueue,
                    highlight = true,
                    onCopy = { onCopy("Free Storage", "$storageFreeGb GB") }
                )
            }
        }

        // JVM Runtime Heap
        item {
            LiquidGlassCard(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "JAVA / ART RUNTIME HEAP",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontFamily = CinzelFontFamily,
                        color = BerserkCrimsonGlow,
                        letterSpacing = 1.sp
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                BerserkDetailRow(
                    title = "MAX HEAP MEMORY",
                    value = "$jvmMaxMb MB",
                    icon = Icons.Default.Code,
                    onCopy = { onCopy("JVM Max Heap", "$jvmMaxMb MB") }
                )
                HorizontalDivider(color = Color(0x1FFFFFFF), thickness = 0.5.dp)

                BerserkDetailRow(
                    title = "ALLOCATED HEAP",
                    value = "$jvmTotalMb MB",
                    icon = Icons.Default.DeveloperMode,
                    onCopy = { onCopy("Allocated Heap", "$jvmTotalMb MB") }
                )
                HorizontalDivider(color = Color(0x1FFFFFFF), thickness = 0.5.dp)

                BerserkDetailRow(
                    title = "FREE HEAP AVAILABLE",
                    value = "$jvmFreeMb MB",
                    icon = Icons.Default.DataUsage,
                    onCopy = { onCopy("Free Heap", "$jvmFreeMb MB") }
                )
            }
        }
    }
}
