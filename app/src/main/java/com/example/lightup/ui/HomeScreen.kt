package com.example.lightup.ui

import android.Manifest
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lightup.R
import com.example.lightup.constant.ColorDefiner
import com.example.lightup.ui.theme.LightUpTheme
import com.example.lightup.ui.theme.lightBlack
import com.example.lightup.util.GetCamera
import com.example.lightup.util.isPermanentDeclined
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.launch

var listeners: SensorEventListener? = null
    private set
var systemSensor: SensorManager? = null

@ExperimentalPermissionsApi
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Mains(vm: HomeScreenViewModel = viewModel()) {
    val scafstate = rememberScaffoldState()
    val permission = rememberPermissionState(permission = Manifest.permission.CAMERA)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    SideEffect {
        systemSensor = vm.provideSensorService
    }
    LaunchedEffect(key1 = listeners) {
        listeners = object : SensorEventListener {
            override fun onSensorChanged(sens: SensorEvent?) {
                if (sens?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
                    sens.values.let { out ->
                        vm.setFlipValue(out[0])
                    }
                }
            }

            override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
                return
            }
        }
    }

    LightUpTheme {
        Scaffold(Modifier.fillMaxSize(), scafstate) {
            LaunchedEffect(key1 = permission.status) {
                when {
                    permission.status.isGranted -> {
                        scope.launch {
                            scafstate.snackbarHostState.showSnackbar(
                                context.getString(R.string.permission_gr),
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                    permission.isPermanentDeclined() -> {
                        scope.launch {
                            scafstate.snackbarHostState.showSnackbar(
                                context.getString(R.string.permission_dn),
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                }
            }
            Column(Modifier.fillMaxSize()) {
                Header(permission)
                MainDisplay(context, systemSensor, vm)
                LifecyleObservers(permission = permission, systemSensor)
            }
        }
    }
}

@ExperimentalPermissionsApi
@Composable
fun Header(permission: PermissionState) {
    val statusLight by remember {
        mutableStateOf("")
    }
    Row(
        Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(ColorDefiner()),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.fl),
            contentDescription = "Header Icon",
            modifier = Modifier
                .padding(start = 12.dp)
                .clip(CircleShape)
                .size(50.dp)
                .clickable {
                    if (!permission.status.isGranted) {
                        permission.launchPermissionRequest()
                    }
                }
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = "${stringResource(R.string.header_status)}: $statusLight",
            style = MaterialTheme.typography.body2
        )
    }

}

private var cameraFlashlight: GetCamera? = null

@Composable
private fun MainDisplay(context: Context, systemSensor: SensorManager?, vm: HomeScreenViewModel) {
    var errorMsg by rememberSaveable {
        mutableStateOf("")
    }
    SideEffect {
        cameraFlashlight = GetCamera(vm.provideCameraForFlashlight)
    }
    if (errorMsg.isNotEmpty()) {
        ShouldShowAlertCameraUnavailable(textState = {
            errorMsg = it
        })
    }
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                cameraFlashlight?.openCamera(vm.lightStatus, onStateChange = { out ->
                    vm.changeStateLight(out)
                }, onErrorOccured = {
                    errorMsg = it.toString()
                })
            },
            modifier = Modifier.size(300.dp, 50.dp),
            elevation = ButtonDefaults.elevation(14.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (isSystemInDarkTheme()) lightBlack
                else Color.Blue
            )
        ) {
            Text(
                text = "Turn ${
                    if (vm.lightStatus) context.getString(R.string.OnState) else context.getString(
                        R.string.OffState
                    )
                } Flashlight", color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "Total Sensor Tersedia : ${systemSensor?.getSensorList(Sensor.TYPE_ALL)?.size} ")
        Text(text = "Rotasi : ${vm.flipDegree}")
    }

}

@ExperimentalPermissionsApi
@Composable
private fun LifecyleObservers(permission: PermissionState, systemSensor: SensorManager?) {
    val lifecycle = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycle, effect = {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    permission.launchPermissionRequest()
                }
                Lifecycle.Event.ON_STOP -> {
                    systemSensor?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER).also { out ->
                        systemSensor?.unregisterListener(listeners)
                    }
                }
                Lifecycle.Event.ON_RESUME -> {
                    systemSensor?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER).also { out ->
                        systemSensor?.registerListener(
                            listeners,
                            out,
                            SensorManager.SENSOR_DELAY_FASTEST,
                            SensorManager.SENSOR_DELAY_FASTEST
                        )
                    }
                }
            }
        }
        lifecycle.lifecycle.addObserver(observer)
        onDispose {
            lifecycle.lifecycle.removeObserver(observer)
        }
    })
}

@Composable
fun ShouldShowAlertCameraUnavailable(textState: (String) -> Unit) {
    AlertDialog(
        onDismissRequest = { textState("") },
        title = { Text(text = "LED is not recognized") },
        text = {
            Text(
                text = stringResource(R.string.errorledturningon)
            )
        }, confirmButton = {
            Button(onClick = {

            }) {
                Text(text = "Report Miscellaneous", style = MaterialTheme.typography.body1)
            }
        }
    )
}