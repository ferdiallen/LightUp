package com.example.lightup.module

import android.app.Application
import android.content.Context
import android.hardware.SensorManager
import android.hardware.camera2.CameraManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun provideSensorService(app: Application): SensorManager {
        return app.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    @Provides
    @Singleton
    fun providesFlashlight(app: Application): CameraManager {
        return app.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    }
}