package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ui.components.LiquidGlassCard
import com.example.ui.theme.*

@Composable
fun TestsScreen(
    isScreenTestActive: Boolean,
    isMultiTouchTestActive: Boolean,
    isTorchActive: Boolean,
    isTonePlaying: Boolean,
    onStartScreenTest: () -> Unit,
    onStopScreenTest: () -> Unit,
    onStartMultiTouchTest: () -> Unit,
    onStopMultiTouchTest: () -> Unit,
    onTestVibration: (Int) -> Unit,
    onToggleTorch: () -> Unit,
    onPlayAudioTone: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Screen Dead Pixel Test Dialog
    if (isScreenTestActive) {
        FullScreenColorTestDialog(onDismiss = onStopScreenTest)
    }

    // Multi-Touch Test Dialog
    if (isMultiTouchTestActive) {
        MultiTouchTestDialog(onDismiss = onStopMultiTouchTest)
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 96.dp)
    ) {
        // Hero Header
        item {
            LiquidGlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("tests_hero_card"),
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
                            imageVector = Icons.Default.Hardware,
                            contentDescription = "Diagnostics",
                            tint = BerserkCrimsonGlow,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "TRIAL OF THE BERSERKER",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontFamily = CinzelFontFamily,
                                color = BerserkCrimsonGlow,
                                letterSpacing = 1.sp
                            )
                        )
                        Text(
                            text = "HARDWARE DIAGNOSTIC SUITE",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontFamily = CinzelFontFamily,
                                color = Color.White,
                                fontSize = 17.sp
                            )
                        )
                        Text(
                            text = "Test display pixels, digitizer, haptics, torch & speakers",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontFamily = RajdhaniFontFamily,
                                color = BerserkSteelDim
                            )
                        )
                    }
                }
            }
        }

        // Screen Color / Dead Pixel Test
        item {
            DiagnosticTestCard(
                title = "DISPLAY DEAD-PIXEL TEST",
                subtitle = "Cycle pure RGB, white, black, and crimson screens",
                icon = Icons.Default.AspectRatio,
                actionLabel = "LAUNCH SCREEN TEST",
                onClick = onStartScreenTest,
                testTag = "test_screen_btn"
            )
        }

        // Multi-touch Digitizer Test
        item {
            DiagnosticTestCard(
                title = "MULTI-TOUCH DIGITIZER TEST",
                subtitle = "Inspect multi-finger tracking and touch latency",
                icon = Icons.Default.TouchApp,
                actionLabel = "LAUNCH TOUCH TEST",
                onClick = onStartMultiTouchTest,
                testTag = "test_touch_btn"
            )
        }

        // Haptic / Vibration Motor Test
        item {
            LiquidGlassCard(modifier = Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0x33FF1744)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Vibration,
                            contentDescription = "Vibration",
                            tint = BerserkCrimsonGlow,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "HAPTIC MOTOR TRIAL",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontFamily = CinzelFontFamily,
                                color = Color.White
                            )
                        )
                        Text(
                            text = "Execute haptic vibrations and click patterns",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontFamily = RajdhaniFontFamily,
                                color = BerserkSteelDim
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { onTestVibration(0) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("test_vibe_single"),
                        colors = ButtonDefaults.buttonColors(containerColor = BerserkBloodRed)
                    ) {
                        Text("PULSE", fontFamily = RajdhaniFontFamily, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = { onTestVibration(1) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("test_vibe_triple"),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC62828))
                    ) {
                        Text("BURST", fontFamily = RajdhaniFontFamily, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = { onTestVibration(2) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("test_vibe_heavy"),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF37474F))
                    ) {
                        Text("CLICK", fontFamily = RajdhaniFontFamily, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Torch / Flashlight Test
        item {
            DiagnosticTestCard(
                title = "FLASHLIGHT / TORCH TEST",
                subtitle = "Toggle camera LED emitter flash",
                icon = Icons.Default.FlashlightOn,
                actionLabel = if (isTorchActive) "TURN OFF TORCH" else "IGNITE TORCH",
                isHighlighted = isTorchActive,
                onClick = onToggleTorch,
                testTag = "test_torch_btn"
            )
        }

        // Speaker Test Tone
        item {
            DiagnosticTestCard(
                title = "LOUDSPEAKER SOUND TEST",
                subtitle = "Generate 440 Hz sinusoidal audio tone via AudioTrack",
                icon = Icons.Default.VolumeUp,
                actionLabel = if (isTonePlaying) "PLAYING TONE..." else "PLAY 440 HZ BEEP",
                isHighlighted = isTonePlaying,
                onClick = onPlayAudioTone,
                testTag = "test_speaker_btn"
            )
        }
    }
}

@Composable
private fun DiagnosticTestCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    actionLabel: String,
    onClick: () -> Unit,
    testTag: String,
    isHighlighted: Boolean = false
) {
    LiquidGlassCard(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (isHighlighted) Color(0x55FFB300) else Color(0x33FF1744)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = if (isHighlighted) BerserkBehelitGold else BerserkCrimsonGlow,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontFamily = CinzelFontFamily,
                        color = Color.White
                    )
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontFamily = RajdhaniFontFamily,
                        color = BerserkSteelDim
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .testTag(testTag),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isHighlighted) BerserkBehelitGold else BerserkBloodRed,
                contentColor = if (isHighlighted) Color.Black else Color.White
            )
        ) {
            Text(
                text = actionLabel,
                fontFamily = RajdhaniFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                letterSpacing = 0.5.sp
            )
        }
    }
}

@Composable
private fun FullScreenColorTestDialog(onDismiss: () -> Unit) {
    val colors = listOf(
        Color.Red,
        Color.Green,
        Color.Blue,
        Color.White,
        Color.Black,
        Color(0xFFFF1744) // Berserk Blood Crimson
    )
    var currentIndex by remember { mutableIntStateOf(0) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        BackHandler { onDismiss() }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colors[currentIndex])
                .clickable {
                    if (currentIndex < colors.size - 1) {
                        currentIndex++
                    } else {
                        onDismiss()
                    }
                },
            contentAlignment = Alignment.BottomCenter
        ) {
            Box(
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0x99000000))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Tap to cycle colors (${currentIndex + 1}/${colors.size}) • Back to exit",
                    color = Color.White,
                    fontFamily = RajdhaniFontFamily,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
private fun MultiTouchTestDialog(onDismiss: () -> Unit) {
    val touches = remember { mutableStateMapOf<Long, Offset>() }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        BackHandler { onDismiss() }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BerserkObsidian)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = { offset ->
                            val id = System.currentTimeMillis()
                            touches[id] = offset
                            tryAwaitRelease()
                            touches.remove(id)
                        }
                    )
                }
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { offset ->
                            touches[1L] = offset
                        },
                        onDrag = { change, _ ->
                            touches[1L] = change.position
                        },
                        onDragEnd = {
                            touches.remove(1L)
                        }
                    )
                }
        ) {
            // Draw interactive touch points
            Canvas(modifier = Modifier.fillMaxSize()) {
                touches.values.forEachIndexed { index, pos ->
                    drawCircle(
                        color = Color(0x33FF1744),
                        radius = 48.dp.toPx(),
                        center = pos
                    )
                    drawCircle(
                        color = BerserkCrimsonGlow,
                        radius = 24.dp.toPx(),
                        center = pos
                    )
                    drawCircle(
                        color = Color.White,
                        radius = 6.dp.toPx(),
                        center = pos
                    )
                }
            }

            // Top Status Overlay
            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "MULTI-TOUCH DIGITIZER TEST",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontFamily = CinzelFontFamily,
                        color = BerserkCrimsonGlow
                    )
                )
                Text(
                    text = "Touch screen with multiple fingers • Count: ${touches.size}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = RajdhaniFontFamily,
                        color = Color.White
                    )
                )
            }

            // Bottom Exit Button
            Button(
                onClick = onDismiss,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BerserkBloodRed)
            ) {
                Text("EXIT TEST", fontFamily = RajdhaniFontFamily, fontWeight = FontWeight.Bold)
            }
        }
    }
}
