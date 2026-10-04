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
import com.example.model.DisplayInfoData
import com.example.ui.components.BerserkDetailRow
import com.example.ui.components.LiquidGlassCard
import com.example.ui.theme.*

@Composable
fun DisplayScreen(
    displayInfo: DisplayInfoData,
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
        // Display Hero Preview Card
        item {
            LiquidGlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("display_hero_card"),
                cornerRadius = 24.dp
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "BEHELIT EYE • DISPLAY METRICS",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontFamily = CinzelFontFamily,
                            color = BerserkCrimsonGlow,
                            letterSpacing = 1.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Stylized Phone Frame mockup
                    Box(
                        modifier = Modifier
                            .width(130.dp)
                            .height(210.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color(0x3BFF1744)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "${displayInfo.refreshRateHz.toInt()} Hz",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontFamily = RajdhaniFontFamily,
                                    color = BerserkBehelitGold,
                                    fontSize = 26.sp
                                )
                            )
                            Text(
                                text = "${displayInfo.widthPixels} x ${displayInfo.heightPixels}",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontFamily = RajdhaniFontFamily,
                                    color = Color.White
                                )
                            )
                            Text(
                                text = "~${displayInfo.physicalSizeInches} Inches",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontFamily = CinzelFontFamily,
                                    color = BerserkSteelDim
                                )
                            )
                        }
                    }
                }
            }
        }

        // Detailed Metrics
        item {
            LiquidGlassCard(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "PANEL & GRAPHICS SPECIFICATIONS",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontFamily = CinzelFontFamily,
                        color = BerserkCrimsonGlow,
                        letterSpacing = 1.sp
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                BerserkDetailRow(
                    title = "SCREEN RESOLUTION",
                    value = "${displayInfo.widthPixels} x ${displayInfo.heightPixels} Pixels",
                    icon = Icons.Default.AspectRatio,
                    highlight = true,
                    onCopy = { onCopy("Resolution", "${displayInfo.widthPixels}x${displayInfo.heightPixels}") }
                )
                HorizontalDivider(color = Color(0x1FFFFFFF), thickness = 0.5.dp)

                BerserkDetailRow(
                    title = "REFRESH RATE",
                    value = "${displayInfo.refreshRateHz} Hz",
                    icon = Icons.Default.Refresh,
                    highlight = true,
                    onCopy = { onCopy("Refresh Rate", "${displayInfo.refreshRateHz} Hz") }
                )
                HorizontalDivider(color = Color(0x1FFFFFFF), thickness = 0.5.dp)

                BerserkDetailRow(
                    title = "PIXEL DENSITY",
                    value = "${displayInfo.densityDpi} DPI (${displayInfo.densityBucket})",
                    icon = Icons.Default.GridOn,
                    onCopy = { onCopy("Pixel Density", "${displayInfo.densityDpi} DPI") }
                )
                HorizontalDivider(color = Color(0x1FFFFFFF), thickness = 0.5.dp)

                BerserkDetailRow(
                    title = "PHYSICAL SCREEN SIZE",
                    value = "~${displayInfo.physicalSizeInches} Inches (Diagonal)",
                    icon = Icons.Default.Straighten,
                    onCopy = { onCopy("Screen Size", "${displayInfo.physicalSizeInches}\"") }
                )
                HorizontalDivider(color = Color(0x1FFFFFFF), thickness = 0.5.dp)

                BerserkDetailRow(
                    title = "PRECISE XDPI / YDPI",
                    value = "${displayInfo.xdpi} xdpi  /  ${displayInfo.ydpi} ydpi",
                    icon = Icons.Default.CenterFocusStrong,
                    onCopy = { onCopy("XDPI/YDPI", "${displayInfo.xdpi} / ${displayInfo.ydpi}") }
                )
                HorizontalDivider(color = Color(0x1FFFFFFF), thickness = 0.5.dp)

                BerserkDetailRow(
                    title = "HIGH DYNAMIC RANGE (HDR)",
                    value = if (displayInfo.isHdrSupported) "Supported (HDR10/HLG)" else "Standard Dynamic Range (SDR)",
                    icon = Icons.Default.HdrOn,
                    onCopy = { onCopy("HDR", if (displayInfo.isHdrSupported) "Yes" else "No") }
                )
                HorizontalDivider(color = Color(0x1FFFFFFF), thickness = 0.5.dp)

                BerserkDetailRow(
                    title = "WIDE COLOR GAMUT (DCI-P3)",
                    value = if (displayInfo.isWideColorGamut) "Supported (Wide Gamut)" else "Standard sRGB",
                    icon = Icons.Default.Palette,
                    onCopy = { onCopy("Wide Color Gamut", if (displayInfo.isWideColorGamut) "Yes" else "No") }
                )
            }
        }
    }
}
