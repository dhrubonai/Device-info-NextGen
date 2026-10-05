# ⚔️ Device Info NextGen (Berserk Edition)

[![Android](https://img.shields.io/badge/Platform-Android-3DDC84.svg?style=flat&logo=android)](https://www.android.com)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin%202.0-7F52FF.svg?style=flat&logo=kotlin)](https://kotlinlang.org)
[![Compose](https://img.shields.io/badge/UI-Jetpack%20Compose%20M3-4285F4.svg?style=flat&logo=jetpackcompose)](https://developer.android.com/jetpack/compose)
[![License: MIT](https://img.shields.io/badge/License-MIT-crimson.svg)](https://opensource.org/licenses/MIT)

> *"In this world, is the destiny of mankind controlled by some transcendental entity or law? Like the hand of God hovering above?"*

**Device Info NextGen** is a high-performance Android hardware and diagnostics application enveloped in a dark fantasy **Berserk anime aesthetic**. Featuring **liquid glassmorphism surfaces**, glowing crimson Brand of Sacrifice runes, and dual typography (majestic gothic *Cinzel* paired with tactical cybernetic *Rajdhani*).

---

## 🗡️ Features Overview

### 1. 🩸 Eclipse Core (Dashboard)
- **Live Brand of Sacrifice Rune**: Custom procedural vector canvas with breathing animated pulse.
- **Liquid Glass Dual Gauges**: Real-time RAM utilization and flash storage allocation arc meters.
- **Brand Flame Quick Peek**: Real-time battery status, health, charging source, and temperature.
- **Dragon Slayer CPU Monitor**: Active core count, architecture, and live processor state.
- **Hardware Badges**: Quick telemetry for Uptime, Android security patch, and screen refresh rate.

### 2. ⚡ Dragon Slayer Engine (CPU & Architecture)
- Real-time CPU frequencies per core (`/sys/devices/system/cpu/cpu*/cpufreq/scaling_cur_freq`).
- Active CPU governor detection (schedutil, performance, interactive).
- Full ABI instruction set support (`arm64-v8a`, `armeabi-v7a`, `x86_64`).
- BogoMIPS hardware benchmarking metrics.

### 3. 🛡️ Abyssal Vault (Memory & Storage)
- **RAM Pool**: Total, used, available memory, and low-memory system threshold warnings via `ActivityManager`.
- **Internal Storage**: Partition blocks, total storage capacity, used bytes, and free storage calculation via `StatFs`.
- **JVM / ART Runtime Heap**: Max heap, allocated heap, and free JVM heap.

### 4. 🔥 Brand Flame (Battery & Power)
- Circular flame meter with charging status.
- Battery health condition (Good, Overheat, Dead, Over Voltage).
- Power source identification (AC wall adapter, USB cable, Wireless dock).
- Precision voltage in millivolts (mV) and dual temperature readouts in Celsius and Fahrenheit.
- Cell chemistry and technology (Li-ion, Li-poly).

### 5. 👁️ Behelit Eye (Display & Graphics)
- Screen resolution in physical pixels.
- Panel refresh rate (60Hz, 90Hz, 120Hz, 144Hz).
- Pixel density (DPI, density factor, bucket: `xxhdpi`, `xxxhdpi`).
- Calculated physical screen diagonal size in inches.
- High Dynamic Range (HDR10, HLG, Dolby Vision) & Wide Color Gamut (DCI-P3) support.

### 6. 🌌 Astral Realm (Hardware Sensors)
- **Interactive 3D Tilt Level**: Live bubble level indicator animated with accelerometer and gravity physics.
- **Azimuth Compass**: Real-time magnetic azimuth angle and direction finder.
- Real-time 3-axis telemetry: Accelerometer (m/s²), Gyroscope (rad/s), and Magnetometer (µT).
- Complete hardware sensor inventory with vendor, power consumption, resolution, and maximum range.

### 7. 🦅 Hawk Comms (Network & Telemetry)
- Connection status and network transport (Wi-Fi, Cellular, Ethernet, VPN).
- Local IPv4 and IPv6 network interface address resolution.
- Wi-Fi SSID, channel frequency, and physical link speed in Mbps.
- Metered connection detection.

### 8. 📜 Brand of Fate (OS & Security)
- Android version release and dessert codename (Android 16 Baklava, Android 15 Vanilla Ice Cream, Android 14 Upside Down Cake, etc.).
- API Level, Build ID, Bootloader, and Kernel version from `/proc/version`.
- SELinux security enforcement status.
- Root access detection and Project Treble architecture support.
- Java VM ART runtime version.

### 9. ⚔️ Trial of the Berserker (Hardware Diagnostics)
- **Display Dead-Pixel Test**: Full-screen cycling RGB, pure white, pure black, and blood crimson pixel inspector.
- **Multi-Touch Digitizer Test**: Interactive canvas tracking up to 10 simultaneous touch points with live coordinates and touch point counter.
- **Haptic Motor Trial**: Single pulse, heavy click, and rapid burst vibration patterns.
- **Flashlight / Torch Test**: Camera flash LED toggle.
- **Loudspeaker Test**: Direct 440 Hz sinusoidal audio tone generation via `AudioTrack`.

### 10. 📖 Sacrifice Tome (Export Diagnostics)
- Complete formatted ASCII/Markdown diagnostic report.
- One-tap clipboard copy.
- Instant system share sheet export.

---

## 🎨 Design Philosophy & UI Aesthetics

- **Liquid Glassmorphism**: Multilayered frosted dark surfaces (`Color(0x381E1219)`) with specular top gleams and glowing crimson borders (`Color(0x55FF1744)`).
- **Berserk Color Palette**: Obsidian black (`#09070A`), Blood Red (`#FF1744`), Crimson Glow (`#FF5252`), Solar Eclipse (`#FF3D00`), and Dragon Slayer cold steel (`#CFD8DC`).
- **Custom Adaptive Icons**: Berserk Brand of Sacrifice fused with high-tech cybernetic traces and obsidian-to-crimson halo background.
- **Bespoke Typography**:
  - `Cinzel`: Classical gothic and epic fantasy headings.
  - `Rajdhani`: Tactical cyber-HUD telemetry, numbers, and stats.

---

## 🛠️ Tech Stack & Architecture

- **Language**: Kotlin 2.0
- **UI Framework**: Jetpack Compose with Material 3
- **Architecture**: Clean Architecture / MVVM (`DeviceViewModel`, `DeviceRepository`, `DiagnosticManager`)
- **Reactive Streams**: Kotlin Coroutines & `StateFlow`
- **Low-level Hardware APIs**: Android `SensorManager`, `BatteryManager`, `ActivityManager`, `StatFs`, `ConnectivityManager`, `CameraManager`, `AudioTrack`, `VibratorManager`

---

## 🚀 Building & Running

Clone the repository and open in Android Studio:

```bash
git clone https://github.com/dhrubonai/Device-info-NextGen.git
cd Device-info-NextGen
./gradlew assembleDebug
```

---

## ⚖️ License

Distributed under the MIT License. See `LICENSE` for more information.
