package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.LiquidGlassCard
import com.example.ui.theme.*

@Composable
fun ReportScreen(
    reportText: String,
    onCopyReport: () -> Unit,
    onShareReport: () -> Unit,
    modifier: Modifier = Modifier
) {
    val hScrollState = rememberScrollState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 96.dp)
    ) {
        item {
            LiquidGlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("report_hero_card"),
                cornerRadius = 24.dp
            ) {
                Text(
                    text = "TOME OF THE SACRIFICED",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontFamily = CinzelFontFamily,
                        color = BerserkCrimsonGlow,
                        letterSpacing = 1.sp
                    )
                )
                Text(
                    text = "COMPLETE HARDWARE TOME",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = CinzelFontFamily,
                        color = Color.White,
                        fontSize = 18.sp
                    )
                )
                Text(
                    text = "Generate and export an immutable hardware signature report",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontFamily = RajdhaniFontFamily,
                        color = BerserkSteelDim
                    )
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = onCopyReport,
                        modifier = Modifier
                            .weight(1f)
                            .testTag("report_copy_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = BerserkBloodRed)
                    ) {
                        Icon(Icons.Default.ContentCopy, contentDescription = "Copy", modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("COPY TOME", fontFamily = RajdhaniFontFamily, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = onShareReport,
                        modifier = Modifier
                            .weight(1f)
                            .testTag("report_share_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF37474F))
                    ) {
                        Icon(Icons.Default.Share, contentDescription = "Share", modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("SHARE", fontFamily = RajdhaniFontFamily, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Terminal Output Preview
        item {
            LiquidGlassCard(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "DIAGNOSTIC REPORT OUTPUT",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontFamily = CinzelFontFamily,
                        color = BerserkCrimsonGlow,
                        letterSpacing = 1.sp
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF070407))
                        .padding(14.dp)
                        .horizontalScroll(hScrollState)
                ) {
                    Text(
                        text = reportText,
                        color = BerserkSteel,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp,
                        lineHeight = 17.sp
                    )
                }
            }
        }
    }
}
