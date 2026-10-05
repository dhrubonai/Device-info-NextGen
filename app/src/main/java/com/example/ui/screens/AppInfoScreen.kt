package com.example.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
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
import com.example.ui.components.BrandOfSacrificeCanvas
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

    // Breathing ring animation for developer photo
    val infiniteTransition = rememberInfiniteTransition(label = "dev_photo_pulse")
    val borderPulse by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "borderPulse"
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 96.dp)
    ) {
        // Prestige Developer Profile Card
        item {
            LiquidGlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("developer_hero_card"),
                cornerRadius = 26.dp,
                borderColor = BerserkCrimsonGlow
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Status Badge
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color(0x3300E676))
                            .border(1.dp, Color(0x6600E676), RoundedCornerShape(20.dp))
                            .padding(horizontal = 14.dp, vertical = 4.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(BerserkEmeraldSafe)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "ARCHITECT & LEAD DEVELOPER",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontFamily = CinzelFontFamily,
                                    color = Color.White,
                                    letterSpacing = 1.sp,
                                    fontSize = 10.sp
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Developer Avatar with Animated Multi-Ring Bezel
                    Box(
                        modifier = Modifier
                            .size(118.dp)
                            .drawBehind {
                                drawCircle(
                                    brush = Brush.radialGradient(
                                        colors = listOf(
                                            Color(0xFFFF1744).copy(alpha = 0.35f * borderPulse),
                                            Color(0xFFFFD700).copy(alpha = 0.15f * borderPulse),
                                            Color.Transparent
                                        )
                                    ),
                                    radius = size.width * 0.72f
                                )
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(108.dp)
                                .clip(CircleShape)
                                .border(
                                    width = 3.dp,
                                    brush = Brush.sweepGradient(
                                        listOf(
                                            BerserkBloodRed,
                                            BerserkBehelitGold,
                                            BerserkCrimsonGlow,
                                            BerserkBloodRed
                                        )
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
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Developer Name
                    Text(
                        text = "Mohiuddin Abdul Kadir Dhrubo",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontFamily = CinzelFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 20.sp,
                            letterSpacing = 0.5.sp
                        )
                    )

                    Text(
                        text = "@dhrubonai • 創造者 (Creator & Architect)",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontFamily = RajdhaniFontFamily,
                            color = BerserkCrimsonGlow,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Author's Personal Note Block
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                Brush.verticalGradient(
                                    listOf(Color(0x3D3A1B28), Color(0x241E0F17))
                                )
                            )
                            .border(1.dp, Color(0x35FF1744), RoundedCornerShape(16.dp))
                            .padding(16.dp)
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.FormatQuote,
                                    contentDescription = "Quote",
                                    tint = BerserkBehelitGold,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "FROM THE DEVELOPER",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontFamily = CinzelFontFamily,
                                        color = BerserkBehelitGold,
                                        letterSpacing = 1.sp,
                                        fontSize = 11.sp
                                    )
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "\"Device Info NextGen is an app that I have created for myself actually in my free time. It's a hobby project but you guys can use it. And if you guys love it then share it up and star the repo, and you guys can modify it and use it up!\"",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontFamily = RajdhaniFontFamily,
                                    color = Color(0xFFF0F0F0),
                                    fontSize = 14.sp,
                                    lineHeight = 21.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "— Mohiuddin Abdul Kadir Dhrubo",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontFamily = CinzelFontFamily,
                                    color = BerserkCrimsonGlow,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                ),
                                modifier = Modifier.align(Alignment.End)
                            )
                        }
                    }
                }
            }
        }

        // Connect & Social Matrix
        item {
            LiquidGlassCard(modifier = Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Connect",
                        tint = BerserkCrimsonGlow,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "COMMUNICATION & CHANNELS",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontFamily = CinzelFontFamily,
                            color = BerserkCrimsonGlow,
                            letterSpacing = 1.sp
                        )
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    SocialTile(
                        title = "GitHub Profile",
                        subtitle = "github.com/dhrubonai",
                        badge = "DEVELOPER",
                        icon = Icons.Default.Code,
                        accentColor = Color(0xFF24292E),
                        onClick = { openUrl("https://github.com/dhrubonai") }
                    )

                    SocialTile(
                        title = "GitHub Repository",
                        subtitle = "Device-info-NextGen (Star & Fork)",
                        badge = "OPEN SOURCE",
                        icon = Icons.Default.Star,
                        accentColor = BerserkBloodRed,
                        onClick = { openUrl("https://github.com/dhrubonai/Device-info-NextGen") }
                    )

                    SocialTile(
                        title = "Telegram Channel",
                        subtitle = "t.me/dhrubo_moira_geche",
                        badge = "COMMUNITY",
                        icon = Icons.Default.Send,
                        accentColor = Color(0xFF0088CC),
                        onClick = { openUrl("https://t.me/dhrubo_moira_geche") }
                    )

                    SocialTile(
                        title = "Instagram",
                        subtitle = "@dhrubo_morse",
                        badge = "PERSONAL",
                        icon = Icons.Default.CameraAlt,
                        accentColor = Color(0xFFC13584),
                        onClick = { openUrl("https://www.instagram.com/dhrubo_morse?igsh=aHNsazZyOTY4dWN2") }
                    )

                    SocialTile(
                        title = "Facebook",
                        subtitle = "Mohiuddin Abdul Kadir Dhrubo",
                        badge = "PROFILE",
                        icon = Icons.Default.Public,
                        accentColor = Color(0xFF1877F2),
                        onClick = { openUrl("https://www.facebook.com/share/1LLhAmRUp2/") }
                    )

                    SocialTile(
                        title = "Direct Email",
                        subtitle = "dhrubobear@gmail.com",
                        badge = "INQUIRIES",
                        icon = Icons.Default.Email,
                        accentColor = Color(0xFF37474F),
                        onClick = { openUrl("mailto:dhrubobear@gmail.com") }
                    )
                }
            }
        }

        // About Device Info NextGen Card
        item {
            LiquidGlassCard(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "ABOUT THE APPLICATION",
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
                        lineHeight = 22.sp,
                        fontWeight = FontWeight.Medium
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                // App Specs Matrix
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0x22FFFFFF))
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    SpecLine("App Codename", "Device Info NextGen (Berserk Anime Edition)")
                    SpecLine("Version", "v1.2.0 (Official Release)")
                    SpecLine("UI Style", "Liquid Glassmorphism • iOS Spring Physics")
                    SpecLine("Typography", "Cinzel & Rajdhani")
                    SpecLine("Architecture", "MVVM • Jetpack Compose • Kotlin Flow")
                }
            }
        }

        // Zero-Data Privacy Policy
        item {
            LiquidGlassCard(
                modifier = Modifier.fillMaxWidth(),
                borderColor = Color(0x5500E676)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = "Privacy Shield",
                        tint = BerserkEmeraldSafe,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "ZERO DATA PRIVACY POLICY",
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
                modifier = Modifier.fillMaxWidth(),
                borderColor = Color(0x55FFD700)
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
private fun SocialTile(
    title: String,
    subtitle: String,
    badge: String,
    icon: ImageVector,
    accentColor: Color,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    val scaleAnim by animateFloatAsState(
        targetValue = if (isPressed) 0.96f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "tileScale"
    )

    Surface(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .scale(scaleAnim),
        shape = RoundedCornerShape(14.dp),
        color = Color(0x261F121C),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x2890A4AE))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(accentColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontFamily = CinzelFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 13.sp
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(accentColor.copy(alpha = 0.35f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = badge,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontFamily = RajdhaniFontFamily,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 9.sp
                            )
                        )
                    }
                }

                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontFamily = RajdhaniFontFamily,
                        color = BerserkSteelDim,
                        fontSize = 12.sp
                    )
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Open Link",
                tint = BerserkSteelDim,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
private fun SpecLine(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
                fontFamily = CinzelFontFamily,
                color = BerserkSteelDim,
                fontSize = 11.sp
            )
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall.copy(
                fontFamily = RajdhaniFontFamily,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                fontSize = 12.sp
            )
        )
    }
}
