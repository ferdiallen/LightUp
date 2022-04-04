package com.example.lightup.ui

import android.hardware.SensorManager
import android.hardware.camera2.CameraManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    provideSensorManager: SensorManager,
    provideCamera: CameraManager
) : ViewModel() {
    val provideSensorService = provideSensorManager
    val provideCameraForFlashlight = provideCamera
    var lightStatus by mutableStateOf(false)
        private set
    
    fun changeStateLight(state:Boolean){
        lightStatus = state
    }
    var flipDegree by mutableStateOf(0)
        private set

    fun setFlipValue(value: Float) {
        flipDegree = -value.times(3F).toInt()
    }

}