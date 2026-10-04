package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.model.NavTab
import com.example.ui.components.BerserkAtmosphericBackground
import com.example.ui.components.BrandOfSacrificeCanvas
import com.example.ui.screens.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.DeviceViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: DeviceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {
                val selectedTab by viewModel.selectedTab.collectAsStateWithLifecycle()
                val generalInfo by viewModel.generalInfo.collectAsStateWithLifecycle()
                val cpuInfo by viewModel.cpuInfo.collectAsStateWithLifecycle()
                val memoryInfo by viewModel.memoryInfo.collectAsStateWithLifecycle()
                val batteryInfo by viewModel.batteryInfo.collectAsStateWithLifecycle()
                val displayInfo by viewModel.displayInfo.collectAsStateWithLifecycle()
                val sensorsList by viewModel.sensorsList.collectAsStateWithLifecycle()
                val liveTelemetry by viewModel.liveTelemetry.collectAsStateWithLifecycle()
                val networkInfo by viewModel.networkInfo.collectAsStateWithLifecycle()
                val systemSecurity by viewModel.systemSecurity.collectAsStateWithLifecycle()

                val isScreenTestActive by viewModel.isScreenTestActive.collectAsStateWithLifecycle()
                val isMultiTouchTestActive by viewModel.isMultiTouchTestActive.collectAsStateWithLifecycle()
                val isTorchActive by viewModel.isTorchActive.collectAsStateWithLifecycle()
                val isTonePlaying by viewModel.isTonePlaying.collectAsStateWithLifecycle()

                // BackHandler: Return to Dashboard if on another sub-screen
                BackHandler(enabled = selectedTab != NavTab.DASHBOARD) {
                    viewModel.selectTab(NavTab.DASHBOARD)
                }

                BerserkAtmosphericBackground {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        containerColor = Color.Transparent,
                        topBar = {
                            BerserkTopAppBar(
                                selectedTab = selectedTab,
                                onRefresh = { viewModel.refreshAll() },
                                onShare = { viewModel.shareReport() }
                            )
                        },
                        bottomBar = {
                            BerserkBottomNav(
                                selectedTab = selectedTab,
                                onSelectTab = { viewModel.selectTab(it) }
                            )
                        }
                    ) { innerPadding ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                        ) {
                            // Sub-tabs scrollable bar for complete module access
                            TabsScrollRow(
                                selectedTab = selectedTab,
                                onSelectTab = { viewModel.selectTab(it) }
                            )

                            Crossfade(
                                targetState = selectedTab,
                                animationSpec = tween(300),
                                label = "tabCrossfade"
                            ) { tab ->
                                when (tab) {
                                    NavTab.DASHBOARD -> DashboardScreen(
                                        generalInfo = generalInfo,
                                        cpuInfo = cpuInfo,
                                        memoryInfo = memoryInfo,
                                        batteryInfo = batteryInfo,
                                        displayInfo = displayInfo,
                                        onNavigateTab = { viewModel.selectTab(it) },
                                        onCopy = { label, text -> viewModel.copyToClipboard(label, text) }
                                    )
                                    NavTab.CPU -> CpuScreen(
                                        cpuInfo = cpuInfo,
                                        onCopy = { label, text -> viewModel.copyToClipboard(label, text) }
                                    )
                                    NavTab.MEMORY -> MemoryScreen(
                                        memoryInfo = memoryInfo,
                                        onCopy = { label, text -> viewModel.copyToClipboard(label, text) }
                                    )
                                    NavTab.BATTERY -> BatteryScreen(
                                        batteryInfo = batteryInfo,
                                        onCopy = { label, text -> viewModel.copyToClipboard(label, text) }
                                    )
                                    NavTab.DISPLAY -> DisplayScreen(
                                        displayInfo = displayInfo,
                                        onCopy = { label, text -> viewModel.copyToClipboard(label, text) }
                                    )
                                    NavTab.SENSORS -> SensorsScreen(
                                        sensorsList = sensorsList,
                                        liveTelemetry = liveTelemetry,
                                        onCopy = { label, text -> viewModel.copyToClipboard(label, text) }
                                    )
                                    NavTab.NETWORK -> NetworkScreen(
                                        networkInfo = networkInfo,
                                        onCopy = { label, text -> viewModel.copyToClipboard(label, text) }
                                    )
                                    NavTab.SYSTEM -> SystemScreen(
                                        generalInfo = generalInfo,
                                        systemSecurity = systemSecurity,
                                        onCopy = { label, text -> viewModel.copyToClipboard(label, text) }
                                    )
                                    NavTab.TESTS -> TestsScreen(
                                        isScreenTestActive = isScreenTestActive,
                                        isMultiTouchTestActive = isMultiTouchTestActive,
                                        isTorchActive = isTorchActive,
                                        isTonePlaying = isTonePlaying,
                                        onStartScreenTest = { viewModel.startScreenTest() },
                                        onStopScreenTest = { viewModel.stopScreenTest() },
                                        onStartMultiTouchTest = { viewModel.startMultiTouchTest() },
                                        onStopMultiTouchTest = { viewModel.stopMultiTouchTest() },
                                        onTestVibration = { viewModel.testVibration(it) },
                                        onToggleTorch = { viewModel.toggleTorch() },
                                        onPlayAudioTone = { viewModel.playAudioBeep() }
                                    )
                                    NavTab.REPORT -> ReportScreen(
                                        reportText = viewModel.generateReportText(),
                                        onCopyReport = {
                                            viewModel.copyToClipboard("Hardware Report", viewModel.generateReportText())
                                        },
                                        onShareReport = { viewModel.shareReport() }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BerserkTopAppBar(
    selectedTab: NavTab,
    onRefresh: () -> Unit,
    onShare: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BrandOfSacrificeCanvas(
            modifier = Modifier
                .size(38.dp)
                .testTag("app_bar_brand_icon")
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "DEVICE INFO NEXTGEN",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontFamily = CinzelFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    letterSpacing = 1.sp,
                    fontSize = 16.sp
                )
            )
            Text(
                text = selectedTab.subtitle.uppercase(),
                style = MaterialTheme.typography.labelSmall.copy(
                    fontFamily = RajdhaniFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = BerserkCrimsonGlow,
                    letterSpacing = 0.8.sp,
                    fontSize = 11.sp
                )
            )
        }

        IconButton(
            onClick = onRefresh,
            modifier = Modifier
                .size(48.dp)
                .testTag("app_bar_refresh_btn")
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Refresh Telemetry",
                tint = BerserkSteel,
                modifier = Modifier.size(20.dp)
            )
        }

        IconButton(
            onClick = onShare,
            modifier = Modifier
                .size(48.dp)
                .testTag("app_bar_share_btn")
        ) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = "Share Hardware Report",
                tint = BerserkBloodRed,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
private fun TabsScrollRow(
    selectedTab: NavTab,
    onSelectTab: (NavTab) -> Unit
) {
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState)
            .padding(horizontal = 12.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        NavTab.entries.forEach { tab ->
            val isSelected = tab == selectedTab
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .border(
                        width = 1.dp,
                        color = if (isSelected) BerserkBloodRed else Color(0x2890A4AE),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .background(
                        if (isSelected) {
                            Brush.linearGradient(
                                listOf(Color(0x66FF1744), Color(0x33B71C1C))
                            )
                        } else {
                            Brush.linearGradient(
                                listOf(Color(0x22251620), Color(0x18120B13))
                            )
                        }
                    )
                    .clickable { onSelectTab(tab) }
                    .padding(horizontal = 14.dp, vertical = 6.dp)
                    .testTag("tab_${tab.name.lowercase()}"),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = tab.title.uppercase(),
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontFamily = CinzelFontFamily,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) Color.White else BerserkSteelDim,
                        letterSpacing = 0.5.sp,
                        fontSize = 11.sp
                    )
                )
            }
        }
    }
}

@Composable
private fun BerserkBottomNav(
    selectedTab: NavTab,
    onSelectTab: (NavTab) -> Unit
) {
    val primaryTabs = listOf(
        Pair(NavTab.DASHBOARD, Icons.Default.Dashboard),
        Pair(NavTab.CPU, Icons.Default.Memory),
        Pair(NavTab.BATTERY, Icons.Default.BatteryChargingFull),
        Pair(NavTab.SENSORS, Icons.Default.Sensors),
        Pair(NavTab.TESTS, Icons.Default.Hardware)
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.navigationBars),
        color = Color(0xCC0D080E),
        tonalElevation = 6.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            primaryTabs.forEach { (tab, icon) ->
                val isSelected = tab == selectedTab
                IconButton(
                    onClick = { onSelectTab(tab) },
                    modifier = Modifier
                        .size(48.dp)
                        .testTag("bottom_nav_${tab.name.lowercase()}")
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = tab.title,
                            tint = if (isSelected) BerserkBloodRed else BerserkSteelDim,
                            modifier = Modifier.size(22.dp)
                        )
                        if (isSelected) {
                            Box(
                                modifier = Modifier
                                    .padding(top = 2.dp)
                                    .size(4.dp)
                                    .clip(CircleShape)
                                    .background(BerserkCrimsonGlow)
                            )
                        }
                    }
                }
            }
        }
    }
}
