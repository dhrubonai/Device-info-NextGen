package com.example.data

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.os.BatteryManager
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.os.SystemClock
import android.util.DisplayMetrics
import android.view.WindowManager
import com.example.model.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.net.Inet4Address
import java.net.Inet6Address
import java.net.NetworkInterface
import java.util.Locale
import java.util.concurrent.TimeUnit
import kotlin.math.atan2
import kotlin.math.roundToInt
import kotlin.math.sqrt

class DeviceRepository(private val context: Context) {

    fun getGeneralInfo(): GeneralInfo {
        val uptimeMs = SystemClock.elapsedRealtime()
        val hours = TimeUnit.MILLISECONDS.toHours(uptimeMs)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(uptimeMs) % 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(uptimeMs) % 60
        val uptimeFormatted = String.format(Locale.US, "%02dh %02dm %02ds", hours, minutes, seconds)

        val codeName = when (Build.VERSION.SDK_INT) {
            36 -> "Android 16 (Baklava)"
            35 -> "Android 15 (Vanilla Ice Cream)"
            34 -> "Android 14 (Upside Down Cake)"
            33 -> "Android 13 (Tiramisu)"
            32, 31 -> "Android 12 (Snow Cone)"
            30 -> "Android 11 (Red Velvet Cake)"
            29 -> "Android 10 (Quince Tart)"
            28 -> "Android 9 (Pie)"
            else -> "Android API ${Build.VERSION.SDK_INT}"
        }

        return GeneralInfo(
            model = Build.MODEL ?: "Unknown",
            manufacturer = Build.MANUFACTURER.replaceFirstChar { it.uppercase() },
            brand = Build.BRAND.replaceFirstChar { it.uppercase() },
            device = Build.DEVICE ?: "Unknown",
            board = Build.BOARD ?: "Unknown",
            hardware = Build.HARDWARE ?: "Unknown",
            product = Build.PRODUCT ?: "Unknown",
            androidVersion = Build.VERSION.RELEASE ?: "Unknown",
            apiLevel = Build.VERSION.SDK_INT,
            codeName = codeName,
            securityPatch = Build.VERSION.SECURITY_PATCH ?: "Not Available",
            buildId = Build.ID ?: "Unknown",
            fingerprint = Build.FINGERPRINT ?: "Unknown",
            kernelVersion = getKernelVersion(),
            uptimeFormatted = uptimeFormatted
        )
    }

    fun getCpuInfo(): CpuInfo {
        val coreCount = Runtime.getRuntime().availableProcessors()
        val cores = mutableListOf<CpuCoreInfo>()
        var governor = "Unknown"

        for (i in 0 until coreCount) {
            val curFreq = readFrequency("/sys/devices/system/cpu/cpu$i/cpufreq/scaling_cur_freq")
                .ifZero { readFrequency("/sys/devices/system/cpu/cpu$i/cpufreq/cpuinfo_cur_freq") }
            val maxFreq = readFrequency("/sys/devices/system/cpu/cpu$i/cpufreq/cpuinfo_max_freq")
            val minFreq = readFrequency("/sys/devices/system/cpu/cpu$i/cpufreq/cpuinfo_min_freq")
            cores.add(CpuCoreInfo(coreIndex = i, currentFreqKHz = curFreq, maxFreqKHz = maxFreq, minFreqKHz = minFreq))
            if (governor == "Unknown") {
                governor = readFirstLine("/sys/devices/system/cpu/cpu$i/cpufreq/scaling_governor")
            }
        }

        val procName = getProcessorNameFromCpuInfo()
        val bogoMips = getBogoMipsFromCpuInfo()

        return CpuInfo(
            processorName = procName,
            architecture = System.getProperty("os.arch") ?: (Build.SUPPORTED_ABIS.firstOrNull() ?: "Unknown"),
            supportedAbis = Build.SUPPORTED_ABIS.toList(),
            is64Bit = Build.SUPPORTED_64_BIT_ABIS.isNotEmpty(),
            coreCount = coreCount,
            governor = governor.ifEmpty { "schedutil / interactive" },
            cores = cores,
            bogoMips = bogoMips
        )
    }

    fun getMemoryInfo(): MemoryInfoData {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager
        val memInfo = ActivityManager.MemoryInfo()
        am?.getMemoryInfo(memInfo)

        val totalRam = memInfo.totalMem
        val availRam = memInfo.availMem
        val usedRam = (totalRam - availRam).coerceAtLeast(0)
        val ramPercent = if (totalRam > 0) (usedRam.toFloat() / totalRam.toFloat()) * 100f else 0f

        val stat = StatFs(Environment.getDataDirectory().path)
        val blockSize = stat.blockSizeLong
        val totalBlocks = stat.blockCountLong
        val availBlocks = stat.availableBlocksLong

        val totalStorage = totalBlocks * blockSize
        val availStorage = availBlocks * blockSize
        val usedStorage = (totalStorage - availStorage).coerceAtLeast(0)
        val storagePercent = if (totalStorage > 0) (usedStorage.toFloat() / totalStorage.toFloat()) * 100f else 0f

        val runtime = Runtime.getRuntime()
        val jvmMax = runtime.maxMemory()
        val jvmTotal = runtime.totalMemory()
        val jvmFree = runtime.freeMemory()

        return MemoryInfoData(
            totalRamBytes = totalRam,
            availableRamBytes = availRam,
            usedRamBytes = usedRam,
            ramUsedPercent = ramPercent,
            isLowMemory = memInfo.lowMemory,
            lowMemoryThresholdBytes = memInfo.threshold,
            totalStorageBytes = totalStorage,
            availableStorageBytes = availStorage,
            usedStorageBytes = usedStorage,
            storageUsedPercent = storagePercent,
            jvmMaxMemoryBytes = jvmMax,
            jvmTotalMemoryBytes = jvmTotal,
            jvmFreeMemoryBytes = jvmFree
        )
    }

    fun getBatteryInfo(): BatteryInfoData {
        val batteryIntent = context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        val level = batteryIntent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: 50
        val scale = batteryIntent?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: 100
        val percentage = if (level >= 0 && scale > 0) ((level.toFloat() / scale.toFloat()) * 100).roundToInt() else 50

        val statusInt = batteryIntent?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
        val isCharging = statusInt == BatteryManager.BATTERY_STATUS_CHARGING || statusInt == BatteryManager.BATTERY_STATUS_FULL
        val status = when (statusInt) {
            BatteryManager.BATTERY_STATUS_CHARGING -> "Charging"
            BatteryManager.BATTERY_STATUS_DISCHARGING -> "Discharging"
            BatteryManager.BATTERY_STATUS_FULL -> "Full"
            BatteryManager.BATTERY_STATUS_NOT_CHARGING -> "Not Charging"
            else -> "Unknown"
        }

        val healthInt = batteryIntent?.getIntExtra(BatteryManager.EXTRA_HEALTH, -1) ?: -1
        val health = when (healthInt) {
            BatteryManager.BATTERY_HEALTH_GOOD -> "Good (Brand Intact)"
            BatteryManager.BATTERY_HEALTH_OVERHEAT -> "Overheat (Abyssal Fire)"
            BatteryManager.BATTERY_HEALTH_DEAD -> "Dead"
            BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE -> "Over Voltage"
            BatteryManager.BATTERY_HEALTH_COLD -> "Cold"
            else -> "Normal"
        }

        val pluggedInt = batteryIntent?.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1) ?: 0
        val plugged = when (pluggedInt) {
            BatteryManager.BATTERY_PLUGGED_AC -> "AC Charger"
            BatteryManager.BATTERY_PLUGGED_USB -> "USB Cable"
            BatteryManager.BATTERY_PLUGGED_WIRELESS -> "Wireless"
            else -> "Battery Power"
        }

        val tempRaw = batteryIntent?.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) ?: 0
        val tempCelsius = tempRaw / 10.0f
        val voltageMv = batteryIntent?.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0) ?: 0
        val technology = batteryIntent?.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY) ?: "Li-ion"

        return BatteryInfoData(
            percentage = percentage,
            status = status,
            health = health,
            plugged = plugged,
            temperatureCelsius = tempCelsius,
            voltageMillivolts = voltageMv,
            technology = technology,
            isCharging = isCharging
        )
    }

    @Suppress("DEPRECATION")
    fun getDisplayInfo(): DisplayInfoData {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val metrics = DisplayMetrics()
        val display = wm.defaultDisplay
        display.getRealMetrics(metrics)

        val width = metrics.widthPixels
        val height = metrics.heightPixels
        val dpi = metrics.densityDpi
        val densityFactor = metrics.density
        val refreshRate = display.refreshRate

        val xdpi = if (metrics.xdpi > 0) metrics.xdpi else dpi.toFloat()
        val ydpi = if (metrics.ydpi > 0) metrics.ydpi else dpi.toFloat()

        val widthInches = width / xdpi
        val heightInches = height / ydpi
        val diagonalInches = sqrt((widthInches * widthInches + heightInches * heightInches).toDouble()).toFloat()

        val densityBucket = when {
            dpi <= DisplayMetrics.DENSITY_LOW -> "ldpi (~120 dpi)"
            dpi <= DisplayMetrics.DENSITY_MEDIUM -> "mdpi (~160 dpi)"
            dpi <= DisplayMetrics.DENSITY_HIGH -> "hdpi (~240 dpi)"
            dpi <= DisplayMetrics.DENSITY_XHIGH -> "xhdpi (~320 dpi)"
            dpi <= DisplayMetrics.DENSITY_XXHIGH -> "xxhdpi (~480 dpi)"
            dpi <= DisplayMetrics.DENSITY_XXXHIGH -> "xxxhdpi (~640 dpi)"
            else -> "Ultra High Density ($dpi dpi)"
        }

        val hdrSupported = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            display.isHdr
        } else false

        val wideColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            display.isWideColorGamut
        } else false

        return DisplayInfoData(
            widthPixels = width,
            heightPixels = height,
            refreshRateHz = refreshRate,
            densityDpi = dpi,
            densityBucket = densityBucket,
            densityFactor = densityFactor,
            physicalSizeInches = (diagonalInches * 10).roundToInt() / 10.0f,
            xdpi = (xdpi * 10).roundToInt() / 10f,
            ydpi = (ydpi * 10).roundToInt() / 10f,
            isHdrSupported = hdrSupported,
            isWideColorGamut = wideColor
        )
    }

    fun getSensorsList(): List<SensorItem> {
        val sm = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val list = sm.getSensorList(Sensor.TYPE_ALL)
        return list.mapIndexed { index, s ->
            SensorItem(
                id = index,
                name = s.name,
                typeName = getSensorTypeName(s.type),
                vendor = s.vendor ?: "Unknown",
                version = s.version,
                powerMa = s.power,
                maxRange = s.maximumRange,
                resolution = s.resolution
            )
        }
    }

    fun observeLiveSensors(): Flow<LiveSensorTelemetry> = callbackFlow {
        val sm = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val accel = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val gyro = sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        val mag = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        val light = sm.getDefaultSensor(Sensor.TYPE_LIGHT)
        val prox = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY)

        var telemetry = LiveSensorTelemetry()
        var gravity: FloatArray? = null
        var geomagnetic: FloatArray? = null

        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                if (event == null) return
                when (event.sensor.type) {
                    Sensor.TYPE_ACCELEROMETER -> {
                        gravity = event.values.clone()
                        telemetry = telemetry.copy(
                            accelX = event.values.getOrNull(0) ?: 0f,
                            accelY = event.values.getOrNull(1) ?: 0f,
                            accelZ = event.values.getOrNull(2) ?: 0f
                        )
                    }
                    Sensor.TYPE_GYROSCOPE -> {
                        telemetry = telemetry.copy(
                            gyroX = event.values.getOrNull(0) ?: 0f,
                            gyroY = event.values.getOrNull(1) ?: 0f,
                            gyroZ = event.values.getOrNull(2) ?: 0f
                        )
                    }
                    Sensor.TYPE_MAGNETIC_FIELD -> {
                        geomagnetic = event.values.clone()
                        telemetry = telemetry.copy(
                            magX = event.values.getOrNull(0) ?: 0f,
                            magY = event.values.getOrNull(1) ?: 0f,
                            magZ = event.values.getOrNull(2) ?: 0f
                        )
                    }
                    Sensor.TYPE_LIGHT -> {
                        telemetry = telemetry.copy(lightLux = event.values.getOrNull(0) ?: 0f)
                    }
                    Sensor.TYPE_PROXIMITY -> {
                        telemetry = telemetry.copy(proximityCm = event.values.getOrNull(0) ?: 0f)
                    }
                }

                if (gravity != null && geomagnetic != null) {
                    val r = FloatArray(9)
                    val i = FloatArray(9)
                    if (SensorManager.getRotationMatrix(r, i, gravity, geomagnetic)) {
                        val orientation = FloatArray(3)
                        SensorManager.getOrientation(r, orientation)
                        var azimuth = Math.toDegrees(orientation[0].toDouble()).toFloat()
                        if (azimuth < 0) azimuth += 360f
                        telemetry = telemetry.copy(azimuthDegrees = azimuth)
                    }
                }

                trySend(telemetry)
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        accel?.let { sm.registerListener(listener, it, SensorManager.SENSOR_DELAY_UI) }
        gyro?.let { sm.registerListener(listener, it, SensorManager.SENSOR_DELAY_UI) }
        mag?.let { sm.registerListener(listener, it, SensorManager.SENSOR_DELAY_UI) }
        light?.let { sm.registerListener(listener, it, SensorManager.SENSOR_DELAY_UI) }
        prox?.let { sm.registerListener(listener, it, SensorManager.SENSOR_DELAY_UI) }

        awaitClose {
            sm.unregisterListener(listener)
        }
    }

    @Suppress("DEPRECATION")
    fun getNetworkInfo(): NetworkInfoData {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        val wm = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as? WifiManager

        var isConnected = false
        var connType = "Disconnected"
        var isMetered = false

        val network = cm?.activeNetwork
        val caps = cm?.getNetworkCapabilities(network)

        if (caps != null) {
            isConnected = caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            isMetered = !caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)
            connType = when {
                caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> "Wi-Fi (Astral Link)"
                caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> "Cellular (Falcon Network)"
                caps.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> "Ethernet"
                caps.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> "VPN Tunnel"
                else -> "Active Connection"
            }
        }

        val (ipv4, ipv6) = getIpAddresses()

        var ssid = "Unknown"
        var linkSpeed = "Unknown"
        var frequency = "Unknown"

        try {
            val wifiInfo = wm?.connectionInfo
            if (wifiInfo != null) {
                ssid = wifiInfo.ssid.replace("\"", "").let { if (it == "<unknown ssid>") "Wi-Fi Connected" else it }
                linkSpeed = "${wifiInfo.linkSpeed} Mbps"
                frequency = "${wifiInfo.frequency} MHz"
            }
        } catch (_: Exception) {}

        return NetworkInfoData(
            isConnected = isConnected,
            connectionType = connType,
            isMetered = isMetered,
            ipv4Address = ipv4.ifEmpty { "127.0.0.1 (Loopback)" },
            ipv6Address = ipv6.ifEmpty { "fe80::1 (Link Local)" },
            wifiSsid = ssid,
            wifiLinkSpeed = linkSpeed,
            wifiFrequency = frequency
        )
    }

    fun getSystemSecurityInfo(): SystemSecurityInfo {
        var isRooted = false
        val rootPaths = arrayOf(
            "/system/app/Superuser.apk",
            "/sbin/su",
            "/system/bin/su",
            "/system/xbin/su",
            "/data/local/xbin/su",
            "/data/local/bin/su",
            "/system/sd/xbin/su",
            "/system/bin/failsafe/su",
            "/data/local/su"
        )
        for (p in rootPaths) {
            if (File(p).exists()) {
                isRooted = true
                break
            }
        }

        val selinux = try {
            val c = Class.forName("android.os.SELinux")
            val m = c.getMethod("isSELinuxEnforced")
            val enforced = m.invoke(null) as Boolean
            if (enforced) "Enforcing (Secured)" else "Permissive"
        } catch (_: Exception) {
            "Enforcing"
        }

        val javaVm = System.getProperty("java.vm.name") + " " + (System.getProperty("java.vm.version") ?: "")

        return SystemSecurityInfo(
            bootloader = Build.BOOTLOADER ?: "Unknown",
            selinuxStatus = selinux,
            javaVm = javaVm,
            playServicesAvailable = true,
            isRooted = isRooted,
            trebleSupport = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
        )
    }

    private fun getIpAddresses(): Pair<String, String> {
        var ipv4 = ""
        var ipv6 = ""
        try {
            val interfaces = NetworkInterface.getNetworkInterfaces()
            while (interfaces.hasMoreElements()) {
                val iface = interfaces.nextElement()
                if (iface.isLoopback || !iface.isUp) continue
                val addrs = iface.inetAddresses
                while (addrs.hasMoreElements()) {
                    val addr = addrs.nextElement()
                    if (addr is Inet4Address && ipv4.isEmpty()) {
                        ipv4 = addr.hostAddress ?: ""
                    } else if (addr is Inet6Address && ipv6.isEmpty() && !addr.isLinkLocalAddress) {
                        ipv6 = addr.hostAddress ?: ""
                    }
                }
            }
        } catch (_: Exception) {}
        return Pair(ipv4, ipv6)
    }

    private fun getKernelVersion(): String {
        return try {
            val reader = BufferedReader(FileReader("/proc/version"))
            val line = reader.readLine()
            reader.close()
            line ?: System.getProperty("os.version") ?: "Linux Kernel"
        } catch (_: Exception) {
            System.getProperty("os.version") ?: "Linux"
        }
    }

    private fun getProcessorNameFromCpuInfo(): String {
        var name = Build.HARDWARE
        try {
            val reader = BufferedReader(FileReader("/proc/cpuinfo"))
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                if (line?.startsWith("Hardware") == true || line?.startsWith("model name") == true) {
                    val parts = line!!.split(":")
                    if (parts.size > 1) {
                        name = parts[1].trim()
                        break
                    }
                }
            }
            reader.close()
        } catch (_: Exception) {}
        return if (name.isNullOrEmpty() || name == "unknown") Build.BOARD else name
    }

    private fun getBogoMipsFromCpuInfo(): String {
        try {
            val reader = BufferedReader(FileReader("/proc/cpuinfo"))
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                if (line?.startsWith("BogoMIPS") == true || line?.startsWith("bogomips") == true) {
                    val parts = line!!.split(":")
                    if (parts.size > 1) {
                        reader.close()
                        return parts[1].trim()
                    }
                }
            }
            reader.close()
        } catch (_: Exception) {}
        return "N/A"
    }

    private fun readFrequency(path: String): Long {
        return try {
            val file = File(path)
            if (file.exists()) {
                val line = file.readText().trim()
                line.toLongOrNull() ?: 0L
            } else 0L
        } catch (_: Exception) {
            0L
        }
    }

    private fun readFirstLine(path: String): String {
        return try {
            val file = File(path)
            if (file.exists()) file.readLines().firstOrNull()?.trim() ?: "" else ""
        } catch (_: Exception) {
            ""
        }
    }

    private fun Long.ifZero(fallback: () -> Long): Long = if (this == 0L) fallback() else this

    private fun getSensorTypeName(type: Int): String {
        return when (type) {
            Sensor.TYPE_ACCELEROMETER -> "Accelerometer"
            Sensor.TYPE_GYROSCOPE -> "Gyroscope"
            Sensor.TYPE_MAGNETIC_FIELD -> "Magnetic Field"
            Sensor.TYPE_LIGHT -> "Ambient Light"
            Sensor.TYPE_PROXIMITY -> "Proximity"
            Sensor.TYPE_PRESSURE -> "Barometer (Pressure)"
            Sensor.TYPE_GRAVITY -> "Gravity"
            Sensor.TYPE_LINEAR_ACCELERATION -> "Linear Acceleration"
            Sensor.TYPE_ROTATION_VECTOR -> "Rotation Vector"
            Sensor.TYPE_STEP_COUNTER -> "Step Counter"
            Sensor.TYPE_STEP_DETECTOR -> "Step Detector"
            Sensor.TYPE_HEART_RATE -> "Heart Rate"
            Sensor.TYPE_AMBIENT_TEMPERATURE -> "Ambient Temperature"
            Sensor.TYPE_RELATIVE_HUMIDITY -> "Humidity"
            else -> "Hardware Sensor ($type)"
        }
    }
}
