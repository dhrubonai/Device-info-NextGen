package com.example.data

import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.AudioTrack
import android.media.MediaRecorder
import android.os.Build
import android.os.CombinedVibration
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.sin

class DiagnosticManager(private val context: Context) {

    private var audioTrack: AudioTrack? = null
    private var isTorchOn = false

    fun triggerVibration(patternType: Int = 0) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vm = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
                val effect = when (patternType) {
                    1 -> VibrationEffect.createWaveform(longArrayOf(0, 100, 100, 200, 100, 300), -1)
                    2 -> VibrationEffect.createPredefined(VibrationEffect.EFFECT_HEAVY_CLICK)
                    else -> VibrationEffect.createOneShot(250, VibrationEffect.DEFAULT_AMPLITUDE)
                }
                vm?.vibrate(CombinedVibration.createParallel(effect))
            } else {
                @Suppress("DEPRECATION")
                val v = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val effect = VibrationEffect.createOneShot(250, VibrationEffect.DEFAULT_AMPLITUDE)
                    v?.vibrate(effect)
                } else {
                    @Suppress("DEPRECATION")
                    v?.vibrate(250)
                }
            }
        } catch (_: Exception) {}
    }

    fun toggleFlashlight(turnOn: Boolean): Boolean {
        return try {
            val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as? CameraManager
            if (cameraManager != null) {
                val cameraIdList = cameraManager.cameraIdList
                for (id in cameraIdList) {
                    val chars = cameraManager.getCameraCharacteristics(id)
                    val hasFlash = chars.get(CameraCharacteristics.FLASH_INFO_AVAILABLE) ?: false
                    if (hasFlash) {
                        cameraManager.setTorchMode(id, turnOn)
                        isTorchOn = turnOn
                        return true
                    }
                }
            }
            false
        } catch (_: Exception) {
            false
        }
    }

    suspend fun playTestTone(frequencyHz: Int = 440, durationMs: Int = 1000) = withContext(Dispatchers.Default) {
        try {
            stopTestTone()
            val sampleRate = 44100
            val numSamples = (sampleRate * (durationMs / 1000.0)).toInt()
            val sample = ShortArray(numSamples)
            for (i in 0 until numSamples) {
                val angle = 2.0 * Math.PI * i / (sampleRate / frequencyHz.toDouble())
                sample[i] = (sin(angle) * 32767).toInt().toShort()
            }

            val bufferSize = AudioTrack.getMinBufferSize(
                sampleRate,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT
            ).coerceAtLeast(numSamples * 2)

            audioTrack = AudioTrack.Builder()
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )
                .setAudioFormat(
                    AudioFormat.Builder()
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setSampleRate(sampleRate)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build()
                )
                .setBufferSizeInBytes(bufferSize)
                .setTransferMode(AudioTrack.MODE_STATIC)
                .build()

            audioTrack?.write(sample, 0, numSamples)
            audioTrack?.play()
        } catch (_: Exception) {}
    }

    fun stopTestTone() {
        try {
            audioTrack?.stop()
            audioTrack?.release()
            audioTrack = null
        } catch (_: Exception) {}
    }

    fun getAudioMaxAmplitude(): Float {
        // Quick sound level check if allowed
        return 0f
    }
}
