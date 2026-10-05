# ⚔️ Device Info NextGen (Berserk Edition)

[![Platform: Android](https://img.shields.io/badge/Platform-Android-3DDC84.svg?style=flat&logo=android)](https://www.android.com)
[![Language: Kotlin](https://img.shields.io/badge/Language-Kotlin%202.0-7F52FF.svg?style=flat&logo=kotlin)](https://kotlinlang.org)
[![UI: Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose%20M3-4285F4.svg?style=flat&logo=jetpackcompose)](https://developer.android.com/jetpack/compose)
[![License: Open Source Attribution](https://img.shields.io/badge/License-Attribution%20Open%20Source-crimson.svg)](./LICENSE)
[![GitHub Stars](https://img.shields.io/github/stars/dhrubonai/Device-info-NextGen?style=social)](https://github.com/dhrubonai/Device-info-NextGen)

> *"In this world, is the destiny of mankind controlled by some transcendental entity or law? Like the hand of God hovering above?"*

**Device Info NextGen** is a high-performance Android hardware and diagnostics application enveloped in a dark fantasy **Berserk anime aesthetic**. Featuring **liquid glassmorphism surfaces**, fluid iOS-style spring animations, glowing crimson Brand of Sacrifice runes, and dual bespoke typography (*Cinzel* paired with tactical cybernetic *Rajdhani*).

---

## 👨‍💻 Creator & Developer

<div align="center">
  <img src="https://i.ibb.co/hRSzrw2N/file-69.jpg" width="160" height="213" style="border-radius: 50%; border: 3px solid #FF1744;" alt="Mohiuddin Abdul Kadir Dhrubo" />
  <h3>Mohiuddin Abdul Kadir Dhrubo</h3>
  <p><b>Creator of Device Info NextGen</b></p>
</div>

> *"Device Info NextGen is an app that I have created for myself actually in my free time. It's a hobby project but you guys can use it. And if you guys love it then share it up and star the repo, and you guys can modify it and use it up!"*
> 
> — **Mohiuddin Abdul Kadir Dhrubo**

### 🌐 Connect With Me

- 📧 **Email**: [dhrubobear@gmail.com](mailto:dhrubobear@gmail.com)
- 🐙 **GitHub**: [@dhrubonai](https://github.com/dhrubonai)
- 💬 **Telegram Channel**: [@dhrubo_moira_geche](https://t.me/dhrubo_moira_geche)
- 📸 **Instagram**: [@dhrubo_morse](https://www.instagram.com/dhrubo_morse?igsh=aHNsazZyOTY4dWN2)
- 👤 **Facebook**: [Mohiuddin Abdul Kadir Dhrubo](https://www.facebook.com/share/1LLhAmRUp2/)

---

## 🛡️ Privacy Policy — Zero Data Collection

**We don't track anything or store anything. We don't take any data.**

- **100% Offline & Local**: All hardware telemetry, battery rates, memory stats, and sensor readouts are read directly in-memory via Android SDK APIs.
- **No Analytics & No Trackers**: No third-party analytics libraries, advertising SDKs, tracking IDs, or remote database synchronizations.
- **Permission Transparency**: Hardware permissions (e.g. camera flash for torch test, vibrator for haptic test) are used solely for interactive hardware diagnostics on device.

---

## 🗡️ Features & Hardware Telemetry

### 1. 🩸 Eclipse Core (Dashboard)
- **Authentic Berserk Artwork**: Custom manga splash screen with Guts' grinning face, "ベルセルク" title, and custom icon branding.
- **Liquid Glass Dual Gauges**: Real-time RAM utilization and flash storage allocation arc meters with smooth spring animations.
- **Brand Flame Battery Monitor**: Instant battery percentage, charge counter in mAh, charging status, and live current.
- **Dragon Slayer CPU Monitor**: Active core count, architecture, and live processor state.

### 2. 🔥 Brand Flame (Deep Battery Telemetry)
- **Battery Charge Counter**: Exact remaining charge counter in **mAh** and **µAh** (`BATTERY_PROPERTY_CHARGE_COUNTER`).
- **Instantaneous Current (`currentNowMa`)**: Live positive charging current or negative discharging draw in **mA**.
- **Average Current Draw**: Continuous average current consumption in mA.
- **Design & Estimated Capacity**: Design battery capacity (`5000 mAh`).
- **Energy Counter**: Remaining energy in **mWh** (`BATTERY_PROPERTY_ENERGY_COUNTER`).
- **Detailed Charge Status**: Live power rate and power supply detection (AC Fast Charger, USB Data Cable, Wireless Qi Dock).
- **Electrical & Thermal Health**: Terminal voltage in millivolts (mV) and volts (V), temperature in both Celsius (°C) and Fahrenheit (°F), power-save mode state, and battery cycle count.

### 3. ⚡ Dragon Slayer Engine (CPU & Architecture)
- Real-time CPU frequencies per core (`/sys/devices/system/cpu/cpu*/cpufreq/scaling_cur_freq`).
- Active CPU governor detection (schedutil, performance, interactive).
- Full ABI instruction set support (`arm64-v8a`, `armeabi-v7a`, `x86_64`).
- BogoMIPS hardware benchmarking and system load averages.

### 4. 🛡️ Abyssal Vault (Memory & Storage)
- **RAM Pool**: Total, used, available memory, and low-memory system threshold warnings via `ActivityManager`.
- **Internal Storage**: Partition blocks, total storage capacity, used bytes, and free storage calculation via `StatFs`.
- **Swap & ZRAM**: Real-time swap memory telemetry from `/proc/meminfo`.
- **JVM & Native Heap**: Max heap, allocated heap, free heap, and native memory.

### 5. 👁️ Behelit Eye (Display & Graphics)
- Screen resolution in physical pixels and aspect ratio.
- Refresh rate (60Hz, 90Hz, 120Hz, 144Hz) with supported display modes.
- Pixel density (DPI, density factor, bucket: `xxhdpi`, `xxxhdpi`).
- Physical diagonal screen size calculation in inches.
- High Dynamic Range (HDR10, HLG, Dolby Vision) & Wide Color Gamut (DCI-P3).

### 6. 🌌 Astral Realm (Hardware Sensors)
- **Interactive 3D Tilt Level**: Live bubble level indicator animated with accelerometer and gravity physics.
- **Azimuth Compass**: Real-time magnetic azimuth angle and direction finder.
- Real-time 3-axis telemetry: Accelerometer (m/s²), Gyroscope (rad/s), and Magnetometer (µT).
- Complete hardware sensor inventory with vendor, power consumption, resolution, and range.

### 7. 🦅 Hawk Comms (Network & Telemetry)
- Connection status and network transport (Wi-Fi, Cellular 5G/LTE, Ethernet, VPN).
- Local IPv4 and IPv6 network interface address resolution.
- Wi-Fi SSID, channel frequency, and physical link speed in Mbps.

### 8. 📜 Brand of Fate (OS & Security)
- Android version release and dessert codenames (Android 16 Baklava, Android 15 Vanilla Ice Cream, Android 14, etc.).
- Baseband radio modem version, API Level, Build ID, Bootloader, and Kernel version.
- SELinux security enforcement status, root access check, and Project Treble support.

### 9. ⚔️ Trial of the Berserker (Hardware Diagnostics)
- **Display Dead-Pixel Test**: Full-screen cycling RGB, pure white, pure black, and blood crimson pixel inspector.
- **Multi-Touch Digitizer Test**: Interactive canvas tracking up to 10 simultaneous touch points with live coordinates.
- **Haptic Motor Trial**: Single pulse, heavy click, and burst vibration patterns.
- **Flashlight / Torch Test**: Camera flash LED toggle.
- **Loudspeaker Test**: Direct 440 Hz sinusoidal audio tone generation via `AudioTrack`.

### 10. 📖 Sacred Codex & Tome
- Full App & Developer Info with one-tap social links.
- Formatted ASCII/Markdown diagnostic report generator with one-tap clipboard copy and native share sheet.

---

## 🎨 Design Philosophy & Animations

- **Liquid Glassmorphism**: Multilayered frosted dark surfaces (`Color(0x3E23141F)`) with animated specular light gleams gliding across glowing crimson borders (`Color(0x55FF1744)`).
- **Fluid iOS-Style Animations**: Spring-based bounce on touch press (`DampingRatioMediumBouncy`) and spring-interpolated progress arcs.
- **Bespoke Typography**:
  - `Cinzel`: Classical gothic and epic fantasy headings.
  - `Rajdhani`: Tactical cyber-HUD telemetry, numbers, and stats.

---

## ⚖️ Open Source License & Attribution Requirement

Distributed under the **Open Source Attribution License**.

You are completely free to use, copy, modify, and build upon this application. **However, you MUST give credit to Mohiuddin Abdul Kadir Dhrubo and link back to this original repository:**

```
Original App: Device Info NextGen by Mohiuddin Abdul Kadir Dhrubo (@dhrubonai)
Repository: https://github.com/dhrubonai/Device-info-NextGen
```

See [LICENSE](./LICENSE) for the full legal text.
