package com.example.lightup.util

import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import javax.inject.Inject

class GetCamera @Inject constructor(
    private val camera: CameraManager
) {
    fun openCamera(
        cameraState: Boolean,
        onStateChange: (Boolean) -> Unit,
        onErrorOccured: ((String?) -> Unit)? = null
    ) {
        val service = camera
        val cameraArray = service.cameraIdList[0]
        val characteristic = service.getCameraCharacteristics(cameraArray)
            .get(CameraCharacteristics.FLASH_INFO_AVAILABLE) == true
        if (!cameraState) {
            try {
                if(characteristic){
                    service.setTorchMode(cameraArray, true)
                    onStateChange(true)
                }
                onErrorOccured?.invoke("Unavailable")
            } catch (e: Exception) {
                e.printStackTrace()
                onErrorOccured?.invoke(e.localizedMessage)
            }
        } else {
            try {
                service.setTorchMode(cameraArray, false)
                onStateChange(false)
            } catch (e: Exception) {
                e.printStackTrace()
                onErrorOccured?.invoke(e.localizedMessage)
            }
        }
    }
}
