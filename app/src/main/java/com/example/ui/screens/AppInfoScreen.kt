package com.example.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.components.LiquidGlassCard
import com.example.ui.theme.*

@Composable
fun AppInfoScreen(
    onCopy: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    fun openUrl(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (_: Exception) {
            Toast.makeText(context, "Could not open link", Toast.LENGTH_SHORT).show()
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 96.dp)
    ) {
        // Developer Profile Hero Card
        item {
            LiquidGlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("developer_hero_card"),
                cornerRadius = 24.dp
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Developer Photo
                    Box(
                        modifier = Modifier
                            .size(86.dp)
                            .clip(CircleShape)
                            .border(
                                width = 2.dp,
                                brush = Brush.linearGradient(
                                    listOf(BerserkBloodRed, BerserkBehelitGold, BerserkCrimsonGlow)
                                ),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.developer_photo),
                            contentDescription = "Mohiuddin Abdul Kadir Dhrubo",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "CREATOR & DEVELOPER",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontFamily = CinzelFontFamily,
                                color = BerserkCrimsonGlow,
                                letterSpacing = 1.sp
                            )
                        )
                        Text(
                            text = "Mohiuddin Abdul Kadir Dhrubo",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontFamily = CinzelFontFamily,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 18.sp
                            )
                        )
                        Text(
                            text = "GitHub: @dhrubonai",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontFamily = RajdhaniFontFamily,
                                color = BerserkCyanPulse,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Developer's Personal Message
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0x33000000))
                        .padding(14.dp)
                ) {
                    Text(
                        text = "\"Device Info NextGen is an app that I have created for myself actually in my free time. It's a hobby project but you guys can use it. And if you guys love it then share it up and star the repo, and you guys can modify it and use it up!\"",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontFamily = RajdhaniFontFamily,
                            color = BerserkSteel,
                            fontSize = 14.sp,
                            lineHeight = 20.sp
                        )
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Social Media & Contact Links
                Text(
                    text = "CONNECT WITH THE DEVELOPER",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontFamily = CinzelFontFamily,
                        color = BerserkSteelDim,
                        letterSpacing = 0.5.sp
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    SocialLinkButton(
                        label = "GitHub Profile (@dhrubonai)",
                        icon = Icons.Default.Code,
                        color = Color(0xFF24292E),
                        onClick = { openUrl("https://github.com/dhrubonai") }
                    )
                    SocialLinkButton(
                        label = "GitHub Repository (Star & Fork)",
                        icon = Icons.Default.Star,
                        color = BerserkDeepBlood,
                        onClick = { openUrl("https://github.com/dhrubonai/Device-info-NextGen") }
                    )
                    SocialLinkButton(
                        label = "Telegram Channel (dhrubo_moira_geche)",
                        icon = Icons.Default.Send,
                        color = Color(0xFF0088CC),
                        onClick = { openUrl("https://t.me/dhrubo_moira_geche") }
                    )
                    SocialLinkButton(
                        label = "Instagram (@dhrubo_morse)",
                        icon = Icons.Default.CameraAlt,
                        color = Color(0xFFC13584),
                        onClick = { openUrl("https://www.instagram.com/dhrubo_morse?igsh=aHNsazZyOTY4dWN2") }
                    )
                    SocialLinkButton(
                        label = "Facebook Profile",
                        icon = Icons.Default.Share,
                        color = Color(0xFF1877F2),
                        onClick = { openUrl("https://www.facebook.com/share/1LLhAmRUp2/") }
                    )
                    SocialLinkButton(
                        label = "Email: dhrubobear@gmail.com",
                        icon = Icons.Default.Email,
                        color = Color(0xFF37474F),
                        onClick = { openUrl("mailto:dhrubobear@gmail.com") }
                    )
                }
            }
        }

        // App Information Card
        item {
            LiquidGlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("app_info_details_card")
            ) {
                Text(
                    text = "ABOUT DEVICE INFO NEXTGEN",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontFamily = CinzelFontFamily,
                        color = BerserkCrimsonGlow,
                        letterSpacing = 1.sp
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "This app is about device info but with next generation with berserk with anime theme UI and other stuff. Is completely open source so other people can use it modify it.",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontFamily = RajdhaniFontFamily,
                        color = Color.White,
                        fontSize = 15.sp,
                        lineHeight = 22.sp
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Version: 1.1.0 (Berserk Anime Edition)\nPackage: com.example\nUI Design: Liquid Glassmorphism & M3\nFonts: Cinzel & Rajdhani",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontFamily = RajdhaniFontFamily,
                        color = BerserkSteelDim,
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    )
                )
            }
        }

        // Privacy Policy Card
        item {
            LiquidGlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("privacy_policy_card")
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Security,
                        contentDescription = "Privacy Shield",
                        tint = BerserkEmeraldSafe,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "ZERO DATA COLLECTION PRIVACY POLICY",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontFamily = CinzelFontFamily,
                            color = BerserkEmeraldSafe,
                            letterSpacing = 0.5.sp
                        )
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "We don't track anything or store anything. We don't take any data.",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontFamily = RajdhaniFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 16.sp
                    )
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "All system statistics, CPU telemetry, battery current rates, memory allocations, and hardware sensor readings are computed strictly in real time in volatile device memory via Android SDK APIs. Zero analytics SDKs, zero remote servers, and zero cloud databases are used.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = RajdhaniFontFamily,
                        color = BerserkSteelDim,
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    )
                )
            }
        }

        // Open Source & Credit Notice Card
        item {
            LiquidGlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("open_source_license_card")
            ) {
                Text(
                    text = "OPEN SOURCE & ATTRIBUTION NOTICE",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontFamily = CinzelFontFamily,
                        color = BerserkBehelitGold,
                        letterSpacing = 0.5.sp
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "This project is open-source. Anyone is free to use, copy, inspect, and modify the code for personal, academic, or commercial purposes. However, as an open-source project, you MUST give credit to Mohiuddin Abdul Kadir Dhrubo and link back to the original Device Info NextGen repository.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = RajdhaniFontFamily,
                        color = BerserkSteel,
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    )
                )
            }
        }
    }
}

@Composable
private fun SocialLinkButton(
    label: String,
    icon: ImageVector,
    color: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color),
        shape = RoundedCornerShape(10.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = Color.White,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = label,
            fontFamily = RajdhaniFontFamily,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 13.sp
        )
    }
}
