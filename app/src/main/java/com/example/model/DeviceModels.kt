package com.example.model

data class GeneralInfo(
    val model: String,
    val manufacturer: String,
    val brand: String,
    val device: String,
    val board: String,
    val hardware: String,
    val product: String,
    val androidVersion: String,
    val apiLevel: Int,
    val codeName: String,
    val securityPatch: String,
    val buildId: String,
    val fingerprint: String,
    val kernelVersion: String,
    val uptimeFormatted: String
)

data class CpuCoreInfo(
    val coreIndex: Int,
    val currentFreqKHz: Long,
    val maxFreqKHz: Long,
    val minFreqKHz: Long
)

data class CpuInfo(
    val processorName: String,
    val architecture: String,
    val supportedAbis: List<String>,
    val is64Bit: Boolean,
    val coreCount: Int,
    val governor: String,
    val cores: List<CpuCoreInfo>,
    val bogoMips: String
)

data class MemoryInfoData(
    val totalRamBytes: Long,
    val availableRamBytes: Long,
    val usedRamBytes: Long,
    val ramUsedPercent: Float,
    val isLowMemory: Boolean,
    val lowMemoryThresholdBytes: Long,
    val totalStorageBytes: Long,
    val availableStorageBytes: Long,
    val usedStorageBytes: Long,
    val storageUsedPercent: Float,
    val jvmMaxMemoryBytes: Long,
    val jvmTotalMemoryBytes: Long,
    val jvmFreeMemoryBytes: Long
)

data class BatteryInfoData(
    val percentage: Int,
    val status: String,
    val health: String,
    val plugged: String,
    val temperatureCelsius: Float,
    val voltageMillivolts: Int,
    val technology: String,
    val isCharging: Boolean
)

data class DisplayInfoData(
    val widthPixels: Int,
    val heightPixels: Int,
    val refreshRateHz: Float,
    val densityDpi: Int,
    val densityBucket: String,
    val densityFactor: Float,
    val physicalSizeInches: Float,
    val xdpi: Float,
    val ydpi: Float,
    val isHdrSupported: Boolean,
    val isWideColorGamut: Boolean
)

data class SensorItem(
    val id: Int,
    val name: String,
    val typeName: String,
    val vendor: String,
    val version: Int,
    val powerMa: Float,
    val maxRange: Float,
    val resolution: Float
)

data class LiveSensorTelemetry(
    val accelX: Float = 0f,
    val accelY: Float = 0f,
    val accelZ: Float = 0f,
    val gyroX: Float = 0f,
    val gyroY: Float = 0f,
    val gyroZ: Float = 0f,
    val magX: Float = 0f,
    val magY: Float = 0f,
    val magZ: Float = 0f,
    val azimuthDegrees: Float = 0f,
    val lightLux: Float = 0f,
    val proximityCm: Float = 0f
)

data class NetworkInfoData(
    val isConnected: Boolean,
    val connectionType: String,
    val isMetered: Boolean,
    val ipv4Address: String,
    val ipv6Address: String,
    val wifiSsid: String,
    val wifiLinkSpeed: String,
    val wifiFrequency: String
)

data class SystemSecurityInfo(
    val bootloader: String,
    val selinuxStatus: String,
    val javaVm: String,
    val playServicesAvailable: Boolean,
    val isRooted: Boolean,
    val trebleSupport: Boolean
)

enum class NavTab(val title: String, val subtitle: String) {
    DASHBOARD("Eclipse Core", "Hardware Overview"),
    CPU("Dragon Slayer", "CPU & Architecture"),
    MEMORY("Abyssal Vault", "RAM & Storage"),
    BATTERY("Brand Flame", "Power & Health"),
    DISPLAY("Behelit Eye", "Screen & Graphics"),
    SENSORS("Astral Realm", "Hardware Sensors"),
    NETWORK("Hawk Comms", "Network & WiFi"),
    SYSTEM("Brand of Fate", "OS & Security"),
    TESTS("Berserk Trial", "Hardware Diagnostics"),
    REPORT("Sacrifice Tome", "Export Diagnostics")
}
