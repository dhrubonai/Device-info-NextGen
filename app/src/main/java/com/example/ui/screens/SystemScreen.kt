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
import com.example.model.GeneralInfo
import com.example.model.SystemSecurityInfo
import com.example.ui.components.BerserkDetailRow
import com.example.ui.components.LiquidGlassCard
import com.example.ui.theme.*

@Composable
fun SystemScreen(
    generalInfo: GeneralInfo,
    systemSecurity: SystemSecurityInfo,
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
        // Hero Android Card
        item {
            LiquidGlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("system_hero_card"),
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
                            .background(Color(0x33FF1744)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Android,
                            contentDescription = "Android OS",
                            tint = BerserkCrimsonGlow,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "BRAND OF FATE • OS MATRIX",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontFamily = CinzelFontFamily,
                                color = BerserkCrimsonGlow,
                                letterSpacing = 1.sp
                            )
                        )
                        Text(
                            text = generalInfo.codeName,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontFamily = CinzelFontFamily,
                                color = Color.White,
                                fontSize = 18.sp
                            )
                        )
                        Text(
                            text = "API Level ${generalInfo.apiLevel} • ${generalInfo.buildId}",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontFamily = RajdhaniFontFamily,
                                color = BerserkSteelDim
                            )
                        )
                    }
                }
            }
        }

        // Operating System Specs
        item {
            LiquidGlassCard(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "OPERATING SYSTEM PROPERTIES",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontFamily = CinzelFontFamily,
                        color = BerserkCrimsonGlow,
                        letterSpacing = 1.sp
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                BerserkDetailRow(
                    title = "ANDROID RELEASE VERSION",
                    value = generalInfo.androidVersion,
                    icon = Icons.Default.SystemUpdate,
                    highlight = true,
                    onCopy = { onCopy("Android Version", generalInfo.androidVersion) }
                )
                HorizontalDivider(color = Color(0x1FFFFFFF), thickness = 0.5.dp)

                BerserkDetailRow(
                    title = "SECURITY PATCH LEVEL",
                    value = generalInfo.securityPatch,
                    icon = Icons.Default.Security,
                    highlight = true,
                    onCopy = { onCopy("Security Patch", generalInfo.securityPatch) }
                )
                HorizontalDivider(color = Color(0x1FFFFFFF), thickness = 0.5.dp)

                BerserkDetailRow(
                    title = "KERNEL VERSION",
                    value = generalInfo.kernelVersion,
                    icon = Icons.Default.Terminal,
                    onCopy = { onCopy("Kernel", generalInfo.kernelVersion) }
                )
                HorizontalDivider(color = Color(0x1FFFFFFF), thickness = 0.5.dp)

                BerserkDetailRow(
                    title = "SYSTEM UPTIME",
                    value = generalInfo.uptimeFormatted,
                    icon = Icons.Default.Timer,
                    onCopy = { onCopy("Uptime", generalInfo.uptimeFormatted) }
                )
                HorizontalDivider(color = Color(0x1FFFFFFF), thickness = 0.5.dp)

                BerserkDetailRow(
                    title = "BOOTLOADER VERSION",
                    value = systemSecurity.bootloader,
                    icon = Icons.Default.RestartAlt,
                    onCopy = { onCopy("Bootloader", systemSecurity.bootloader) }
                )
            }
        }

        // Security & Integrity
        item {
            LiquidGlassCard(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "SECURITY, SELINUX & RUNTIME INTEGRITY",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontFamily = CinzelFontFamily,
                        color = BerserkCrimsonGlow,
                        letterSpacing = 1.sp
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                BerserkDetailRow(
                    title = "SELINUX ENFORCEMENT",
                    value = systemSecurity.selinuxStatus,
                    icon = Icons.Default.VerifiedUser,
                    highlight = true,
                    onCopy = { onCopy("SELinux", systemSecurity.selinuxStatus) }
                )
                HorizontalDivider(color = Color(0x1FFFFFFF), thickness = 0.5.dp)

                BerserkDetailRow(
                    title = "ROOT ACCESS PRIVILEGES",
                    value = if (systemSecurity.isRooted) "Root Privileges Detected" else "Not Rooted (Enforced)",
                    icon = Icons.Default.AdminPanelSettings,
                    onCopy = { onCopy("Root", if (systemSecurity.isRooted) "Yes" else "No") }
                )
                HorizontalDivider(color = Color(0x1FFFFFFF), thickness = 0.5.dp)

                BerserkDetailRow(
                    title = "PROJECT TREBLE ARCHITECTURE",
                    value = if (systemSecurity.trebleSupport) "Treble Enabled" else "Legacy Architecture",
                    icon = Icons.Default.Layers,
                    onCopy = { onCopy("Treble", if (systemSecurity.trebleSupport) "Yes" else "No") }
                )
                HorizontalDivider(color = Color(0x1FFFFFFF), thickness = 0.5.dp)

                BerserkDetailRow(
                    title = "JAVA RUNTIME ENVIRONMENT",
                    value = systemSecurity.javaVm,
                    icon = Icons.Default.Code,
                    onCopy = { onCopy("Java VM", systemSecurity.javaVm) }
                )
            }
        }
    }
}
