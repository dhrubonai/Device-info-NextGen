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
import com.example.model.NetworkInfoData
import com.example.ui.components.BerserkDetailRow
import com.example.ui.components.LiquidGlassCard
import com.example.ui.theme.*

@Composable
fun NetworkScreen(
    networkInfo: NetworkInfoData,
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
        // Network Hero Card
        item {
            LiquidGlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("network_hero_card"),
                cornerRadius = 24.dp
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(if (networkInfo.isConnected) Color(0x3300E676) else Color(0x33FF1744)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (networkInfo.isConnected) Icons.Default.Wifi else Icons.Default.WifiOff,
                            contentDescription = "Network",
                            tint = if (networkInfo.isConnected) BerserkEmeraldSafe else BerserkBloodRed,
                            modifier = Modifier.size(30.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "HAWK COMMS • TELEMETRY",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontFamily = CinzelFontFamily,
                                color = BerserkCrimsonGlow,
                                letterSpacing = 1.sp
                            )
                        )
                        Text(
                            text = if (networkInfo.isConnected) "LINK ESTABLISHED" else "SEVERED LINK",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontFamily = CinzelFontFamily,
                                color = if (networkInfo.isConnected) BerserkEmeraldSafe else BerserkBloodRed,
                                fontSize = 18.sp
                            )
                        )
                        Text(
                            text = networkInfo.connectionType,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontFamily = RajdhaniFontFamily,
                                color = BerserkSteelDim
                            )
                        )
                    }
                }
            }
        }

        // Detailed IP & Interface Specs
        item {
            LiquidGlassCard(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "COMMUNICATION PROTOCOLS & INTERFACES",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontFamily = CinzelFontFamily,
                        color = BerserkCrimsonGlow,
                        letterSpacing = 1.sp
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                BerserkDetailRow(
                    title = "LOCAL IPV4 ADDRESS",
                    value = networkInfo.ipv4Address,
                    icon = Icons.Default.Language,
                    highlight = true,
                    onCopy = { onCopy("IPv4", networkInfo.ipv4Address) }
                )
                HorizontalDivider(color = Color(0x1FFFFFFF), thickness = 0.5.dp)

                BerserkDetailRow(
                    title = "IPV6 ADDRESS",
                    value = networkInfo.ipv6Address,
                    icon = Icons.Default.VpnLock,
                    onCopy = { onCopy("IPv6", networkInfo.ipv6Address) }
                )
                HorizontalDivider(color = Color(0x1FFFFFFF), thickness = 0.5.dp)

                BerserkDetailRow(
                    title = "WI-FI NETWORK (SSID)",
                    value = networkInfo.wifiSsid,
                    icon = Icons.Default.NetworkWifi,
                    onCopy = { onCopy("SSID", networkInfo.wifiSsid) }
                )
                HorizontalDivider(color = Color(0x1FFFFFFF), thickness = 0.5.dp)

                BerserkDetailRow(
                    title = "LINK SPEED",
                    value = networkInfo.wifiLinkSpeed,
                    icon = Icons.Default.Speed,
                    onCopy = { onCopy("Link Speed", networkInfo.wifiLinkSpeed) }
                )
                HorizontalDivider(color = Color(0x1FFFFFFF), thickness = 0.5.dp)

                BerserkDetailRow(
                    title = "CHANNEL FREQUENCY",
                    value = networkInfo.wifiFrequency,
                    icon = Icons.Default.SettingsInputAntenna,
                    onCopy = { onCopy("Frequency", networkInfo.wifiFrequency) }
                )
                HorizontalDivider(color = Color(0x1FFFFFFF), thickness = 0.5.dp)

                BerserkDetailRow(
                    title = "METERED CONNECTION",
                    value = if (networkInfo.isMetered) "Metered (Limited Data)" else "Unmetered (Unlimited)",
                    icon = Icons.Default.DataThresholding,
                    onCopy = { onCopy("Metered", if (networkInfo.isMetered) "Yes" else "No") }
                )
            }
        }
    }
}
