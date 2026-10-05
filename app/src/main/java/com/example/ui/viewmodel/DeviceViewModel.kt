package com.example.ui.viewmodel

import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.DeviceRepository
import com.example.data.DiagnosticManager
import com.example.model.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DeviceViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = DeviceRepository(application)
    private val diagnosticManager = DiagnosticManager(application)

    private val _selectedTab = MutableStateFlow(NavTab.DASHBOARD)
    val selectedTab: StateFlow<NavTab> = _selectedTab.asStateFlow()

    private val _generalInfo = MutableStateFlow(repository.getGeneralInfo())
    val generalInfo: StateFlow<GeneralInfo> = _generalInfo.asStateFlow()

    private val _cpuInfo = MutableStateFlow(repository.getCpuInfo())
    val cpuInfo: StateFlow<CpuInfo> = _cpuInfo.asStateFlow()

    private val _memoryInfo = MutableStateFlow(repository.getMemoryInfo())
    val memoryInfo: StateFlow<MemoryInfoData> = _memoryInfo.asStateFlow()

    private val _batteryInfo = MutableStateFlow(repository.getBatteryInfo())
    val batteryInfo: StateFlow<BatteryInfoData> = _batteryInfo.asStateFlow()

    private val _displayInfo = MutableStateFlow(repository.getDisplayInfo())
    val displayInfo: StateFlow<DisplayInfoData> = _displayInfo.asStateFlow()

    private val _sensorsList = MutableStateFlow(repository.getSensorsList())
    val sensorsList: StateFlow<List<SensorItem>> = _sensorsList.asStateFlow()

    private val _liveTelemetry = MutableStateFlow(LiveSensorTelemetry())
    val liveTelemetry: StateFlow<LiveSensorTelemetry> = _liveTelemetry.asStateFlow()

    private val _networkInfo = MutableStateFlow(repository.getNetworkInfo())
    val networkInfo: StateFlow<NetworkInfoData> = _networkInfo.asStateFlow()

    private val _systemSecurity = MutableStateFlow(repository.getSystemSecurityInfo())
    val systemSecurity: StateFlow<SystemSecurityInfo> = _systemSecurity.asStateFlow()

    // Interactive Test States
    private val _isScreenTestActive = MutableStateFlow(false)
    val isScreenTestActive: StateFlow<Boolean> = _isScreenTestActive.asStateFlow()

    private val _isMultiTouchTestActive = MutableStateFlow(false)
    val isMultiTouchTestActive: StateFlow<Boolean> = _isMultiTouchTestActive.asStateFlow()

    private val _isTorchActive = MutableStateFlow(false)
    val isTorchActive: StateFlow<Boolean> = _isTorchActive.asStateFlow()

    private val _isTonePlaying = MutableStateFlow(false)
    val isTonePlaying: StateFlow<Boolean> = _isTonePlaying.asStateFlow()

    init {
        // Real-time sensor stream
        viewModelScope.launch {
            repository.observeLiveSensors().collect { telemetry ->
                _liveTelemetry.value = telemetry
            }
        }

        // Periodic telemetry refresher
        viewModelScope.launch {
            while (true) {
                delay(1200)
                refreshDynamicStats()
            }
        }
    }

    fun selectTab(tab: NavTab) {
        _selectedTab.value = tab
    }

    fun refreshAll() {
        _generalInfo.value = repository.getGeneralInfo()
        _cpuInfo.value = repository.getCpuInfo()
        _memoryInfo.value = repository.getMemoryInfo()
        _batteryInfo.value = repository.getBatteryInfo()
        _displayInfo.value = repository.getDisplayInfo()
        _sensorsList.value = repository.getSensorsList()
        _networkInfo.value = repository.getNetworkInfo()
        _systemSecurity.value = repository.getSystemSecurityInfo()
    }

    private fun refreshDynamicStats() {
        _generalInfo.value = repository.getGeneralInfo()
        _cpuInfo.value = repository.getCpuInfo()
        _memoryInfo.value = repository.getMemoryInfo()
        _batteryInfo.value = repository.getBatteryInfo()
        _networkInfo.value = repository.getNetworkInfo()
    }

    // Diagnostics Controls
    fun startScreenTest() {
        _isScreenTestActive.value = true
    }

    fun stopScreenTest() {
        _isScreenTestActive.value = false
    }

    fun startMultiTouchTest() {
        _isMultiTouchTestActive.value = true
    }

    fun stopMultiTouchTest() {
        _isMultiTouchTestActive.value = false
    }

    fun testVibration(pattern: Int = 0) {
        diagnosticManager.triggerVibration(pattern)
    }

    fun toggleTorch() {
        val newState = !_isTorchActive.value
        val success = diagnosticManager.toggleFlashlight(newState)
        if (success) {
            _isTorchActive.value = newState
        } else {
            Toast.makeText(getApplication(), "Flashlight hardware unavailable", Toast.LENGTH_SHORT).show()
        }
    }

    fun playAudioBeep() {
        viewModelScope.launch {
            _isTonePlaying.value = true
            diagnosticManager.playTestTone(440, 1000)
            delay(1050)
            _isTonePlaying.value = false
        }
    }

    fun copyToClipboard(label: String, text: String) {
        val cm = getApplication<Application>().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        cm.setPrimaryClip(clip)
        Toast.makeText(getApplication(), "Copied $label to clipboard", Toast.LENGTH_SHORT).show()
    }

    fun generateReportText(): String {
        val gen = _generalInfo.value
        val cpu = _cpuInfo.value
        val mem = _memoryInfo.value
        val bat = _batteryInfo.value
        val disp = _displayInfo.value
        val net = _networkInfo.value
        val sec = _systemSecurity.value

        return buildString {
            appendLine("==============================================")
            appendLine("   BERSERK DEVICE INFO NEXTGEN - HARDWARE TOME")
            appendLine("==============================================")
            appendLine("Timestamp: ${System.currentTimeMillis()}")
            appendLine()
            appendLine("[DEVICE & OS]")
            appendLine("Device Model: ${gen.manufacturer} ${gen.model} (${gen.device})")
            appendLine("Brand / Board: ${gen.brand} / ${gen.board}")
            appendLine("Android OS: ${gen.androidVersion} (${gen.codeName})")
            appendLine("API Level: ${gen.apiLevel}")
            appendLine("Security Patch: ${gen.securityPatch}")
            appendLine("Build ID: ${gen.buildId}")
            appendLine("Kernel: ${gen.kernelVersion}")
            appendLine("System Uptime: ${gen.uptimeFormatted}")
            appendLine()
            appendLine("[PROCESSOR]")
            appendLine("Processor: ${cpu.processorName}")
            appendLine("Architecture: ${cpu.architecture} (64-bit: ${cpu.is64Bit})")
            appendLine("Core Count: ${cpu.coreCount}")
            appendLine("Governor: ${cpu.governor}")
            appendLine("Supported ABIs: ${cpu.supportedAbis.joinToString(", ")}")
            appendLine()
            appendLine("[MEMORY & STORAGE]")
            appendLine("RAM Total: ${mem.totalRamBytes / (1024 * 1024)} MB")
            appendLine("RAM Used: ${mem.usedRamBytes / (1024 * 1024)} MB (${String.format("%.1f", mem.ramUsedPercent)}%)")
            appendLine("RAM Free: ${mem.availableRamBytes / (1024 * 1024)} MB")
            appendLine("Storage Total: ${mem.totalStorageBytes / (1024 * 1024 * 1024)} GB")
            appendLine("Storage Used: ${mem.usedStorageBytes / (1024 * 1024 * 1024)} GB (${String.format("%.1f", mem.storageUsedPercent)}%)")
            appendLine("Storage Free: ${mem.availableStorageBytes / (1024 * 1024 * 1024)} GB")
            appendLine()
            appendLine("[BATTERY & POWER]")
            appendLine("Level: ${bat.percentage}%")
            appendLine("Status: ${bat.status} (${bat.plugged})")
            appendLine("Health: ${bat.health}")
            appendLine("Voltage: ${bat.voltageMillivolts} mV")
            appendLine("Temperature: ${bat.temperatureCelsius} °C")
            appendLine("Technology: ${bat.technology}")
            appendLine()
            appendLine("[DISPLAY]")
            appendLine("Resolution: ${disp.widthPixels} x ${disp.heightPixels}")
            appendLine("Physical Size: ~${disp.physicalSizeInches}\"")
            appendLine("Refresh Rate: ${disp.refreshRateHz} Hz")
            appendLine("Density: ${disp.densityDpi} DPI (${disp.densityBucket})")
            appendLine("HDR Supported: ${disp.isHdrSupported}")
            appendLine()
            appendLine("[NETWORK]")
            appendLine("Status: ${if (net.isConnected) "Connected" else "Disconnected"}")
            appendLine("Type: ${net.connectionType}")
            appendLine("IPv4: ${net.ipv4Address}")
            appendLine("Wi-Fi SSID: ${net.wifiSsid}")
            appendLine("Link Speed: ${net.wifiLinkSpeed}")
            appendLine()
            appendLine("[SECURITY & RUNTIME]")
            appendLine("SELinux: ${sec.selinuxStatus}")
            appendLine("Bootloader: ${sec.bootloader}")
            appendLine("Java VM: ${sec.javaVm}")
            appendLine("Root Access: ${if (sec.isRooted) "Detected" else "None"}")
            appendLine("Treble Support: ${sec.trebleSupport}")
            appendLine("Total Sensors: ${_sensorsList.value.size}")
            appendLine("==============================================")
        }
    }

    fun shareReport() {
        val report = generateReportText()
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, report)
            putExtra(Intent.EXTRA_TITLE, "Berserk Device Info Hardware Report")
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, "Share Device Info Report")
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        getApplication<Application>().startActivity(shareIntent)
    }

    override fun onCleared() {
        super.onCleared()
        diagnosticManager.stopTestTone()
        if (_isTorchActive.value) {
            diagnosticManager.toggleFlashlight(false)
        }
    }
}
